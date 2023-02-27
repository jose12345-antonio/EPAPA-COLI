package com.example.epapa_coli;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class updateUser extends AppCompatActivity {

    EditText edtCedulaUpdate, edtCorreoUpdate, edtCelularUpdate;
    Button updateButton;
    String user, cedula;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);
        user =  Preferences.obtenerPreferenceString(getApplicationContext(), Preferences.PREFERENCE_USUARIO_LOGIN);
        edtCedulaUpdate = findViewById(R.id.edtCedulaUpdate);
        edtCorreoUpdate = findViewById(R.id.edtCorreoUpdate);
        edtCelularUpdate = findViewById(R.id.edtCelularUpdate);
        obtenerUsuario();
        updateButton = findViewById(R.id.btnUsersUpdate);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtCorreoUpdate.getText().toString().equals("")){
                    edtCorreoUpdate.setError("El campo está vacío");
                }else if(edtCelularUpdate.getText().toString().equals("")){
                    edtCelularUpdate.setError("El campo está vacío");
                }else{
                    UpdateCorreo("https://epapa-coli.es/tesis-epapacoli/updateCorreo.php?usuario="+user+"&correo="+edtCorreoUpdate.getText().toString()+"&celular="+edtCelularUpdate.getText().toString());
                }
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
                        edtCedulaUpdate.setText(jsonObject1.getString("cedula"));
                        edtCorreoUpdate.setText(jsonObject1.getString("email"));
                        edtCelularUpdate.setText(jsonObject1.getString("celular"));
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

    public void UpdateCorreo(String URL){
        final RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Correo actualizado con éxito", Toast.LENGTH_SHORT).show();
                //sendMail();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getApplicationContext(), "No se pudo registrar", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);
        queue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
            @Override
            public void onRequestFinished(Request<Object> request) {
                queue.getCache().clear();
            }
        });
    }

}