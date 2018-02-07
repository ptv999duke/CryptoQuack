package com.cryptoquack.cryptoquack.Presenter;

import com.cryptoquack.cryptoquack.IResourceManager;
import com.cryptoquack.cryptoquack.View.IOrderItemView;
import com.cryptoquack.cryptoquack.View.ITradingView;
import com.cryptoquack.model.IModel;
import com.cryptoquack.model.order.Order;

/**
 * Created by Duke on 2/5/2018.
 */

public interface IOrderItemPresenter {

    public void onCreate(IOrderItemView view, IModel model, IResourceManager rm);

    public void setOrder(Order order);

    public void onAdditionalActionClicked();
}
