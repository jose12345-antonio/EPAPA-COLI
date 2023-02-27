package com.example.epapa_coli;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class DetallePago extends AppCompatActivity {
    String user;
    TextView txtConsumoAnterior, txtConsumoActual, txtConsumo, txttotalMes, txttotalPagar, txtMesFactura, viewTerminos, txtaguapotable,txtalcantarillado,
            txtdesc, txtmedidordetalle;
    CheckBox chTerminoCondiciones;
    int valorconsumo, consumoActual;
    String fecha, pagartotal;
    Button btnPagar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_pago);
        user =  Preferences.obtenerPreferenceString(getApplicationContext(), Preferences.PREFERENCE_USUARIO_LOGIN);
        txtConsumoAnterior = findViewById(R.id.consumoAnterior);
        txtConsumoActual = findViewById(R.id.consumoActual);
        txtConsumo = findViewById(R.id.totalConsumo);
        txttotalMes = findViewById(R.id.totalConsumoMes);

        txtaguapotable = findViewById(R.id.txtaguapotable);
        txtalcantarillado = findViewById(R.id.txtalcantarillado);
        txtdesc = findViewById(R.id.txtdesc);

        txttotalPagar = findViewById(R.id.totalPagar);
        txtMesFactura = findViewById(R.id.txtMesFactura);
        txtmedidordetalle = findViewById(R.id.txtmedidordetalle);
        viewTerminos = findViewById(R.id.viewTerminos);
        viewTerminos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View alertCustomDialog = LayoutInflater.from(DetallePago.this).inflate(R.layout.dialog_termino, null);
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(DetallePago.this);

                alertDialog.setView(alertCustomDialog);

                Button btn = (Button) alertCustomDialog.findViewById(R.id.btnCancelar);

                final AlertDialog alertDialog1 = alertDialog.create();
                alertDialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alertDialog1.show();
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog1.cancel();
                        Toast.makeText(getApplicationContext(), "Gracias por revisar los términos y condiciones", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });
        chTerminoCondiciones = findViewById(R.id.checTerminoCondiciones);
        btnPagar = findViewById(R.id.btnPagar);

        btnPagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chTerminoCondiciones.isChecked()){
                    Bundle code = new Bundle();
                    code.putString("pagar", String.valueOf(pagartotal));
                    Intent i = new Intent(DetallePago.this, CardPago.class);
                    i.putExtras(code);
                    startActivity(i);
                    finish();
                }else if(!chTerminoCondiciones.isChecked()){
                    Toast.makeText(getApplicationContext(), "Aceptar términos y condiciones", Toast.LENGTH_SHORT).show();
                }
            }
        });
        obtenerFactura();
    }


    private void obtenerFactura() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://epapa-coli.es/tesis-epapacoli/mostrarFactura.php?correo="+user, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray jsonArray = jsonObject.getJSONArray("facturas");

                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        txtConsumoActual.setText(jsonObject1.getString("valor_lectura"));
                        consumoActual = jsonObject1.getInt("valor_lectura");
                        txtConsumo.setText(jsonObject1.getString("consumo_m3"));
                        valorconsumo = jsonObject1.getInt("consumo_m3");
                        txttotalMes.setText("$ "+jsonObject1.getString("total"));
                        txttotalPagar.setText("$ "+jsonObject1.getString("total"));
                        pagartotal = jsonObject1.getString("total");
                        txtaguapotable.setText("$ "+jsonObject1.getString("agua_potable"));
                        txtalcantarillado.setText("$ "+jsonObject1.getString("alcantarillado"));
                        fecha = jsonObject1.getString("fecha");
                        txtmedidordetalle.setText(jsonObject1.getString("numero_medidor"));
                    }

                    String[] partsFechaHora = fecha.split(" ");
                    String partFecha = partsFechaHora[0];
                    String[] partsFecha = partFecha.split("-");
                    String partMes = partsFecha[1];


                    if(partMes=="1"){
                        txtMesFactura.setText("Enero");
                    }else if(partMes=="2"){
                        txtMesFactura.setText("Febrero");
                    }else if(partMes=="3"){
                        txtMesFactura.setText("Marzo");
                    }else if(partMes=="4"){
                        txtMesFactura.setText("Abril");
                    }else if(partMes=="5"){
                        txtMesFactura.setText("Mayo");
                    }else if(partMes=="6"){
                        txtMesFactura.setText("Junio");
                    }else if(partMes=="7"){
                        txtMesFactura.setText("Julio");
                    }else if(partMes=="8"){
                        txtMesFactura.setText("Agosto");
                    }else if(partMes=="9"){
                        txtMesFactura.setText("Septiembre");
                    }else if(partMes=="10"){
                        txtMesFactura.setText("Octubre");
                    }else if(partMes=="11"){
                        txtMesFactura.setText("Noviembre");
                    }else if(partMes=="12"){
                        txtMesFactura.setText("Diciembre");
                    }


                    int totalAnterior = consumoActual-valorconsumo;
                    txtConsumoAnterior.setText(""+totalAnterior);

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
}