package com.cryptoquack.cryptoquack.View;

import android.os.Bundle;

import com.cryptoquack.cryptoquack.Presenter.Interfaces.IOrderItemPresenter;
import com.cryptoquack.cryptoquack.R;
import com.cryptoquack.cryptoquack.View.Interfaces.IOrderItemView;

import javax.inject.Inject;

/**
 * Created by Duke on 5/1/2018.
 */

public class OrderItemFullActivity extends CryptoQuackActivity implements IOrderItemView {

    @Inject
    public IOrderItemPresenter orderItemPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_item_full);
        this.setTitle(getString(R.string.order_item_header));

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
}
