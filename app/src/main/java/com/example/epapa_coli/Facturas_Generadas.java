package com.example.epapa_coli;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.epapa_coli.Adapter.AdapterListFactura;
import com.example.epapa_coli.Adapter.AdapterMedidorCliente;
import com.example.epapa_coli.Model.GetSetFactura;
import com.example.epapa_coli.Model.GetSetMedidorCliente;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Facturas_Generadas extends AppCompatActivity {

    List<GetSetFactura> playerlist1;
    RecyclerView recyclerView;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    String user;
    Button btnRegistrarCliente;
    AdapterListFactura adaptador;
    TextView nombreAsig, cedulaAsig;
    EditText edtBuscarClientes;
    int id_cliente;
    String nombres, cedula;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facturas_generadas);
        user =  Preferences.obtenerPreferenceString(getApplicationContext(), Preferences.PREFERENCE_USUARIO_LOGIN);
        recyclerView = findViewById(R.id.recyclerViewFacturas);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        playerlist1= new ArrayList<>();
        request = Volley.newRequestQueue(getApplicationContext());
        String url = "https://epapa-coli.es/tesis-epapacoli/listFacturas.php?correo="+user;
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
                            JSONArray jsonArray1 = jsonObject.getJSONArray("facturas");
                            playerlist1.clear();
                            for (int i = 0; i < jsonArray1.length(); i++) {
                                JSONObject jsonObject1 = jsonArray1.getJSONObject(i);
                                int id = jsonObject1.getInt("id_factura");
                                String fecha = jsonObject1.getString("fecha");
                                String total = jsonObject1.getString("total");
                                String numero_medidor = jsonObject1.getString("numero_medidor");
                                String estado_pag = jsonObject1.getString("estado_pago");

                                playerlist1.add(new GetSetFactura(id, fecha, total, estado_pag, numero_medidor));
                            }
                            adaptador = new AdapterListFactura(playerlist1, Facturas_Generadas.this);
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