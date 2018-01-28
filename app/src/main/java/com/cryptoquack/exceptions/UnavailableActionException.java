package com.cryptoquack.exceptions;

import com.cryptoquack.model.exchange.ExchangeAction;
import com.cryptoquack.model.exchange.Exchanges;

/**
 * Created by Duke on 1/27/2018.
 */

public class UnavailableActionException extends CryptoQuackException {

    private ExchangeAction.ExchangeActions action;

    public UnavailableActionException(ExchangeAction.ExchangeActions action) {
        this(action, null);
    }

    public UnavailableActionException(ExchangeAction.ExchangeActions action, String message) {
        this(action, message, null);
    }

    public UnavailableActionException(ExchangeAction.ExchangeActions action, String message,
                                      Exception e) {
        super(message, e);
        this.action = action;
    }

    public ExchangeAction.ExchangeActions getAction() {
        return action;
    }
}
