package com.cryptoquack.model.exchange.Gemini.DTOs;

import com.cryptoquack.model.currency.ExchangeMarket;
import com.cryptoquack.model.currency.MonetaryAmount;
import com.cryptoquack.model.exchange.ExchangeAction;
import com.cryptoquack.model.exchange.Gemini.GeminiHelper;
import com.cryptoquack.model.order.Order;
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
        order.setAmountFulfilled(executedAmount);
        double remainingAmountDouble = Double.parseDouble(this.remainingAmount);
        MonetaryAmount remainingAmount = new MonetaryAmount(remainingAmountDouble,
                market.getSourceCurrency());
        order.setAmountRemaining(remainingAmount);
        if (this.isLive) {
            if (executedAmountDouble > 0.0) {
                order.setOrderStatus(Order.OrderStatus.PARTIALLY_FILLED);
            } else {
                order.setOrderStatus(Order.OrderStatus.NEW);
            }
        } else if (this.isCancelled) {
            order.setOrderStatus(Order.OrderStatus.CANCELLED);
        } else {
            order.setOrderStatus(Order.OrderStatus.FILLED);
        }

        order.setOrderType(Order.OrderType.LIMIT);
        Date orderDate = new Date(this.timestampmillis);
        order.setOrderTime(orderDate);
        return order;
    }
}
