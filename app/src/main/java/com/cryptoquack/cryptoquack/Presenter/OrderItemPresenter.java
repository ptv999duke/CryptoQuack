package com.cryptoquack.cryptoquack.Presenter;

import com.cryptoquack.cryptoquack.Presenter.Interfaces.IOrderItemPresenter;
import com.cryptoquack.cryptoquack.ResourceManager.IResourceManager;
import com.cryptoquack.cryptoquack.View.Interfaces.IOrderItemView;
import com.cryptoquack.model.IModel;
import com.cryptoquack.model.currency.Currencies;
import com.cryptoquack.model.order.Order;

import java.util.Date;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Duke on 2/5/2018.
 */

public class OrderItemPresenter implements IOrderItemPresenter {

    private IOrderItemView view;
    private IModel model;
    private IResourceManager rm;
    private Order order;

    private final Scheduler uiScheduler;
    private Scheduler bgScheduler = null;

    public OrderItemPresenter(Scheduler uiScheduler) {
        this.uiScheduler = uiScheduler;
    }

    @Override
    public void onCreate(IOrderItemView view, IModel model, IResourceManager rm) {
        this.bgScheduler = Schedulers.io();
        this.view = view;
        this.model = model;
        this.rm = rm;
    }

    public void setOrder(Order order) {
        this.order = order;
        Date orderDate = this.order.getOrderTime();
        String dateString = this.rm.getOrderDateString(orderDate);
        this.view.setOrderTime(dateString, true);
        this.view.setOrderType(this.order.getOrderType().toString(), true);
        String actionString = this.order.getAction().toString();
        Currencies.Currency priceCurrency = this.order.getMarket().getDestinationCurrency();
        String orderSummaryString = this.rm.getOrderSummaryString(this.order.getAction(),
                this.order.getTotalAmount(),
                this.order.getPrice(),
                priceCurrency);
        this.view.setOrderSummaryTextview(orderSummaryString, true);
        Order.OrderStatus orderStatus = order.getOrderStatus();
        if (orderStatus == Order.OrderStatus.CANCELLED) {
            this.view.setOrderProgress(this.rm.getOrderCancelledStatusString(), true);
        } else if (orderStatus == Order.OrderStatus.FILLED) {
            this.view.setOrderProgress(this.rm.getOrderCompletedStatusString(), true);
        } else {
            int percentFulfilled = (int)(100*this.order.getAmountFulfilled().getAmount() /
                    this.order.getTotalAmount().getAmount());
            String progressText = this.rm.getOrderFulfilledPercentageString(percentFulfilled);
            this.view.setOrderProgress(progressText, true);
        }
    }

    public void onAdditionalActionClicked() {

    }
}
