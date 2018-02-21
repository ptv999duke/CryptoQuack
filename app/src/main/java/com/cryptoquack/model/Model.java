package com.cryptoquack.model;

import com.cryptoquack.model.credentials.AccessKeyCredentials;
import com.cryptoquack.model.credentials.ICredentialsStore;
import com.cryptoquack.model.currency.ExchangeMarket;
import com.cryptoquack.model.currency.MonetaryAmount;
import com.cryptoquack.model.exchange.BaseExchange;
import com.cryptoquack.model.exchange.ExchangeAction;
import com.cryptoquack.model.exchange.Exchanges;
import com.cryptoquack.model.exchange.Gemini.GeminiExchange;
import com.cryptoquack.model.logger.ILogger;
import com.cryptoquack.model.order.Order;

import java.util.ArrayList;
import java.util.HashMap;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Single;

/**
 * Created by Duke on 1/30/2018.
 */

public class Model implements IModel {

    private final GeminiExchange geminiExchangeSandbox;
    private HashMap<Exchanges.Exchange, BaseExchange> exchangeMap;
    private final ICredentialsStore credentialsStore;
    private GeminiExchange geminiExchange;
    private ILogger logger;

    @Inject
    public Model(ICredentialsStore credentialsStore,
                 @Named("real") GeminiExchange geminiExchange,
                 @Named("sandbox") GeminiExchange geminiExchangeSandbox,
                 ILogger logger) {
        this.logger = logger;
        this.exchangeMap = new HashMap<>();
        this.geminiExchange = geminiExchange;
        this.geminiExchangeSandbox = geminiExchangeSandbox;
        this.credentialsStore = credentialsStore;

        this.exchangeMap.put(this.geminiExchange.getExchangeType(), geminiExchange);
        this.exchangeMap.put(this.geminiExchangeSandbox.getExchangeType(), geminiExchangeSandbox);
    }

    @Override
    public ArrayList<ExchangeMarket> getAvailableMarkets(Exchanges.Exchange exchange) {
        return this.exchangeMap.get(exchange).getAvailableMarkets();
    }

    @Override
    public Double getCurrentPrice(Exchanges.Exchange exchange, ExchangeMarket market) {
        return this.exchangeMap.get(exchange).getCurrentPrice(market);
    }

    @Override
    public Single<Double> getCurrentPriceAsync(Exchanges.Exchange exchange, ExchangeMarket market) {
        return this.exchangeMap.get(exchange).getCurrentPriceAsync(market);
    }

    @Override
    public MonetaryAmount calculateFee(Exchanges.Exchange exchange,
                                       ExchangeAction.ExchangeActions action,
                                       MonetaryAmount amount,
                                       ExchangeMarket market) {
        return this.exchangeMap.get(exchange).calculateFee(action, amount, market);
    }

    @Override
    public ArrayList<ExchangeAction.ExchangeActions> getAvailableActions(Exchanges.Exchange exchange,
                                                                         ExchangeMarket market) {
        return this.exchangeMap.get(exchange).getAvailableActions(market);
    }

    @Override
    public Single<Order> makeOrderAsync(Exchanges.Exchange exchange, Order orderRequest) {
        return this.exchangeMap.get(exchange).makeOrderAsync(orderRequest);
    }

    @Override
    public AccessKeyCredentials loadCredentials(Exchanges.Exchange exchange) {
        AccessKeyCredentials credentials = this.credentialsStore.getAccessKeyCredentials(exchange);
        if (credentials != null) {
            this.exchangeMap.get(exchange).setCredentials(credentials);
        }

        return credentials;
    }

    @Override
    public AccessKeyCredentials saveCredentials(Exchanges.Exchange exchange,
                                String accessKey,
                                String secretKey,
                                boolean temporary) {
        if (!temporary) {
            this.credentialsStore.saveAccessKeyCredentials(exchange, accessKey, secretKey);
        }

        AccessKeyCredentials credentials = new AccessKeyCredentials(accessKey, secretKey);
        this.exchangeMap.get(exchange).setCredentials(credentials);
        return credentials;
    }

    @Override
    public Order makeOrder(Exchanges.Exchange exchange, Order orderRequest) {
        return this.exchangeMap.get(exchange).makeOrder(orderRequest);
    }
}
