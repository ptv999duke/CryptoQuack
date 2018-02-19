package com.cryptoquack.cryptoquack;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import com.cryptoquack.cryptoquack.ResourceManager.AndroidResourceManager;
import com.cryptoquack.cryptoquack.ResourceManager.IResourceManager;
import com.cryptoquack.model.IModel;
import com.cryptoquack.model.Model;
import com.cryptoquack.model.credentials.ICredentials;
import com.cryptoquack.model.credentials.ICredentialsStore;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Duke on 2/15/2018.
 */

@Module(includes = ActivityModule.class)
public class AppExternalModule {

    private final Scheduler uiScheduler;
    private final Scheduler backgroundScheduler;
    private Application application;
    private Context context;
    private Resources r;

    public AppExternalModule(Application application, Context context, Resources r) {
        this.application = application;
        this.context = context;
        this.r = r;
        this.uiScheduler = AndroidSchedulers.mainThread();
        this.backgroundScheduler = Schedulers.io();
    }

    @Provides
    @Singleton
    public Application providesApplication() {
        return this.application;
    }

    @Provides
    @Singleton
    public Context providesContext() {
        return this.context;
    }

    @Provides
    @Singleton
    @Named("UI_thread")
    public Scheduler providesUIScheduler() {
        return this.uiScheduler;
    }

    @Provides
    @Singleton
    @Named("BG_thread")
    public Scheduler providesBackgroundScheduler() {
        return this.backgroundScheduler;
    }

    @Provides
    @Singleton
    public Resources providesResources() {
        return this.r;
    }
}
