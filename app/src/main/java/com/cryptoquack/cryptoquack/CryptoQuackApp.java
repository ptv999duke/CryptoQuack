package com.cryptoquack.cryptoquack;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import javax.inject.Inject;

/**
 * Created by Duke on 2/15/2018.
 */

public class CryptoQuackApp extends Application {

    private static AppComponent appComponent;
    private static ActivityComponent activityComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        DaggerAppComponent.Builder b = DaggerAppComponent.builder();
        Context context = this.getApplicationContext();
        Resources r = this.getResources();
        AppExternalModule appExternalModule = new AppExternalModule(this, context, r);
        b.appExternalModule(appExternalModule);
        this.appComponent = b.build();
        this.activityComponent = this.appComponent.addActivityComponent();
    }

    public static AppComponent getAppComponent() {
        return CryptoQuackApp.appComponent;
    }

    public static ActivityComponent getActivityComponent() {
        return CryptoQuackApp.activityComponent;
    }
}
