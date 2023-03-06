package com.example.epapa_coli;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
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
import com.example.epapa_coli.Adapter.AdapterMedidorCliente;
import com.example.epapa_coli.Model.GetSetCliente;
import com.example.epapa_coli.Model.GetSetMedidorCliente;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Lista_medidores extends AppCompatActivity {

    List<GetSetMedidorCliente> playerlist1;
    RecyclerView recyclerView;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    String user;
    Button btnRegistrarCliente;
    AdapterMedidorCliente adaptador;
    EditText edtBuscarClientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_medidores);
        recyclerView = findViewById(R.id.recyclerViewListaMedidor);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        playerlist1= new ArrayList<>();
        request = Volley.newRequestQueue(getApplicationContext());
        String url = "https://epapa-coli.es/tesis-epapacoli/listar_medidor_cliente.php?id_cliente=1";
        cargarservice1(url);
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
                                int id = jsonObject1.getInt("id_asignacion");
                                String ubicacion = jsonObject1.getString("ubicacion");
                                String latitud = jsonObject1.getString("latitud");
                                String longitud = jsonObject1.getString("longitud");
                                String codigo_medidor = jsonObject1.getString("codigo_medidor");
                                String marca = jsonObject1.getString("marca");
                                String tipo_material = jsonObject1.getString("tipo_material");
                                String medidas = jsonObject1.getString("medidas");
                                String diametro = jsonObject1.getString("diametro");


                                playerlist1.add(new GetSetMedidorCliente(id, ubicacion, latitud, longitud, codigo_medidor, marca, tipo_material, medidas, diametro));
                            }
                            adaptador = new AdapterMedidorCliente(playerlist1, getApplicationContext());
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
}