package com.cryptoquack.cryptoquack;

import com.cryptoquack.com.cryptoquack.model.exchange.Exchanges;

import java.util.HashMap;
import java.util.Map;

import static android.provider.Settings.Global.getString;

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
