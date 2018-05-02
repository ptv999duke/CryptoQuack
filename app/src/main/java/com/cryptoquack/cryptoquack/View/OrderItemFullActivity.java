package com.cryptoquack.cryptoquack.View;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.cryptoquack.cryptoquack.Presenter.Interfaces.IOrderItemPresenter;
import com.cryptoquack.cryptoquack.R;
import com.cryptoquack.cryptoquack.View.Interfaces.IOrderItemView;
import com.cryptoquack.model.exchange.Exchanges;

import org.w3c.dom.Text;

import javax.inject.Inject;

/**
 * Created by Duke on 5/1/2018.
 */

public class OrderItemFullActivity extends CryptoQuackActivity implements IOrderItemView {

    public static final String EXTRA_ORDER_ITEM_FULL_ACTIVITY_EXCHANGE_TYPE = String.format(
            "%s.exchange_type",
            OrderItemFullActivity.class.getCanonicalName());
    public static final String EXTRA_ORDER_ITEM_FULL_ACTIVITY_ORDER_ID = String.format(
            "%s.order_id",
            OrderItemFullActivity.class.getCanonicalName());

    private TextView exchangeTextView;
    private TextView orderTimeTextView;
    private TextView orderTypeTextView;
    private TextView orderProgressTextView;
    private TextView orderSummaryTextview;

    @Inject
    public IOrderItemPresenter orderItemPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_item_full);
        this.setTitle(getString(R.string.order_item_full_header));
        

        this.exchangeTextView = (TextView) this.findViewById(R.id.exchange_text_view);
        this.orderTimeTextView = (TextView) this.findViewById(R.id.order_date_text_view);
        this.orderSummaryTextview = (TextView) this.findViewById(R.id.order_summary_text_view);
        this.orderTypeTextView = (TextView) this.findViewById(R.id.order_type_text_view);
        this.orderProgressTextView = (TextView) this.findViewById(R.id.order_progress_full_text_view);

        this.orderItemPresenter.onCreate(this);
        this.orderItemPresenter.setOrder(null);
    }

    @Override
    public void setOrderTime(String timeString, boolean visible) {

    }

    @Override
    public void setOrderType(String orderTypeString, boolean visible) {

    }

    @Override
    public void setOrderProgress(String orderProgress, boolean visible) {

    }

    @Override
    public void setOrderSummaryTextview(String orderSummary, boolean visible) {

    }

    public void setExchange(Exchanges.Exchange exchange) {
        
    }
}
