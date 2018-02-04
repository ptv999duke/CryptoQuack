package com.cryptoquack.model.exchange.Gemini.DTOs;

import com.cryptoquack.model.exchange.BaseExchange;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.Assert;

import java.util.HashSet;
import java.util.Iterator;

/**
 * Created by Duke on 2/4/2018.
 */

public class GeminiNewOrderRequestTests {

    @Test
    public void gson_Serialization_Success() throws JSONException {
        String symbol = "mock_symbol";
        double amount = 0.0;
        double price = 0.0;
        String actionSymbol = "mock_action";
        GeminiNewOrderRequest geminiOrderRequest = new GeminiNewOrderRequest(symbol,
                amount, price, actionSymbol);
        String json = BaseExchange.GSON.toJson(geminiOrderRequest);
        JSONObject jsonObj = new JSONObject(json);

        HashSet<String> expectedKeys = new HashSet<>();
        expectedKeys.add("symbol");
        expectedKeys.add("amount");
        expectedKeys.add("price");
        expectedKeys.add("side");
        expectedKeys.add("type");
        HashSet<String> jsonKeys = new HashSet<>();
        Iterator<String> iter = jsonObj.keys();
        while (iter.hasNext()) {
            jsonKeys.add((String) iter.next());
        }

        Assert.assertEquals(jsonKeys, expectedKeys);
    }
}
