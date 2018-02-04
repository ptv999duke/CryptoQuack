package com.cryptoquack.exceptions;

import com.cryptoquack.model.order.Order;

/**
 * Created by Duke on 2/3/2018.
 */

public class UnavailableOrderTypeException extends CryptoQuackException {

    private Order.OrderType orderType;

    public UnavailableOrderTypeException(Order.OrderType orderType) {
        this(orderType, null);
    }

    public UnavailableOrderTypeException(Order.OrderType orderType, String message) {
        this(orderType, message, null);
    }

    public UnavailableOrderTypeException(Order.OrderType orderType, String message,
                                      Exception e) {
        super(message, e);
        this.orderType = orderType;
    }

    public Order.OrderType getOrderType() {
        return this.orderType;
    }
}
