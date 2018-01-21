package com.cryptoquack.cryptoquack;

import android.content.Intent;
import android.os.Bundle;

import com.cryptoquack.com.cryptoquack.model.exchange.Exchanges;

public class TradingActivity extends CryptoQuackActivity {

    private Exchanges.Exchange myExchangeType;
    public static final String EXTRA_TRADING_ACTIVITY_EXCHANGE_TYPE = String.format("%s.exchange_type", TradingActivity.class.getCanonicalName());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trading_activity);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        String exchangeTypeString = extras.getString(TradingActivity.EXTRA_TRADING_ACTIVITY_EXCHANGE_TYPE);
        Exchanges.Exchange exchangeType = Exchanges.Exchange.valueOf(exchangeTypeString);
        this.myExchangeType = exchangeType;
        this.setTitle(this.getExchangeName(this.myExchangeType));
        
        this.initializeModel();
    }

    private void initializeModel() {

    }
}
