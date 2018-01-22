package com.cryptoquack.model.credentials;

/**
 * Created by Duke on 1/21/2018.
 */

public class AccessKeyCredentials implements ICredentials {

    private String myAccessKey;
    private String mySecretKey;

    public AccessKeyCredentials(String accessKey, String secretKey) {
        this.myAccessKey = accessKey;
        this.mySecretKey = secretKey;
    }

    @Override
    public String Sign(String payload) {
        return "";
    }
}
