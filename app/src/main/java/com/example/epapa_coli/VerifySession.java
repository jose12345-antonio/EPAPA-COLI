package com.example.epapa_coli;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class VerifySession extends AppCompatActivity {

    CardView credencialSession, biometricSession, pinSession;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_session);
        getSupportActionBar().hide();
        Variable();
    }

    private void Variable(){
        credencialSession = findViewById(R.id.credencialSession);
        credencialSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });
    }
}