package com.cryptoquack.cryptoquack;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import com.cryptoquack.model.logger.ILogger;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by Duke on 2/15/2018.
 */

@Module()
public class AppExternalModule {

    private final Scheduler uiScheduler;
    private final Scheduler backgroundScheduler;
    private final Timber.Tree tree;
    private Application application;
    private Context context;
    private Resources r;

    public AppExternalModule(Application application,
                             Context context,
                             Resources r,
                             Timber.Tree tree) {
        this.application = application;
        this.context = context;
        this.r = r;
        this.uiScheduler = AndroidSchedulers.mainThread();
        this.backgroundScheduler = Schedulers.io();
        this.tree = tree;
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

    @Provides
    @Singleton
    public ILogger providesLogger() {
        return new AndroidLogger(this.tree);
    }
}
