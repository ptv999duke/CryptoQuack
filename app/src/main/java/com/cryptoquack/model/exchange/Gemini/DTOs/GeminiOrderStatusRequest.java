package com.cryptoquack.model.exchange.Gemini.DTOs;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Duke on 2/23/2018.
 */

public class GeminiOrderStatusRequest extends BaseSignedGeminiRequest {

    @Expose
    @SerializedName("order_id")
    private String orderId;

    public GeminiOrderStatusRequest(String orderId) {
        this.orderId = orderId;
    }
}
