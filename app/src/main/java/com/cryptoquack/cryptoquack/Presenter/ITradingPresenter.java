package com.cryptoquack.cryptoquack.Presenter;

import com.cryptoquack.cryptoquack.IResourceManager;
import com.cryptoquack.cryptoquack.View.ITradingView;
import com.cryptoquack.model.IModel;
import com.cryptoquack.model.currency.ExchangeMarket;
import com.cryptoquack.model.exchange.ExchangeAction;
import com.cryptoquack.model.exchange.Exchanges;
import com.cryptoquack.model.order.Order;

/**
 * Created by Duke on 1/28/2018.
 */

public interface ITradingPresenter {

    public void onCreate(ITradingView view, IModel model, IResourceManager rm, Exchanges.Exchange exchange);

    public void onMarketChanged(ExchangeMarket market);

    public void onOrderPriceEntered(ExchangeMarket market, ExchangeAction.ExchangeActions action,
                                    double amount, double price);

    public void onOrderQuantityEntered(ExchangeMarket market, ExchangeAction.ExchangeActions action,
                                       double amount, double price);

    public void onNewOrder(double price, double quantity, ExchangeAction.ExchangeActions action,
                           Order.OrderType orderType, ExchangeMarket market);
}
