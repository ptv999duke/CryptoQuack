package com.cryptoquack.cryptoquack.Presenter;

import com.cryptoquack.cryptoquack.IResourceManager;
import com.cryptoquack.cryptoquack.View.ICredentialsActivity;
import com.cryptoquack.model.IModel;
import com.cryptoquack.model.exchange.Exchanges;

/**
 * Created by Duke on 2/11/2018.
 */

public class CredentialsPresenter implements ICredentialsPresenter {

    private IModel model;
    private IResourceManager rm;
    private Exchanges.Exchange exchange;

    public CredentialsPresenter() {

    }

    @Override
    public void onCreate(ICredentialsActivity view,
                         IModel model,
                         IResourceManager rm,
                         Exchanges.Exchange exchange) {
        this.model = model;
        this.rm = rm;
        this.exchange = exchange;
        this.model
    }

    @Override
    public void onCredentialsSaveClick(String accessKey, String secretKey) {

    }
}
