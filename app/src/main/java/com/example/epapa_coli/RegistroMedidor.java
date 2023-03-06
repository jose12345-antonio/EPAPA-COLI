package com.example.epapa_coli;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
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

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class RegistroMedidor extends AppCompatActivity {

    EditText edtCodigo, edtMarca, edtTipo, edtMedidas, edtDiametro, fechaR;
    Button btnMedidor;
    DatePickerDialog.OnDateSetListener onDateSetListener;
    String fecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_medidor);
        Variables();
        Fecha();

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
        edtMedidas = findViewById(R.id.medidasMedidor);
        edtDiametro = findViewById(R.id.diametroMedidor);
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
        }else if(edtMarca.getText().toString().equals("")) {
            edtMarca.setError("El campo no puede estar vacío");
        }else if(edtTipo.getText().toString().equals("")) {
            edtTipo.setError("El campo no puede estar vacío");
        }else if(edtMedidas.getText().toString().equals("")) {
            edtMedidas.setError("El campo no puede estar vacío");
        }else if(edtDiametro.getText().toString().equals("")) {
            edtDiametro.setError("El campo no puede estar vacío");
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
                    params.put("marca", edtMarca.getText().toString());
                    params.put("tipo", edtTipo.getText().toString());
                    params.put("medidas", edtMedidas.getText().toString());
                    params.put("diametro", edtDiametro.getText().toString());
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
}