package com.cryptoquack.exceptions;

/**
 * Created by Duke on 2/13/2018.
 */

public class CredentialsNotSetException extends CryptoQuackException {

    public CredentialsNotSetException() {
        this(null);
    }

    public CredentialsNotSetException(String message) {
        super(message, null);
    }

    public CredentialsNotSetException(String message, Exception e) {
        super(message, e);
    }
}
