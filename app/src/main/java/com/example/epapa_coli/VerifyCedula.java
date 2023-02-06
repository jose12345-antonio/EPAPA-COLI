package com.example.epapa_coli;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class VerifyCedula extends AppCompatActivity {

    EditText edtCedula;
    Button btnVerificarCedula;
    int contadorCedula, contadorRegistro, id_cedula;
    String nombres, apellidos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_cedula);
        getSupportActionBar().hide();
        Variable();
    }

    public void Variable(){
        edtCedula = findViewById(R.id.edtCedula);
        btnVerificarCedula = findViewById(R.id.btnVerificarCedula);
        btnVerificarCedula.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerCedula();
                obtenerRegistro();

            }
        });
    }

    private void obtenerRegistro() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://devtesis.com/tesis-epapacoli/obtenerUsers.php?cedula="+edtCedula.getText().toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray jsonArray = jsonObject.getJSONArray("usuario");
                    contadorRegistro = jsonArray.length();
                    if(contadorCedula==0){
                        edtCedula.setError("La cédula no se encuentra registrada");
                    }else if(contadorRegistro==1){
                        edtCedula.setError("La cédula ya fue registrada anteriormente");
                        edtCedula.setText("");
                    }else{
                        Bundle code = new Bundle();
                        code.putString("id_cedula", String.valueOf(id_cedula));
                        code.putString("nombres", nombres);
                        code.putString("apellidos", apellidos);
                        Intent i = new Intent(VerifyCedula.this, RegisterUsers.class);
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

    private void obtenerCedula() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://devtesis.com/tesis-epapacoli/validar_cedula.php?cedula="+edtCedula.getText().toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray jsonArray = jsonObject.getJSONArray("cedula");
                    contadorCedula = jsonArray.length();

                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        id_cedula = jsonObject1.getInt("id_cedula");
                        nombres = jsonObject1.getString("nombres");
                        apellidos = jsonObject1.getString("apellidos");
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

}