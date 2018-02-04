package com.cryptoquack.model.exchange.Gemini.DTOs;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Duke on 1/27/2018.
 * Corresponds to the GeminiTicker returned by the Gemini API as documented in
 * https://docs.gemini.com/rest-api/#ticker
 */

public class GeminiTicker {

    @Expose
    @SerializedName("bid")
    private double currentBidPrice;

    @Expose
    @SerializedName("ask")
    private double currentAskingPrice;

    @Expose
    @SerializedName("last")
    private double lastPrice;

    public GeminiTicker() {

    }

    public double getCurrentBidPrice() {
        return this.currentBidPrice;
    }

    public double getCurrentAskingPrice() {
        return this.currentAskingPrice;
    }

    public double getLastPrice() {
        return this.lastPrice;
    }

}
