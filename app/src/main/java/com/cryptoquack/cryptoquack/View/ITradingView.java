package com.cryptoquack.cryptoquack.View;

import com.cryptoquack.model.IModel;
import com.cryptoquack.model.currency.Currencies;
import com.cryptoquack.model.currency.ExchangeMarket;
import com.cryptoquack.model.exchange.ExchangeAction;

import java.util.ArrayList;

/**
 * Created by Duke on 1/28/2018.
 */

public interface ITradingView {

    public void updateCurrentPrice(String priceString);

    public void updateSubtotal(double amount, Currencies.Currency currency);

    public void updateOrderFee(double amount, Currencies.Currency currency);

    public void updateOrderTotal(double amount, Currencies.Currency currency);

    public void setAvailableMarkets(ArrayList<ExchangeMarket> availableMarkets);

    public void setAvailableActions(ArrayList<ExchangeAction.ExchangeActions> availableActions);

    public void showIncorrectPriceError();

    public void showIncorrectQuantityError();
}
