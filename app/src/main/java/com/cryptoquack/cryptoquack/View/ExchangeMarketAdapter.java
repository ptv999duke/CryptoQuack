package com.cryptoquack.cryptoquack.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cryptoquack.cryptoquack.R;
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
        ExchangeMarket market = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_market_exchange, parent, false);
        }

        TextView marketNameView = (TextView) convertView.findViewById(R.id.exchange_market_name);
        String marketName = market.toString();
        marketNameView.setText(marketName);
        return convertView;
    }
}
