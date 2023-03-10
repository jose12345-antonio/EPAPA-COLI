package com.example.epapa_coli;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.epapa_coli.Model.GetSetDiametro;
import com.example.epapa_coli.Model.GetSetMarca;
import com.example.epapa_coli.Model.GetSetMedidas;
import com.example.epapa_coli.Model.GetSetMedidor;
import com.example.epapa_coli.Model.GetSetTipo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

public class RegistroMedidor extends AppCompatActivity {

    EditText edtCodigo, fechaR;
    AutoCompleteTextView edtMarca, edtTipo, edtMedidas, edtDiametro;
    Button btnMedidor;
    DatePickerDialog.OnDateSetListener onDateSetListener;
    String fecha;
    int idmarca, idtipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_medidor);

        Calendar cal = new GregorianCalendar();
        Date date = cal.getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formatteDate = df.format(date);

        Variables();
        fechaR.setText(formatteDate);
        Fecha();
        llenarspinnerMarca();

        llenarspinnerTipo();

    }
    private void Fecha(){
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        fechaR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(RegistroMedidor.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        onDateSetListener, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() + 1000);
                datePickerDialog.show();
            }
        });
        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                i1 = i1 + 1;
                fecha = i + "-" + i1 + "-" + i2;
                fechaR.setText(fecha);
            }
        };
    }

    private void Variables(){
        edtCodigo = findViewById(R.id.codigoMedidor);
        edtMarca = findViewById(R.id.marcaMedidor2);
        edtTipo = findViewById(R.id.tipoMaterialMedidor);

        fechaR = findViewById(R.id.fechaR);

        btnMedidor = findViewById(R.id.btnMedidor);
        btnMedidor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegistrarMedidor("https://epapa-coli.es/tesis-epapacoli/insertar_medidor.php");
            }
        });

    }

    public void RegistrarMedidor(String URL){
        if (edtCodigo.getText().toString().equals("")){
            edtCodigo.setError("El campo no puede estar vacío");
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
                    params.put("codigo", edtCodigo.getText().toString());
                    params.put("marca", String.valueOf(idmarca));
                    params.put("tipo", String.valueOf(idtipo));
                    params.put("fecha", fechaR.getText().toString());

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

    public void llenarspinnerMarca() {
        String URL = "https://epapa-coli.es/tesis-epapacoli/listmarca.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    final ArrayList<GetSetMarca> documento = new ArrayList<GetSetMarca>();

                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("medidor");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                        GetSetMarca p = new GetSetMarca();
                        p.setId(jsonObject1.getInt("id"));
                        p.setNombre(jsonObject1.getString("nombre"));

                        documento.add(p);

                    }
                    ArrayAdapter<GetSetMarca> documentotipo = new ArrayAdapter<GetSetMarca>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, documento);
                    edtMarca.setAdapter(documentotipo);
                    edtMarca.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            idmarca = documento.get(i).getId();
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

    public void llenarspinnerTipo() {
        String URL = "https://epapa-coli.es/tesis-epapacoli/listtipo.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    final ArrayList<GetSetTipo> documento = new ArrayList<GetSetTipo>();

                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("medidor");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                        GetSetTipo p = new GetSetTipo();
                        p.setId(jsonObject1.getInt("id"));
                        p.setNombre(jsonObject1.getString("nombre"));

                        documento.add(p);

                    }
                    ArrayAdapter<GetSetTipo> documentotipo = new ArrayAdapter<GetSetTipo>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, documento);
                    edtTipo.setAdapter(documentotipo);
                    edtTipo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            idtipo = documento.get(i).getId();
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
}