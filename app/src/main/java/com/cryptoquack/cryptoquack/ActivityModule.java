package com.cryptoquack.cryptoquack;

import com.cryptoquack.cryptoquack.Presenter.CredentialsPresenter;
import com.cryptoquack.cryptoquack.Presenter.Interfaces.ICredentialsPresenter;
import com.cryptoquack.cryptoquack.Presenter.Interfaces.IOrderItemPresenter;
import com.cryptoquack.cryptoquack.Presenter.Interfaces.ISettingsPresenter;
import com.cryptoquack.cryptoquack.Presenter.Interfaces.ITradingPresenter;
import com.cryptoquack.cryptoquack.Presenter.OrderItemPresenter;
import com.cryptoquack.cryptoquack.Presenter.SettingsPresenter;
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

    @Binds
    public abstract ISettingsPresenter providesSettingsPresenter(
            SettingsPresenter settingsPresenter);

    @Binds
    public abstract ICredentialsPresenter providesCredentialsPresenter(
            CredentialsPresenter credentialsPresentercredentialsPresenter);
    @Binds
    public abstract IOrderItemPresenter providesOrderItemPresenter(
            OrderItemPresenter orderItemPresenter);
}
