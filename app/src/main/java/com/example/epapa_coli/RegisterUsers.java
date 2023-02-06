package com.example.epapa_coli;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
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
import com.example.epapa_coli.Model.GetSetTipoUsuario;
import com.example.epapa_coli.Received.ReceivedRegisterUsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class RegisterUsers extends AppCompatActivity {

    EditText edtCorreo, edtCelular, edtPassword, edtRepeatPassword;
    String id_cedula, nombres, apellidos;
    AutoCompleteTextView tipoUsuario;
    int contadorCorreo;
    Button btnNext;
    DatePickerDialog.OnDateSetListener onDateSetListener;
    ImageView imgReset;
    int id_tipoUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_users);
        getSupportActionBar().hide();
         id_cedula = getIntent().getStringExtra("id_cedula");
        nombres = getIntent().getStringExtra("nombres");
        apellidos = getIntent().getStringExtra("apellidos");

        tipoUsuario = findViewById(R.id.tipoUser);
        edtCorreo = findViewById(R.id.edtCorreoRegister);
        edtCelular = findViewById(R.id.celularRegister);
        edtPassword = findViewById(R.id.newPasswordRegister);
        edtRepeatPassword = findViewById(R.id.repetPasswordRegister);
        btnNext = findViewById(R.id.btnUsersRegister);
        llenarspinnerRol();
         btnNext.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 obtenerCorreo();
             }
         });
         imgReset = findViewById(R.id.imgRegresar);
         imgReset.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 startActivity(new Intent(getApplicationContext(), Login.class));
                 finish();
             }
         });
    }

    private void obtenerCorreo() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://devtesis.com/tesis-epapacoli/validar_correo.php?usuario="+edtCorreo.getText().toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("email");
                    contadorCorreo = jsonArray.length();
                    RegistrarUsuario("https://devtesis.com/tesis-epapacoli/insertar_usuario.php");

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

    private boolean validarPassword() {
        String passwordP = edtPassword.getText().toString();
        String password2 = edtRepeatPassword.getText().toString();
        if (passwordP.equals(password2)) {
            return true;
        } else {
            return false;
        }
    }

    //Método de validación de correo electrónico
    private boolean ValidationEmail(){
        String emailInput = edtCorreo.getText().toString().trim();

        if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            return false;
        } else {
            return true;
        }
    }

    public void RegistrarUsuario(String URL){
        if (edtCorreo.getText().toString().equals("")){
            edtCorreo.setError("El campo está vacío");
        }else if(ValidationEmail()==false){
            edtCorreo.setError("Ingrese un correo válido");
        } else if(edtCelular.getText().toString().equals("")){
            edtCelular.setError("El campo está vacío");
        }else if(contadorCorreo>=1){
            Toast.makeText(getApplicationContext(), "El correo ya existe", Toast.LENGTH_SHORT).show();
        }else if (edtPassword.getText().toString().equals("") || validarPassword() == false) {
            edtPassword.setError("Las contraseñas no coinciden");
        } else if (edtPassword.getText().toString().length() < 8) {
            edtPassword.setError("La contraseña requiere mínimo de 8 caracteres");
        } else if (edtRepeatPassword.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "el campo confirmar contraseña está vacía está vacío", Toast.LENGTH_SHORT).show();
        }else {
            final RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(getApplicationContext(), "Se registro correctamente", Toast.LENGTH_SHORT).show();
                    //sendMail();
                    Bundle code = new Bundle();
                    code.putString("correo", edtCorreo.getText().toString());
                    Intent i = new Intent(RegisterUsers.this, RegisterCode.class);
                    i.putExtras(code);
                    startActivity(i);
                    finish();
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
                    params.put("email", edtCorreo.getText().toString());
                    params.put("celular", edtCelular.getText().toString());
                    params.put("password", edtPassword.getText().toString());
                    params.put("tipoUsuario", String.valueOf(id_tipoUsuario));
                    params.put("cedula", id_cedula);


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

    private void sendMail() {
        String nombre = nombres;
        String apellido = apellidos;
        String subject = "DISTRIBUIDORA LA BODEGA";
        String message = nombre+" "+apellido;
        String frase = "REGISTRO DE USUARIO.";
        String mensaje = frase+"\n\nEl usuario "+message+" se registró con éxito";

        ReceivedRegisterUsers javaMailAPI = new ReceivedRegisterUsers(getApplicationContext(), edtCorreo.getText().toString(), subject, mensaje);
        javaMailAPI.execute();
    }

    public void llenarspinnerRol() {
        String URL = "https://devtesis.com/tesis-epapacoli/tipoUsuario.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    final ArrayList<GetSetTipoUsuario> listRol = new ArrayList<GetSetTipoUsuario>();

                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("tipoUsuario");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                        GetSetTipoUsuario p = new GetSetTipoUsuario();
                        p.setId_tipoUsuario(jsonObject1.getInt("id_tipoUsuario"));
                        p.setNombre_tipoUsuario(jsonObject1.getString("nombre_tipoUsuario"));
                        listRol.add(p);

                    }
                    ArrayAdapter<GetSetTipoUsuario> tipoRol = new ArrayAdapter<GetSetTipoUsuario>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, listRol);
                    tipoUsuario.setAdapter(tipoRol);
                    tipoUsuario.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            id_tipoUsuario = listRol.get(i).getId_tipoUsuario();
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