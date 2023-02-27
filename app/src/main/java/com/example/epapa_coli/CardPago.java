package com.example.epapa_coli;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.paymentez.android.Paymentez;
import com.paymentez.android.model.Card;
import com.paymentez.android.rest.TokenCallback;
import com.paymentez.android.rest.model.PaymentezError;
import com.paymentez.android.view.CardMultilineWidget;

public class CardPago extends AppCompatActivity {


    WebView webViewPago;
    String user, totalPagar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_pago);



        user =  Preferences.obtenerPreferenceString(getApplicationContext(), Preferences.PREFERENCE_USUARIO_LOGIN);
        totalPagar = getIntent().getStringExtra("pagar");
        webViewPago = findViewById(R.id.webViewPago);
        webViewPago.getSettings().setJavaScriptEnabled(true);

        //System.out.println("https://devtesis.com/tesis-epapacoli/pago_tarjeta.php?correo="+user+"&valor="+totalPagar);

        webViewPago.loadUrl("https://epapa-coli.es/tesis-epapacoli/pago_tarjeta.php?correo="+user+"&valor="+totalPagar);


    }
}