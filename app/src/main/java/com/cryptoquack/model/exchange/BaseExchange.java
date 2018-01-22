package com.cryptoquack.model.exchange;

import com.cryptoquack.model.currency.Currencies;
import com.cryptoquack.model.credentials.ICredentials;
import com.cryptoquack.model.currency.ExchangeMarket;

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
}
