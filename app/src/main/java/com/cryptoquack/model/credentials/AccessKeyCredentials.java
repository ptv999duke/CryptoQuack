package com.cryptoquack.model.credentials;

import java.security.InvalidParameterException;

/**
 * Created by Duke on 1/21/2018.
 */

public class AccessKeyCredentials implements ICredentials {

    private final String accessKey;
    private final String secretKey;

    public AccessKeyCredentials(String accessKey, String secretKey) {
        if (accessKey == null || secretKey == null) {
            throw new InvalidParameterException("Access and secret key cannot be null");
        }

        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    public String getSecretKey() { return this.secretKey; }

    public String getAccessKey() {
        return this.accessKey;
    }

    @Override
    public String Sign(String payload) {
        return "";
    }
}
