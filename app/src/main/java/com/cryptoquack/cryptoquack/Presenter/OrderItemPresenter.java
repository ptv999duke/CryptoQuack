package com.cryptoquack.cryptoquack.Presenter;

import com.cryptoquack.cryptoquack.IResourceManager;
import com.cryptoquack.cryptoquack.View.IOrderItemView;
import com.cryptoquack.cryptoquack.View.ITradingView;
import com.cryptoquack.model.IModel;
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
        this.view.setOrderTime(orderDate.toString(), true);
        this.view.setOrderType(this.order.getOrderType().toString(), true);
        String actionString = this.order.getAction().toString();
        String orderSummaryString = String.format("%s %f@%f", actionString,
                this.order.getTotalAmount().getAmount(),
                order.getPrice());
        this.view.setOrderSummaryTextview(orderSummaryString, true);
        Order.OrderStatus orderStatus = order.getOrderStatus();
        if (orderStatus == Order.OrderStatus.CANCELLED) {
            // TODO: Put all the hard coded strings in resource manager
            this.view.setOrderProgress(this.rm.getOrderCancelledStatusString(), true);
        } else if (orderStatus == Order.OrderStatus.FILLED) {
            this.view.setOrderProgress(this.rm.getOrderCompletedStatusString(), true);
        } else {
            this.view.setOrderProgress(this.rm.getOrderInProgressStatusString(), true);
        }
    }

    public void onAdditionalActionClicked() {

    }
}
