package com.cryptoquack.model.order;

import com.cryptoquack.model.currency.ExchangeMarket;
import com.cryptoquack.model.currency.MonetaryAmount;
import com.cryptoquack.model.exchange.ExchangeAction;

/**
 * Created by Duke on 1/24/2018.
 */

public class Order {

    public static enum OrderType {
        LIMIT,
        STOPLIMIT
    }

    private String orderId;
    private OrderType orderType;
    private ExchangeAction.ExchangeActions action;
    private ExchangeMarket market;
    private MonetaryAmount amount;
    private double price;

    private MonetaryAmount fee;

    public Order(Order order) {
        this(order.getAction(), order.getMarket(), order.getOrderType(), order.amount,
                order.getPrice());
        this.orderId = order.getOrderId();
    }

    public Order() {}

    public Order(String orderId) {
        this.setOrderId(orderId);
    }

    public Order(ExchangeAction.ExchangeActions action, ExchangeMarket market, OrderType orderType,
                 MonetaryAmount amount, double price) {
        this.setAction(action);
        this.setOrderMarketAmount(market, amount);
        this.setOrderType(orderType);
        this.setPrice(price);
    }

    public String getOrderId() {
        return this.orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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

    public MonetaryAmount getAmount() {
        return amount;
    }

    public void setOrderMarketAmount(ExchangeMarket market, MonetaryAmount amount) {
        if (market == null || amount == null) {
            throw new NullPointerException("Null is not a valid value for market and/or amount.");
        }

        if (!market.getSourceCurrency().equals(amount.getCurrency())) {
            throw new IllegalArgumentException("Market's source currency and amount's currency must" +
                    "be equivalent");
        }

        this.market = market;
        this.amount = amount;
    }

}
