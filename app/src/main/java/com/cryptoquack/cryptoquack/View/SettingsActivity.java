package com.cryptoquack.cryptoquack.View;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.cryptoquack.cryptoquack.AndroidResourceManager;
import com.cryptoquack.cryptoquack.CryptoQuackActivity;
import com.cryptoquack.cryptoquack.ExchangesActivity;
import com.cryptoquack.cryptoquack.ExchangesAdapter;
import com.cryptoquack.cryptoquack.LandingActivity;
import com.cryptoquack.cryptoquack.Presenter.ISettingsPresenter;
import com.cryptoquack.cryptoquack.Presenter.SettingsPresenter;
import com.cryptoquack.cryptoquack.Presenter.TradingPresenter;
import com.cryptoquack.cryptoquack.R;
import com.cryptoquack.model.Common;
import com.cryptoquack.model.IModel;
import com.cryptoquack.model.Model;
import com.cryptoquack.model.exchange.Exchanges;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by Duke on 2/8/2018.
 */

public class SettingsActivity extends CryptoQuackActivity implements ISettingsActivity {

    private final IModel model;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ISettingsPresenter presenter;
    private ExchangesAdapter adapter;

    public SettingsActivity() {
        super();
        IModel model = new Model();
        this.model = model;
        this.presenter = new SettingsPresenter();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        this.setTitle(this.getString(R.string.activity_settings_title));
        this.recyclerView = (RecyclerView) findViewById(R.id.settings_list_recycler_view);
        this.recyclerView.setHasFixedSize(true);
        this.layoutManager = new LinearLayoutManager(this);
        this.recyclerView.setLayoutManager(this.layoutManager);
        this.setAvailableExchanges(Common.exchanges);
    }

    @Override
    public void setAvailableExchanges(Exchanges.Exchange[] availableExchanges) {
        this.adapter = new ExchangesAdapter(Common.exchanges, this.exchangeToNameMap, this);
        this.presenter.onCreate(this,
                this.model,
                new AndroidResourceManager(this.getResources()));
        this.adapter.setCallBack(new ExchangesAdapter.ExchangesRecyclerViewListener() {

            @Override
            public void onExchangeClick(Exchanges.Exchange[] exchanges, int position) {
                Exchanges.Exchange exchangeType = exchanges[position];
                Bundle credentialsActivityBundle = new Bundle();
                credentialsActivityBundle.putString(CredentialsActivity.EXTRA_CREDENTIALS_ACTIVITY_EXCHANGE_TYPE,
                        exchangeType.name());
                Intent intent = new Intent(SettingsActivity.this, CredentialsActivity.class);
                intent.putExtras(credentialsActivityBundle);
                startActivity(intent);
            }
        });

        this.recyclerView.setAdapter(this.adapter);
        DividerItemDecoration deco = new DividerItemDecoration(this.recyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        this.recyclerView.addItemDecoration(deco);
    }
}
