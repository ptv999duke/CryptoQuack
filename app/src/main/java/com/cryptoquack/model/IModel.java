package com.cryptoquack.model;

import com.cryptoquack.model.credentials.AccessKeyCredentials;
import com.cryptoquack.model.currency.ExchangeMarket;
import com.cryptoquack.model.currency.MonetaryAmount;
import com.cryptoquack.model.exchange.ExchangeAction;
import com.cryptoquack.model.exchange.Exchanges;
import com.cryptoquack.model.order.Order;

import java.util.ArrayList;

import io.reactivex.Single;

/**
 * Created by Duke on 1/28/2018.
 */

public interface IModel {

    public ArrayList<ExchangeMarket> getAvailableMarkets(Exchanges.Exchange exchange);

    public Double getCurrentPrice(Exchanges.Exchange exchange, ExchangeMarket market);

    public Single<Double> getCurrentPriceAsync(Exchanges.Exchange exchange, ExchangeMarket market);

    public MonetaryAmount calculateFee(Exchanges.Exchange exchange,
                                       ExchangeAction.ExchangeActions action,
                                       MonetaryAmount amount,
                                       ExchangeMarket market);

    public abstract ArrayList<ExchangeAction.ExchangeActions> getAvailableActions(
            Exchanges.Exchange exchange, ExchangeMarket market);

    public abstract Order makeOrder(Exchanges.Exchange exchange, Order orderRequest);

    public abstract Single<Order> makeOrderAsync(Exchanges.Exchange exchange, Order orderRequest);

    public AccessKeyCredentials loadCredentials(Exchanges.Exchange exchange);

    public void saveCredentials(Exchanges.Exchange exchange,
                                AccessKeyCredentials credentials,
                                boolean temporary);
}
