package com.cryptoquack.exceptions;

/**
 * Created by Duke on 1/27/2018.
 */

public class UnavailableMarketException extends CryptoQuackException {

    public UnavailableMarketException() {
        super();
    }

    public UnavailableMarketException(String message) {
        super(message);
    }

    public UnavailableMarketException(String message, Exception e) {
        super(message, e);
    }
}
