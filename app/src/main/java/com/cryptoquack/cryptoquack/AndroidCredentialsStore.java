package com.cryptoquack.cryptoquack;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.security.KeyPairGeneratorSpec;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.support.annotation.RequiresApi;
import android.util.Base64;

import com.cryptoquack.model.credentials.AccessKeyCredentials;
import com.cryptoquack.model.credentials.ICredentialsStore;
import com.cryptoquack.model.exchange.Exchanges;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.util.ArrayList;
import java.util.HashMap;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;

/**
 * Created by Duke on 2/11/2018.
 */

public class AndroidCredentialsStore implements ICredentialsStore {

    public static final String KEY_ALIAS = "com.cryptoquack.credentials_alias_v1";
    private static final String KEYSTORE_PROVIDER = "AndroidKeyStore";
    private static final String PADDING = "RSA/ECB/PKCS1Padding";
    private final Context context;
    private KeyStore keyStore;
    private HashMap<Exchanges.Exchange, String> accessKeyKeyMap;
    private HashMap<Exchanges.Exchange, String> secretKeyKeyMap;
    private final String credentialsPreferenceFileKey;

    public AndroidCredentialsStore(Context context) {
        try {
            this.keyStore = KeyStore.getInstance(this.KEYSTORE_PROVIDER);
        } catch (KeyStoreException ex) {
            int f = 4;
            // Currently using android key store provider. This should not throw. Will need to
            // handle errors better if user specified keystore providers are allowed.
        }

        this.context = context;
        this.accessKeyKeyMap = new HashMap<>();
        this.secretKeyKeyMap = new HashMap<>();
        this.accessKeyKeyMap.put(
                Exchanges.Exchange.GEMINI,
                this.context.getString(R.string.gemini_access_key_key));
        this.secretKeyKeyMap.put(
                Exchanges.Exchange.GEMINI,
                this.context.getString(R.string.gemini_secret_key_key));
        this.credentialsPreferenceFileKey = this.context.getString(
                R.string.credentials_preference_file_key);
    }

    public AndroidCredentialsStore(Context context,
                                   HashMap<Exchanges.Exchange, String> accessKeyKeyMap,
                                   HashMap<Exchanges.Exchange, String> secretKeyKeyMap,
                                   String credentialsPreferenceFileKey) {
        this.context = context;
        this.accessKeyKeyMap = accessKeyKeyMap;
        this.secretKeyKeyMap = secretKeyKeyMap;
        this.credentialsPreferenceFileKey = credentialsPreferenceFileKey;
    }

