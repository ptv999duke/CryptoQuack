package com.cryptoquack.cryptoquack;

import com.cryptoquack.cryptoquack.View.CredentialsActivity;
import com.cryptoquack.cryptoquack.View.OrderItemView;
import com.cryptoquack.cryptoquack.View.SettingsActivity;
import com.cryptoquack.cryptoquack.View.TradingActivity;

import javax.inject.Singleton;

import dagger.Component;
import dagger.Subcomponent;

/**
 * Created by Duke on 2/18/2018.
 */

@Subcomponent(modules={ActivityModule.class})
public interface ActivityComponent {

    public void inject(TradingActivity activity);

    public void inject(SettingsActivity activity);

    public void inject(CredentialsActivity activity);

    public void inject(OrderItemView activity);
}
