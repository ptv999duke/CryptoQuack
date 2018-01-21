package com.cryptoquack.cryptoquack;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.cryptoquack.com.cryptoquack.model.exchange.Exchanges;

import java.util.HashMap;

/**
 * Created by Duke on 1/20/2018.
 */

public abstract class CryptoQuackActivity extends AppCompatActivity {

    protected final HashMap<Exchanges.Exchange, String> myExchangeToNameMap = new HashMap<>();

    public CryptoQuackActivity() {
        super();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.myExchangeToNameMap.put(Exchanges.Exchange.GEMINI, getString(R.string.exchange_gemini_name));
        this.myExchangeToNameMap.put(Exchanges.Exchange.BITTREX, getString(R.string.exchange_bittrex_name));
        this.myExchangeToNameMap.put(Exchanges.Exchange.BINANCE, getString(R.string.exchange_binance_name));
    }

    public String getExchangeName(Exchanges.Exchange exchange) {
        return myExchangeToNameMap.get(exchange);
    }
}
