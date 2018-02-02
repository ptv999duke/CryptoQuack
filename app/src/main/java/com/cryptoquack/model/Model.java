package com.cryptoquack.model;

import com.cryptoquack.model.IModel;
import com.cryptoquack.model.currency.ExchangeMarket;
import com.cryptoquack.model.currency.MonetaryAmount;
import com.cryptoquack.model.exchange.BaseExchange;
import com.cryptoquack.model.exchange.ExchangeAction;
import com.cryptoquack.model.exchange.Exchanges;
import com.cryptoquack.model.exchange.Gemini.GeminiExchange;
import com.cryptoquack.model.order.Order;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Single;

/**
 * Created by Duke on 1/30/2018.
 */

public class Model implements IModel {

    private HashMap<Exchanges.Exchange, BaseExchange> exchangeMap;

    public Model() {
        this.exchangeMap = new HashMap<>();
        GeminiExchange geminiExchange = new GeminiExchange();
        this.exchangeMap.put(Exchanges.Exchange.GEMINI, geminiExchange);
    }

    @Override
    public ArrayList<ExchangeMarket> getAvailableMarkets(Exchanges.Exchange exchange) {
        return this.exchangeMap.get(exchange).getAvailableMarkets();
    }

    @Override
    public Double getCurrentPrice(Exchanges.Exchange exchange, ExchangeMarket market) {
        return this.exchangeMap.get(exchange).getCurrentPrice(market);
    }

    @Override
    public Single<Double> getCurrentPriceAsync(Exchanges.Exchange exchange, ExchangeMarket market) {
        return this.exchangeMap.get(exchange).getCurrentPriceAsync(market);
    }

    @Override
    public MonetaryAmount calculateFee(Exchanges.Exchange exchange, ExchangeAction.ExchangeActions action, MonetaryAmount amount, ExchangeMarket market) {
        return this.exchangeMap.get(exchange).calculateFee(action, amount, market);
    }

    @Override
    public ArrayList<ExchangeAction.ExchangeActions> getAvailableActions(Exchanges.Exchange exchange, ExchangeMarket market) {
        return this.exchangeMap.get(exchange).getAvailableActions(market);
    }

    @Override
    public Single<Order> makeOrder(Exchanges.Exchange exchange, ExchangeAction.ExchangeActions action, Order.OrderType orderType, MonetaryAmount monetaryAmount, double price, ExchangeMarket market) {
        return null;
    }
}
