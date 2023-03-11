package com.example.epapa_coli;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class CardVerFactura extends AppCompatActivity {
    WebView webViewPago;
    String user, totalPagar, id;
    LinearLayout regresarCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_ver_factura);
        getSupportActionBar().hide();

        user =  Preferences.obtenerPreferenceString(getApplicationContext(), Preferences.PREFERENCE_USUARIO_LOGIN);
        id = getIntent().getStringExtra("id");
        webViewPago = findViewById(R.id.webViewPago2);
        webViewPago.getSettings().setJavaScriptEnabled(true);
        regresarCard = findViewById(R.id.regresarCard2);
        Toast.makeText(getApplicationContext(), ""+id, Toast.LENGTH_SHORT).show();
        webViewPago.loadUrl("https://epapa-coli.es/tesis-epapacoli/reportes/report.php?id_factura="+id);

        regresarCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), HomeUser.class));
                finish();
            }
        });


    }
}