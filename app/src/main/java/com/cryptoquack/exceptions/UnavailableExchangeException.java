package com.cryptoquack.exceptions;

import com.cryptoquack.model.currency.ExchangeMarket;
import com.cryptoquack.model.exchange.Exchanges;

/**
 * Created by Duke on 2/19/2018.
 */

public class UnavailableExchangeException extends CryptoQuackException {


    private Exchanges.Exchange exchange;

    public UnavailableExchangeException(Exchanges.Exchange exchange) {
        this(exchange, null);
    }

    public UnavailableExchangeException(Exchanges.Exchange exchange, String message) {
        this(exchange, message, null);
    }

    public UnavailableExchangeException(Exchanges.Exchange exchange, String message,
                                      Exception e) {
        super(message, e);
        this.exchange = exchange;
    }

    public Exchanges.Exchange getExchange() {
        return this.exchange;
    }
}
