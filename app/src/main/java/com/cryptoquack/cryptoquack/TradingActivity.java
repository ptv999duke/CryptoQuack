package com.cryptoquack.cryptoquack;

import android.os.Bundle;

import com.cryptoquack.com.cryptoquack.model.exchange.Exchanges;

public class TradingActivity extends CryptoQuackActivity {

    private Exchanges.Exchange myExchangeType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trading_activity);

        String title = this.getExchangeName(this.myExchangeType);
        setTitle(title);
    }
}
