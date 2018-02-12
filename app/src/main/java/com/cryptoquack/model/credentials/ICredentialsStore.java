package com.cryptoquack.model.credentials;

import com.cryptoquack.model.exchange.Exchanges;

/**
 * Created by Duke on 2/11/2018.
 */

public interface ICredentialsStore {

    public AccessKeyCredentials getAccessKeyCredentials(Exchanges.Exchange exchange);

}
