package com.cryptoquack.cryptoquack.Presenter.Interfaces;

import com.cryptoquack.cryptoquack.ResourceManager.IResourceManager;
import com.cryptoquack.cryptoquack.View.Interfaces.ICredentialsActivity;
import com.cryptoquack.model.IModel;
import com.cryptoquack.model.exchange.Exchanges;

/**
 * Created by Duke on 2/11/2018.
 */

public interface ICredentialsPresenter {

    public void onCreate(ICredentialsActivity view,
                         Exchanges.Exchange exchange);

    public void onCredentialsSaveClick(String accessKey, String secretKey);
}
