package com.cryptoquack.cryptoquack;

import android.content.Context;
import android.content.res.Resources;

import com.cryptoquack.cryptoquack.R;
import com.cryptoquack.model.currency.Currencies;
import com.cryptoquack.model.currency.MonetaryAmount;
import com.cryptoquack.model.exchange.ExchangeAction;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Duke on 1/30/2018.
 */

public class AndroidResourceManager implements IResourceManager {

    private Resources resources;

    public AndroidResourceManager(Resources r) {
        this.resources = r;
    }

    @Override
    public String getPriceLoadingString() {

        return this.resources.getString(R.string.loading);
    }

    @Override
    public String getOrderCancelledStatusString() {
        return this.resources.getString(R.string.order_cancelled_status_string);
    }

    @Override
    public String getOrderCompletedStatusString() {
        return this.resources.getString(R.string.order_completed_status_string);
    }

    @Override
    public String getOrderInProgressStatusString() {
        return this.resources.getString(R.string.order_in_progress_status_string);
    }

    @Override
    public String getOrderCancelButtonString() {
        return this.resources.getString(R.string.order_cancel_button_string);
    }

    @Override
    public String getOrderDateString(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return dateFormat.format(date);
    }

    @Override
    public String getOrderSummaryString(ExchangeAction.ExchangeActions action, MonetaryAmount amount,
                                        double price, Currencies.Currency priceCurrency) {
        String priceString = new MonetaryAmount(price, priceCurrency).toString();
        String summaryString = String.format("%s %s@%s",
                action.toString(),
                amount.toString(),
                priceString);
        return summaryString;
    }

    @Override
    public String getOrderFulfilledPercentageString(int percent) {
        return String.format(this.resources.getString(R.string.order_filled_progress_string_format),
                percent);
    }

    @Override
    public String getCredentialsNotSetErrorString() {
        return this.resources.getString(R.string.credentials_not_set_error);
    }
}
