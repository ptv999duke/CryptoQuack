package com.cryptoquack.model.exchange.Gemini.DTO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Duke on 1/27/2018.
 * Corresponds to the Ticker returned by the Gemini API as documented in
 * https://docs.gemini.com/rest-api/#ticker
 */

public class Ticker {

    @Expose
    @SerializedName("bid")
    public double currentBidPrice;

    @Expose
    @SerializedName("ask")
    public double currentAskingPrice;

    @Expose
    @SerializedName("last")
    public double lastPrice;
}
