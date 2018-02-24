package com.cryptoquack.model.exchange.Gemini.DTOs;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Duke on 2/23/2018.
 */

public class GeminiOrderStatus {

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
    @SerializedName("exchange")
    private String exchange;

    @Expose
    @SerializedName("price")
    private String price;

    @Expose
    @SerializedName("avg_execution_price")
    private String averageExecutionPrice;

    @Expose
    @SerializedName("side")
    private String action;

    @Expose
    @SerializedName("type")
    private String type;

    @Expose
    @SerializedName("timestampms")
    private long timestampmillis;

    @Expose
    @SerializedName("is_live")
    private boolean isLive;

    @Expose
    @SerializedName("is_cancelled")
    private boolean isCancelled;

    @Expose
    @SerializedName("executed_amount")
    private String executedAmount;

    @Expose
    @SerializedName("remaining_amount")
    private String remainingAmount;

    @Expose
    @SerializedName("original_amount")
    private String originalAmount;

    public String getOrderId() {
        return this.orderId;
    }

    public String getClientOrderId() {
        return this.clientOrderId;
    }

    public String getSymbol() {
        return this.symbol;
    }

    public String getExchange() {
        return this.exchange;
    }

    public String getPrice() {
        return this.price;
    }

    public String getAverageExecutionPrice() {
        return this.averageExecutionPrice;
    }

    public String getAction() {
        return this.action;
    }

    public String getType() {
        return this.type;
    }

    public long getTimestampmillis() {
        return this.timestampmillis;
    }

    public boolean isLive() {
        return this.isLive;
    }

    public boolean isCancelled() {
        return this.isCancelled;
    }

    public String getExecutedAmount() {
        return this.executedAmount;
    }

    public String getRemainingAmount() {
        return this.remainingAmount;
    }

    public String getOriginalAmount() {
        return this.originalAmount;
    }
}
