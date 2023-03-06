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
import com.example.epapa_coli.Model.GetSetCategoriaUsuario;
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

    int id_tipoUsuario, id_tipoDocumento, id_categoriaUsuario;
    AutoCompleteTextView tipoUsuario, tipoDocumento, categoriaUser;
    DatePickerDialog.OnDateSetListener onDateSetListener;
    String fecha;
    EditText edtFecha, edtnombre, edtapellido, edtdireccion, edtCedula, edtcodigo;
    Button registerClientes;

    //Variables para la validacion de cedula
    int listar = 0;
    int listarCedula = 0;
    private String cedulaValidate;
    int codigo;
    private static final int num_provincias = 24;
    //public static String rucPrueba = “1790011674001″;
    private static int[] coeficientes = {4, 3, 2, 7, 6, 5, 4, 3, 2};
    private static int constante = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_cliente);
        tipoUsuario = findViewById(R.id.tipoUser);
        categoriaUser = findViewById(R.id.categoriaUser);
        tipoDocumento = findViewById(R.id.tipoDocumento);
        edtFecha = findViewById(R.id.fechaClientes);
        edtnombre = findViewById(R.id.nombreClientes);
        edtapellido = findViewById(R.id.apellidosClientes);
        edtdireccion = findViewById(R.id.direccionClientes);
        edtcodigo = findViewById(R.id.codigoClientes);
        edtCedula = findViewById(R.id.cedulaClientes);
        registerClientes = findViewById(R.id.btnClienteRegister);
        obtenerCodigo();
        registerClientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegistrarUsuario("https://epapa-coli.es/tesis-epapacoli/insertar_cliente.php");
            }
        });
        Fecha();
        llenarspinnerRol();
        llenarspinnerDocumento();
        llenarspinnerCategoria();
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

    public void llenarspinnerCategoria() {
        String URL = "https://epapa-coli.es/tesis-epapacoli/categoriaUsuario.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    final ArrayList<GetSetCategoriaUsuario> listCategoria = new ArrayList<GetSetCategoriaUsuario>();

                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("categoriaUsuario");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                        GetSetCategoriaUsuario p = new GetSetCategoriaUsuario();
                        p.setId_categoriaUsuario(jsonObject1.getInt("id_categoriaUsuario"));
                        p.setNombre_categoriaUsuario(jsonObject1.getString("nombre_categoriaUsuario"));
                        listCategoria.add(p);

                    }
                    ArrayAdapter<GetSetCategoriaUsuario> tipoRol = new ArrayAdapter<GetSetCategoriaUsuario>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, listCategoria);
                    categoriaUser.setAdapter(tipoRol);
                    categoriaUser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            id_categoriaUsuario = listCategoria.get(i).getId_categoriaUsuario();
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
            Toast.makeText(getApplicationContext(), "El campo cédula está vacío", Toast.LENGTH_SHORT).show();
        } else if(id_tipoDocumento==1){
            if (!validacionRUC(edtCedula.getText().toString())) {
                edtCedula.setError("RUC ingresado es incorrecto");
                Toast.makeText(getApplicationContext(), "El RUC que ingresó es incorrecto", Toast.LENGTH_SHORT).show();
                System.out.println("RUC FALLO");
            }else if(cantidad<=12){
                Toast.makeText(getApplicationContext(), "El campo debe mantener 13 dígitos", Toast.LENGTH_SHORT).show();
            }
        } else if(id_tipoDocumento==2){
            if (isEcuadorianDocumentValid() == false) {
                edtCedula.setError("Cédula ingresada es incorrecta");
                Toast.makeText(getApplicationContext(), "La cédula ingresada es incorrecta", Toast.LENGTH_SHORT).show();
                System.out.println("CEDULA FALLO");
            }else if(cantidad <= 9){
                edtCedula.setError("La cédula es de 10 dígitos");
                Toast.makeText(getApplicationContext(), "El campo cédula debe mantener 10 dígitos", Toast.LENGTH_SHORT).show();
            }
        }else if(listarCedula>=1){
            edtCedula.setError("La cédula se encuentra registrada");
            Toast.makeText(getApplicationContext(), "La cédula ya se encuentra registrada", Toast.LENGTH_SHORT).show();
        }else if(edtnombre.getText().toString().equals("")) {
            edtnombre.setError("El campo no puede estar vacío");
            Toast.makeText(getApplicationContext(), "El campo nombre está vacío", Toast.LENGTH_SHORT).show();
        }else if(edtapellido.getText().toString().equals("")) {
            edtapellido.setError("El campo no puede estar vacío");
            Toast.makeText(getApplicationContext(), "El campo cédula apellido vacío", Toast.LENGTH_SHORT).show();
        }else if(edtcodigo.getText().toString().equals("")) {
            edtcodigo.setError("El campo no puede estar vacío");
            Toast.makeText(getApplicationContext(), "El campo código único está vacío", Toast.LENGTH_SHORT).show();
        }else if(edtdireccion.getText().toString().equals("")) {
            edtdireccion.setError("El campo no puede estar vacío");
            Toast.makeText(getApplicationContext(), "El campo cédula dirección vacío", Toast.LENGTH_SHORT).show();
        }else if(edtFecha.getText().toString().equals("")){
            edtFecha.setError("El campo no puede estar vacío");
            Toast.makeText(getApplicationContext(), "El campo fecha de nacimiento está vacío", Toast.LENGTH_SHORT).show();
        }else {
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(getApplicationContext(), "El cliente fue registrado exitosamente", Toast.LENGTH_SHORT).show();
                    //mostrarMensaje();
                    startActivity(new Intent(getApplicationContext(), AsignacionMedidor.class));
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

                    //Toast.makeText(getApplicationContext(), radioButton.getText(), Toast.LENGTH_SHORT).show();
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("cedula", edtCedula.getText().toString());
                    params.put("nombre", edtnombre.getText().toString());
                    params.put("apellido", edtapellido.getText().toString());
                    params.put("tipo_documento", String.valueOf(id_tipoDocumento));
                    params.put("codigo", edtcodigo.getText().toString());
                    params.put("fecha", edtFecha.getText().toString());
                    params.put("direccion", edtdireccion.getText().toString());
                    params.put("cargo", String.valueOf(id_tipoUsuario));
                    params.put("categoria", String.valueOf(id_categoriaUsuario));

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

    public void obtenerCodigo(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://epapa-coli.es/tesis-epapacoli/obtener_codigoUnico.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray jsonArray = jsonObject.getJSONArray("codigo");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        codigo = jsonObject1.getInt("codigo");
                    }
                    int sum = codigo+1;
                    edtcodigo.setText("000"+sum);

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


        public static Boolean validacionRUC(String ruc) {
//verifica que los dos primeros dígitos correspondan a un valor entre 1 y NUMERO_DE_PROVINCIAS
            int prov = Integer.parseInt(ruc.substring(0, 2));

            if (!((prov > 0) && (prov <= num_provincias))) {
                System.out.println("Error:ruc ingresada mal");
                return false;
            }

//verifica que el último dígito de la cédula sea válido
            int[] d = new int[10];
            int suma = 0;

//Asignamos el string a un array
            for (int i = 0; i < d.length; i++) {
                d[i] = Integer.parseInt(ruc.charAt(i) + "");
            }

            for (int i = 0; i < d.length - 1;
            i++){
                d[i] = d[i] * coeficientes[i];
                suma += d[i];
//System.out.println(“Vector d en ” + i + ” es ” + d[i]);
            }

            System.out.println("Suma es: "+suma);

            int aux, resp;

            aux = suma % constante;
            resp = constante - aux;

            resp = (resp == 10) ? 0 : resp;

            System.out.println("Aux: "+aux);
            System.out.println("Resp " + resp);
            System.out.println("d[9] " + d[9]);

            if (resp == d[9]) {
                return true;
            } else
                return false;
        }
    }
