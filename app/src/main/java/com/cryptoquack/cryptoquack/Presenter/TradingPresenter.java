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
import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableSingleObserver;
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
    private DisposableSingleObserver<Double> getCurrentPriceSubscription;
    private IResourceManager rm;

    private Timer getCurrentPriceTimer;

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
        if (this.getCurrentPriceTimer != null) {
            this.getCurrentPriceTimer.cancel();
            this.getCurrentPriceTimer.purge();
        }

        if (this.getCurrentPriceSubscription != null) {
            this.getCurrentPriceSubscription.dispose();
        }

        String zeroString = new MonetaryAmount(0.0, market.getDestinationCurrency()).toString();
        this.view.updateOrderFee(zeroString);
        this.view.updateSubtotal(zeroString);
        this.view.updateOrderTotal(zeroString);
        if (market != null) {
            ArrayList<ExchangeAction.ExchangeActions> availableActions =  this.model.getAvailableActions
                    (this.exchange, market);
            this.view.setAvailableActions(availableActions);
            this.view.updateCurrentPrice(this.rm.getPriceLoadingString());
            this.registerCurrentPriceGetter(this.exchange, market);
        }
    }

    private void registerCurrentPriceGetter(final Exchanges.Exchange exchange, final ExchangeMarket market) {
        this.getCurrentPriceTimer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {

            @Override
            public void run() {
                Single<Double> single = model.getCurrentPriceAsync(exchange, market);
                DisposableSingleObserver<Double> subscription = new DisposableSingleObserver<Double>() {

                    @Override
                    public void onSuccess(@NonNull Double price) {
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
                        // TODO: If error occurrs too many times in a row, display a warning/error.
                    }
                };

                single.subscribeOn(bgScheduler)
                        .observeOn(uiScheduler)
                        .subscribe(subscription);
                getCurrentPriceSubscription = subscription;
            }
        };

        this.getCurrentPriceTimer.schedule(doAsynchronousTask, 0, 2500);
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
                                    String quantity, String price) {
        double subtotal = 0.0;
        double total = 0.0;
        double quantityDouble = 0.0;
        double priceDouble = 0.0;
        Currencies.Currency destinationCurrency = market.getDestinationCurrency();
        MonetaryAmount fee = new MonetaryAmount(0.0, destinationCurrency);
        boolean incorrectValues = false;
        try {
            quantityDouble = Double.parseDouble(quantity);
            if (quantityDouble < 0.0) {
                incorrectValues = true;
                quantityDouble = 0.0;
            }
        } catch (NumberFormatException e) {
            incorrectValues = true;
        }

        try {
            priceDouble = Double.parseDouble(price);
            if (priceDouble < 0.0) {
                incorrectValues = true;
                priceDouble = 0.0;
            }
        } catch (NumberFormatException e) {
            incorrectValues = true;
        }

        if (!incorrectValues) {
            subtotal = quantityDouble * priceDouble;
            MonetaryAmount monetaryAmount = new MonetaryAmount(subtotal, market.getSourceCurrency());
            fee = this.model.calculateFee(this.exchange, action, monetaryAmount, market);
            if (action.equals(ExchangeAction.ExchangeActions.BUY)) {
                total = subtotal + fee.getAmount();
            } else if (action.equals(ExchangeAction.ExchangeActions.SELL)) {
                total = subtotal - fee.getAmount();
            }
        }


        this.view.updateSubtotal(new MonetaryAmount(subtotal, destinationCurrency).toString());
        this.view.updateOrderFee(fee.toString());
        this.view.updateOrderTotal(new MonetaryAmount(total, destinationCurrency).toString());
    }

    @Override
    public void onOrderQuantityEntered(ExchangeMarket market, ExchangeAction.ExchangeActions action,
                                       String amount, String price) {
        this.onOrderPriceEntered(market, action, amount, price);
    }
}
