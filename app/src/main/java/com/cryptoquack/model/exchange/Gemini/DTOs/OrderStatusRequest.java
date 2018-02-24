package com.cryptoquack.model.exchange.Gemini.DTOs;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Duke on 2/23/2018.
 */

public class OrderStatusRequest extends BaseSignedGeminiRequest {

    @Expose
    @SerializedName("order_id")
    private String orderId;

    public void OrderStatusRequest(String orderId) {
        this.orderId = orderId;
    }
}
