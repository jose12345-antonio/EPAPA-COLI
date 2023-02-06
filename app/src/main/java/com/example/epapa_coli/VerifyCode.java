package com.example.epapa_coli;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class VerifyCode extends AppCompatActivity {

    EditText edtCode;
    TextView reenviarCodigo;
    Button btnVerificar;
    String code, correo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_code);
        getSupportActionBar().hide();
        code = getIntent().getStringExtra("code");
        correo = getIntent().getStringExtra("correo");
        Variable();
    }

    public void Variable(){
        edtCode = findViewById(R.id.edtCode);
        reenviarCodigo = findViewById(R.id.reenvioCode);
        btnVerificar = findViewById(R.id.btnCode);
        btnVerificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!edtCode.getText().toString().equals(code)){
                    edtCode.setError("Código incorrecto. Verifique su correo");
                }else{
                    toastVerify();
                    Bundle email = new Bundle();
                    email.putString("correo", correo);
                    Intent i = new Intent(VerifyCode.this, UpdatePasswordCode.class);
                    i.putExtras(email);
                    startActivity(i);
                    finish();
                }
            }
        });
    }
    public void toastVerify() {
        Toast toast = new Toast(this);

        View toast_layout = getLayoutInflater().inflate(R.layout.toast_verify, (ViewGroup) findViewById(R.id.lytLayoutverify));
        toast.setView(toast_layout);
        TextView textView = (TextView) toast_layout.findViewById(R.id.toastMessageSession);
        textView.setText("Código correcto. Actualice su contraseña");
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }
}