package com.cryptoquack.cryptoquack;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cryptoquack.com.cryptoquack.model.exchange.Exchanges;

import java.util.HashMap;

/**
 * Created by Duke on 1/21/2018.
 */

public class ExchangesAdapter extends RecyclerView.Adapter<ExchangesAdapter.ViewHolder> {

    private Exchanges.Exchange[] myExchangeTypes;
    private HashMap<Exchanges.Exchange, String> myExchangeTypeToNameMap;
    private Context myContext;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView myTextView;

        public ViewHolder(TextView v) {
            super(v);
            this.myTextView = v;
        }
    }

    public ExchangesAdapter(Exchanges.Exchange[] exchangeTypes, HashMap<Exchanges.Exchange, String>
            exchangeTypeToNameMap, Context context) {
        this.myExchangeTypes = exchangeTypes;
        this.myExchangeTypeToNameMap = exchangeTypeToNameMap;
        this.myContext = context;
    }

    @Override
    public ExchangesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView v = new TextView(this.myContext);
        ExchangesAdapter.ViewHolder vh = new ExchangesAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ExchangesAdapter.ViewHolder holder, int position) {
        Exchanges.Exchange exchangeType = this.myExchangeTypes[position];
        String name = this.myExchangeTypeToNameMap.get(exchangeType);
        holder.myTextView.setText(name);
    }

    @Override
    public int getItemCount() {
        return this.myExchangeTypes.length;
    }
}