package com.example.epapa_coli;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Registro_Lectura extends AppCompatActivity {

    EditText edtlecturaactual, edtconsumo, edtpotable, edtalcantarillado, edtTotal, lecturaanteriorRe, fechaRe;
    String id_cliente, id_tipoUsuario;
    int total;
    int estadoActual,lecturaAnt;
    double desc, totalDesc, totalPagar,potable, alcantarillado;
    Button btnLecturaRegister;
    TextView txtMen;
    DatePickerDialog.OnDateSetListener onDateSetListener;
    String fecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_lectura);
        id_cliente = getIntent().getStringExtra("id");
        id_tipoUsuario = getIntent().getStringExtra("id_tipoUsuario");
        txtMen = findViewById(R.id.texttipoUsuario);
        if(id_tipoUsuario.equals("1") || id_tipoUsuario.equals("2")){
            desc = 0.5;
            txtMen.setText("El cliente mantiene un descuento del 50% en su consumo de agua potable y alcantarillado");
        }else{
            desc = 0.0;
            txtMen.setText("El cliente no mantiene descuento en su consumo de agua potable y alcantarillado");
        }


        obtenerUltimaLectura();
        edtlecturaactual = findViewById(R.id.lecturaactualRe);
        edtconsumo = findViewById(R.id.consumom3Re);
        edtpotable = findViewById(R.id.aguapotable);
        edtTotal = findViewById(R.id.edtTotal);
        edtalcantarillado = findViewById(R.id.alcantarilladoRe);
        fechaRe = findViewById(R.id.fechaRe);
        lecturaanteriorRe = findViewById(R.id.lecturaanteriorRe);
        Fecha();
        edtlecturaactual.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String actu = edtlecturaactual.getText().toString();
                if(!actu.equals("")) {
                    estadoActual = Integer.parseInt(actu);
                    total = estadoActual - lecturaAnt;
                    //Calculo para el agua potable
                    //Cobro $0,30 por cada m3

                    potable = (double) (total * 0.3);
                    //Calculo para el alcantarillado
                    //Cobro $0,14
                    alcantarillado = (double) (total * 0.14);
                    totalDesc = (potable + alcantarillado) * desc;
                    totalPagar = (double) (potable + alcantarillado - totalDesc);


                    if (estadoActual >= lecturaAnt) {
                        edtconsumo.setText("" + total);
                        edtpotable.setText("" + obtieneDosDecimales(potable));
                        edtalcantarillado.setText("" + obtieneDosDecimales(alcantarillado));
                        edtTotal.setText("$ " + String.valueOf(obtieneDosDecimales(totalPagar)));
                    } else {
                        edtconsumo.setText("");
                        edtpotable.setText("");
                        edtalcantarillado.setText("");
                        edtTotal.setText("$ 0");
                    }
                }else{
                    edtconsumo.setText("");
                    edtpotable.setText("");
                    edtalcantarillado.setText("");
                    edtTotal.setText("$ 0");
                }
            }
        });

        btnLecturaRegister = findViewById(R.id.btnLecturaRegister);
        btnLecturaRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegistrarLectura("https://epapa-coli.es/tesis-epapacoli/insertar_lecturas.php");
            }
        });

    }
    private void Fecha(){
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        fechaRe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(Registro_Lectura.this,
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
                fechaRe.setText(fecha);
            }
        };
    }

    private String obtieneDosDecimales(double valor){
        DecimalFormat format = new DecimalFormat();
        format.setMaximumFractionDigits(2); //Define 2 decimales.
        return format.format(valor);
    }

    private void obtenerUltimaLectura(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://epapa-coli.es/tesis-epapacoli/obtener_lectura.php?id="+id_cliente, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("lectura");
                    int contador = jsonArray.length();
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            lecturaAnt = jsonObject1.getInt("valor");
                        }
                        if(lecturaAnt != 0){
                            lecturaanteriorRe.setText(""+lecturaAnt);
                            lecturaanteriorRe.setEnabled(false);
                        }else{
                            lecturaanteriorRe.setText("0");
                            lecturaanteriorRe.setEnabled(true);
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

    public void RegistrarLectura(String URL){
        if (edtlecturaactual.getText().toString().equals("")){
            edtlecturaactual.setError("El campo no puede estar vacío");
        }else if(edtconsumo.getText().toString().equals("")) {
            edtconsumo.setError("El campo no puede estar vacío");
        }else if(edtpotable.getText().toString().equals("")) {
            edtpotable.setError("El campo no puede estar vacío");
        }else if(edtalcantarillado.getText().toString().equals("")) {
            edtalcantarillado.setError("El campo no puede estar vacío");
        }else {
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(getApplicationContext(), "Se registró exitosamente la lectura", Toast.LENGTH_SHORT).show();
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
                    params.put("lecturaActual", edtlecturaactual.getText().toString());
                    params.put("consumo", edtconsumo.getText().toString());
                    params.put("potable", edtpotable.getText().toString());
                    params.put("alcantarillado", edtalcantarillado.getText().toString());
                    params.put("id", id_cliente);
                    params.put("pagar", String.valueOf(totalPagar));
                    params.put("fecha", fechaRe.getText().toString());
                    System.out.println(params);
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