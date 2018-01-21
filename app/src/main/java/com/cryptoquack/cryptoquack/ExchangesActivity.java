package com.cryptoquack.cryptoquack;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cryptoquack.com.cryptoquack.model.exchange.BaseExchange;
import com.cryptoquack.com.cryptoquack.model.exchange.Exchanges;
import com.cryptoquack.com.cryptoquack.model.exchange.GeminiExchange;

import java.util.HashMap;

public class ExchangesActivity extends CryptoQuackActivity {

    private RecyclerView myRecyclerView;
    private RecyclerView.LayoutManager myLayoutManager;
    private RecyclerView.Adapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchanges);

        this.myRecyclerView = (RecyclerView) findViewById(R.id.exchange_list_recycler_view);
        this.myRecyclerView.setHasFixedSize(true);
        this.myLayoutManager = new LinearLayoutManager(this);
        this.myRecyclerView.setLayoutManager(this.myLayoutManager);

        this.myAdapter = new ExchangesAdapter(Common.exchanges, this.myExchangeToNameMap, this);
        this.myRecyclerView.setAdapter(this.myAdapter);
    }
}
