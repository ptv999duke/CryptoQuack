package com.cryptoquack.cryptoquack.ResourceManager;

import com.cryptoquack.model.currency.Currencies;
import com.cryptoquack.model.currency.MonetaryAmount;
import com.cryptoquack.model.exchange.ExchangeAction;

import java.util.Date;

/**
 * Created by Duke on 1/30/2018.
 */

public interface IResourceManager {

    public String getPriceLoadingString();

    public String getOrderCancelledStatusString();

    public String getOrderCompletedStatusString();

    public String getOrderInProgressStatusString();

    public String getOrderCancelButtonString();

    public String getOrderDateString(Date date);

    public String getOrderSummaryString(ExchangeAction.ExchangeActions action, MonetaryAmount amount,
                                        double price, Currencies.Currency priceCurrency);

    public String getOrderFulfilledPercentageString(int percent);

    public String getCredentialsNotSetErrorString();

    public String getUnknownErrorWhenPlacingOrderString();

    public String getInvalidPriceErrorString();

    public String getInvalidQuantityErrorString();
}
