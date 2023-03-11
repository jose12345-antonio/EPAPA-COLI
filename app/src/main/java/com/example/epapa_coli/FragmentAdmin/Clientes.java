package com.example.epapa_coli.FragmentAdmin;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.epapa_coli.Adapter.AdapterListPago;
import com.example.epapa_coli.Model.GetSetCliente;
import com.example.epapa_coli.Model.GetSetPago;
import com.example.epapa_coli.Preferences;
import com.example.epapa_coli.R;
import com.example.epapa_coli.Registro_Cliente;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Clientes extends Fragment {

    List<GetSetCliente> playerlist1;
    RecyclerView recyclerView;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    String user;
    Button btnRegistrarCliente;
    AdapterCliente adaptador;
    EditText edtBuscarClientes;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_clientes, container, false);
        user =  Preferences.obtenerPreferenceString(getContext(), Preferences.PREFERENCE_USUARIO_LOGIN);
        btnRegistrarCliente = view.findViewById(R.id.btnRegistrarCliente);
        btnRegistrarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Registro_Cliente.class));
            }
        });
        recyclerView = view.findViewById(R.id.recyclerViewCliente);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        playerlist1= new ArrayList<>();
        request = Volley.newRequestQueue(getContext());

        cargarservice1("https://epapa-coli.es/tesis-epapacoli/listar_clientes.php");
        edtBuscarClientes = view.findViewById(R.id.edtBuscarClientes);
        edtBuscarClientes.addTextChangedListener(new TextWatcher() {
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
        return view;
    }

    private void cargarservice1(String URL) {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray1 = jsonObject.getJSONArray("clientes");
                            playerlist1.clear();
                            for (int i = 0; i < jsonArray1.length(); i++) {
                                JSONObject jsonObject1 = jsonArray1.getJSONObject(i);
                                int id = jsonObject1.getInt("id_cliente");
                                String nobre_documento = jsonObject1.getString("nombre_documento");
                                String numero_cedula = jsonObject1.getString("numero_documento");
                                String nombres = jsonObject1.getString("nombres");
                                String apellidos = jsonObject1.getString("apellidos");
                                String direccion = jsonObject1.getString("direccion");
                                String fecha = jsonObject1.getString("fecha_nacimiento");
                                String tipousuario = jsonObject1.getString("nombre_tipoUsuario");
                                int id_tipoUsuario = jsonObject1.getInt("id_tipoUsuario");
                                String codigo_unico = jsonObject1.getString("codigo_unico");
                                String categoria = jsonObject1.getString("categoria");

                                playerlist1.add(new GetSetCliente(id, numero_cedula, nombres, apellidos, direccion, fecha, tipousuario, id_tipoUsuario, nobre_documento, codigo_unico, categoria));
                            }
                                 adaptador = new AdapterCliente(playerlist1, getContext());
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
        ArrayList<GetSetCliente> filtrarLista = new ArrayList<>();

        for(GetSetCliente contenido : playerlist1) {
            if(contenido.getNombres().toLowerCase().contains(texto.toLowerCase()) || contenido.getApellidos().toLowerCase().contains(texto.toLowerCase()) || contenido.getNumero_cedula().toLowerCase().contains(texto.toLowerCase())) {
                filtrarLista.add(contenido);
            }
        }

        adaptador.filtrar(filtrarLista);
    }
}