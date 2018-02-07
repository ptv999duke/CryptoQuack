package com.cryptoquack.cryptoquack.View;

/**
 * Created by Duke on 2/5/2018.
 */

public interface IOrderItemView {

    public void setOrderTime(String timeString, boolean visible);

    public void setOrderAction(String orderAction, boolean visible);

    public void setOrderType(String orderTypeString, boolean visible);

    public void setOrderAmountPrice(String orderAmountPriceString, boolean visible);

    public void setOrderProgress(String orderProgress, boolean visible);

    public void setAdditionalActionButton(String additionalActionText, boolean visible,
                                          boolean enabled);
}
