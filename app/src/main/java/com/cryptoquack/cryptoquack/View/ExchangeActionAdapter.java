package com.cryptoquack.cryptoquack.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cryptoquack.cryptoquack.R;
import com.cryptoquack.model.currency.ExchangeMarket;
import com.cryptoquack.model.exchange.ExchangeAction;

import java.util.ArrayList;

/**
 * Created by Duke on 1/21/2018.
 */

public class ExchangeActionAdapter extends ArrayAdapter<ExchangeAction.ExchangeActions> {

    private Context context;

    public ExchangeActionAdapter(Context context, int textViewResourceId, ArrayList<ExchangeAction.ExchangeActions> actions) {
        super(context, textViewResourceId, actions);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ExchangeAction.ExchangeActions exchangeAction = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_market_action, parent, false);
        }

        TextView marketActionView = (TextView) convertView.findViewById(R.id.exchange_market_action);
        String actionString = this.context.getString(R.string.unkonwn_action);
        if (exchangeAction == ExchangeAction.ExchangeActions.BUY) {
            actionString = this.context.getString(R.string.buy_action);
        } else if (exchangeAction == ExchangeAction.ExchangeActions.SELL) {
            actionString = this.context.getString(R.string.sell_action);
        }

        marketActionView.setText(actionString);
        return convertView;
    }
}
