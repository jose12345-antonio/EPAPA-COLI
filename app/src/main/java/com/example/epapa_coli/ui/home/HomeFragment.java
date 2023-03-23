package com.example.epapa_coli.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.epapa_coli.Adapter.AdapterListPago;
import com.example.epapa_coli.DetallePago;
import com.example.epapa_coli.Facturas_Generadas;
import com.example.epapa_coli.Model.GetSetPago;
import com.example.epapa_coli.Preferences;
import com.example.epapa_coli.R;
import com.example.epapa_coli.databinding.FragmentHomeBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {

    CardView cardPago;
    List<GetSetPago> playerlist1;
    RecyclerView recyclerView;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    String user;
    int estado;
    TextView txtestado, txttotalFactura, txtNombresHome, txtMensaje, txtmedidor, txtcodigo;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        user =  Preferences.obtenerPreferenceString(getContext(), Preferences.PREFERENCE_USUARIO_LOGIN);
        txtestado = root.findViewById(R.id.estadoPago);
        txttotalFactura = root.findViewById(R.id.totalFactura);
        txtNombresHome = root.findViewById(R.id.txtNombresHome);
        txtMensaje = root.findViewById(R.id.txtMensaje);
        txtmedidor = root.findViewById(R.id.medidor);
        cardPago= root.findViewById(R.id.cardPago);
        obtenerUsuario();
        obtenerFactura();

        recyclerView = root.findViewById(R.id.pagoHome);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        playerlist1= new ArrayList<>();
        request = Volley.newRequestQueue(getContext());

        cargarservice1();


        return root;
    }

    private void cargarservice1() {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://epapa-coli.es/tesis-epapacoli/listar_pagos.php?correo="+user,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray1 = jsonObject.getJSONArray("pago");
                            int contador = jsonArray1.length();
                            if(contador==0){
                                txtMensaje.setVisibility(View.VISIBLE);
                                recyclerView.setVisibility(View.GONE);
                            }else{
                                txtMensaje.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);

                                //playerlist1.clear();
                                for (int i = 0; i < jsonArray1.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray1.getJSONObject(i);
                                    int id = jsonObject1.getInt("id_pago");
                                    Double valorPago = jsonObject1.getDouble("valor");
                                    String codigo_transaccion = jsonObject1.getString("codigo_transaccion");
                                    String fechaPago = jsonObject1.getString("fecha_pago");
                                    String fechaFactura = jsonObject1.getString("fecha_factura");
                                    String razon_social = jsonObject1.getString("razon_social");

                                    playerlist1.add(new GetSetPago(id, valorPago, codigo_transaccion, fechaPago, fechaFactura, razon_social));
                                }
                                AdapterListPago adaptador = new AdapterListPago(playerlist1, getContext());
                                recyclerView.setAdapter(adaptador);
                            }


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

    private void obtenerFactura() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://epapa-coli.es/tesis-epapacoli/mostrarFactura.php?correo="+user, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("facturas");
                    int contador = jsonArray.length();
                    if(contador==0){
                        txtestado.setText("No mantiene factura generada");
                        txttotalFactura.setText("$ 0,00");
                        cardPago.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                    Toast.makeText(getContext(), "No mantiene factura generada", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else{
                        Double suma=0.0;
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            estado = jsonObject1.getInt("estado_pago");
                            Double valor = jsonObject1.getDouble("total");
                            txtmedidor.setText("Nro° "+jsonObject1.getString("numero_medidor"));
                            txttotalFactura.setText("$ "+obtieneDosDecimales(valor));
                        }

                        if (estado==1){
                            txtestado.setText("Pagado");
                            txttotalFactura.setText("$ 0.00");
                        }else if(estado==0) {
                            txtestado.setText("Pendiente");
                        }
                        cardPago.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (estado==1){
                                    Toast.makeText(getContext(), "Pago ya fue realizado", Toast.LENGTH_SHORT).show();
                                    txtestado.setText("Pagado");
                                }else if(estado==0) {
                                    txtestado.setText("Pendiente");
                                    startActivity(new Intent(getContext(), Facturas_Generadas.class));
                                }
                            }
                        });


                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
        requestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
            @Override
            public void onRequestFinished(Request<Object> request) {
                requestQueue.getCache().clear();
            }
        });
    }

    private void obtenerUsuario() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://epapa-coli.es/tesis-epapacoli/listarPerfilUsuario.php?correo="+user, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray jsonArray = jsonObject.getJSONArray("usuario");

                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        txtNombresHome.setText("Hola, "+jsonObject1.getString("nombres"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
        requestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
            @Override
            public void onRequestFinished(Request<Object> request) {
                requestQueue.getCache().clear();
            }
        });
    }

    private String obtieneDosDecimales(double valor){
        DecimalFormat format = new DecimalFormat();
        format.setMaximumFractionDigits(2); //Define 2 decimales.
        return format.format(valor);
    }
}