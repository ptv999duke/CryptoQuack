package com.cryptoquack.model.currency;

/**
 * Created by Duke on 1/21/2018.
 */

public class ExchangeMarket {

    public static final ExchangeMarket BTCUSD = new ExchangeMarket(Currencies.Currency.BTC, Currencies.Currency.USD);
    public static final ExchangeMarket BTCUSDT = new ExchangeMarket(Currencies.Currency.BTC, Currencies.Currency.USDT);

    public static final ExchangeMarket ETHUSD = new ExchangeMarket(Currencies.Currency.ETH, Currencies.Currency.USD);
    public static final ExchangeMarket ETHBTC = new ExchangeMarket(Currencies.Currency.ETH, Currencies.Currency.BTC);
    public static final ExchangeMarket ETHUSDT = new ExchangeMarket(Currencies.Currency.ETH, Currencies.Currency.USDT);

    public static final ExchangeMarket BCHBTC = new ExchangeMarket(Currencies.Currency.BCH, Currencies.Currency.BTC);
    public static final ExchangeMarket BCHETH = new ExchangeMarket(Currencies.Currency.BCH, Currencies.Currency.ETH);
    public static final ExchangeMarket BCHUSDT = new ExchangeMarket(Currencies.Currency.BCH, Currencies.Currency.USDT);

    public static final ExchangeMarket NEOBTC = new ExchangeMarket(Currencies.Currency.NEO, Currencies.Currency.BTC);
    public static final ExchangeMarket NEOETH = new ExchangeMarket(Currencies.Currency.NEO, Currencies.Currency.ETH);
    public static final ExchangeMarket NEOUSDT = new ExchangeMarket(Currencies.Currency.NEO, Currencies.Currency.USDT);

    private Currencies.Currency sourceCurrency;
    private Currencies.Currency destinationCurrency;

    public ExchangeMarket(Currencies.Currency sourceCurrency, Currencies.Currency destinationCurrency) {
        this.sourceCurrency = sourceCurrency;
        this.destinationCurrency = destinationCurrency;
    }

    public Currencies.Currency getSourceCurrency() {
        return sourceCurrency;
    }

    public Currencies.Currency getDestinationCurrency() {
        return destinationCurrency;
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ExchangeMarket) {
            ExchangeMarket otherMarket = (ExchangeMarket) o;
            return this.sourceCurrency.equals(otherMarket.sourceCurrency) &&
                    this.destinationCurrency.equals(otherMarket.destinationCurrency);
        }

        return false;
    }

    @Override
    public String toString() {
        return String.format("%s/%s", this.sourceCurrency, this.destinationCurrency);
    }
}
