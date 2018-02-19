package com.cryptoquack.cryptoquack.Presenter.Interfaces;

import com.cryptoquack.cryptoquack.View.Interfaces.ITradingView;
import com.cryptoquack.model.currency.ExchangeMarket;
import com.cryptoquack.model.exchange.ExchangeAction;
import com.cryptoquack.model.exchange.Exchanges;
import com.cryptoquack.model.order.Order;

/**
 * Created by Duke on 1/28/2018.
 */

public interface ITradingPresenter {

    public void onCreate(ITradingView view, Exchanges.Exchange exchange);

    public void onMarketChanged(ExchangeMarket market);

    public void onOrderPriceEntered(ExchangeMarket market, ExchangeAction.ExchangeActions action,
                                    String quantity, String price);

    public void onOrderQuantityEntered(ExchangeMarket market, ExchangeAction.ExchangeActions action,
                                       String quantity, String price);

    public void onNewOrderClick(String priceString, String quantityString, ExchangeAction.ExchangeActions action,
                           Order.OrderType orderType, ExchangeMarket market);

}
