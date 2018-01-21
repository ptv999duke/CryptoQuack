package com.cryptoquack.cryptoquack;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cryptoquack.com.cryptoquack.model.exchange.Common;
import com.cryptoquack.com.cryptoquack.model.exchange.Exchanges;

public class ExchangesActivity extends CryptoQuackActivity {

    private RecyclerView myRecyclerView;
    private RecyclerView.LayoutManager myLayoutManager;
    private ExchangesAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchanges);

        this.setTitle(getString(R.string.exchanges_header));
        this.myRecyclerView = (RecyclerView) findViewById(R.id.exchange_list_recycler_view);
        this.myRecyclerView.setHasFixedSize(true);
        this.myLayoutManager = new LinearLayoutManager(this);
        this.myRecyclerView.setLayoutManager(this.myLayoutManager);

        this.myAdapter = new ExchangesAdapter(Common.exchanges, this.myExchangeToNameMap, this);
        this.myAdapter.setCallBack(new ExchangesAdapter.ExchangesRecyclerViewListener() {

            @Override
            public void onExchangeClick(Exchanges.Exchange[] exchanges, int position) {
                Exchanges.Exchange exchangeType = exchanges[position];
                Bundle tradingActivityBundle = new Bundle();
                tradingActivityBundle.putString(TradingActivity.EXTRA_TRADING_ACTIVITY_EXCHANGE_TYPE, exchangeType.name());
                Intent intent = new Intent(ExchangesActivity.this, TradingActivity.class);
                intent.putExtras(tradingActivityBundle);
                startActivity(intent);
            }
        });

        this.myRecyclerView.setAdapter(this.myAdapter);
        DividerItemDecoration deco = new DividerItemDecoration(this.myRecyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        this.myRecyclerView.addItemDecoration(deco);
    }
}
