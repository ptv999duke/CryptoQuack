package com.cryptoquack.exceptions;

import com.cryptoquack.cryptoquack.CryptoQuackActivity;

/**
 * Created by Duke on 1/27/2018.
 */

public class CryptoQuackException extends RuntimeException {

    public CryptoQuackException() {
        super();
    }

    public CryptoQuackException(String message) {
        super(message);
    }

    public CryptoQuackException(String message, Exception e) {
        super(message, e);
    }
}
