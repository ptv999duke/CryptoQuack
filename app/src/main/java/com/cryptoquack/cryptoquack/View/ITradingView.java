package com.cryptoquack.cryptoquack.View;

import com.cryptoquack.model.IModel;
import com.cryptoquack.model.currency.Currencies;
import com.cryptoquack.model.currency.ExchangeMarket;
import com.cryptoquack.model.exchange.ExchangeAction;
import com.cryptoquack.model.order.Order;

import java.util.ArrayList;

/**
 * Created by Duke on 1/28/2018.
 */

public interface ITradingView {

    public void updateCurrentPrice(String price);

    public void updateSubtotal(String subtotal);

    public void updateOrderFee(String fee);

    public void updateOrderTotal(String orderTotal);

    public void setAvailableMarkets(ArrayList<ExchangeMarket> availableMarkets);

    public void setAvailableActions(ArrayList<ExchangeAction.ExchangeActions> availableActions);

    public void clearErrorText();

    public void showError(String message);

    public void addOrderItem(Order order);
}
