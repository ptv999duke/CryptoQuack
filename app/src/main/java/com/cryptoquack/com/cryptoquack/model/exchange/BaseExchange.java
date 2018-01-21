package com.cryptoquack.com.cryptoquack.model.exchange;

/**
 * Created by Duke on 1/20/2018.
 */

public abstract class BaseExchange {

    protected Exchanges.Exchange myExchange;

    public BaseExchange(Exchanges.Exchange exchangeType) {
        this.myExchange = exchangeType;
    }

    public Exchanges.Exchange getExchangeType() {
        return this.myExchange;
    }
}
