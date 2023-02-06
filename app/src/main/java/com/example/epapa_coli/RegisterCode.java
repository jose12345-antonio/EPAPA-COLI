package com.example.epapa_coli;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.epapa_coli.Confirm.ConfirmUpdatePassword;

import java.util.HashMap;
import java.util.Map;

public class RegisterCode extends AppCompatActivity {

    EditText edtCode, edtConfirmCode;
    Button btnRegisterCode;
    String correo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_code);
        correo = getIntent().getStringExtra("correo");
        edtCode = findViewById(R.id.newPin);
        edtConfirmCode = findViewById(R.id.confirmPin);
        btnRegisterCode = findViewById(R.id.RegisterCode);
        btnRegisterCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String URL = "https://devtesis.com/tesis-epapacoli/insertCode.php";
                RegistrarCode(URL);
            }
        });
    }

    private boolean validarCode() {
        String passwordP = edtCode.getText().toString();
        String password2 = edtConfirmCode.getText().toString();
        if (passwordP.equals(password2)) {
            return true;
        } else {
            return false;
        }
    }

    public void RegistrarCode(String URL){
        if (edtCode.getText().toString().equals("") || validarCode() == false) {
            edtCode.setError("Los códigos no coinciden");
        } else if (edtCode.getText().toString().length() < 6) {
            edtCode.setError("El código requiere mínimo de 6 números");
        } else if (edtConfirmCode.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "el campo confirmar código está vacía está vacío", Toast.LENGTH_SHORT).show();
        } else{
            final RequestQueue queue = Volley.newRequestQueue(this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(getApplicationContext(), "El código se registró con éxito", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), Login.class));

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Error al registrar", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("code", edtCode.getText().toString());
                    params.put("email", correo);

                    return params;
                }
            };
            queue.add(stringRequest);
            queue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
                @Override
                public void onRequestFinished(Request<Object> request) {
                    queue.getCache().clear();
                }
            });
        }
    }

}