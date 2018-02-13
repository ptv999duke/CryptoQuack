package com.cryptoquack.cryptoquack.Presenter;

import com.cryptoquack.cryptoquack.IResourceManager;
import com.cryptoquack.cryptoquack.View.ICredentialsActivity;
import com.cryptoquack.model.IModel;
import com.cryptoquack.model.credentials.AccessKeyCredentials;
import com.cryptoquack.model.exchange.Exchanges;

/**
 * Created by Duke on 2/11/2018.
 */

public class CredentialsPresenter implements ICredentialsPresenter {

    private IModel model;
    private IResourceManager rm;
    private Exchanges.Exchange exchange;
    private ICredentialsActivity view;

    public CredentialsPresenter() {

    }

    @Override
    public void onCreate(ICredentialsActivity view,
                         IModel model,
                         IResourceManager rm,
                         Exchanges.Exchange exchange) {
        this.view = view;
        this.model = model;
        this.rm = rm;
        this.exchange = exchange;
        AccessKeyCredentials credentials = this.model.loadCredentials(this.exchange);
        if (credentials != null) {
            this.view.setAccessKeyText(credentials.getAccessKey());
            this.view.setSecretKeyText(credentials.getSecretKey());
        }
    }

    @Override
    public void onCredentialsSaveClick(String accessKey, String secretKey) {
        this.model.saveCredentials(this.exchange,
                accessKey,
                secretKey,
                false);
    }
}
