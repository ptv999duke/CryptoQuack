package com.cryptoquack.cryptoquack.Presenter;

import com.cryptoquack.cryptoquack.Presenter.Interfaces.ICredentialsPresenter;
import com.cryptoquack.cryptoquack.ResourceManager.IResourceManager;
import com.cryptoquack.cryptoquack.View.Interfaces.ICredentialsActivity;
import com.cryptoquack.model.IModel;
import com.cryptoquack.model.credentials.AccessKeyCredentials;
import com.cryptoquack.model.exchange.Exchanges;

import javax.inject.Inject;

/**
 * Created by Duke on 2/11/2018.
 */

public class CredentialsPresenter implements ICredentialsPresenter {

    private IModel model;
    private IResourceManager rm;
    private Exchanges.Exchange exchange;
    private ICredentialsActivity view;

    @Inject
    public CredentialsPresenter(IModel model, IResourceManager rm) {
        this.model = model;
        this.rm = rm;
    }

    @Override
    public void onCreate(ICredentialsActivity view,
                         Exchanges.Exchange exchange) {
        this.view = view;
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
