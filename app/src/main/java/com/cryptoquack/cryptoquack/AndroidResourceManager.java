package com.cryptoquack.cryptoquack;

import android.content.Context;
import android.content.res.Resources;

import com.cryptoquack.cryptoquack.R;

/**
 * Created by Duke on 1/30/2018.
 */

public class AndroidResourceManager implements IResourceManager {

    @javax.inject.Inject
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

    public String getOrderCancelButtonString() {
        return this.resources.getString(R.string.order_cancel_button_string);
    }
}
