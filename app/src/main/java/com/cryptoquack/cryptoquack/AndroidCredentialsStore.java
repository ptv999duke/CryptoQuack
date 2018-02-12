package com.cryptoquack.cryptoquack;

import android.os.Build;
import android.security.KeyPairGeneratorSpec;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.support.annotation.RequiresApi;

import com.cryptoquack.model.credentials.AccessKeyCredentials;
import com.cryptoquack.model.credentials.ICredentialsStore;
import com.cryptoquack.model.exchange.Exchanges;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;

/**
 * Created by Duke on 2/11/2018.
 */

public class AndroidCredentialsStore implements ICredentialsStore {

    public static final String KEY_ALIAS = "com.cryptoquack.credentials_alias_v1";
    private static final String KEYSTORE_PROVIDER = "AndroidKeyStore";
    private KeyStore keyStore;

    public AndroidCredentialsStore() {
        try {
            this.keyStore = KeyStore.getInstance(this.KEYSTORE_PROVIDER);
        } catch (KeyStoreException ex) {
            // Currently using android key store provider. This should not throw. Will need to
            // handle errors better if user specified keystore providers are allowed.
        }
    }

    private void getEncryptionKey() {
        try {
            if (!this.keyStore.containsAlias(this.KEY_ALIAS)) {
                KeyPairGenerator kpg = KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_HMAC_SHA512,
                        this.KEYSTORE_PROVIDER);

                KeyGenParameterSpec spec = new KeyGenParameterSpec.Builder(
                        this.KEY_ALIAS,
                        KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                        .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                        .build();
                kpg.initialize(spec);
                KeyPair keyPair = kpg.generateKeyPair();
                keyPair.
            } else {
                this.keyStore.getEntry(this.KEY_ALIAS, null);
            }
        } catch (KeyStoreException  e) {

        } catch (Exception e) {

        }
    }

    public void saveAccessKeyCredentials(Exchanges.Exchange exchange) {

    }

    @Override
    public AccessKeyCredentials getAccessKeyCredentials(Exchanges.Exchange exchange) {
        return null;
    }
}
