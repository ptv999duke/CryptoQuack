package com.cryptoquack.model;

import com.cryptoquack.model.exchange.Exchanges;

/**
 * Created by Duke on 1/20/2018.
 */

public class Common {

    public static final Exchanges.Exchange[] exchanges = new Exchanges.Exchange[]{
            Exchanges.Exchange.BITTREX,
            Exchanges.Exchange.BINANCE,
            Exchanges.Exchange.GEMINI
    };
}
