package com.cryptoquack.cryptoquack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.cryptoquack.model.currency.ExchangeMarket;
import com.cryptoquack.model.exchange.BaseExchange;
import com.cryptoquack.model.exchange.ExchangeAction;
import com.cryptoquack.model.exchange.Exchanges;
import com.cryptoquack.model.exchange.GeminiExchange;

import java.util.ArrayList;

public class TradingActivity extends CryptoQuackActivity {

    public static final String EXTRA_TRADING_ACTIVITY_EXCHANGE_TYPE = String.format("%s.exchange_type", TradingActivity.class.getCanonicalName());

    private Exchanges.Exchange exchangeType;
    private BaseExchange exchange;
    private ArrayList<ExchangeMarket> availableMarkets;

    private ExchangeMarketAdapter exchangeMarketAdapter;
    private Spinner marketChoiceSpinner;
    private Spinner actionChoiceSpinner;
    private ArrayList<MarketExchangeSpinnerActivity> availableActions;

    private class MarketExchangeSpinnerActivity implements AdapterView.OnItemSelectedListener {

        private TradingActivity tradingActivity;

        public MarketExchangeSpinnerActivity(TradingActivity tradingActivity) {
            this.tradingActivity = tradingActivity;
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            ExchangeMarket exchangeMarket = (ExchangeMarket) parent.getItemAtPosition(position);
            this.tradingActivity.onExchangeMarketChanged(exchangeMarket);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            this.tradingActivity.onExchangeMarketChanged(null);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trading);

        this.marketChoiceSpinner = (Spinner) findViewById(R.id.market_choice_spinner);
        this.actionChoiceSpinner = (Spinner) findViewById(R.id.action_choice_spinner);

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
        MarketExchangeSpinnerActivity marketExchangeSpinnerActivity = new MarketExchangeSpinnerActivity(this);
        this.marketChoiceSpinner.setOnItemSelectedListener(marketExchangeSpinnerActivity);
    }

    private void onExchangeMarketChanged(ExchangeMarket exchangeMarket) {
        if (exchangeMarket == null) {
            // TODO: Implement clearing out all the info on screen.
        } else {
            ArrayList<ExchangeAction.ExchangeActions> availbleActions = this.exchange.getAvailableActions(exchangeMarket);
            ExchangeActionAdapter exchangeActionAdapter = new ExchangeActionAdapter(this,
                    android.R.layout.simple_spinner_item, availbleActions);
            exchangeActionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            this.actionChoiceSpinner.setAdapter(exchangeActionAdapter);
        }
    }
}
