package com.cryptoquack.cryptoquack;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Spinner;

import com.cryptoquack.model.currency.ExchangeMarket;
import com.cryptoquack.model.exchange.BaseExchange;
import com.cryptoquack.model.exchange.Exchanges;
import com.cryptoquack.model.exchange.GeminiExchange;

import java.util.ArrayList;

public class TradingActivity extends CryptoQuackActivity {

    private BaseExchange exchange;
    private ArrayList<ExchangeMarket> availableMarkets;
    private ExchangeMarketAdapter exchangeMarketAdapter;

    private Exchanges.Exchange exchangeType;
    public static final String EXTRA_TRADING_ACTIVITY_EXCHANGE_TYPE = String.format("%s.exchange_type", TradingActivity.class.getCanonicalName());
    private Spinner marketChoiceSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trading);

        this.marketChoiceSpinner = (Spinner) findViewById(R.id.market_choice_spinner);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        String exchangeTypeString = extras.getString(TradingActivity.EXTRA_TRADING_ACTIVITY_EXCHANGE_TYPE);
        Exchanges.Exchange exchangeType = Exchanges.Exchange.valueOf(exchangeTypeString);
        this.exchangeType = exchangeType;

        this.initializeModel();
        this.initializeActivity();
    }

    private void initializeModel() {
        if (this.exchangeType == Exchanges.Exchange.GEMINI) {
            this.exchange = new GeminiExchange();
        }

        this.availableMarkets = this.exchange.getAvailableMarkets();
    }

    private void initializeActivity() {
        this.setTitle(this.getExchangeName(this.exchangeType));
        ExchangeMarketAdapter exchangeMarketAdapter = new ExchangeMarketAdapter(this,
                android.R.layout.simple_spinner_item, this.availableMarkets);
        this.exchangeMarketAdapter = exchangeMarketAdapter;
        this.exchangeMarketAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.marketChoiceSpinner.setAdapter(this.exchangeMarketAdapter);
    }
}
