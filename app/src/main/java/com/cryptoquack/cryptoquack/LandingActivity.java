package com.cryptoquack.cryptoquack;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.cryptoquack.com.cryptoquack.model.exchange.Exchanges;

import java.util.HashMap;

public class LandingActivity extends CryptoQuackActivity {


    public LandingActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_page);

        Intent intent = new Intent(this, ExchangesActivity.class);
        startActivity(intent);

    }

}
