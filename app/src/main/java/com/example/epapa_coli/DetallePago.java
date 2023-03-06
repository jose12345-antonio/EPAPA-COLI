package com.example.epapa_coli;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
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

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class DetallePago extends AppCompatActivity {
    String user;
    TextView txtConsumoAnterior, txtConsumoActual, txtConsumo, txttotalMes, txttotalPagar, txtMesFactura, viewTerminos, txtaguapotable,txtalcantarillado,
            txtdesc, txtmedidordetalle;
    CheckBox chTerminoCondiciones;
    int valorconsumo, consumoActual, id_factura;
    String fecha, pagartotal, mensaje;
    Button btnPagar;
    String latitud, longitud;
    public static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_pago);
        user =  Preferences.obtenerPreferenceString(getApplicationContext(), Preferences.PREFERENCE_USUARIO_LOGIN);
        ObtenerCoordenadas();

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
                        id_factura = jsonObject1.getInt("id_factura");
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

                    if(partMes.equals("01")){
                        txtMesFactura.setText("Enero");
                    }else if(partMes.equals("02")){
                        txtMesFactura.setText("Febrero");
                    }else if(partMes.equals("03")){
                        txtMesFactura.setText("Marzo");
                    }else if(partMes.equals("04")){
                        txtMesFactura.setText("Abril");
                    }else if(partMes.equals("05")){
                        txtMesFactura.setText("Mayo");
                    }else if(partMes.equals("06")){
                        txtMesFactura.setText("Junio");
                    }else if(partMes.equals("07")){
                        txtMesFactura.setText("Julio");
                    }else if(partMes.equals("08")){
                        txtMesFactura.setText("Agosto");
                    }else if(partMes.equals("09")){
                        txtMesFactura.setText("Septiembre");
                    }else if(partMes.equals("10")){
                        txtMesFactura.setText("Octubre");
                    }else if(partMes.equals("11")){
                        txtMesFactura.setText("Noviembre");
                    }else if(partMes.equals("12")){
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

    public void responsee(String latitud, String longitud){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://epapa-coli.es/tesis-epapacoli/poligono.php?latitud="+latitud+"&longitud="+longitud, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    mensaje = jsonObject.getString("response");
                    if(mensaje.equals("FUERA DEL AREA")){
                        toastError();
                    }else if(mensaje.equals("DENTRO DEL AREA")){
                        toastVerify();
                        btnPagar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (chTerminoCondiciones.isChecked()){
                                    Bundle code = new Bundle();
                                    code.putString("pagar", String.valueOf(pagartotal));
                                    code.putString("id", String.valueOf(id_factura));
                                    Intent i = new Intent(DetallePago.this, CardPago.class);
                                    i.putExtras(code);
                                    startActivity(i);
                                    finish();
                                }else if(!chTerminoCondiciones.isChecked()){
                                    Toast.makeText(getApplicationContext(), "Aceptar términos y condiciones", Toast.LENGTH_SHORT).show();
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

    public void ObtenerCoordenadas() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(DetallePago.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
        } else {
            locationStart();
        }
    }

    private void locationStart() {
        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        DetallePago.Localizacion Local = new DetallePago.Localizacion();
        Local.setMainActivity(this);
        final boolean gpsEnabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gpsEnabled) {
            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(settingsIntent);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            return;
        }
        mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 20000, 20000, (LocationListener) Local);
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 20000, 20000, (LocationListener) Local);
        //latitud.setText("Localización agregada");
        //edtubicacion.setText("");
    }
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationStart();
                return;
            }
        }
    }
    public void setLocation(Location loc) {
        //Obtener la direccion de la calle a partir de la latitud y la longitud
        if (loc.getLatitude() != 0.0 && loc.getLongitude() != 0.0) {
            try {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> list = geocoder.getFromLocation(
                        loc.getLatitude(), loc.getLongitude(), 1);
                if (!list.isEmpty()) {
                    Address DirCalle = list.get(0);
          //          edtubicacion.setText(DirCalle.getAddressLine(0));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /* Aqui empieza la Clase Localizacion */
    public class Localizacion implements LocationListener {
        DetallePago mainActivity;

        public DetallePago getMainActivity() {
            return mainActivity;
        }

        public void setMainActivity(DetallePago mainActivity) {
            this.mainActivity = mainActivity;
        }

        @Override
        public void onLocationChanged(Location loc) {
            // Este metodo se ejecuta cada vez que el GPS recibe nuevas coordenadas
            // debido a la deteccion de un cambio de ubicacion
            loc.getLatitude();
            loc.getLongitude();
            latitud = String.valueOf(loc.getLatitude());
            longitud = String.valueOf(loc.getLongitude());
            responsee(latitud, longitud);
            //edtlatitud.setText(latitud);
            //edtlongitud.setText(longitud);
            this.mainActivity.setLocation(loc);
        }

        @Override
        public void onProviderDisabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es desactivado
            latitud = "GPS Desactivado";
        }

        @Override
        public void onProviderEnabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es activado
            latitud = "GPS Activado";
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch (status) {
                case LocationProvider.AVAILABLE:
                    Log.d("debug", "LocationProvider.AVAILABLE");
                    break;
                case LocationProvider.OUT_OF_SERVICE:
                    Log.d("debug", "LocationProvider.OUT_OF_SERVICE");
                    break;
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Log.d("debug", "LocationProvider.TEMPORARILY_UNAVAILABLE");
                    break;
            }
        }
    }

    public void toastError() {
        Toast toast = new Toast(this);
        View toast_layout = getLayoutInflater().inflate(R.layout.toast_error, (ViewGroup) findViewById(R.id.lytLayout));
        toast.setView(toast_layout);
        TextView textView = (TextView) toast_layout.findViewById(R.id.toastMessage);
        textView.setText("Su ubicación no está dentro de la ubicación del Cantón Colimes. \nNo puede proceder a pagar");
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

    public void toastVerify() {
        Toast toast = new Toast(this);
        View toast_layout = getLayoutInflater().inflate(R.layout.toast_verify, (ViewGroup) findViewById(R.id.lytLayoutverify));
        toast.setView(toast_layout);
        TextView textView = (TextView) toast_layout.findViewById(R.id.toastMessageSession);
        textView.setText("Su ubicación está dentro de la ubicación del Cantón Colimes. \nProceder a Pagar.");
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }
}