package com.cryptoquack.cryptoquack;

import com.cryptoquack.cryptoquack.View.CryptoQuackActivity;
import com.cryptoquack.cryptoquack.View.TradingActivity;

import javax.inject.Singleton;

import dagger.Component;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * Created by Duke on 2/18/2018.
 */

@Subcomponent(modules={ActivityModule.class})
public interface ActivityComponent {

    public void inject(TradingActivity activity);

}
