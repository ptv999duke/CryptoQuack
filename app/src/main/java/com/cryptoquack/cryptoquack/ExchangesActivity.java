package com.cryptoquack.cryptoquack;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cryptoquack.model.Common;
import com.cryptoquack.model.exchange.Exchanges;

public class ExchangesActivity extends CryptoQuackActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ExchangesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchanges);

        this.setTitle(getString(R.string.exchanges_header));
        this.recyclerView = (RecyclerView) findViewById(R.id.exchange_list_recycler_view);
        this.recyclerView.setHasFixedSize(true);
        this.layoutManager = new LinearLayoutManager(this);
        this.recyclerView.setLayoutManager(this.layoutManager);

        this.adapter = new ExchangesAdapter(Common.exchanges, this.exchangeToNameMap, this);
        this.adapter.setCallBack(new ExchangesAdapter.ExchangesRecyclerViewListener() {

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

        this.recyclerView.setAdapter(this.adapter);
        DividerItemDecoration deco = new DividerItemDecoration(this.recyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        this.recyclerView.addItemDecoration(deco);
    }


}
