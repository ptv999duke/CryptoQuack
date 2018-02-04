package com.cryptoquack.exceptions;

import com.cryptoquack.model.currency.ExchangeMarket;

/**
 * Created by Duke on 1/27/2018.
 */

public class UnavailableMarketException extends CryptoQuackException {

    private ExchangeMarket market;

    public UnavailableMarketException(ExchangeMarket market) {
        this(market, null);
    }

    public UnavailableMarketException(ExchangeMarket market, String message) {
        this(market, message, null);
    }

    public UnavailableMarketException(ExchangeMarket market, String message,
                                      Exception e) {
        super(message, e);
        this.market = market;
    }

    public ExchangeMarket getMarket() {
        return this.market;
    }
}
