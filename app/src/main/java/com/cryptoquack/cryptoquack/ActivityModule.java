package com.cryptoquack.cryptoquack;

import com.cryptoquack.cryptoquack.Presenter.Interfaces.ITradingPresenter;
import com.cryptoquack.cryptoquack.Presenter.TradingPresenter;
import com.cryptoquack.cryptoquack.ResourceManager.AndroidResourceManager;
import com.cryptoquack.cryptoquack.ResourceManager.IResourceManager;
import com.cryptoquack.model.IModel;
import com.cryptoquack.model.Model;
import com.cryptoquack.model.credentials.ICredentialsStore;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

/**
 * Created by Duke on 2/15/2018.
 */

@Singleton
@Module()
public abstract class ActivityModule {

    @Binds
    public abstract ITradingPresenter providesTradingPresenter(TradingPresenter tradingPresenter);

}
