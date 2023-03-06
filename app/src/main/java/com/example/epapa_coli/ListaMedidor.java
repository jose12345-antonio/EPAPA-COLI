package com.example.epapa_coli;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.epapa_coli.Adapter.AdapterCliente;
import com.example.epapa_coli.Adapter.AdapterMedidor;
import com.example.epapa_coli.Model.GetSetCliente;
import com.example.epapa_coli.Model.GetSetMedidor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListaMedidor extends AppCompatActivity {

    List<GetSetMedidor> playerlist1;
    RecyclerView recyclerView;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    String user;
    Button btnRegistrarMedidor;
    AdapterMedidor adaptador;
    EditText edtBuscarMedidor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_medidor);

        user =  Preferences.obtenerPreferenceString(getApplicationContext(), Preferences.PREFERENCE_USUARIO_LOGIN);
        btnRegistrarMedidor = findViewById(R.id.btnRegistrarMedidor);
        btnRegistrarMedidor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegistroMedidor.class));
            }
        });
        recyclerView = findViewById(R.id.recyclerViewMedidor);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        playerlist1= new ArrayList<>();
        request = Volley.newRequestQueue(getApplicationContext());

        cargarservice1("https://epapa-coli.es/tesis-epapacoli/listar_medidor.php");
        edtBuscarMedidor = findViewById(R.id.edtBuscarMedidor);
        edtBuscarMedidor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filtrar(s.toString());
            }
        });
    }

    private void cargarservice1(String URL) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray1 = jsonObject.getJSONArray("medidor");
                            playerlist1.clear();
                            for (int i = 0; i < jsonArray1.length(); i++) {
                                JSONObject jsonObject1 = jsonArray1.getJSONObject(i);
                                int id = jsonObject1.getInt("id_medidor");
                                String codigo_medidor = jsonObject1.getString("codigo_medidor");
                                String fecha_registro = jsonObject1.getString("fecha_registro");
                                String marca = jsonObject1.getString("marca");
                                String tipo_material = jsonObject1.getString("tipo_material");
                                String medidas = jsonObject1.getString("medidas");
                                String diametro = jsonObject1.getString("diametro");
                                String nombre_estado = jsonObject1.getString("nombre_estado");

                                playerlist1.add(new GetSetMedidor(id, codigo_medidor, fecha_registro, marca, tipo_material, medidas, diametro, nombre_estado));
                            }

                            adaptador = new AdapterMedidor(playerlist1, ListaMedidor.this);
                            recyclerView.setAdapter(adaptador);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(stringRequest);
        requestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
            @Override
            public void onRequestFinished(Request<Object> request) {
                requestQueue.getCache().clear();
            }
        });

    }

    public void filtrar(String texto) {
        ArrayList<GetSetMedidor> filtrarLista = new ArrayList<>();

        for(GetSetMedidor contenido : playerlist1) {
            if(contenido.getCodigo_medidor().toLowerCase().contains(texto.toLowerCase()) || contenido.getMarca().toLowerCase().contains(texto.toLowerCase()) || contenido.getTipo_material().toLowerCase().contains(texto.toLowerCase())) {
                filtrarLista.add(contenido);
            }
        }

        adaptador.filtrar(filtrarLista);
    }
}