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

import com.example.epapa_coli.Received.ReceivedResetPassword;

import java.util.ArrayList;

public class ResetPassword extends AppCompatActivity {

    Button btnRecuperarPassword;
    EditText edtCorreo;
    int codigo;
    String receivedCodigo, correo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        getSupportActionBar().hide();
        Variable();
    }

    private void Variable(){
        edtCorreo = findViewById(R.id.edtCorreoReset);

        btnRecuperarPassword = findViewById(R.id.btnRecuperarPassword);
        btnRecuperarPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //startActivity(new Intent(getApplicationContext(), VerifyCode.class));
                //sendMail();
                ArrayList numeros = new ArrayList();
                for(int i=1; i<=6; i++){
                    codigo = (int) (Math.random() * 9+1);
                    if(numeros.contains(codigo)){
                        i--;
                    }else{
                        numeros.add(codigo);
                    }
                }
                correo="josecarrasco1998@outlook.com";
                if(edtCorreo.getText().toString().isEmpty()){
                    edtCorreo.setError("El campo está vacío");
                }else if(!edtCorreo.getText().toString().equals(correo)){

                    toastError();
                }else{
                    receivedCodigo = String.valueOf(numeros.get(0)+""+numeros.get(1)+""+numeros.get(2)+""+numeros.get(3)+""+numeros.get(4)+""+numeros.get(5));
                    sendMail(receivedCodigo);
                    Bundle code = new Bundle();
                    code.putString("code", receivedCodigo);

                    Intent i = new Intent(ResetPassword.this, VerifyCode.class);
                    i.putExtras(code);
                    startActivity(i);
                }
            }
        });
    }

    private void sendMail(String codigo2) {
        String subject = "EPAPA-COLI";
        String frase = "CONTRASEÑA OLVIDADA \n Bienvenido a la app de pago de línea y alcantarillado.";
        String mensaje = frase+"\n Su código es: "+codigo2;
        ReceivedResetPassword javaMailAPI = new ReceivedResetPassword(ResetPassword.this, edtCorreo.getText().toString(), subject, mensaje);
        javaMailAPI.execute();
    }

    public void toastError() {
        Toast toast = new Toast(this);

        View toast_layout = getLayoutInflater().inflate(R.layout.toast_error, (ViewGroup) findViewById(R.id.lytLayout));
        toast.setView(toast_layout);

        TextView textView = (TextView) toast_layout.findViewById(R.id.toastMessage);
        textView.setText("Correo no se encuentra registrado");
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

}