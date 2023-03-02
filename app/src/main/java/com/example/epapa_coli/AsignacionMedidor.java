package com.example.epapa_coli;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
import com.example.epapa_coli.Model.GetSetDocumento;
import com.example.epapa_coli.Model.GetSetMedidor;
import com.google.android.material.badge.BadgeUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AsignacionMedidor extends AppCompatActivity {

    AutoCompleteTextView medidorList;
    EditText edtubicacion, edtlatitud, edtlongitud;
    Button btnAsignacion;
    int id_medidor, id_cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asignacion_medidor);

        llenarspinnerMedidor();
        obtenerCliente();
        medidorList = findViewById(R.id.medidorList);
        edtubicacion = findViewById(R.id.ubicacionAsignacion);
        edtlatitud = findViewById(R.id.latitudAsignacion);
        edtlongitud = findViewById(R.id.longitudAsignacion);

        btnAsignacion = findViewById(R.id.btnAsignacion);
        btnAsignacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsignarMedidor("https://epapa-coli.es/tesis-epapacoli/insertar_asignacion.php");
            }
        });
    }

    public void obtenerCliente(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://epapa-coli.es/tesis-epapacoli/obtenerCliente.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray jsonArray = jsonObject.getJSONArray("cliente");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        id_cliente = jsonObject1.getInt("id");

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
    public void llenarspinnerMedidor() {
        String URL = "https://epapa-coli.es/tesis-epapacoli/listar_medidor.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    final ArrayList<GetSetMedidor> documento = new ArrayList<GetSetMedidor>();

                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("medidor");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                        GetSetMedidor p = new GetSetMedidor();
                        p.setId_medidor(jsonObject1.getInt("id_medidor"));
                        p.setCodigo_medidor(jsonObject1.getString("codigo_medidor"));
                        p.setMarca(jsonObject1.getString("marca"));
                        documento.add(p);

                    }
                    ArrayAdapter<GetSetMedidor> documentotipo = new ArrayAdapter<GetSetMedidor>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, documento);
                    medidorList.setAdapter(documentotipo);
                    medidorList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            id_medidor = documento.get(i).getId_medidor();
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
        requestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
            @Override
            public void onRequestFinished(Request<Object> request) {
                requestQueue.getCache().clear();
            }
        });
    }
    public void AsignarMedidor(String URL){
        if (edtubicacion.getText().toString().equals("")){
            edtubicacion.setError("El campo no puede estar vacío");
        }else if(edtlatitud.getText().toString().equals("")) {
            edtlatitud.setError("El campo no puede estar vacío");
        }else if(edtlongitud.getText().toString().equals("")) {
            edtlongitud.setError("El campo no puede estar vacío");
        }else {
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(getApplicationContext(), "Se registró exitosamente", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), HomeAdmin.class));
                    finish();
                    //sendMail();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "No se pudo registrar", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String, String> params = new HashMap<String, String>();
                    params.put("ubicacion", edtubicacion.getText().toString());
                    params.put("latitud", edtlatitud.getText().toString());
                    params.put("longitud", edtlongitud.getText().toString());
                    params.put("cliente", String.valueOf(id_cliente));
                    params.put("medidor", String.valueOf(id_medidor));


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