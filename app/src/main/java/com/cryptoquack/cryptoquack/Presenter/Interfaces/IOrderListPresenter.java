package com.cryptoquack.cryptoquack.Presenter.Interfaces;

import com.cryptoquack.model.order.Order;

/**
 * Created by Duke on 4/15/2018.
 */

public interface IOrderListPresenter {

    public int getOrdersCount();

    public Order getOrderAtPosition(int position);

    public void onOrderClick(int position);
}
