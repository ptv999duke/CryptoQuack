package com.cryptoquack.cryptoquack;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.security.KeyPairGeneratorSpec;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.support.annotation.RequiresApi;
import android.util.Base64;

import com.cryptoquack.exceptions.UnavailableExchangeException;
import com.cryptoquack.model.credentials.AccessKeyCredentials;
import com.cryptoquack.model.credentials.ICredentialsStore;
import com.cryptoquack.model.exchange.Exchanges;
import com.cryptoquack.model.logger.ILogger;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.UnrecoverableEntryException;
import java.security.interfaces.RSAPrivateKey;
import java.util.ArrayList;
import java.util.HashMap;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.inject.Inject;

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
    private ILogger logger;

    @Inject
    public AndroidCredentialsStore(Context context, ILogger logger) {
        this.logger = logger;
        try {
            this.keyStore = KeyStore.getInstance(this.KEYSTORE_PROVIDER);
        } catch (KeyStoreException ex) {
            this.logger.e(ex, "Error when instantiating AndroidCredentialsStore");
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
        this.accessKeyKeyMap.put(
                Exchanges.Exchange.GEMINI_SANDBOX,
                this.context.getString(R.string.gemini_sandbox_access_key_key));
        this.secretKeyKeyMap.put(
                Exchanges.Exchange.GEMINI_SANDBOX,
                this.context.getString(R.string.gemini_sandbox_secret_key_key));
        this.credentialsPreferenceFileKey = this.context.getString(
                R.string.credentials_preference_file_key);
    }

    public AndroidCredentialsStore(Context context,
                                   HashMap<Exchanges.Exchange, String> accessKeyKeyMap,
                                   HashMap<Exchanges.Exchange, String> secretKeyKeyMap,
                                   String credentialsPreferenceFileKey,
                                   ILogger logger) {
        this.context = context;
        this.accessKeyKeyMap = accessKeyKeyMap;
        this.secretKeyKeyMap = secretKeyKeyMap;
        this.credentialsPreferenceFileKey = credentialsPreferenceFileKey;
        this.logger = logger;
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
                    RuntimeException e = new RuntimeException("Error when getting private key while encrypting. Key entry is of wrong type");
                    this.logger.e(e);
                    throw e;
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

        } catch (java.io.IOException | NoSuchAlgorithmException | NoSuchPaddingException
                | InvalidKeyException | InvalidAlgorithmParameterException
                | java.security.cert.CertificateException | KeyStoreException
                | NoSuchProviderException | UnrecoverableEntryException e) {
            this.logger.e(e, "Error when encrypting key");
            throw new RuntimeException(e);
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
                RuntimeException e = new RuntimeException("Error when getting private key while encrypting. Key entry is of wrong type");
                throw e;
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
        } catch (java.io.IOException | NoSuchAlgorithmException | NoSuchPaddingException
                | InvalidKeyException | java.security.cert.CertificateException | KeyStoreException
                | UnrecoverableEntryException e) {
            this.logger.e(e, "Error when decrypting key");
            throw new RuntimeException(e);
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
        String accessKeyKey = this.accessKeyKeyMap.get(exchange);
        String secretKeyKey = this.secretKeyKeyMap.get(exchange);
        if (accessKeyKey == null || secretKeyKey == null) {
            throw new UnavailableExchangeException(exchange, "Error when saving credentials");
        }

        String encryptedAccessKey = this.encryptData(accessKey);
        String encryptedSecretKey = this.encryptData(secretKey);
        SharedPreferences pref = this.context.getSharedPreferences(
                this.credentialsPreferenceFileKey,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(accessKeyKey, encryptedAccessKey);
        editor.putString(secretKeyKey, encryptedSecretKey);
        editor.commit();
    }

    @Override
    public AccessKeyCredentials getAccessKeyCredentials(Exchanges.Exchange exchange) {
        SharedPreferences sharedPref = this.context.getSharedPreferences(
                this.credentialsPreferenceFileKey,
                Context.MODE_PRIVATE);
        if (!this.accessKeyKeyMap.containsKey(exchange) ||
                !this.secretKeyKeyMap.containsKey(exchange)) {
            throw new UnavailableExchangeException(exchange, "Error when getting credentials");
        }

        String accessKeyKey = this.accessKeyKeyMap.get(exchange);
        String secretKeyKey = this.secretKeyKeyMap.get(exchange);
        if (accessKeyKey == null || secretKeyKey == null) {
            return null;
        }

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
