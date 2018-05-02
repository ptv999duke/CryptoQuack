package com.cryptoquack.model.exchange;

import android.net.Credentials;

import com.cryptoquack.exceptions.CredentialsNotSetException;
import com.cryptoquack.model.credentials.AccessKeyCredentials;
import com.cryptoquack.model.currency.Currencies;
import com.cryptoquack.model.credentials.ICredentials;
import com.cryptoquack.model.currency.ExchangeMarket;
import com.cryptoquack.model.currency.MonetaryAmount;
import com.cryptoquack.model.order.Order;
import com.cryptoquack.model.order.OrderStatus;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Date;

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

    protected void validateCredentialsLoaded() {
        if (this.credentials == null) {
            throw new CredentialsNotSetException();
        }
    }

    public abstract ArrayList<ExchangeMarket> getAvailableMarkets();

    public abstract Single<Double> getCurrentPriceAsync(ExchangeMarket market);

    public Double getCurrentPrice(ExchangeMarket market) {
        Single<Double> single = this.getCurrentPriceAsync(market);
        return single.blockingGet();
    }

    public abstract MonetaryAmount calculateFee(ExchangeAction.ExchangeActions action,
                                                MonetaryAmount amount,
                                                ExchangeMarket market);

    public abstract ArrayList<ExchangeAction.ExchangeActions> getAvailableActions(
            ExchangeMarket market);

    public abstract Single<Order> makeOrderAsync(Order orderRequest);

    public Order makeOrder(Order orderRequest) {
        Single<Order> single = this.makeOrderAsync(orderRequest);
        return single.blockingGet();
    }

    public abstract Single<OrderStatus> getOrderStatusAsync(Order order);

    public OrderStatus getOrderStatus(Order order) {
        Single<OrderStatus> single = this.getOrderStatusAsync(order);
        return single.blockingGet();
    }

    public Single<ArrayList<Order>> getOrdersAsync(ExchangeMarket market, boolean liveOnly) {
        return this.getOrdersAsync(market, null, null, liveOnly);
    }

    public ArrayList<Order> getOrders(ExchangeMarket market, boolean liveOnly) {
        Single<ArrayList<Order>> single = this.getOrdersAsync(market, liveOnly);
        return single.blockingGet();
    }

    public abstract Single<ArrayList<Order>> getOrdersAsync(ExchangeMarket market,
                                                   final Date startTime,
                                                   final Date endTime,
                                                   boolean liveOnly);

    public ArrayList<Order> getOrders(ExchangeMarket market,
                                      final Date startTime,
                                      final Date endTime,
                                      boolean liveOnly) {
        Single<ArrayList<Order>> single = this.getOrdersAsync(market, startTime, endTime, liveOnly);
        return single.blockingGet();
    }
}
