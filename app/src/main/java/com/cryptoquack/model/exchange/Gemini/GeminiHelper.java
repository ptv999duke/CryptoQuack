package com.cryptoquack.model.exchange.Gemini;

import com.cryptoquack.model.currency.Currencies;
import com.cryptoquack.model.currency.ExchangeMarket;
import com.cryptoquack.model.exchange.ExchangeAction;
import com.cryptoquack.model.exchange.Exchanges;

import java.security.InvalidParameterException;
import java.util.HashMap;

/**
 * Created by Duke on 1/27/2018.
 */

public class GeminiHelper {

    private static final HashMap<ExchangeMarket, String> marketSymbolMap = new HashMap<>();
    private static final HashMap<String, ExchangeMarket> symbolMarketMap = new HashMap<>();

    private static final HashMap<ExchangeAction.ExchangeActions, String> actionActionSymbolMap =
            new HashMap<>();
    private static final HashMap<String, ExchangeAction.ExchangeActions> actionSymbolActionMap =
            new HashMap<>();

    static {
        GeminiHelper.marketSymbolMap.put(ExchangeMarket.BTCUSD, "btcusd");
        GeminiHelper.marketSymbolMap.put(ExchangeMarket.ETHUSD, "ethusd");
        GeminiHelper.marketSymbolMap.put(ExchangeMarket.ETHBTC, "ethbtc");
        for (ExchangeMarket market : GeminiHelper.marketSymbolMap.keySet()) {
            GeminiHelper.symbolMarketMap.put(marketSymbolMap.get(market), market);
        }

        GeminiHelper.actionActionSymbolMap.put(ExchangeAction.ExchangeActions.BUY, "buy");
        GeminiHelper.actionActionSymbolMap.put(ExchangeAction.ExchangeActions.SELL, "sell");
        for (ExchangeAction.ExchangeActions action : GeminiHelper.actionActionSymbolMap.keySet()) {
            GeminiHelper.actionSymbolActionMap.put(actionActionSymbolMap.get(action), action);
        }
    }

    public static final String REST_API_URL = "https://api.gemini.com";
    public static final String SANDBOX_REST_API_URL = "https://api.sandbox.gemini.com";

    public static String convertMarketToSymbol(ExchangeMarket market) {
        if (market == null) {
            throw new NullPointerException();
        }

        String symbol = GeminiHelper.marketSymbolMap.get(market);
        if (symbol == null) {
            throw new InvalidParameterException(String.format("Unsupported exchange market type: {0}"
                    , market));
        }

        return symbol;
    }

    public static ExchangeMarket convertSymbolToMarket(String symbol) {
        if (symbol == null) {
            throw new NullPointerException();
        }

        ExchangeMarket market = GeminiHelper.symbolMarketMap.get(symbol.toLowerCase());
        if (market == null) {
            throw new InvalidParameterException(String.format("Unsupported Gemini symbol type: {0}"
                    , symbol));
        }

        return market;
    }

    public static String convertActionToActionSymbol(ExchangeAction.ExchangeActions action) {
        if (action == null) {
            throw new NullPointerException();
        }

        String symbol = GeminiHelper.actionActionSymbolMap.get(action);
        if (symbol == null) {
            throw new InvalidParameterException(String.format("Unsupported action type: {0}"
                    , action));
        }

        return symbol;
    }
}
