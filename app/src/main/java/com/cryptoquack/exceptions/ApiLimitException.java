package com.cryptoquack.exceptions;

import com.cryptoquack.model.exchange.Exchanges;

/**
 * Created by Duke on 2/20/2018.
 */


public class ApiLimitException extends CryptoQuackException {


    private Exchanges.Exchange exchange;

    public ApiLimitException(Exchanges.Exchange exchange) {
        this(exchange, null);
    }

    public ApiLimitException(Exchanges.Exchange exchange, String message) {
        this(exchange, message, null);
    }

    public ApiLimitException(Exchanges.Exchange exchange, String message,
                                        Exception e) {
        super(message, e);
        this.exchange = exchange;
    }

    public Exchanges.Exchange getExchange() {
        return this.exchange;
    }
}
