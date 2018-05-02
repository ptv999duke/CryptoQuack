package com.cryptoquack.cryptoquack.Presenter;

import com.cryptoquack.cryptoquack.Presenter.Interfaces.IOrderItemPresenter;
import com.cryptoquack.cryptoquack.ResourceManager.IResourceManager;
import com.cryptoquack.cryptoquack.View.Interfaces.IOrderItemView;
import com.cryptoquack.model.logger.ILogger;
import com.cryptoquack.model.IModel;
import com.cryptoquack.model.currency.Currencies;
import com.cryptoquack.model.order.Order;
import com.cryptoquack.model.order.OrderStatus;

import java.util.Date;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Scheduler;

/**
 * Created by Duke on 2/5/2018.
 */

public class OrderItemPresenter implements IOrderItemPresenter {

    private IOrderItemView view;
    private IModel model;
    private IResourceManager rm;
    private Scheduler uiScheduler;
    private Scheduler bgScheduler;
    private final ILogger logger;

    private Order order;

    @Inject
    public OrderItemPresenter(@Named("UI_thread")Scheduler uiScheduler,
                              @Named("BG_thread") Scheduler bgScheduler,
                              IModel model,
                              IResourceManager rm,
                              ILogger logger) {
        this.uiScheduler = uiScheduler;
        this.bgScheduler = bgScheduler;
        this.model = model;
        this.rm = rm;
        this.logger = logger;
    }

    @Override
    public void onCreate(IOrderItemView view) {
        this.view = view;
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
        OrderStatus orderStatus = order.getOrderStatus();
        if (orderStatus.getStatus() == OrderStatus.Status.CANCELLED) {
            this.view.setOrderProgress(this.rm.getOrderCancelledStatusString(), true);
        } else if (orderStatus.getStatus() == OrderStatus.Status.FILLED) {
            this.view.setOrderProgress(this.rm.getOrderCompletedStatusString(), true);
        } else {
            int percentFulfilled = (int)(100*orderStatus.getAmountFulfilled().getAmount() /
                    this.order.getTotalAmount().getAmount());
            String progressText = this.rm.getOrderFulfilledPercentageString(percentFulfilled);
            this.view.setOrderProgress(progressText, true);
        }
    }
}
