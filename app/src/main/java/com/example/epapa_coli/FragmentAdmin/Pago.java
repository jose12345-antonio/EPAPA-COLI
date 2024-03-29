package com.example.epapa_coli.FragmentAdmin;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.epapa_coli.Adapter.AdapterCliente;
import com.example.epapa_coli.Adapter.AdapterFacturaAdmin;
import com.example.epapa_coli.Model.GetSetCliente;
import com.example.epapa_coli.Model.GetSetFacturaHome;
import com.example.epapa_coli.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class Pago extends Fragment {

    List<GetSetFacturaHome> playerlist1;
    List<GetSetFacturaHome> playerlist2;
    RecyclerView recyclerView;
    RequestQueue request;
    String dataURL;
    private int dia,mes,ano;
    JsonObjectRequest jsonObjectRequest;
    String user;
    EditText edtFechaInicio, edtFechaFin;
    Button btnBuscar;
    TextView textTotal2, contaor;
    Double total;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_pago, container, false);

        textTotal2 = view.findViewById(R.id.textTotal2);
        contaor = view.findViewById(R.id.contaor);

        ObtenerTotal();
        edtFechaInicio = view.findViewById(R.id.edtFechaInicio);
        edtFechaInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                dia=c.get(Calendar.DAY_OF_MONTH);
                mes=c.get(Calendar.MONTH);
                ano=c.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        edtFechaInicio.setText(year+"-"+(month+1)+"-"+dayOfMonth);
                    }
                },ano,mes,dia);
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });
        btnBuscar = view.findViewById(R.id.btnBuscar);
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtFechaInicio.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "El campo Fecha Inicio no puede estar vacío", Toast.LENGTH_SHORT).show();
                } else {
                    playerlist1.clear();
                    String ESCUDOS_URL2 = "https://epapa-coli.es/tesis-epapacoli/listarFacturasBusqueda.php?fechaInicio=" + edtFechaInicio.getText().toString();
                    cargarservice2(ESCUDOS_URL2);
                    ObtenerTotal2();
                }
            }
        });
        recyclerView = view.findViewById(R.id.recyclerViewPago);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        playerlist1= new ArrayList<>();
        request = Volley.newRequestQueue(getContext());
        cargarservice1("https://epapa-coli.es/tesis-epapacoli/listarFacturas.php");
        return view;
    }

    private void ObtenerTotal() {
        String URL3 = "https://epapa-coli.es/tesis-epapacoli/ObtenerTotalPagoDiario.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL3, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("totalDia");

                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        total = jsonObject1.getDouble("total");
                        System.out.println("$ "+obtieneDosDecimales(total));
                    }
                    textTotal2.setText("Total: $ "+obtieneDosDecimales(total));
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
        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
        requestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
            @Override
            public void onRequestFinished(Request<Object> request) {
                requestQueue.getCache().clear();
            }
        });
    }

    private void ObtenerTotal2() {
        String URL3 = "https://epapa-coli.es/tesis-epapacoli/ObtenerTotalPagoDiarioBusqueda.php?fechaInicio="+edtFechaInicio.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL3, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("totalDia");

                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        total = jsonObject1.getDouble("total");
                        System.out.println("$ "+obtieneDosDecimales(total));
                    }
                    textTotal2.setText("Total: $ "+obtieneDosDecimales(total));
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
        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
        requestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
            @Override
            public void onRequestFinished(Request<Object> request) {
                requestQueue.getCache().clear();
            }
        });
    }


    private void cargarservice1(String URL) {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray1 = jsonObject.getJSONArray("facturasPago");
                            int contador = jsonArray1.length();
                            if(contador!=0){
                                recyclerView.setVisibility(View.VISIBLE);
                                contaor.setVisibility(View.GONE);
                            }else{
                                recyclerView.setVisibility(View.GONE);
                                contaor.setVisibility(View.VISIBLE);
                            }
                            System.out.println(jsonArray1);
                            for (int i = 0; i < jsonArray1.length(); i++) {
                                JSONObject jsonObject1 = jsonArray1.getJSONObject(i);
                                String id_pago = jsonObject1.getString("id_pago");
                                String codigotransaccion = jsonObject1.getString("codigo_transaccion");
                                String id_transaccion = jsonObject1.getString("id_transaccion");
                                String id_factura = jsonObject1.getString("id_factura");
                                String estado_pago = jsonObject1.getString("estado_pago");
                                String valor_lectura = jsonObject1.getString("valor_lectura");
                                String consumo_m3 = jsonObject1.getString("consumo_m3");
                                String estado_lectura = jsonObject1.getString("estado_lectura");
                                String valor_pago = jsonObject1.getString("valorPago");
                                String fechaPago = jsonObject1.getString("fechaPago");
                                String fecha_factura = jsonObject1.getString("fechaFactura");
                                String totalFactura = jsonObject1.getString("totalFactura");
                                String fecha_lectura = jsonObject1.getString("fecha_lectura");
                                String numero_cedula = jsonObject1.getString("numero_cedula");
                                String codigo_unico = jsonObject1.getString("codigo_unico");
                                String nombres = jsonObject1.getString("nombres");

                                playerlist1.add(new GetSetFacturaHome(id_pago, codigotransaccion, id_transaccion, id_factura, estado_pago,valor_lectura, consumo_m3, estado_lectura, valor_pago, fechaPago, fecha_factura, totalFactura, fecha_lectura, numero_cedula, codigo_unico, nombres));
                            }
                            AdapterFacturaAdmin adaptador = new AdapterFacturaAdmin(playerlist1, getContext());
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
    private void cargarservice2(String URL) {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray1 = jsonObject.getJSONArray("facturasPago");

                            int contador = jsonArray1.length();
                            if(contador!=0){
                                recyclerView.setVisibility(View.VISIBLE);
                                contaor.setVisibility(View.GONE);
                            }else{
                                recyclerView.setVisibility(View.GONE);
                                contaor.setVisibility(View.VISIBLE);
                            }
                            System.out.println(jsonArray1);
                            for (int i = 0; i < jsonArray1.length(); i++) {
                                JSONObject jsonObject1 = jsonArray1.getJSONObject(i);
                                String id_pago = jsonObject1.getString("id_pago");
                                String codigotransaccion = jsonObject1.getString("codigo_transaccion");
                                String id_transaccion = jsonObject1.getString("id_transaccion");
                                String id_factura = jsonObject1.getString("id_factura");
                                String estado_pago = jsonObject1.getString("estado_pago");
                                String valor_lectura = jsonObject1.getString("valor_lectura");
                                String consumo_m3 = jsonObject1.getString("consumo_m3");
                                String estado_lectura = jsonObject1.getString("estado_lectura");
                                String valor_pago = jsonObject1.getString("valorPago");
                                String fechaPago = jsonObject1.getString("fechaPago");
                                String fecha_factura = jsonObject1.getString("fechaFactura");
                                String totalFactura = jsonObject1.getString("totalFactura");
                                String fecha_lectura = jsonObject1.getString("fecha_lectura");
                                String numero_cedula = jsonObject1.getString("numero_cedula");
                                String codigo_unico = jsonObject1.getString("codigo_unico");
                                String nombres = jsonObject1.getString("nombres");

                                playerlist1.add(new GetSetFacturaHome(id_pago, codigotransaccion, id_transaccion, id_factura, estado_pago,valor_lectura, consumo_m3, estado_lectura, valor_pago, fechaPago, fecha_factura, totalFactura, fecha_lectura, numero_cedula, codigo_unico, nombres));
                            }
                            AdapterFacturaAdmin adaptador = new AdapterFacturaAdmin(playerlist1, getContext());
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

    private String obtieneDosDecimales(double valor){
        DecimalFormat format = new DecimalFormat();
        format.setMaximumFractionDigits(2); //Define 2 decimales.
        return format.format(valor);
    }

}