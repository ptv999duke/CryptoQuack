package com.cryptoquack.cryptoquack.View;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.cryptoquack.cryptoquack.AndroidCredentialsStore;
import com.cryptoquack.cryptoquack.CryptoQuackApp;
import com.cryptoquack.cryptoquack.ResourceManager.AndroidResourceManager;
import com.cryptoquack.cryptoquack.Presenter.Interfaces.IOrderItemPresenter;
import com.cryptoquack.cryptoquack.Presenter.OrderItemPresenter;
import com.cryptoquack.cryptoquack.R;
import com.cryptoquack.cryptoquack.View.Interfaces.IOrderItemView;
import com.cryptoquack.model.Model;
import com.cryptoquack.model.order.Order;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by Duke on 2/5/2018.
 */

public class OrderItemView extends ConstraintLayout implements IOrderItemView {

    private TextView orderTimeTextView;
    private TextView orderTypeTextView;
    private TextView orderProgressTextView;
    private TextView orderSummaryTextview;

    @Inject
    public IOrderItemPresenter presenter;

    public OrderItemView(Context context) {
        this(context, null);
    }

    public OrderItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void Init() {
        CryptoQuackApp.getActivityComponent().inject(this);
        this.presenter.onCreate(this);
        LayoutInflater inflater = (LayoutInflater) this.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_order_item, this, true);
        this.orderTimeTextView = (TextView) this.findViewById(R.id.order_time_text_view);
        this.orderSummaryTextview = (TextView) this.findViewById(R.id.order_summary_text_view);
        this.orderTypeTextView = (TextView) this.findViewById(R.id.order_type_text_view);
        this.orderProgressTextView = (TextView) this.findViewById(R.id.order_progress_text_view);
    }

    public void setOrder(Order order) {
        this.presenter.setOrder(order);
    }

    @Override
    public void setOrderTime(String timeString, boolean visible) {
        this.orderTimeTextView.setText(timeString);
        this.orderTimeTextView.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setOrderType(String orderTypeString, boolean visible) {
        this.orderTypeTextView.setText(orderTypeString);
        this.orderTypeTextView.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setOrderProgress(String orderProgress, boolean visible) {
        this.orderProgressTextView.setText(orderProgress);
        this.orderProgressTextView.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setOrderSummaryTextview(String orderSummary, boolean visible) {
        this.orderSummaryTextview.setText(orderSummary);
        this.orderSummaryTextview.setVisibility(visible ? View.VISIBLE : View.GONE);
    }
}
