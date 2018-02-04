package com.cryptoquack.model.exchange.Gemini;

import com.cryptoquack.model.exchange.Gemini.DTOs.GeminiNewOrderRequest;
import com.cryptoquack.model.exchange.Gemini.DTOs.GeminiOrder;
import com.cryptoquack.model.exchange.Gemini.DTOs.GeminiTicker;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Duke on 1/25/2018.
 */

public interface GeminiApiClientV1 {

    @GET("/v1/pubticker/{symbol}")
    public Single<GeminiTicker> getPubTicker(@Path("symbol") String symbol);

    @POST("/v1/order/new")
    public Single<GeminiOrder> newOrder(@Body GeminiNewOrderRequest newOrderRequest);

}
