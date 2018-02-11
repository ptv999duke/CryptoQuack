package com.cryptoquack.cryptoquack.Presenter;

import com.cryptoquack.cryptoquack.IResourceManager;
import com.cryptoquack.cryptoquack.View.IOrderItemView;
import com.cryptoquack.cryptoquack.View.ISettingsActivity;
import com.cryptoquack.model.IModel;

/**
 * Created by Duke on 2/10/2018.
 */

public interface ISettingsPresenter {

    public void onCreate(ISettingsActivity view, IModel model, IResourceManager rm);

}