    private String encryptData(String data) {
        ByteArrayOutputStream stream = null;
        CipherOutputStream cipherStream = null;
        try {
            PublicKey publicKey;
            this.keyStore.load(null);
            if (!this.keyStore.containsAlias(this.KEY_ALIAS)) {
                KeyPairGenerator kpg = KeyPairGenerator.getInstance(
                        KeyProperties.KEY_ALGORITHM_RSA,
                        this.KEYSTORE_PROVIDER);

                KeyGenParameterSpec.Builder builder = new KeyGenParameterSpec.Builder(
                        this.KEY_ALIAS,
                        KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT);
                builder.setDigests(KeyProperties.DIGEST_SHA256, KeyProperties.DIGEST_SHA512);
                builder.setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1);
                KeyGenParameterSpec spec = builder.build();
                kpg.initialize(spec);
                KeyPair keyPair = kpg.generateKeyPair();
                publicKey = keyPair.getPublic();
            } else {
                KeyStore.Entry entry = this.keyStore.getEntry(this.KEY_ALIAS, null);
                if (!(entry instanceof KeyStore.PrivateKeyEntry)) {
                    // TODO: Log and throw
                }

                KeyStore.PrivateKeyEntry privateKey = (KeyStore.PrivateKeyEntry) entry;
                publicKey = privateKey.getCertificate().getPublicKey();
            }

            Cipher input = Cipher.getInstance(this.PADDING);
            input.init(Cipher.ENCRYPT_MODE, publicKey);
            stream = new ByteArrayOutputStream();
            cipherStream = new CipherOutputStream(
                    stream, input);
            byte[] unencryptedBytes = data.getBytes("UTF-8");
            cipherStream.write(unencryptedBytes);
            cipherStream.close();
            byte[] encryptedBytes = stream.toByteArray();
            String base64Encoded = Base64.encodeToString(encryptedBytes, Base64.NO_WRAP);
            return base64Encoded;

        } catch (KeyStoreException  e) {
            // TODO: Handle exceptions better
            throw new RuntimeException();
        } catch (Exception e) {
            throw new RuntimeException();
        } finally {
            try {
                if (cipherStream != null) {
                    cipherStream.close();;
                }

                if (stream != null) {
                    stream.close();
                }
            } catch (Exception e) { }
        }
    }

    public String decryptData(String data) {
        CipherInputStream cipherStream = null;
        try {
            this.keyStore.load(null);
            if (!this.keyStore.containsAlias(this.KEY_ALIAS)) {
                return null;
            }

            KeyStore.Entry entry = this.keyStore.getEntry(this.KEY_ALIAS, null);
            if (!(entry instanceof KeyStore.PrivateKeyEntry)) {
                // TODO: Log and throw
            }

            KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) entry;
            Cipher output = Cipher.getInstance(this.PADDING);
            output.init(Cipher.DECRYPT_MODE, privateKeyEntry.getPrivateKey());
            byte[] encryptedBytes = Base64.decode(data, Base64.NO_WRAP);
            cipherStream = new CipherInputStream(
                    new ByteArrayInputStream(encryptedBytes), output);
            ArrayList<Byte> decryptedBytes = new ArrayList<>();
            int nextByte;
            while ((nextByte = cipherStream.read()) != -1) {
                decryptedBytes.add((byte)nextByte);
            }

            byte[] bytes = new byte[decryptedBytes.size()];
            for (int i = 0; i < decryptedBytes.size(); i++) {
                bytes[i] = decryptedBytes.get(i).byteValue();
            }

            String decryptedData = new String(bytes, "UTF-8");
            return decryptedData;
        } catch (Exception e) {
            // TODO: better error handling
            throw new RuntimeException();
        } finally {
            try {
                if (cipherStream != null) {
                    cipherStream.close();
                }
            } catch (Exception e) { }
        }
    }

    public void saveAccessKeyCredentials(Exchanges.Exchange exchange,
                                         String accessKey,
                                         String secretKey) {
        String encryptedAccessKey = this.encryptData(accessKey);
        String encryptedSecretKey = this.encryptData(secretKey);
        SharedPreferences pref = this.context.getSharedPreferences(
                this.credentialsPreferenceFileKey,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        // TODO: Throw exception if the exchange is not supported.
        String accessKeyKey = this.accessKeyKeyMap.get(exchange);
        String secretKeyKey = this.secretKeyKeyMap.get(exchange);
        editor.putString(accessKeyKey, encryptedAccessKey);
        editor.putString(secretKeyKey, encryptedSecretKey);
        editor.commit();
    }

    @Override
    public AccessKeyCredentials getAccessKeyCredentials(Exchanges.Exchange exchange) {
        SharedPreferences sharedPref = this.context.getSharedPreferences(
                this.credentialsPreferenceFileKey,
                Context.MODE_PRIVATE);
        String accessKeyKey = this.accessKeyKeyMap.get(exchange);
        String secretKeyKey = this.secretKeyKeyMap.get(exchange);
        String encryptedAccessKey = sharedPref.getString(accessKeyKey, null);
        String encryptedSecretKey = sharedPref.getString(secretKeyKey, null);
        if ((encryptedAccessKey == null) || (encryptedSecretKey == null)) {
            return null;
        }

        String accessKey = this.decryptData(encryptedAccessKey);
        String secretKey = this.decryptData(encryptedSecretKey);
        return new AccessKeyCredentials(accessKey, secretKey);
    }
}
