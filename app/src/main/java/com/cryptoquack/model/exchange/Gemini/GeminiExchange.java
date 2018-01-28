package com.cryptoquack.model.exchange.Gemini;

import com.cryptoquack.exceptions.UnavailableMarketException;
import com.cryptoquack.model.credentials.AccessKeyCredentials;
import com.cryptoquack.model.currency.ExchangeMarket;
import com.cryptoquack.model.currency.MonetaryAmount;
import com.cryptoquack.model.exchange.BaseExchange;
import com.cryptoquack.model.exchange.ExchangeAction;
import com.cryptoquack.model.exchange.Exchanges;
import com.cryptoquack.model.exchange.Gemini.DTO.Ticker;
import com.cryptoquack.model.order.Order;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Random;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
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
        this.setCredentials(new AccessKeyCredentials("a", "a"));
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
        apiClientBuilder.addConverterFactory(GsonConverterFactory.create());
        Retrofit r = apiClientBuilder.build();
        this.apiClient = r.create(GeminiApiClientV1.class);
    }

    @Override
    public void setCredentials(AccessKeyCredentials credentials) {
        super.setCredentials(credentials);
        this.initializeApiClient();
    }

    private void loadCrdentials() {
        this.setCredentials(null);
    }

    @Override
    public ArrayList<ExchangeMarket> getAvailableMarkets() {
        return (ArrayList<ExchangeMarket>) this.availableMarkets.clone();
    }

    @Override
    public double getCurrentPrice(ExchangeMarket market) throws UnavailableMarketException {
        String symbol;
        try {
            symbol = GeminiHelper.convertMarketToSymbol(market);
        } catch (InvalidParameterException e){
            throw new UnavailableMarketException(e.getMessage(), e);
        }

        Call<Ticker> tickerCall = this.apiClient.getPubTicker(symbol);
        try {
            Response<Ticker> response = tickerCall.execute();
            int statusCode = response.code();
            Ticker ticker = response.body();
            return ticker.lastPrice;
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public MonetaryAmount calculateFee(ExchangeAction action, MonetaryAmount amount,
                                          ExchangeMarket market) {
        return null;
    }

    @Override
    public ArrayList<ExchangeAction.ExchangeActions> getAvailableActions(
        ExchangeMarket market) {
        return (ArrayList<ExchangeAction.ExchangeActions>) this.availableActions.clone();
    }

    @Override
    public Order makeOrder(ExchangeAction action, Order.OrderType orderType,
                           MonetaryAmount monetaryAmount, ExchangeMarket market) {
        return null;
    }
}
