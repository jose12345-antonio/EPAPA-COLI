package com.example.epapa_coli;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
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
import com.example.epapa_coli.Model.GetSetDocumento;
import com.example.epapa_coli.Model.GetSetTipoUsuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Registro_Cliente extends AppCompatActivity {

    int id_tipoUsuario, id_tipoDocumento;
    AutoCompleteTextView tipoUsuario, tipoDocumento;
    DatePickerDialog.OnDateSetListener onDateSetListener;
    String fecha;
    EditText edtFecha, edtnombre, edtapellido, edtdireccion, edtCedula, edtcodigo;
    Button registerClientes;

    //Variables para la validacion de cedula
    int listar = 0;
    int listarCedula = 0;
    private String cedulaValidate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_cliente);
        tipoUsuario = findViewById(R.id.tipoUser);
        tipoDocumento = findViewById(R.id.tipoDocumento);
        edtFecha = findViewById(R.id.fechaClientes);
        edtnombre = findViewById(R.id.nombreClientes);
        edtapellido = findViewById(R.id.apellidosClientes);
        edtdireccion = findViewById(R.id.direccionClientes);
        edtcodigo = findViewById(R.id.codigoClientes);
        edtCedula = findViewById(R.id.cedulaClientes);
        registerClientes = findViewById(R.id.btnClienteRegister);
        registerClientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegistrarUsuario("https://epapa-coli.es/tesis-epapacoli/insertar_cliente.php");
            }
        });
        Fecha();
        llenarspinnerRol();
        llenarspinnerDocumento();
    }
    public void llenarspinnerRol() {
        String URL = "https://epapa-coli.es/tesis-epapacoli/tipoUsuario.php";
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

    public void llenarspinnerDocumento() {
        String URL = "https://epapa-coli.es/tesis-epapacoli/tipoDocumento.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    final ArrayList<GetSetDocumento> documento = new ArrayList<GetSetDocumento>();

                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("documento");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                        GetSetDocumento p = new GetSetDocumento();
                        p.setId_documento(jsonObject1.getInt("id_tipoDocumento"));
                        p.setNombreDocumento(jsonObject1.getString("nombre_documento"));
                        documento.add(p);

                    }
                    ArrayAdapter<GetSetDocumento> documentotipo = new ArrayAdapter<GetSetDocumento>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, documento);
                    tipoDocumento.setAdapter(documentotipo);
                    tipoDocumento.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            id_tipoDocumento = documento.get(i).getId_documento();
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

    private void Fecha(){
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        edtFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(Registro_Cliente.this,
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
                edtFecha.setText(fecha);
            }
        };
    }

    public void RegistrarUsuario(String URL){
        cedulaRegister();
        String cedula = edtCedula.getText().toString();
        int cantidad = 0;
        cantidad = cedula.length();
        if (edtCedula.getText().toString().equals("")){
            edtCedula.setError("El campo no puede estar vacío");
        } else if(cantidad <= 9){
            edtCedula.setError("La cédula es de 10 dígitos");
        } else if (isEcuadorianDocumentValid() == false) {
            edtCedula.setError("Cédula ingresada es incorrecta");
        }else if(listarCedula>=1){
            edtCedula.setError("La cédula se encuentra registrada");
        }else if(edtnombre.getText().toString().equals("")) {
            edtnombre.setError("El campo no puede estar vacío");
        }else if(edtapellido.getText().toString().equals("")) {
            edtapellido.setError("El campo no puede estar vacío");
        }else if(edtcodigo.getText().toString().equals("")) {
            edtcodigo.setError("El campo no puede estar vacío");
        }else if(edtdireccion.getText().toString().equals("")) {
            edtdireccion.setError("El campo no puede estar vacío");
        }else if(edtFecha.getText().toString().equals("")){
            edtFecha.setError("El campo no puede estar vacío");
        }else {
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(getApplicationContext(), "El cliente fue registrado exitosamente", Toast.LENGTH_SHORT).show();
                    mostrarMensaje();
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

                    //Toast.makeText(getApplicationContext(), radioButton.getText(), Toast.LENGTH_SHORT).show();
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("cedula", edtCedula.getText().toString());
                    params.put("nombre", edtnombre.getText().toString());
                    params.put("apellido", edtapellido.getText().toString());
                    params.put("plantilla", String.valueOf(id_tipoDocumento));
                    params.put("codigo", edtcodigo.getText().toString());
                    params.put("fecha", edtFecha.getText().toString());
                    params.put("direccion", edtdireccion.getText().toString());
                    params.put("cargo", String.valueOf(id_tipoUsuario));

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

    public void cedulaRegister() {
        String url_Cedula = "https://epapa-coli.es/tesis-epapacoli/validar_cedula.php?cedula="+edtCedula.getText().toString();
        StringRequest request3 = new StringRequest(Request.Method.GET, url_Cedula, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("cedula");
                    listarCedula = jsonArray.length();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Registro_Cliente.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        );
        RequestQueue requestQueue3 = Volley.newRequestQueue(Registro_Cliente.this);
        requestQueue3.add(request3);
        requestQueue3.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
            @Override
            public void onRequestFinished(Request<Object> request) {
                requestQueue3.getCache().clear();
            }
        });
    }
    private boolean isEcuadorianDocumentValid() {

        cedulaValidate = edtCedula.getText().toString();
        int suma = 0;
        int a[] = new int[cedulaValidate.length() / 2];
        int b[] = new int[(cedulaValidate.length() / 2)];
        int c = 0;
        int d = 1;
        for (int i = 0; i < cedulaValidate.length() / 2; i++) {
            a[i] = Integer.parseInt(String.valueOf(cedulaValidate.charAt(c)));
            c = c + 2;
            if (i < (cedulaValidate.length() / 2) - 1) {
                b[i] = Integer.parseInt(String.valueOf(cedulaValidate.charAt(d)));
                d = d + 2;
            }
        }

        for (int i = 0; i < a.length; i++) {
            a[i] = a[i] * 2;
            if (a[i] > 9) {
                a[i] = a[i] - 9;
            }
            suma = suma + a[i] + b[i];
        }
        int aux = suma / 10;
        int dec = (aux + 1) * 10;
        if ((dec - suma) == Integer.parseInt(String.valueOf(cedulaValidate.charAt(cedulaValidate.length() - 1))))
            return true;
        else if (suma % 10 == 0 && cedulaValidate.charAt(cedulaValidate.length() - 1) == '0') {
            return true;
        } else {
            return false;
        }
    }
    public void mostrarMensaje(){
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
        dialogo1.setTitle("REGISTRO DE CLIENTE");
        dialogo1.setMessage("¿Desea registrar otro usuario?");
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                edtCedula.setText("");
                edtnombre.setText("");
                edtapellido.setText("");
                edtcodigo.setText("");
                edtdireccion.setText("");
                edtFecha.setText("");

                dialogo1.dismiss();

            }
        });
        dialogo1.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                startActivity(new Intent(getApplicationContext(), HomeAdmin.class));
                finish();
            }
        });
        dialogo1.show();
    }



}