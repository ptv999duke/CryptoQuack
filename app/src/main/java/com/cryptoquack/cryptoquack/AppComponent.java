package com.cryptoquack.cryptoquack;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Duke on 2/17/2018.
 */

@Singleton
@Component(modules={AppModule.class})
public interface AppComponent {

    @Singleton
    public ActivityComponent addActivityComponent();

}
