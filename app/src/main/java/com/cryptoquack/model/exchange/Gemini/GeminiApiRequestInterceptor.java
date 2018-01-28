package com.cryptoquack.model.exchange.Gemini;

import android.util.Base64;

import com.cryptoquack.model.Helper;
import com.cryptoquack.model.credentials.AccessKeyCredentials;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * Created by Duke on 1/25/2018.
 */

public class GeminiApiRequestInterceptor implements Interceptor {

    private static final String UTF8_INDICATOR = "UTF8";
    private static final String ENCRYPTION_SCHEME = "HmacSHA384";

    private static final String API_KEY_HEADER = "X-GEMINI-APIKEY";
    private static final String PAYLOAD_HEADER = "X-GEMINI-PAYLOAD";
    private static final String SIGNATURE_HEADER = "X-GEMINI-SIGNATURE";
    private static final String NONCE_KEY = "nonce";
    private static final String REQUEST_KEY = "nonce";

    private AccessKeyCredentials accessKeyCredentials;
    private SecretKeySpec signingKey;

    public GeminiApiRequestInterceptor(AccessKeyCredentials credentials) {
        this.setAccessKeyCredentials(credentials);
    }

    public void setAccessKeyCredentials(AccessKeyCredentials credentials) {
        this.accessKeyCredentials = credentials;
        SecretKeySpec signingKey = new SecretKeySpec(
                this.accessKeyCredentials.getSecretKey().getBytes(),
                this.ENCRYPTION_SCHEME);
        this.signingKey = signingKey;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        if (originalRequest.method().equals("POST")) {
            Request.Builder builder = originalRequest.newBuilder();
            RequestBody originalBody = originalRequest.body();
            Buffer buffer = new Buffer();
            originalBody.writeTo(buffer);
            String originalBodyString = new String(buffer.readByteArray(), this.UTF8_INDICATOR);
            String fullBodyString;
            try {
                fullBodyString = this.addNecessaryFields(originalBodyString,
                        originalRequest.url().encodedPath());
            } catch (JSONException e) {
                throw new IOException("Invalid JSON body", e);
            }

            byte[] fullBodyBytes = fullBodyString.getBytes(this.UTF8_INDICATOR);
            byte[] encodedPayload = Base64.encode(fullBodyBytes, Base64.DEFAULT);
            String encodedPayloadString = new String(encodedPayload,
                    GeminiApiRequestInterceptor.UTF8_INDICATOR);
            String signedPayload;
            try {
                signedPayload = this.signPayload(encodedPayload);
            } catch (NoSuchAlgorithmException | InvalidKeyException e){
                throw new IOException("Error when signing request", e);
            }

            builder.header("Content-Type", "text/plain");
            builder.header("Content-Length", "0");
            builder.header(this.API_KEY_HEADER,
                    this.accessKeyCredentials.getAccessKey());
            builder.delete(originalBody);
            builder.header(this.API_KEY_HEADER,
                    this.accessKeyCredentials.getAccessKey());
            builder.header(this.PAYLOAD_HEADER, encodedPayloadString);
            builder.header(this.SIGNATURE_HEADER, signedPayload);
            return chain.proceed(builder.build());
        } else {
            return chain.proceed(originalRequest);
        }
    }

    private String addNecessaryFields(String bodyString, String relativeUrl) throws JSONException {
        JSONObject jsonObj = new JSONObject(bodyString);
        if (!jsonObj.has(this.NONCE_KEY)) {
            long epochTime = System.currentTimeMillis();
            jsonObj.put(this.NONCE_KEY, epochTime);
        }

        if (!jsonObj.has(this.REQUEST_KEY)) {
            long epochTime = System.currentTimeMillis();
            jsonObj.put(this.REQUEST_KEY, relativeUrl);
        }

        new Gson().toJson(jsonObj);
        return jsonObj.toString();
    }

    private String signPayload(byte[] payload) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance(this.ENCRYPTION_SCHEME);
        mac.init(this.signingKey);
        return Helper.bytesToHex(mac.doFinal(payload));
    }
}
