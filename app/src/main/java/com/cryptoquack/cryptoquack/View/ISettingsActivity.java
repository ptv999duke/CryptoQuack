package com.cryptoquack.cryptoquack.View;

import com.cryptoquack.model.exchange.Exchanges;

import java.util.ArrayList;

/**
 * Created by Duke on 2/8/2018.
 */

public interface ISettingsActivity {

    public void setAvailableExchanges(Exchanges.Exchange[] availableExchanges);
}
