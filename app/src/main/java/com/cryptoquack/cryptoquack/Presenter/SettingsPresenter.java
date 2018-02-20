package com.cryptoquack.cryptoquack.Presenter;

import com.cryptoquack.cryptoquack.Presenter.Interfaces.ISettingsPresenter;
import com.cryptoquack.cryptoquack.ResourceManager.IResourceManager;
import com.cryptoquack.cryptoquack.View.Interfaces.ISettingsActivity;
import com.cryptoquack.model.IModel;

import javax.inject.Inject;

/**
 * Created by Duke on 2/10/2018.
 */

public class SettingsPresenter implements ISettingsPresenter {


    private IModel model;
    private ISettingsActivity view;
    private IResourceManager rm;

    @Inject
    public SettingsPresenter(IModel model, IResourceManager rm) {
        this.model = model;
        this.rm = rm;
    }

    @Override
    public void onCreate(ISettingsActivity view) {
        this.view = view;
    }
}
