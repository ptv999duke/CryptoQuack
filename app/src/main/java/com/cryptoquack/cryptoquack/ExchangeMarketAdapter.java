package com.cryptoquack.cryptoquack;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cryptoquack.model.currency.ExchangeMarket;

import java.util.ArrayList;

/**
 * Created by Duke on 1/21/2018.
 */

public class ExchangeMarketAdapter extends ArrayAdapter<ExchangeMarket> {

    public ExchangeMarketAdapter(Context context, int textViewResourceId, ArrayList<ExchangeMarket> items) {
        super(context, textViewResourceId, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ExchangeMarket exchangeMarket = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_market_exchange, parent, false);
        }

        TextView exchangeMarketNameView = (TextView) convertView.findViewById(R.id.exchange_market_name);
        String marketName = exchangeMarket.toString();
        exchangeMarketNameView.setText(marketName);
        return convertView;
    }
}
