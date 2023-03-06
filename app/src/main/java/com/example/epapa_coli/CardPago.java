package com.example.epapa_coli;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.paymentez.android.Paymentez;
import com.paymentez.android.model.Card;
import com.paymentez.android.rest.TokenCallback;
import com.paymentez.android.rest.model.PaymentezError;
import com.paymentez.android.view.CardMultilineWidget;

public class CardPago extends AppCompatActivity {


    WebView webViewPago;
    String user, totalPagar, id;
    LinearLayout regresarCard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_pago);
        getSupportActionBar().hide();

        user =  Preferences.obtenerPreferenceString(getApplicationContext(), Preferences.PREFERENCE_USUARIO_LOGIN);
        totalPagar = getIntent().getStringExtra("pagar");
        id = getIntent().getStringExtra("id");
        webViewPago = findViewById(R.id.webViewPago);
        webViewPago.getSettings().setJavaScriptEnabled(true);
        regresarCard = findViewById(R.id.regresarCard);
        //System.out.println("https://devtesis.com/tesis-epapacoli/pago_tarjeta.php?correo="+user+"&valor="+totalPagar);
        webViewPago.loadUrl("https://epapa-coli.es/tesis-epapacoli/pago_tarjeta.php?correo="+user+"&valor="+totalPagar+"&id="+id);

        regresarCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), HomeUser.class));
                finish();
            }
        });

    }
}