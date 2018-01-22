package com.cryptoquack.cryptoquack;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cryptoquack.model.exchange.Exchanges;

import java.util.HashMap;

/**
 * Created by Duke on 1/21/2018.
 */

public class ExchangesAdapter extends RecyclerView.Adapter<ExchangesAdapter.ViewHolder> {

    private Exchanges.Exchange[] exchangeTypes;
    private HashMap<Exchanges.Exchange, String> exchangeTypeToNameMap;
    private Context context;
    private ExchangesRecyclerViewListener exchangesRecyclerViewListener;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;

        public ViewHolder(TextView v) {
            super(v);
            this.textView = v;
        }
    }

    public interface ExchangesRecyclerViewListener {
        public abstract void onExchangeClick(Exchanges.Exchange[] exchanges, int position);
    }

    public ExchangesAdapter(Exchanges.Exchange[] exchangeTypes, HashMap<Exchanges.Exchange, String>
            exchangeTypeToNameMap, Context context) {
        this.exchangeTypes = exchangeTypes;
        this.exchangeTypeToNameMap = exchangeTypeToNameMap;
        this.context = context;
    }

    public void setCallBack(ExchangesRecyclerViewListener callBack) {
        this.exchangesRecyclerViewListener = callBack;
    }

    @Override
    public ExchangesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView exchangeView = new TextView(this.context);
        exchangeView.setTextSize(UICommon.LIST_TEXT_SIZE);
        ExchangesAdapter.ViewHolder vh = new ExchangesAdapter.ViewHolder(exchangeView);
        return vh;
    }

    @Override
    public void onBindViewHolder(ExchangesAdapter.ViewHolder holder, final int position) {
        Exchanges.Exchange exchangeType = this.exchangeTypes[position];
        String name = this.exchangeTypeToNameMap.get(exchangeType);
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exchangesRecyclerViewListener.onExchangeClick(exchangeTypes, position);
            }
        });

        holder.textView.setText(name);
    }

    @Override
    public int getItemCount() {
        return this.exchangeTypes.length;
    }
}