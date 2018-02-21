package com.cryptoquack.exceptions;

import com.cryptoquack.model.order.Order;

/**
 * Created by Duke on 2/20/2018.
 */

public class UnknownNetworkException extends CryptoQuackException {

    public UnknownNetworkException() {
        this(null);
    }

    public UnknownNetworkException(String message) {
        this(message, null);
    }

    public UnknownNetworkException(String message, Throwable e) {
        super(message, e);
    }
}
