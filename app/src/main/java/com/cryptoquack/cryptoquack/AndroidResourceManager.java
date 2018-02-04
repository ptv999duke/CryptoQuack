package com.cryptoquack.cryptoquack;

import android.content.res.Resources;

/**
 * Created by Duke on 1/30/2018.
 */

public class AndroidResourceManager implements IResourceManager {

    @javax.inject.Inject
    private Resources r;

    public AndroidResourceManager(Resources r) {
        this.r = r;
    }

    @Override
    public String getPriceLoadingString() {
        return r.getString(R.string.loading);
    }
}
