package com.cryptoquack.cryptoquack;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;

import com.cryptoquack.model.credentials.AccessKeyCredentials;
import com.cryptoquack.model.currency.ExchangeMarket;
import com.cryptoquack.model.exchange.BaseExchange;
import com.cryptoquack.model.exchange.ExchangeAction;
import com.cryptoquack.model.exchange.Exchanges;
import com.cryptoquack.model.exchange.Gemini.GeminiExchange;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class TradingActivity extends CryptoQuackActivity {

    public static final String EXTRA_TRADING_ACTIVITY_EXCHANGE_TYPE = String.format("%s.exchange_type", TradingActivity.class.getCanonicalName());

    private Exchanges.Exchange exchangeType;
    private BaseExchange exchange;
    private ArrayList<ExchangeMarket> availableMarkets;

    // UI elements
    private ExchangeMarketAdapter marketAdapter;
    private Spinner marketChoiceSpinner;
    private Spinner actionChoiceSpinner;
    private TableRow actionRow;
    private TableRow currentPriceRow;
    private TextView currentPriceTextView;
    private EditText quantityEditText;
    private String defaultLoadingString;

    // UI elements that could potentially fit better for a presenter
    private Timer getCurrentPriceTimer;
    private GetCurrentPriceTask getCurrentPriceAsyncTask;

    private class MarketExchangeSpinnerActivity implements AdapterView.OnItemSelectedListener {

        private TradingActivity tradingActivity;

        public MarketExchangeSpinnerActivity(TradingActivity tradingActivity) {
            this.tradingActivity = tradingActivity;
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            ExchangeMarket market = (ExchangeMarket) parent.getItemAtPosition(position);
            this.tradingActivity.onExchangeMarketChanged(market);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            this.tradingActivity.onExchangeMarketChanged(null);
        }
    }

    public TradingActivity() {
        super();
        this.getCurrentPriceTimer = new Timer();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trading);

        this.marketChoiceSpinner = (Spinner) findViewById(R.id.market_choice_spinner);
        this.actionChoiceSpinner = (Spinner) findViewById(R.id.action_choice_spinner);
        this.actionRow = (TableRow) findViewById(R.id.action_row);
        this.currentPriceRow = (TableRow) findViewById(R.id.current_price_row);
        this.currentPriceTextView = (TextView) findViewById(R.id.current_price_text_view);
        this.quantityEditText = (EditText)  findViewById(R.id.quantity_edit_text);
        this.defaultLoadingString = getString(R.string.loading);

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
        ExchangeMarketAdapter marketAdapter = new ExchangeMarketAdapter(this,
                android.R.layout.simple_spinner_item, this.availableMarkets);
        this.marketAdapter = marketAdapter;
        this.marketAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.marketChoiceSpinner.setAdapter(this.marketAdapter);
        MarketExchangeSpinnerActivity marketExchangeSpinnerActivity = new MarketExchangeSpinnerActivity(this);
        this.marketChoiceSpinner.setOnItemSelectedListener(marketExchangeSpinnerActivity);
    }

    private void onExchangeMarketChanged(ExchangeMarket market) {
        this.resetCurrentPrice();
        if (market == null) {
            this.actionRow.setVisibility(View.INVISIBLE);
            this.currentPriceTextView.setVisibility(View.INVISIBLE);
            // TODO: Implement clearing out all the info on screen.
        } else {
            ArrayList<ExchangeAction.ExchangeActions> availbleActions = this.exchange.
                    getAvailableActions(market);
            ExchangeActionAdapter exchangeActionAdapter = new ExchangeActionAdapter(this,
                    android.R.layout.simple_spinner_item, availbleActions);
            exchangeActionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            this.actionChoiceSpinner.setAdapter(exchangeActionAdapter);
            this.currentPriceTextView.setText(this.defaultLoadingString);
            GetCurrentPriceTaskParameter params = new GetCurrentPriceTaskParameter(market,
                    this.exchange);
            this.scheduleGetCurrentPriceTask(params);

            this.actionRow.setVisibility(View.VISIBLE);
            this.currentPriceTextView.setVisibility(View.VISIBLE);
        }
    }

    private void resetCurrentPrice() {
        this.currentPriceTextView.setText(this.defaultLoadingString);
        this.getCurrentPriceTimer.cancel();
        this.getCurrentPriceTimer.purge();
        if (this.getCurrentPriceAsyncTask != null) {
            this.getCurrentPriceAsyncTask.cancel(true);
        }
    }

    private void scheduleGetCurrentPriceTask(final GetCurrentPriceTaskParameter asyncTaskParams) {
        this.getCurrentPriceTimer = new Timer();
        final Handler handler = new Handler();
        TimerTask doAsynchronousTask = new TimerTask() {

            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        getCurrentPriceAsyncTask = new GetCurrentPriceTask();
                        getCurrentPriceAsyncTask.execute(asyncTaskParams);
                    }
                });
            }
        };

        this.getCurrentPriceTimer.schedule(doAsynchronousTask, 0, 2500); //execute in every 50000 ms
    }

    private static class GetCurrentPriceTaskParameter {
        private ExchangeMarket market;
        private BaseExchange exchange;

        public GetCurrentPriceTaskParameter(ExchangeMarket market, BaseExchange exchange) {
            this.market = market;
            this.exchange = exchange;
        }

        public ExchangeMarket getExchangeMarket() {
            return this.market;
        }

        public BaseExchange getExchange() {
            return this.exchange;
        }
    }

    private class GetCurrentPriceTask extends AsyncTask<GetCurrentPriceTaskParameter, Void, Double> {

        private Exception exception;

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Double doInBackground(GetCurrentPriceTaskParameter... params) {
            GetCurrentPriceTaskParameter parameter = params[0];
            try {
                return parameter.getExchange().getCurrentPrice(parameter.getExchangeMarket());
            } catch (Exception e) {
                this.exception = e;
                return -1.0;
            }
        }

        @Override
        protected void onPostExecute(Double result) {
            if (this.exception != null) {
                currentPriceTextView.setText(getString(R.string.get_current_price_error));
            } else{
                currentPriceTextView.setText(result.toString());
            }
        }
    }
}
