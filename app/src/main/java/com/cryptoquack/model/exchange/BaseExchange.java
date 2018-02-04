package com.cryptoquack.model.exchange;

import com.cryptoquack.model.credentials.AccessKeyCredentials;
import com.cryptoquack.model.currency.Currencies;
import com.cryptoquack.model.credentials.ICredentials;
import com.cryptoquack.model.currency.ExchangeMarket;
import com.cryptoquack.model.currency.MonetaryAmount;
import com.cryptoquack.model.order.Order;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import io.reactivex.Single;

/**
 * Created by Duke on 1/20/2018.
 */

public abstract class BaseExchange {

    public static final Gson GSON = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
            .create();

    protected Exchanges.Exchange exchangeType;
    protected AccessKeyCredentials credentials;

    public BaseExchange(Exchanges.Exchange exchangeType) {
        this.exchangeType = exchangeType;
    }

    public Exchanges.Exchange getExchangeType() {
        return this.exchangeType;
    }

    public void setCredentials(AccessKeyCredentials credentials) {
        this.credentials = credentials;
    }

    public abstract ArrayList<ExchangeMarket> getAvailableMarkets();

    public abstract Double getCurrentPrice(ExchangeMarket market);

    public abstract Single<Double> getCurrentPriceAsync(ExchangeMarket market);

    public abstract MonetaryAmount calculateFee(ExchangeAction.ExchangeActions action,
                                                MonetaryAmount amount,
                                                ExchangeMarket market);

    public abstract ArrayList<ExchangeAction.ExchangeActions> getAvailableActions(
            ExchangeMarket market);

    public abstract Order makeOrder(Order orderRequest);

    public abstract Single<Order> makeOrderAsync(Order orderRequest);
}
