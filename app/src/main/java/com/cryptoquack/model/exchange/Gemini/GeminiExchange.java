package com.cryptoquack.model.exchange.Gemini;

import com.cryptoquack.exceptions.UnavailableActionException;
import com.cryptoquack.exceptions.UnavailableMarketException;
import com.cryptoquack.exceptions.UnavailableOrderTypeException;
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

import java.security.InvalidParameterException;
import java.util.ArrayList;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Duke on 1/20/2018.
 */

public class GeminiExchange extends BaseExchange {

    private final ArrayList<ExchangeMarket> availableMarkets = new ArrayList<>();
    private final ArrayList<ExchangeAction.ExchangeActions> availableActions = new ArrayList<>();
    private boolean useSandbox;
    private GeminiApiClientV1 apiClient;

    public GeminiExchange() {
        this(true);
    }

    public GeminiExchange(boolean useSandbox) {
        super(Exchanges.Exchange.GEMINI);
        this.availableMarkets.add(ExchangeMarket.BTCUSD);
        this.availableMarkets.add(ExchangeMarket.ETHUSD);
        this.availableMarkets.add(ExchangeMarket.ETHBTC);

        this.availableActions.add(ExchangeAction.ExchangeActions.BUY);
        this.availableActions.add(ExchangeAction.ExchangeActions.SELL);
        this.useSandbox = useSandbox;
        this.initializeApiClient();
    }

    private void initializeApiClient() {
        Retrofit.Builder apiClientBuilder = new Retrofit.Builder();
        String baseUrl = this.useSandbox ? GeminiHelper.SANDBOX_REST_API_URL : GeminiHelper.REST_API_URL;
        apiClientBuilder.baseUrl(baseUrl);
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        // TODO: Catch exception here. If credentials has not been established yet, then return an
        // an error which would indicate to front end that they have to enter credentials.
        httpClientBuilder.interceptors().add(new GeminiApiRequestInterceptor(this.credentials));
        apiClientBuilder.client(httpClientBuilder.build());
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

        Single<Double> single = this.apiClient.getPubTicker(symbol)
                .flatMap(new Function<GeminiTicker, SingleSource<Double>>() {
                    @Override
                    public SingleSource<Double> apply(GeminiTicker ticker) {
                        Double price = ticker.getLastPrice();
                        return Single.just((price));
                    }
                });
        return single;
    }

    @Override
    public Double getCurrentPrice(ExchangeMarket market) throws UnavailableMarketException {
        Single<Double> single = this.getCurrentPriceAsync(market);
        // TODO: Catch exception and throw custom exception based on error
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
        // TODO: Catch exception and throw custom exception based on error
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
        Single<Order> single = newOrderCall.flatMap(new Function<GeminiOrder, SingleSource<Order>>() {
            @Override
            public SingleSource<Order> apply(GeminiOrder geminiOrder) {
                return Single.just(geminiOrder.convertToOrder());
            }
        });

        return single;
    }
}
