package com.example.epapa_coli;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.epapa_coli.Model.GetSetMedidor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AsigMedidor extends AppCompatActivity {

    AutoCompleteTextView medidorList;
    EditText edtubicacion, edtlatitud, edtlongitud;
    Button btnAsignacion;
    int id_medidor, id_cliente;
    public static final int REQUEST_CODE = 1;
    ImageView ubicacion;
    String latitud="", longitud="", direccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asig_medidor);

        llenarspinnerMedidor();
        id_cliente = Integer.parseInt(getIntent().getStringExtra("id"));
        medidorList = findViewById(R.id.medidorList);
        edtubicacion = findViewById(R.id.ubicacionAsignacion);
        edtlatitud = findViewById(R.id.latitudAsignacion);
        edtlongitud = findViewById(R.id.longitudAsignacion);
        ubicacion = findViewById(R.id.ubicacion);
        ubicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObtenerCoordenadas(v);
            }
        });
        btnAsignacion = findViewById(R.id.btnAsignacion);
        btnAsignacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsignarMedidor("https://epapa-coli.es/tesis-epapacoli/insertar_asignacion.php");
            }
        });
    }

    public void llenarspinnerMedidor() {
        String URL = "https://epapa-coli.es/tesis-epapacoli/listar_medidor.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    final ArrayList<GetSetMedidor> documento = new ArrayList<GetSetMedidor>();

                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("medidor");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                        GetSetMedidor p = new GetSetMedidor();
                        p.setId_medidor(jsonObject1.getInt("id_medidor"));
                        p.setCodigo_medidor(jsonObject1.getString("codigo_medidor"));
                        p.setMarca(jsonObject1.getString("marca"));
                        documento.add(p);

                    }
                    ArrayAdapter<GetSetMedidor> documentotipo = new ArrayAdapter<GetSetMedidor>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, documento);
                    medidorList.setAdapter(documentotipo);
                    medidorList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            id_medidor = documento.get(i).getId_medidor();
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
    public void AsignarMedidor(String URL){
        if (edtubicacion.getText().toString().equals("")){
            edtubicacion.setError("El campo no puede estar vacío");
        }else if(edtlatitud.getText().toString().equals("")) {
            edtlatitud.setError("El campo no puede estar vacío");
        }else if(edtlongitud.getText().toString().equals("")) {
            edtlongitud.setError("El campo no puede estar vacío");
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
                    params.put("ubicacion", edtubicacion.getText().toString());
                    params.put("latitud", edtlatitud.getText().toString());
                    params.put("longitud", edtlongitud.getText().toString());
                    params.put("cliente", String.valueOf(id_cliente));
                    params.put("medidor", String.valueOf(id_medidor));


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
    public void ObtenerCoordenadas(View view) {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AsigMedidor.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
        } else {
            locationStart();
        }
    }
    private void locationStart() {
        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        AsigMedidor.Localizacion Local = new AsigMedidor.Localizacion();
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
        mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, (LocationListener) Local);
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) Local);
        //latitud.setText("Localización agregada");
        edtubicacion.setText("");
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
                    edtubicacion.setText(DirCalle.getAddressLine(0));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /* Aqui empieza la Clase Localizacion */
    public class Localizacion implements LocationListener {
        AsigMedidor mainActivity;

        public AsigMedidor getMainActivity() {
            return mainActivity;
        }

        public void setMainActivity(AsigMedidor mainActivity) {
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

            edtlatitud.setText(latitud);
            edtlongitud.setText(longitud);
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

}