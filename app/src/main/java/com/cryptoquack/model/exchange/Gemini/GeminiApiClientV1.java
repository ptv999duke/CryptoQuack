package com.cryptoquack.model.exchange.Gemini;

import com.cryptoquack.model.exchange.Gemini.DTO.GeminiNewOrderRequest;
import com.cryptoquack.model.exchange.Gemini.DTO.GeminiOrder;
import com.cryptoquack.model.exchange.Gemini.DTO.GeminiTicker;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Duke on 1/25/2018.
 */

public interface GeminiApiClientV1 {

    @GET("/v1/pubticker/{symbol}")
    public Call<GeminiTicker> getPubTicker(@Path("symbol") String symbol);

    @POST("/v1/order/new")
    public Call<GeminiOrder> newOrder(@Body GeminiNewOrderRequest newOrderRequest);

}
