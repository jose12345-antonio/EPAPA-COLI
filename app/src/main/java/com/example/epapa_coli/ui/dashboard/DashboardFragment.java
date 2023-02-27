package com.example.epapa_coli.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.epapa_coli.Adapter.AdapterListPago;
import com.example.epapa_coli.Adapter.AdapterListPagoPDF;
import com.example.epapa_coli.Model.GetSetPago;
import com.example.epapa_coli.Preferences;
import com.example.epapa_coli.R;
import com.example.epapa_coli.databinding.FragmentDashboardBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    List<GetSetPago> playerlist1;
    RecyclerView recyclerView;
    RequestQueue request;
    TextView txtMensajex;
    JsonObjectRequest jsonObjectRequest;
    String user;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        user =  Preferences.obtenerPreferenceString(getContext(), Preferences.PREFERENCE_USUARIO_LOGIN);

        txtMensajex = root.findViewById(R.id.txtMensajex);
        recyclerView = root.findViewById(R.id.pagoDashboard);
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
                            if (contador==0){
                                txtMensajex.setVisibility(View.VISIBLE);
                                recyclerView.setVisibility(View.GONE);
                            }else{
                                txtMensajex.setVisibility(View.GONE);
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
                                AdapterListPagoPDF adaptador = new AdapterListPagoPDF(playerlist1, getContext());
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


}