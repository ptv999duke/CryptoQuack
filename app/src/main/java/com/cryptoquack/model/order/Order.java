package com.cryptoquack.model.order;

import com.cryptoquack.model.currency.ExchangeMarket;
import com.cryptoquack.model.currency.MonetaryAmount;
import com.cryptoquack.model.exchange.ExchangeAction;
import com.cryptoquack.model.exchange.Exchanges;

import java.util.Date;

/**
 * Created by Duke on 1/24/2018.
 */

public class Order {

    public static enum OrderStatus {
        NEW,
        PARTIALLY_FILLED,
        FILLED,
        CANCELLED,
    }

    public static enum OrderType {
        LIMIT,
        STOPLIMIT
    }

    private String orderId;
    private OrderType orderType;
    private ExchangeAction.ExchangeActions action;
    private ExchangeMarket market;
    private MonetaryAmount totalAmount;
    private Exchanges.Exchange exchange;
    private double price;
    private MonetaryAmount amountFulfilled;
    private MonetaryAmount amountRemaining;
    private OrderStatus orderStatus;
    private Date orderTime;
    private MonetaryAmount fee;

    public Order() {}

    public Order(String orderId) {
        this.setOrderId(orderId);
    }

    public Order(ExchangeAction.ExchangeActions action,
                 ExchangeMarket market,
                 OrderType orderType,
                 MonetaryAmount totalAmount,
                 double price) {
        this.setAction(action);
        this.setOrderMarketAmount(market, totalAmount);
        this.setOrderType(orderType);
        this.setPrice(price);
    }

    public String getOrderId() {
        return this.orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Exchanges.Exchange getExchange() {
        return this.exchange;
    }

    public void setExchange(Exchanges.Exchange exchange) {
        this.exchange = exchange;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public MonetaryAmount getFee() {
        return this.fee;
    }

    public void setFee(MonetaryAmount fee) {
        this.fee = fee;
    }

    public ExchangeAction.ExchangeActions getAction() {
        return this.action;
    }

    public void setAction(ExchangeAction.ExchangeActions action) {
        this.action = action;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public ExchangeMarket getMarket() {
        return this.market;
    }

    public MonetaryAmount getTotalAmount() {
        return this.totalAmount;
    }

    public void setOrderMarketAmount(ExchangeMarket market, MonetaryAmount amount) {
        if (market == null || amount == null) {
            throw new NullPointerException("Null is not a valid value for market and/or totalAmount.");
        }

        if (!market.getSourceCurrency().equals(amount.getCurrency())) {
            throw new IllegalArgumentException("Market's source currency and totalAmount's currency must" +
                    "be equivalent");
        }

        this.market = market;
        this.totalAmount = amount;
    }

    public OrderStatus getOrderStatus() { return this.orderStatus; }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public MonetaryAmount getAmountFulfilled() { return this.amountFulfilled; }

    public void setAmountFulfilled(MonetaryAmount amount) {
        this.amountFulfilled = amount;
    }

    public MonetaryAmount getAmountRemaining() { return this.amountRemaining; }

    public void setAmountRemaining(MonetaryAmount amount) {
        this.amountRemaining = amount;
    }

    public Date getOrderTime() {
        return this.orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }
}
