package com.cryptoquack.cryptoquack;

import com.cryptoquack.cryptoquack.ResourceManager.AndroidResourceManager;
import com.cryptoquack.cryptoquack.ResourceManager.IResourceManager;
import com.cryptoquack.model.IModel;
import com.cryptoquack.model.Model;
import com.cryptoquack.model.ModelModule;
import com.cryptoquack.model.credentials.ICredentialsStore;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

/**
 * Created by Duke on 2/18/2018.
 */

@Module(includes = {AppExternalModule.class, ModelModule.class})
public abstract class AppModule {

    @Binds
    @Singleton
    public abstract IModel providesModel(Model model);

    @Binds
    @Singleton
    public abstract IResourceManager provideResourceManager(AndroidResourceManager rm);

    @Binds
    @Singleton
    public abstract ICredentialsStore providesCredentialsStore(AndroidCredentialsStore
                                                                       androidCredentialsStore);

}
