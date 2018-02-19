package com.cryptoquack.cryptoquack.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;

import com.cryptoquack.cryptoquack.AndroidCredentialsStore;
import com.cryptoquack.cryptoquack.CryptoQuackApp;
import com.cryptoquack.cryptoquack.Presenter.Interfaces.ITradingPresenter;
import com.cryptoquack.cryptoquack.R;
import com.cryptoquack.cryptoquack.View.Interfaces.ITradingView;
import com.cryptoquack.model.IModel;
import com.cryptoquack.model.Model;
import com.cryptoquack.model.currency.ExchangeMarket;
import com.cryptoquack.model.exchange.ExchangeAction;
import com.cryptoquack.model.exchange.Exchanges;
import com.cryptoquack.model.order.Order;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class TradingActivity extends CryptoQuackActivity implements ITradingView {

    public static final String EXTRA_TRADING_ACTIVITY_EXCHANGE_TYPE = String.format("%s.exchange_type", TradingActivity.class.getCanonicalName());

    private Exchanges.Exchange exchangeType;

    // UI elements
    private ExchangeMarketAdapter marketAdapter;
    private Spinner marketChoiceSpinner;
    private TableRow currentPriceRow;
    private TextView currentPriceTextView;
    private TableRow actionRow;
    private Spinner actionChoiceSpinner;
    private TableRow orderPriceRow;
    private EditText orderPriceEditText;
    private TableRow quantityRow;
    private EditText quantityEditText;
    private TableRow subtotalRow;
    private TextView subtotalTextView;
    private TableRow feeRow;
    private TextView feeTextView;
    private TableRow totalRow;
    private TextView totalTextView;
    private Button newOrderButton;
    private LinearLayout ordersLayout;
    private TextView errorTextView;

    @Inject
    public ITradingPresenter presenter;

    public TradingActivity() {
        super();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        CryptoQuackApp.getActivityComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trading);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        String exchangeTypeString = extras.getString(TradingActivity.EXTRA_TRADING_ACTIVITY_EXCHANGE_TYPE);
        final Exchanges.Exchange exchangeType = Exchanges.Exchange.valueOf(exchangeTypeString);
        this.exchangeType = exchangeType;

        this.marketChoiceSpinner = (Spinner) findViewById(R.id.market_choice_spinner);
        this.currentPriceRow = (TableRow) findViewById(R.id.current_price_row);
        this.currentPriceTextView = (TextView) findViewById(R.id.current_price_text_view);
        this.actionRow = (TableRow) findViewById(R.id.action_row);
        this.actionChoiceSpinner = (Spinner) findViewById(R.id.action_choice_spinner);
        this.orderPriceRow = (TableRow) findViewById(R.id.order_price_row);
        this.orderPriceEditText = (EditText) findViewById(R.id.order_price_edit_text);
        this.quantityRow = (TableRow) findViewById(R.id.quantity_row);
        this.quantityEditText = (EditText) findViewById(R.id.quantity_edit_text);
        this.subtotalRow = (TableRow) findViewById(R.id.subtotal_price_row);
        this.subtotalTextView = (TextView) findViewById(R.id.subtotal_text_view);
        this.feeRow = (TableRow) findViewById(R.id.fee_row);
        this.feeTextView = (TextView) findViewById(R.id.fee_text_view);
        this.totalRow = (TableRow) findViewById(R.id.total_price_row);
        this.totalTextView = (TextView) findViewById(R.id.total_price_text_view);
        this.newOrderButton = (Button) findViewById(R.id.new_order_button);
        this.ordersLayout = (LinearLayout) findViewById(R.id.orders_layout);
        this.errorTextView = (TextView) findViewById(R.id.error_text_view);

        this.orderPriceEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String orderPrice = orderPriceEditText.getText().toString();
                    String quantity = quantityEditText.getText().toString();
                    ExchangeMarket selectedMarket = (ExchangeMarket) marketChoiceSpinner
                            .getSelectedItem();
                    presenter.onOrderPriceEntered(selectedMarket,
                            ExchangeAction.ExchangeActions.BUY,
                            quantity, orderPrice);
                }
            }
        });

        this.quantityEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String orderPrice = orderPriceEditText.getText().toString();
                    String quantity = quantityEditText.getText().toString();
                    ExchangeMarket selectedMarket = (ExchangeMarket) marketChoiceSpinner
                            .getSelectedItem();
                    presenter.onOrderQuantityEntered(selectedMarket,
                            ExchangeAction.ExchangeActions.BUY,
                            quantity, orderPrice);
                }
            }
        });

        this.setTitle(this.getExchangeName(this.exchangeType));
        this.newOrderButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onNewOrderClick(orderPriceEditText.getText().toString(),
                        quantityEditText.getText().toString(),
                        (ExchangeAction.ExchangeActions) actionChoiceSpinner.getSelectedItem(),
                        Order.OrderType.LIMIT,
                        (ExchangeMarket) marketChoiceSpinner.getSelectedItem());
            }
        });

        this.presenter.onCreate(this, this.exchangeType);
    }

    @Override
    public void updateCurrentPrice(String price) {
        this.currentPriceTextView.setText(price);
    }

    @Override
    public void updateSubtotal(String subtotal) {
        this.subtotalTextView.setText(subtotal);
    }

    @Override
    public void updateOrderFee(String fee) {
        this.feeTextView.setText(fee);
    }

    @Override
    public void updateOrderTotal(String orderTotal) {
        this.totalTextView.setText(orderTotal);
    }

    @Override
    public void setAvailableMarkets(ArrayList<ExchangeMarket> availableMarkets) {
        ExchangeMarketAdapter marketAdapter = new ExchangeMarketAdapter(this,
                android.R.layout.simple_spinner_item, availableMarkets);
        this.marketAdapter = marketAdapter;
        this.marketAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.marketChoiceSpinner.setAdapter(this.marketAdapter);
        MarketExchangeSpinnerActivity marketExchangeSpinnerActivity =
                new MarketExchangeSpinnerActivity(this);
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
    public void clearErrorText() {
        this.showError(null);
    }

    @Override
    public void showError(String message) {
        if (message == null || message.isEmpty()) {
            this.errorTextView.setText("");
            this.errorTextView.setVisibility(View.INVISIBLE);
        } else {
            this.errorTextView.setText(message);
            this.errorTextView.setVisibility(View.VISIBLE);
        }
    }

    private class MarketExchangeSpinnerActivity implements AdapterView.OnItemSelectedListener {

        private TradingActivity tradingActivity;

        public MarketExchangeSpinnerActivity(TradingActivity tradingActivity) {
            this.tradingActivity = tradingActivity;
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            ExchangeMarket market = (ExchangeMarket) parent.getItemAtPosition(position);
            this.tradingActivity.presenter.onMarketChanged(market);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            this.tradingActivity.presenter.onMarketChanged(null);
        }
    }

    @Override
    public void addOrderItem(Order order) {
        OrderItemView item = new OrderItemView(this);
        item.init();
        item.setOrder(order);
        this.ordersLayout.addView(item);
    }
}
