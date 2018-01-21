package com.cryptoquack.cryptoquack;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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
    private ExchangesRecyclerViewListener myExchangesRecyclerViewListener;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView myTextView;

        public ViewHolder(TextView v) {
            super(v);
            this.myTextView = v;
        }
    }

    public interface ExchangesRecyclerViewListener {
        public abstract void onExchangeClick(Exchanges.Exchange[] exchanges, int position);
    }

    public ExchangesAdapter(Exchanges.Exchange[] exchangeTypes, HashMap<Exchanges.Exchange, String>
            exchangeTypeToNameMap, Context context) {
        this.myExchangeTypes = exchangeTypes;
        this.myExchangeTypeToNameMap = exchangeTypeToNameMap;
        this.myContext = context;
    }

    public void setCallBack(ExchangesRecyclerViewListener callBack) {
        this.myExchangesRecyclerViewListener = callBack;
    }

    @Override
    public ExchangesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView exchangeView = new TextView(this.myContext);
        exchangeView.setTextSize(UICommon.LIST_TEXT_SIZE);
        ExchangesAdapter.ViewHolder vh = new ExchangesAdapter.ViewHolder(exchangeView);
        return vh;
    }

    @Override
    public void onBindViewHolder(ExchangesAdapter.ViewHolder holder, final int position) {
        Exchanges.Exchange exchangeType = this.myExchangeTypes[position];
        String name = this.myExchangeTypeToNameMap.get(exchangeType);
        holder.myTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myExchangesRecyclerViewListener.onExchangeClick(myExchangeTypes, position);
            }
        });

        holder.myTextView.setText(name);
    }

    @Override
    public int getItemCount() {
        return this.myExchangeTypes.length;
    }
}