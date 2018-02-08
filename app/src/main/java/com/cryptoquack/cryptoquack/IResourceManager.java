package com.cryptoquack.cryptoquack;
/**
 * Created by Duke on 1/30/2018.
 */

public interface IResourceManager {

    public String getPriceLoadingString();

    public String getOrderCancelledStatusString();

    public String getOrderCompletedStatusString();

    public String getOrderInProgressStatusString();

    public String getOrderCancelButtonString();
}
