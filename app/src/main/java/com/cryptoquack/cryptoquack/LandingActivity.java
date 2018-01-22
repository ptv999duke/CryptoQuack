package com.cryptoquack.cryptoquack;

import android.content.Intent;
import android.os.Bundle;

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
