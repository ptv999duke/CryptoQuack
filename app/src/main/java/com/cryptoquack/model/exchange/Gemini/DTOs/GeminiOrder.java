package com.cryptoquack.model.exchange.Gemini.DTOs;

import com.cryptoquack.model.currency.ExchangeMarket;
import com.cryptoquack.model.currency.MonetaryAmount;
import com.cryptoquack.model.exchange.ExchangeAction;
import com.cryptoquack.model.exchange.Exchanges;
import com.cryptoquack.model.exchange.Gemini.GeminiExchange;
import com.cryptoquack.model.exchange.Gemini.GeminiHelper;
import com.cryptoquack.model.order.Order;
import com.cryptoquack.model.order.OrderStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.Date;

/**
 * Created by Duke on 1/27/2018.
 */

public class GeminiOrder {

    @Expose
    @SerializedName("order_id")
    private String orderId;

    @Expose
    @SerializedName("client_order_id")
    private String clientOrderId;

    @Expose
    @SerializedName("symbol")
    private String symbol;

    @Expose
    @SerializedName("price")
    private String price;

    @Expose
    @SerializedName("side")
    private String action;

    @Expose
    @SerializedName("executed_amount")
    private String executedAmount;

    @Expose
    @SerializedName("remaining_amount")
    private String remainingAmount;

    @Expose
    @SerializedName("original_amount")
    private String originalAmount;

    @Expose
    @SerializedName("is_live")
    private boolean isLive;

    @Expose
    @SerializedName("is_cancelled")
    private boolean isCancelled;

    @Expose
    @SerializedName("timestampms")
    private long timestampmillis;

    public Order convertToOrder() {
        Order order = new Order(this.orderId);
        ExchangeMarket market = GeminiHelper.convertSymbolToMarket(this.symbol);
        double totalAmountDouble = Double.parseDouble(this.originalAmount);
        MonetaryAmount totalAmount = new MonetaryAmount(totalAmountDouble,
                market.getSourceCurrency());
        order.setOrderMarketAmount(market, totalAmount);
        double priceDouble = Double.parseDouble(this.price);
        order.setPrice(priceDouble);
        ExchangeAction.ExchangeActions action = GeminiHelper.convertActionSymbolToAction(this.action);
        order.setAction(action);
        double executedAmountDouble = Double.parseDouble(this.executedAmount);
        MonetaryAmount executedAmount = new MonetaryAmount(executedAmountDouble,
                market.getSourceCurrency());
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setAmountFulfilled(executedAmount);
        double remainingAmountDouble = Double.parseDouble(this.remainingAmount);
        MonetaryAmount remainingAmount = new MonetaryAmount(remainingAmountDouble,
                market.getSourceCurrency());
        orderStatus.setAmountRemaining(remainingAmount);
        orderStatus.setTotalAmount(totalAmount);
        if (this.isLive) {
            if (executedAmountDouble > 0.0) {
                orderStatus.setStatus(OrderStatus.Status.PARTIALLY_FILLED);
            } else {
                orderStatus.setStatus(OrderStatus.Status.NEW);
            }
        } else if (this.isCancelled) {
            orderStatus.setStatus(OrderStatus.Status.CANCELLED);
        } else {
            orderStatus.setStatus(OrderStatus.Status.FILLED);
        }

        order.setOrderStatus(orderStatus);
        order.setOrderType(Order.OrderType.LIMIT);
        Date orderDate = new Date(this.timestampmillis);
        order.setOrderTime(orderDate);
        return order;
    }
}
