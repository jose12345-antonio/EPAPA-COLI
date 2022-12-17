package com.example.epapa_coli;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class VerifyCode extends AppCompatActivity {

    EditText edt1, edt2, edt3, edt4, edt5, edt6;
    TextView reenviarCodigo;
    Button btnVerificar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_code);
        getSupportActionBar().hide();
        String code = getIntent().getStringExtra("code");

    }
}