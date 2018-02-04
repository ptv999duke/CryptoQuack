package com.cryptoquack.model.exchange.Gemini.DTOs;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Duke on 1/27/2018.
 */

public class GeminiNewOrderRequest extends BaseSignedGeminiRequest {

    public static final String EXCHANGE_LIMIT_ORDER_TYPE = "exchange limit";

    @Expose
    @SerializedName("client_order_id")
    private String orderId;

    @Expose
    @SerializedName("symbol")
    private String symbol;

    private double amount;

    @Expose(serialize = true)
    @SerializedName("amount")
    private String amountString;

    private double price;

    @Expose
    @SerializedName("price")
    private String priceString;

    @Expose
    @SerializedName("side")
    private String action;

    @Expose
    @SerializedName("type")
    private String type;

    public GeminiNewOrderRequest() {
        this.type = GeminiNewOrderRequest.EXCHANGE_LIMIT_ORDER_TYPE;
    }

    public GeminiNewOrderRequest(String symbol, double amount, double price, String action) {
        this(null, symbol, amount, price, action);
    }

    public GeminiNewOrderRequest(String orderId, String symbol, double amount, Double price,
                                 String action) {
        this();
        this.orderId = orderId;
        this.symbol = symbol;
        this.setAmount(amount);
        this.setPrice(price);
        this.action = action;
    }

    public double getPrice() {
        return this.price;
    }

    private void setPrice(double price) {
        this.price = price;
        this.priceString = Double.toString(price);
    }

    public double getAmount() {
        return this.amount;
    }

    private void setAmount(double amount) {
        this.amount = amount;
        this.amountString = Double.toString(amount);
    }

    public String getOrderId() {
        return orderId;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getAction() {
        return action;
    }
}
