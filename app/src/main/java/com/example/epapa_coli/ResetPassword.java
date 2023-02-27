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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.epapa_coli.Received.ReceivedResetPassword;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ResetPassword extends AppCompatActivity {

    Button btnRecuperarPassword;
    EditText edtCorreo;
    int codigo, contadorCorreo;
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
                obtenerCorreo();
                //startActivity(new Intent(getApplicationContext(), VerifyCode.class));
                //sendMail();
            }
        });
    }

    private void obtenerCorreo() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://epapa-coli.es/tesis-epapacoli/validar_correo.php?usuario="+edtCorreo.getText().toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray jsonArray = jsonObject.getJSONArray("email");
                    contadorCorreo = jsonArray.length();

                    ArrayList numeros = new ArrayList();
                    for(int i=1; i<=6; i++){
                        codigo = (int) (Math.random() * 9+1);
                        if(numeros.contains(codigo)){
                            i--;
                        }else{
                            numeros.add(codigo);
                        }
                    }

                    if(edtCorreo.getText().toString().isEmpty()){
                        edtCorreo.setError("El campo está vacío");
                    }else if(contadorCorreo==0){
                        toastError();
                    }else if(contadorCorreo>=1){
                        receivedCodigo = String.valueOf(numeros.get(0)+""+numeros.get(1)+""+numeros.get(2)+""+numeros.get(3)+""+numeros.get(4)+""+numeros.get(5));
                        sendMail(receivedCodigo);
                        Bundle code = new Bundle();
                        code.putString("code", receivedCodigo);
                        code.putString("correo", edtCorreo.getText().toString());
                        Intent i = new Intent(ResetPassword.this, VerifyCode.class);
                        i.putExtras(code);
                        startActivity(i);
                        finish();

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
        requestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
            @Override
            public void onRequestFinished(Request<Object> request) {
                requestQueue.getCache().clear();
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