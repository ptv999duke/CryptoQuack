package com.cryptoquack.model.exchange.Gemini;

import com.cryptoquack.model.exchange.Gemini.DTO.Ticker;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Duke on 1/25/2018.
 */

public interface GeminiApiClientV1 {

    @GET("/v1/pubticker/{symbol}")
    public Call<Ticker> getPubTicker(@Path("symbol") String symbol);

}
