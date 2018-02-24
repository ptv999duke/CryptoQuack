package com.cryptoquack.model.order;

import com.cryptoquack.model.currency.ExchangeMarket;
import com.cryptoquack.model.currency.MonetaryAmount;
import com.cryptoquack.model.exchange.Exchanges;

/**
 * Created by Duke on 2/23/2018.
 */

public class OrderStatus {

    public static enum Status {
        NEW,
        PARTIALLY_FILLED,
        FILLED,
        CANCELLED,
    }

    private Status status;
    private MonetaryAmount totalAmount;
    private MonetaryAmount amountFulfilled;
    private MonetaryAmount amountRemaining;

    public OrderStatus.Status getStatus() {
        return this.status;
    }

    public void setStatus(OrderStatus.Status status) {
        this.status = status;
    }

    public MonetaryAmount getAmountFulfilled() {
        return this.amountFulfilled;
    }

    public void setAmountFulfilled(MonetaryAmount amount) {
        this.amountFulfilled = amount;
    }

    public MonetaryAmount getAmountRemaining() {
        return this.amountRemaining;
    }

    public void setAmountRemaining(MonetaryAmount amount) {
        this.amountRemaining = amount;
    }

    public MonetaryAmount getTotalAmount(MonetaryAmount amount) {
        return this.totalAmount;
    }

    public void setTotalAmount(MonetaryAmount amount) {
        this.totalAmount = amount;
    }
}
