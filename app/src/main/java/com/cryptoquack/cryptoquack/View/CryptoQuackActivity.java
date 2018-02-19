package com.cryptoquack.cryptoquack.View;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.cryptoquack.cryptoquack.R;
import com.cryptoquack.model.exchange.Exchanges;

import java.util.HashMap;

/**
 * Created by Duke on 1/20/2018.
 */

public abstract class CryptoQuackActivity extends AppCompatActivity {

    protected final HashMap<Exchanges.Exchange, String> exchangeToNameMap = new HashMap<>();

    public CryptoQuackActivity() {
        super();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.exchangeToNameMap.put(Exchanges.Exchange.GEMINI, getString(R.string.exchange_gemini_name));
        this.exchangeToNameMap.put(Exchanges.Exchange.BITTREX, getString(R.string.exchange_bittrex_name));
        this.exchangeToNameMap.put(Exchanges.Exchange.BINANCE, getString(R.string.exchange_binance_name));
    }

    public String getExchangeName(Exchanges.Exchange exchange) {
        return exchangeToNameMap.get(exchange);
    }
}
