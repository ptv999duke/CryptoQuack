package com.cryptoquack.cryptoquack.Presenter;

import com.cryptoquack.cryptoquack.IResourceManager;
import com.cryptoquack.cryptoquack.View.ICredentialsActivity;
import com.cryptoquack.model.IModel;
import com.cryptoquack.model.exchange.Exchanges;

/**
 * Created by Duke on 2/11/2018.
 */

public interface ICredentialsPresenter {

    public void onCreate(ICredentialsActivity view,
                         IModel model,
                         IResourceManager rm,
                         Exchanges.Exchange exchange);

    public void onCredentialsSaveClick(String accessKey, String secretKey);
}
