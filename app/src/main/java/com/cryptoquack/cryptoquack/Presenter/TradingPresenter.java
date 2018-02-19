package com.cryptoquack.cryptoquack.Presenter;

import com.cryptoquack.cryptoquack.Presenter.Interfaces.ITradingPresenter;
import com.cryptoquack.cryptoquack.ResourceManager.IResourceManager;
import com.cryptoquack.cryptoquack.View.Interfaces.ITradingView;
import com.cryptoquack.exceptions.CredentialsNotSetException;
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

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableSingleObserver;

/**
 * Created by Duke on 1/28/2018.
 */

public class TradingPresenter implements ITradingPresenter {

    private ITradingView view;
    private IModel model;
    private IResourceManager rm;

    private Exchanges.Exchange exchange;

    private Scheduler uiScheduler;
    private Scheduler bgScheduler;

    private DisposableSingleObserver<Double> getCurrentPriceSubscription;
    private Timer getCurrentPriceTimer;

    @Inject
    public TradingPresenter(@Named("UI_thread") Scheduler uiScheduler,
                            @Named("BG_thread") Scheduler bgScheduler,
                            IModel model,
                            IResourceManager rm) {
        this.uiScheduler = uiScheduler;
        this.bgScheduler = bgScheduler;
        this.rm = rm;
        this.model = model;
    }

    @Override
    public void onCreate(ITradingView view, Exchanges.Exchange exchange) {
        this.view = view;
        this.exchange = exchange;
        ArrayList<ExchangeMarket> availableMarkets = this.model.getAvailableMarkets(this.exchange);
        this.view.setAvailableMarkets(availableMarkets);
        this.model.loadCredentials(this.exchange);
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
                        Currencies.Currency destinationCurrency = market.getDestinationCurrency();
                        MonetaryAmount priceAmount = new MonetaryAmount(price, destinationCurrency);
                        view.updateCurrentPrice(priceAmount.toString());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        int f = 3;
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
    public void onNewOrderClick(String priceString, String quantityString,
                                ExchangeAction.ExchangeActions action,
                                Order.OrderType orderType,
                                ExchangeMarket market) {
        double price = 0.0;
        double quantity = 0.0;
        try {
            price = Double.parseDouble(priceString);
            if (price < 0) {
                // TODO: Show error string.
                this.view.showError("");
            }
        } catch (NumberFormatException e) {
            this.view.showError("");
            return;
        }

        try {
            quantity = Double.parseDouble(quantityString);
            if (quantity < 0) {
                // TODO: Show error string.
                this.view.showError("");
            }
        } catch (NumberFormatException e) {
            this.view.showError("");
            return;
        }

        MonetaryAmount amount = new MonetaryAmount(quantity, market.getSourceCurrency());
        Order orderRequest = new Order(action, market, orderType, amount, price);
        Single<Order> single = null;
        try {
            single = this.model.makeOrderAsync(this.exchange, orderRequest);
        } catch (CredentialsNotSetException e) {
            this.view.showError(this.rm.getCredentialsNotSetErrorString());
            return;
        }

        DisposableSingleObserver<Order> subscription = new DisposableSingleObserver<Order>() {

            @Override
            public void onSuccess(@NonNull Order order) {
                view.addOrderItem(order);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                int f= 3;
            }
        };

        single.subscribeOn(bgScheduler)
                .observeOn(uiScheduler)
                .subscribe(subscription);
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
