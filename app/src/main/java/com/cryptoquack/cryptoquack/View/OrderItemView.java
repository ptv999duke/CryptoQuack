package com.cryptoquack.cryptoquack.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cryptoquack.cryptoquack.Presenter.IOrderItemPresenter;
import com.cryptoquack.cryptoquack.Presenter.OrderItemPresenter;
import com.cryptoquack.cryptoquack.R;
import com.cryptoquack.model.order.Order;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by Duke on 2/5/2018.
 */

public class OrderItemView extends LinearLayout implements IOrderItemView {

    private TextView orderTimeTextView;
    private TextView orderActionTextView;
    private TextView orderTypeTextView;
    private TextView orderAmountPriceTextView;
    private TextView orderProgressTextView;
    private Button additionalActionButton;

    private IOrderItemPresenter presenter;

    public OrderItemView(Context context) {
        this(context, null);
    }

    public OrderItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void init() {
        this.presenter = new OrderItemPresenter(AndroidSchedulers.mainThread());
        this.presenter.onCreate(this, null, null);
        LayoutInflater inflater = (LayoutInflater) this.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_order_item, this, true);
        this.orderTimeTextView = (TextView) this.findViewById(R.id.order_time_text_view);
        this.orderActionTextView = (TextView) this.findViewById(R.id.order_action_text_view);
        this.orderTypeTextView = (TextView) this.findViewById(R.id.order_type_text_view);
        this.orderAmountPriceTextView = (TextView) this.findViewById(R.id.order_amount_price_text_view);
        this.orderProgressTextView = (TextView) this.findViewById(R.id.order_progress_text_view);
        this.additionalActionButton = (Button) this.findViewById(R.id.order_additional_action_button);
        this.setOrientation(this.HORIZONTAL);
        this.setWeightSum(6);
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
    public void setOrderAmountPrice(String orderAmountPriceString, boolean visible) {
        this.orderAmountPriceTextView.setText(orderAmountPriceString);
        this.orderAmountPriceTextView.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setOrderProgress(String orderProgress, boolean visible) {
        this.orderProgressTextView.setText(orderProgress);
        this.orderProgressTextView.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setAdditionalActionButton(String additionalActionText, boolean visible,
                                          boolean enabled) {
        this.additionalActionButton.setText(additionalActionText);
        this.additionalActionButton.setVisibility(visible ? View.VISIBLE : View.GONE);
        this.additionalActionButton.setEnabled(enabled);
    }

    @Override
    public void setOrderAction(String orderAction, boolean visible) {
        this.orderActionTextView.setText(orderAction);
        this.orderActionTextView.setVisibility(visible ? View.VISIBLE : View.GONE);
    }
}
