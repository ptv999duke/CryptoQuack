package com.cryptoquack.cryptoquack;

import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

/**
 * Created by Duke on 2/17/2018.
 */

@Singleton
@Component(modules={AppModule.class})
public interface AppComponent {

    @Singleton
    public ActivityComponent addActivityComponent();

}
