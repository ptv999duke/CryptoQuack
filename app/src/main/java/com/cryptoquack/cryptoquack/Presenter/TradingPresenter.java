package com.cryptoquack.cryptoquack.Presenter;

import com.cryptoquack.cryptoquack.IResourceManager;
import com.cryptoquack.cryptoquack.View.ITradingView;
import com.cryptoquack.model.IModel;
import com.cryptoquack.model.currency.Currencies;
import com.cryptoquack.model.currency.ExchangeMarket;
import com.cryptoquack.model.currency.MonetaryAmount;
import com.cryptoquack.model.exchange.ExchangeAction;
import com.cryptoquack.model.exchange.Exchanges;
import com.cryptoquack.model.order.Order;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.ResourceObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Duke on 1/28/2018.
 */

public class TradingPresenter implements ITradingPresenter {

    private ITradingView view;
    private IModel model;
    private Exchanges.Exchange exchange;
    private Scheduler uiScheduler;
    private Scheduler bgScheduler;
    private ResourceObserver<Double> getCurrentPriceSubscription;
    private IResourceManager rm;

    public TradingPresenter(Scheduler uiScheduler) {
        this.uiScheduler = uiScheduler;
        this.bgScheduler = Schedulers.io();
    }

    @Override
    public void onCreate(ITradingView view, IModel model, IResourceManager rm, Exchanges.Exchange exchange) {
        this.view = view;
        this.model = model;
        this.exchange = exchange;
        this.rm = rm;
        ArrayList<ExchangeMarket> availableMarkets = this.model.getAvailableMarkets(this.exchange);
        this.view.setAvailableMarkets(availableMarkets);
    }

    @Override
    public void onMarketChanged(ExchangeMarket market) {
        this.view.updateOrderFee(0.0, market.getDestinationCurrency());
        this.view.updateSubtotal(0.0, market.getDestinationCurrency());
        this.view.updateOrderTotal(0.0, market.getDestinationCurrency());
        ArrayList<ExchangeAction.ExchangeActions> availableActions =  this.model.getAvailableActions
                (this.exchange, market);
        this.view.setAvailableActions(availableActions);
        if (this.getCurrentPriceSubscription != null) {
            this.getCurrentPriceSubscription.dispose();
        }

        this.view.updateCurrentPrice(this.rm.getPriceLoadingString());
        this.registerCurrentPriceGetter(this.exchange, market);
    }

    private void registerCurrentPriceGetter(Exchanges.Exchange exchange, final ExchangeMarket market) {
        Single<Double> single = this.model.getCurrentPriceAsync(exchange, market);
        Observable<Double> observable = single.toObservable();
        ResourceObserver<Double> observer = new ResourceObserver<Double>() {
            @Override
            public void onNext(@NonNull Double price) {
                String priceString = null;
                Currencies.Currency destinationCurrency = market.getDestinationCurrency();
                if (destinationCurrency.equals(Currencies.Currency.USD)) {
                    priceString = String.format("$%s", price);
                } else {
                    priceString = String.format("%f %s", price, destinationCurrency.toString());
                }

                view.updateCurrentPrice(priceString);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        observable.subscribeOn(this.bgScheduler)
                .observeOn(this.uiScheduler)
                .subscribe(observer);

        this.getCurrentPriceSubscription = observer;
    }

    @Override
    public void onNewOrder(double price, double quantity, ExchangeAction.ExchangeActions action,
                           Order.OrderType orderType, ExchangeMarket market) {
        if (price < 0) {
            this.view.showIncorrectPriceError();
            return;
        }

        if (quantity < 0) {
            this.view.showIncorrectQuantityError();
            return;
        }

        MonetaryAmount amount = new MonetaryAmount(quantity, market.getSourceCurrency());
        // TODO: make this call async and update view with order information.
        this.model.makeOrder(this.exchange, action, orderType, amount, price, market);
    }

    @Override
    public void onOrderPriceEntered(ExchangeMarket market, ExchangeAction.ExchangeActions action,
                                    double amount, double price) {
        double subtotal = 0.0;
        double total = 0.0;
        MonetaryAmount fee = new MonetaryAmount(0.0, market.getDestinationCurrency());
        if (amount > 0.0 && price > 0.0) {
            subtotal = amount * price;
            MonetaryAmount monetaryAmount = new MonetaryAmount(subtotal, market.getSourceCurrency());
            fee = this.model.calculateFee(this.exchange, action, monetaryAmount, market);
            if (action.equals(ExchangeAction.ExchangeActions.BUY)) {
                total = subtotal + fee.getAmount();
            } else if (action.equals(ExchangeAction.ExchangeActions.SELL)) {
                total = subtotal - fee.getAmount();
            }
        }

        this.view.updateSubtotal(subtotal, market.getDestinationCurrency());
        this.view.updateOrderFee(fee.getAmount(), fee.getCurrency());
        this.view.updateOrderTotal(total, market.getDestinationCurrency());
    }

    @Override
    public void onOrderQuantityEntered(ExchangeMarket market, ExchangeAction.ExchangeActions action,
                                       double amount, double price) {
        this.onOrderPriceEntered(market, action, amount, price);
    }
}
