package com.cryptoquack.model.exchange.Gemini.DTO;

/**
 * Created by Duke on 1/27/2018.
 */

public abstract class BaseSignedGeminiRequest {

    protected int nonce;
    protected String requestName;

    public void setNonce(int nonce) {
        this.nonce = nonce;
    }

    public void setRequestName(String requestName) {
        this.requestName = requestName;
    }
}
