package com.cryptoquack.model.exchange.Gemini.DTOs;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Duke on 1/27/2018.
 */

public abstract class BaseSignedGeminiRequest {

    @Expose
    @SerializedName("request")
    protected String requestName = null;

    public void setRequestName(String requestName) {
        this.requestName = requestName;
    }
}
