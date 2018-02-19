package com.cryptoquack.cryptoquack.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.cryptoquack.cryptoquack.R;

public class LandingActivity extends CryptoQuackActivity {

    private ListView mainSelectionListView;

    public LandingActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);


        this.mainSelectionListView = (ListView) findViewById(R.id.landing_activity_main_listview);
        final LandingActivity thisReference = this;
        this.mainSelectionListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Intent intent = new Intent(thisReference, ExchangesActivity.class);
                    startActivity(intent);
                } else if (position == 1) {
                    Intent intent = new Intent(thisReference, SettingsActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
