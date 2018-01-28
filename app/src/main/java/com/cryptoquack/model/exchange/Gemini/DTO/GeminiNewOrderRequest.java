package com.cryptoquack.model.exchange.Gemini.DTO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Duke on 1/27/2018.
 */

public class GeminiNewOrderRequest extends BaseSignedGeminiRequest {

    @Expose
    @SerializedName("orderId")
    private String orderId;

    @Expose
    @SerializedName("symbol")
    private String symbol;

    @Expose
    @SerializedName("amount")
    private double amount;

    @Expose(serialize = false)
    private Double price;

    @Expose
    @SerializedName("price")
    private String priceString;

    @Expose
    @SerializedName("side")
    private String action;

    public GeminiNewOrderRequest(String symbol, double amount, Double price, String action) {
        this(null, symbol, amount, price, action);
    }

    public GeminiNewOrderRequest(String orderId, String symbol, double amount, Double price,
                                 String action) {
        this.orderId = orderId;
        this.symbol = symbol;
        this.amount = amount;
        this.setPrice(price);
        this.action = action;
    }

    public Double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
        this.priceString = this.price.toString();
    }

    public String getOrderId() {
        return orderId;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getAmount() {
        return amount;
    }

    public String getAction() {
        return action;
    }
}
