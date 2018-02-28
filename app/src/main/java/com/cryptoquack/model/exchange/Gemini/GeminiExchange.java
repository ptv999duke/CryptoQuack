package com.cryptoquack.model.exchange.Gemini;

import com.cryptoquack.exceptions.ApiLimitException;
import com.cryptoquack.exceptions.UnavailableActionException;
import com.cryptoquack.exceptions.UnavailableMarketException;
import com.cryptoquack.exceptions.UnavailableOrderTypeException;
import com.cryptoquack.exceptions.UnknownNetworkException;
import com.cryptoquack.model.exchange.Gemini.DTOs.GeminiOrderStatus;
import com.cryptoquack.model.exchange.Gemini.DTOs.GeminiOrderStatusRequest;
import com.cryptoquack.model.logger.ILogger;
import com.cryptoquack.model.credentials.AccessKeyCredentials;
import com.cryptoquack.model.currency.ExchangeMarket;
import com.cryptoquack.model.currency.MonetaryAmount;
import com.cryptoquack.model.exchange.BaseExchange;
import com.cryptoquack.model.exchange.ExchangeAction;
import com.cryptoquack.model.exchange.Exchanges;
import com.cryptoquack.model.exchange.Gemini.DTOs.GeminiNewOrderRequest;
import com.cryptoquack.model.exchange.Gemini.DTOs.GeminiOrder;
import com.cryptoquack.model.exchange.Gemini.DTOs.GeminiTicker;
import com.cryptoquack.model.order.Order;
import com.cryptoquack.model.order.OrderStatus;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Date;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import okhttp3.OkHttpClient;
import retrofit2.HttpException;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Duke on 1/20/2018.
 */

public class GeminiExchange extends BaseExchange {

    private final ArrayList<ExchangeMarket> availableMarkets = new ArrayList<>();
    private final ArrayList<ExchangeAction.ExchangeActions> availableActions = new ArrayList<>();
    private final ILogger logger;
    private boolean useSandbox;
    private GeminiApiClientV1 apiClient;
    private String baseUrl;

    public GeminiExchange(ILogger logger) {
        this(true, logger);
    }

    public GeminiExchange(boolean useSandbox, ILogger logger) {
        super(Exchanges.Exchange.GEMINI);
        this.useSandbox = useSandbox;
        if (this.useSandbox) {
            this.exchangeType = Exchanges.Exchange.GEMINI_SANDBOX;
        }

        this.baseUrl = this.useSandbox ? GeminiHelper.SANDBOX_REST_API_URL :
                GeminiHelper.REST_API_URL;

        this.availableMarkets.add(ExchangeMarket.BTCUSD);
        this.availableMarkets.add(ExchangeMarket.ETHUSD);
        this.availableMarkets.add(ExchangeMarket.ETHBTC);

        this.availableActions.add(ExchangeAction.ExchangeActions.BUY);
        this.availableActions.add(ExchangeAction.ExchangeActions.SELL);
        this.logger = logger;
        this.initializeApiClient();
    }

    private void initializeApiClient() {
        Retrofit.Builder apiClientBuilder = new Retrofit.Builder();
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.interceptors().add(new GeminiApiRequestInterceptor(this.credentials));
        apiClientBuilder.client(httpClientBuilder.build());
        apiClientBuilder.baseUrl(this.baseUrl);
        apiClientBuilder.addConverterFactory(GsonConverterFactory.create(BaseExchange.GSON));
        apiClientBuilder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        Retrofit r = apiClientBuilder.build();
        this.apiClient = r.create(GeminiApiClientV1.class);
    }

    @Override
    public void setCredentials(AccessKeyCredentials credentials) {
        super.setCredentials(credentials);
        this.initializeApiClient();
    }

    @Override
    public ArrayList<ExchangeMarket> getAvailableMarkets() {
        return (ArrayList<ExchangeMarket>) this.availableMarkets.clone();
    }

    @Override
    public Single<Double> getCurrentPriceAsync(ExchangeMarket market) throws UnavailableMarketException {
        String symbol;
        try {
            symbol = GeminiHelper.convertMarketToSymbol(market);
        } catch (InvalidParameterException e){
            throw new UnavailableMarketException(market, e.getMessage(), e);
        }

        this.logger.v("Getting current price for %s", market);
        Single<Double> single = this.apiClient.getPubTicker(symbol)
                .flatMap(new Function<GeminiTicker, SingleSource<Double>>() {
                    @Override
                    public SingleSource<Double> apply(GeminiTicker ticker) {
                        Double price = ticker.getLastPrice();
                        return Single.just((price));
                    }
                });
        single = single.onErrorResumeNext(this.<Double>getGenericErrorHandleResumeSingle());
        return single;
    }

    @Override
    public Double getCurrentPrice(ExchangeMarket market) throws UnavailableMarketException {
        Single<Double> single = this.getCurrentPriceAsync(market);
        Double price = single.blockingGet();
        return price;
    }

    @Override
    public MonetaryAmount calculateFee(ExchangeAction.ExchangeActions action, MonetaryAmount amount,
                                          ExchangeMarket market) {
        return new MonetaryAmount(amount.getAmount() * 0.0025, market.getDestinationCurrency());
    }

    @Override
    public ArrayList<ExchangeAction.ExchangeActions> getAvailableActions(
        ExchangeMarket market) {
        return (ArrayList<ExchangeAction.ExchangeActions>) this.availableActions.clone();
    }

    @Override
    public Order makeOrder(Order orderRequest) {
        Single<Order> single = this.makeOrderAsync(orderRequest);
        return single.blockingGet();
    }

