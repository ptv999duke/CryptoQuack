package com.cryptoquack.model.exchange;

import com.cryptoquack.model.currency.ExchangeMarket;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Duke on 1/20/2018.
 */

public class GeminiExchange extends BaseExchange {

    private final ArrayList<ExchangeMarket> availableMarkets = new ArrayList<>();
    private final ArrayList<ExchangeAction.ExchangeActions> availableActions = new ArrayList<>();

    public GeminiExchange() {
        super(Exchanges.Exchange.GEMINI);
        this.availableMarkets.add(ExchangeMarket.BTCUSD);
        this.availableMarkets.add(ExchangeMarket.ETHUSD);
        this.availableMarkets.add(ExchangeMarket.ETHBTC);

        this.availableActions.add(ExchangeAction.ExchangeActions.BUY);
        this.availableActions.add(ExchangeAction.ExchangeActions.SELL);
    }

    @Override
    public ArrayList<ExchangeMarket> getAvailableMarkets() {
        return (ArrayList<ExchangeMarket>) this.availableMarkets.clone();
    }

    @Override
    public double getCurrentPrice(ExchangeMarket exchangeMarket) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e){
            throw new RuntimeException();
        }
        Random r = new Random();
        return r.nextDouble();
    }
    @Override
    public ArrayList<ExchangeAction.ExchangeActions> getAvailableActions(
        ExchangeMarket exchangeMarket) {
        return (ArrayList<ExchangeAction.ExchangeActions>) this.availableActions.clone();
    }
}
