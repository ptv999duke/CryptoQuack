package com.cryptoquack.cryptoquack.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;

import com.cryptoquack.cryptoquack.CryptoQuackActivity;
import com.cryptoquack.cryptoquack.ExchangeActionAdapter;
import com.cryptoquack.cryptoquack.ExchangeMarketAdapter;
import com.cryptoquack.cryptoquack.Presenter.ITradingPresenter;
import com.cryptoquack.cryptoquack.Presenter.TradingPresenter;
import com.cryptoquack.cryptoquack.R;
import com.cryptoquack.cryptoquack.AndroidResourceManager;
import com.cryptoquack.model.IModel;
import com.cryptoquack.model.Model;
import com.cryptoquack.model.currency.Currencies;
import com.cryptoquack.model.currency.ExchangeMarket;
import com.cryptoquack.model.exchange.BaseExchange;
import com.cryptoquack.model.exchange.ExchangeAction;
import com.cryptoquack.model.exchange.Exchanges;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class TradingActivity extends CryptoQuackActivity implements ITradingView {

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
    // private String defaultLoadingString;

    // UI elements that could potentially fit better for a presenter
    // private Timer getCurrentPriceTimer;
    // private GetCurrentPriceTask getCurrentPriceAsyncTask;

    private ITradingPresenter presenter;
    private IModel model;

    @Override
    public void updateCurrentPrice(String priceString) {
        this.currentPriceTextView.setText(priceString);
    }

    @Override
    public void updateSubtotal(double amount, Currencies.Currency currency) {

    }

    @Override
    public void updateOrderFee(double amount, Currencies.Currency currency) {

    }

    @Override
    public void updateOrderTotal(double amount, Currencies.Currency currency) {

    }

    @Override
    public void setAvailableMarkets(ArrayList<ExchangeMarket> availableMarkets) {
        this.availableMarkets = availableMarkets;
        ExchangeMarketAdapter marketAdapter = new ExchangeMarketAdapter(this,
                android.R.layout.simple_spinner_item, this.availableMarkets);
        this.marketAdapter = marketAdapter;
        this.marketAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.marketChoiceSpinner.setAdapter(this.marketAdapter);
        MarketExchangeSpinnerActivity marketExchangeSpinnerActivity = new MarketExchangeSpinnerActivity(this);
        this.marketChoiceSpinner.setOnItemSelectedListener(marketExchangeSpinnerActivity);
    }

    @Override
    public void setAvailableActions(ArrayList<ExchangeAction.ExchangeActions> availableActions) {
        ExchangeActionAdapter exchangeActionAdapter = new ExchangeActionAdapter(this,
                android.R.layout.simple_spinner_item, availableActions);
        exchangeActionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.actionChoiceSpinner.setAdapter(exchangeActionAdapter);
        this.actionRow.setVisibility(View.VISIBLE);
    }

    @Override
    public void showIncorrectPriceError() {

    }

    @Override
    public void showIncorrectQuantityError() {

    }

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
        IModel model = new Model();
        this.model = model;
        this.presenter = new TradingPresenter(AndroidSchedulers.mainThread());
        // this.getCurrentPriceTimer = new Timer();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trading);


        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        String exchangeTypeString = extras.getString(TradingActivity.EXTRA_TRADING_ACTIVITY_EXCHANGE_TYPE);
        Exchanges.Exchange exchangeType = Exchanges.Exchange.valueOf(exchangeTypeString);
        this.exchangeType = exchangeType;

        this.marketChoiceSpinner = (Spinner) findViewById(R.id.market_choice_spinner);
        this.actionChoiceSpinner = (Spinner) findViewById(R.id.action_choice_spinner);
        this.actionRow = (TableRow) findViewById(R.id.action_row);
        this.currentPriceRow = (TableRow) findViewById(R.id.current_price_row);
        this.currentPriceTextView = (TextView) findViewById(R.id.current_price_text_view);
        this.quantityEditText = (EditText)  findViewById(R.id.quantity_edit_text);
        // this.defaultLoadingString = getString(R.string.loading);
        this.setTitle(this.getExchangeName(this.exchangeType));

        this.presenter.onCreate(this, this.model, new AndroidResourceManager(this.getResources()), this.exchangeType);
    }

    private void initializeModel() {

    }

    private void onExchangeMarketChanged(ExchangeMarket market) {
        this.presenter.onMarketChanged(market);
//        this.currentPriceTextView.setVisibility(View.VISIBLE);
//        this.resetCurrentPrice();
//        if (market == null) {
//            this.actionRow.setVisibility(View.INVISIBLE);
//            this.currentPriceTextView.setVisibility(View.INVISIBLE);
//            // TODO: Implement clearing out all the info on screen.
//        }
    }

//    private void resetCurrentPrice() {
//        this.currentPriceTextView.setText(this.defaultLoadingString);
//        this.getCurrentPriceTimer.cancel();
//        this.getCurrentPriceTimer.purge();
//        if (this.getCurrentPriceAsyncTask != null) {
//            this.getCurrentPriceAsyncTask.cancel(true);
//        }
//    }

//    private void scheduleGetCurrentPriceTask(final GetCurrentPriceTaskParameter asyncTaskParams) {
//        this.getCurrentPriceTimer = new Timer();
//        final Handler handler = new Handler();
//        TimerTask doAsynchronousTask = new TimerTask() {
//
//            @Override
//            public void run() {
//                handler.post(new Runnable() {
//                    public void run() {
//                        getCurrentPriceAsyncTask = new GetCurrentPriceTask();
//                        getCurrentPriceAsyncTask.execute(asyncTaskParams);
//                    }
//                });
//            }
//        };
//
//        this.getCurrentPriceTimer.schedule(doAsynchronousTask, 0, 2500); //execute in every 50000 ms
//    }

//    private static class GetCurrentPriceTaskParameter {
//        private ExchangeMarket market;
//        private BaseExchange exchange;
//
//        public GetCurrentPriceTaskParameter(ExchangeMarket market, BaseExchange exchange) {
//            this.market = market;
//            this.exchange = exchange;
//        }
//
//        public ExchangeMarket getExchangeMarket() {
//            return this.market;
//        }
//
//        public BaseExchange getExchange() {
//            return this.exchange;
//        }
//    }
//
//    private class GetCurrentPriceTask extends AsyncTask<GetCurrentPriceTaskParameter, Void, Double> {
//
//        private Exception exception;
//
//        @Override
//        protected void onPreExecute() {
//        }
//
//        @Override
//        protected Double doInBackground(GetCurrentPriceTaskParameter... params) {
//            GetCurrentPriceTaskParameter parameter = params[0];
//            try {
//                return parameter.getExchange().getCurrentPrice(parameter.getExchangeMarket());
//            } catch (Exception e) {
//                this.exception = e;
//                return -1.0;
//            }
//        }
//
//        @Override
//        protected void onPostExecute(Double result) {
//            if (this.exception != null) {
//                currentPriceTextView.setText(getString(R.string.get_current_price_error));
//            } else{
//                currentPriceTextView.setText(result.toString());
//            }
//        }
//    }
}