    @Override
    public Single<Order> makeOrderAsync(Order orderRequest) {
        if (orderRequest == null) {
            throw new NullPointerException("Order request must not be null");
        }

        this.validateCredentialsLoaded();
        if (!orderRequest.getOrderType().equals(Order.OrderType.LIMIT)) {
            throw new UnavailableOrderTypeException(orderRequest.getOrderType());
        }

        if (!this.availableActions.contains(orderRequest.getAction())) {
            throw new UnavailableActionException(orderRequest.getAction());
        }

        if (!this.availableMarkets.contains(orderRequest.getMarket())) {
            throw new UnavailableMarketException(orderRequest.getMarket());
        }

        String actionSymbol = GeminiHelper.convertActionToActionSymbol(orderRequest.getAction());
        String marketSymbol = GeminiHelper.convertMarketToSymbol(orderRequest.getMarket());

        GeminiNewOrderRequest geminiOrderRequest = new GeminiNewOrderRequest(marketSymbol,
                orderRequest.getTotalAmount().getAmount(), orderRequest.getPrice(), actionSymbol);
        Single<GeminiOrder> newOrderCall = this.apiClient.newOrder(geminiOrderRequest);
        this.logger.i("Making order to %s on Gemini exchange", orderRequest.getAction());
        Single<Order> single = newOrderCall.flatMap(new Function<GeminiOrder, SingleSource<Order>>() {

            @Override
            public SingleSource<Order> apply(GeminiOrder geminiOrder) {
                Order order = geminiOrder.convertToOrder();
                order.setExchange(exchangeType);
                return Single.just(order);
            }
        });

        single = single.onErrorResumeNext(this.<Order>getGenericErrorHandleResumeSingle());
        return single;
    }

    @Override
    public OrderStatus getOrderStatus(Order order) {
        Single<OrderStatus> single = this.getOrderStatusAsync(order);
        return single.blockingGet();
    }

    @Override
    public Single<OrderStatus> getOrderStatusAsync(Order order) {
        Single<GeminiOrderStatus> orderStatusCall = this.apiClient.getOrderStatus(
                new GeminiOrderStatusRequest(order.getOrderId()));
        Single<OrderStatus> single = orderStatusCall.flatMap(
                new Function<GeminiOrderStatus, SingleSource<OrderStatus>>() {

                    @Override
                    public SingleSource<OrderStatus> apply(GeminiOrderStatus geminiOrderStatus) {
                        OrderStatus orderStatus = geminiOrderStatus.convertToOrderStatus();
                        return Single.just(orderStatus);
                    }
                });

        return single;
    }

    private <T> Function<Throwable, SingleSource<T>> getGenericErrorHandleResumeSingle() {
        return new Function<Throwable, SingleSource<T>>() {
            @Override
            public SingleSource<T> apply(Throwable t) {
                if (t instanceof retrofit2.adapter.rxjava2.HttpException) {
                    retrofit2.adapter.rxjava2.HttpException e =
                            (retrofit2.adapter.rxjava2.HttpException) t;
                    if (e.code() == 429) {
                        throw new ApiLimitException(exchangeType,
                                "Api limit hit when making order");
                    }
                } else if (t instanceof HttpException) {
                    HttpException e = (HttpException) t;
                    if (e.code() == 429) {
                        throw new ApiLimitException(exchangeType,
                                "Api limit hit when making order");
                    }
                }

                throw new UnknownNetworkException(
                        "Unexpected error when making order request",
                        t);
            }
        };
    }

    public Single<ArrayList<Order>> getOrdersAsync(ExchangeMarket market,
                                                   final Date startTime,
                                                   final Date endTime,
                                                   boolean liveOnly) {
        if (liveOnly) {
            Single<ArrayList<GeminiOrder>> statusCall = this.apiClient.getActiveOrders();
            Single<ArrayList<Order>> single = statusCall.flatMap(
                    new Function<ArrayList<GeminiOrder>, SingleSource<ArrayList<Order>>>() {

                        @Override
                        public SingleSource<ArrayList<Order>> apply(ArrayList<GeminiOrder> orders) {
                            ArrayList<Order> convertedOrders = new ArrayList<Order>();
                            for (GeminiOrder geminiOrder : orders) {
                                Order order = geminiOrder.convertToOrder();
                                Date orderTime = order.getOrderTime();
                                if ((orderTime.after(startTime) || orderTime.equals(startTime)) &&
                                    (orderTime.before(endTime))){
                                    convertedOrders.add(order);
                                }
                            }

                            return Single.just(convertedOrders);
                        }
                    });
            return single;
        } else {
            // TODO: Implement
            return null;
        }
    }

    public Single<ArrayList<Order>> getOrdersAsync(ExchangeMarket market, boolean liveOnly) {
        if (liveOnly) {
            Single<ArrayList<GeminiOrder>> statusCall = this.apiClient.getActiveOrders();
            Single<ArrayList<Order>> single = statusCall.flatMap(
                    new Function<ArrayList<GeminiOrder>, SingleSource<ArrayList<Order>>>() {

                        @Override
                        public SingleSource<ArrayList<Order>> apply(ArrayList<GeminiOrder> orders) {
                            ArrayList<Order> convertedOrders = new ArrayList<Order>();
                            for (GeminiOrder geminiOrder : orders) {
                                convertedOrders.add(geminiOrder.convertToOrder());
                            }

                            return Single.just(convertedOrders);
                        }
                    });
            return single;
        } else {
            // TODO: Implement
            return null;
        }
    }
}
