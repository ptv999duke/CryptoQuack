package com.cryptoquack.model.exchange;

import com.cryptoquack.model.credentials.AccessKeyCredentials;
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

    public abstract double getCurrentPrice(ExchangeMarket market);

    public abstract MonetaryAmount calculateFee(ExchangeAction action, MonetaryAmount amount,
                               ExchangeMarket market);

    public abstract ArrayList<ExchangeAction.ExchangeActions> getAvailableActions(
            ExchangeMarket market);

    public abstract Order makeOrder(ExchangeAction action, Order.OrderType orderType,
                                    MonetaryAmount monetaryAmount, ExchangeMarket market);

}
