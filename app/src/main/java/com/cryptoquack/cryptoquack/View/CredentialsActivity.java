package com.cryptoquack.cryptoquack.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cryptoquack.cryptoquack.AndroidCredentialsStore;
import com.cryptoquack.cryptoquack.CryptoQuackApp;
import com.cryptoquack.cryptoquack.ResourceManager.AndroidResourceManager;
import com.cryptoquack.cryptoquack.Presenter.CredentialsPresenter;
import com.cryptoquack.cryptoquack.Presenter.Interfaces.ICredentialsPresenter;
import com.cryptoquack.cryptoquack.R;
import com.cryptoquack.cryptoquack.View.Interfaces.ICredentialsActivity;
import com.cryptoquack.model.IModel;
import com.cryptoquack.model.Model;
import com.cryptoquack.model.exchange.Exchanges;

import javax.inject.Inject;

/**
 * Created by Duke on 2/11/2018.
 */

public class CredentialsActivity extends CryptoQuackActivity implements ICredentialsActivity {

    public static final String EXTRA_CREDENTIALS_ACTIVITY_EXCHANGE_TYPE = String.format(
            "%s.exchange_type",
            CredentialsActivity.class.getCanonicalName());

    private EditText accessKeyEditText;
    private EditText secretKeyEditText;
    private Button saveButton;

    @Inject
    protected ICredentialsPresenter presenter;
    @Inject
    public IModel model;

    public CredentialsActivity() { }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        CryptoQuackApp.getActivityComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credentials);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        this.accessKeyEditText = (EditText)this.findViewById(R.id.access_key_edit_text);
        this.secretKeyEditText = (EditText)this.findViewById(R.id.secret_key_edit_text);
        this.saveButton = (Button)this.findViewById(R.id.credentials_save_button);

        String exchangeTypeString = extras.getString(this.EXTRA_CREDENTIALS_ACTIVITY_EXCHANGE_TYPE);
        final Exchanges.Exchange exchangeType = Exchanges.Exchange.valueOf(exchangeTypeString);
        this.presenter.onCreate(this, exchangeType);
        this.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onCredentialsSaveClick(accessKeyEditText.getText().toString(),
                        secretKeyEditText.getText().toString());
            }
        });
    }

    @Override
    public void setAccessKeyText(String accessKey) {
        this.accessKeyEditText.setText(accessKey);
    }

    @Override
    public void setSecretKeyText(String secretKey) {
        this.secretKeyEditText.setText(secretKey);
    }
}
