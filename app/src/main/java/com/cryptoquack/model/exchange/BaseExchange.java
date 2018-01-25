package com.cryptoquack.model.exchange;

import com.cryptoquack.model.currency.Currencies;
import com.cryptoquack.model.credentials.ICredentials;
import com.cryptoquack.model.currency.ExchangeMarket;
import com.cryptoquack.model.currency.MonetaryAmount;
import com.cryptoquack.model.order.Order;

import java.util.ArrayList;

/**
 * Created by Duke on 1/20/2018.
 */

public abstract class BaseExchange {

    protected Exchanges.Exchange exchangeType;
    protected ICredentials credentials;

    public BaseExchange(Exchanges.Exchange exchangeType) {
        this.exchangeType = exchangeType;
    }

    public Exchanges.Exchange getExchangeType() {
        return this.exchangeType;
    }

    public void setCredentials(ICredentials credentials) {
        this.credentials = credentials;
    }

    public abstract ArrayList<ExchangeMarket> getAvailableMarkets();

    public abstract double getCurrentPrice(ExchangeMarket exchangeMarket);

    public MonetaryAmount calculateFee(ExchangeAction action, MonetaryAmount amount,
                               ExchangeMarket exchangeMarket) {
        return this.calculateFee(action, amount);
    }

    protected abstract MonetaryAmount calculateFee(ExchangeAction action, MonetaryAmount amount);

    public abstract ArrayList<ExchangeAction.ExchangeActions> getAvailableActions(
            ExchangeMarket exchangeMarket);

    public abstract Order makeOrder(ExchangeAction action, Order.OrderType orderType,
                                    MonetaryAmount monetaryAmount, ExchangeMarket exchangeMarket);
}
