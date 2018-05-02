package com.cryptoquack.cryptoquack.Presenter.Interfaces;

import com.cryptoquack.cryptoquack.ResourceManager.IResourceManager;
import com.cryptoquack.cryptoquack.View.Interfaces.IOrderItemView;
import com.cryptoquack.model.IModel;
import com.cryptoquack.model.order.Order;

/**
 * Created by Duke on 2/5/2018.
 */

public interface IOrderItemPresenter {

    public void onCreate(IOrderItemView view);

    public void setOrder(Order order);
}
