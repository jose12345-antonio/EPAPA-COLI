package com.example.epapa_coli.ui.profile;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.epapa_coli.DetallePago;
import com.example.epapa_coli.Login;
import com.example.epapa_coli.Preferences;
import com.example.epapa_coli.R;
import com.example.epapa_coli.UpdatePassword;
import com.example.epapa_coli.updateUser;
import com.example.epapa_coli.vistaUsuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Profile extends Fragment {

    LinearLayout lndatosPersonales, lnConfiguracionCuenta, lnPassword, lnInformacion, lnContacto, lnTerminosCondiciones;
    TextView txtNombres, txtFechaRegistro;

    String user, rol, nombres;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
            variable(view);
        user =  Preferences.obtenerPreferenceString(getContext(), Preferences.PREFERENCE_USUARIO_LOGIN);
        obtenerUsuario();

        return view;
    }

    public void variable(View view){
        lndatosPersonales = view.findViewById(R.id.datosPersonales);
        lndatosPersonales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), vistaUsuario.class));
            }
        });
        lnConfiguracionCuenta = view.findViewById(R.id.configuracionCuenta);
        lnConfiguracionCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), updateUser.class));
            }
        });
        lnPassword = view.findViewById(R.id.cambiarPassword);
        lnPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), UpdatePassword.class));
            }
        });
        lnInformacion = view.findViewById(R.id.informacionDate);
        lnInformacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View alertCustomDialog = LayoutInflater.from(getContext()).inflate(R.layout.dialog_informacion, null);
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());

                alertDialog.setView(alertCustomDialog);


                final AlertDialog alertDialog1 = alertDialog.create();
                alertDialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alertDialog1.show();



            }
        });
        lnContacto = view.findViewById(R.id.contactanos);
        lnContacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View alertCustomDialog = LayoutInflater.from(getContext()).inflate(R.layout.dialog_contacto, null);
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());

                alertDialog.setView(alertCustomDialog);

                Button btnWhatsapp = (Button) alertCustomDialog.findViewById(R.id.btnWhatsapp);
                Button btnLlamar = (Button) alertCustomDialog.findViewById(R.id.btnLlamar);

                btnWhatsapp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        String uri = "whatsapp://send?phone=593967464705";
                        intent.setData(Uri.parse(uri));
                        startActivity(intent);
                    }
                });

                btnLlamar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:0959027838"));
                        if(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
                            return;
                        getActivity().startActivity(intent);
                    }
                });

                final AlertDialog alertDialog1 = alertDialog.create();
                alertDialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alertDialog1.show();
            }
        });
        txtNombres = view.findViewById(R.id.txtNombre);
        txtFechaRegistro = view.findViewById(R.id.fechaRegistro);

        TextView cerrarSesion = view.findViewById(R.id.cerrarSesion);
        cerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Preferences.guardarPreferenceBoolean(getContext(),false, Preferences.PREFERENCE_ESTADO_BUTTON_SESION);
                startActivity(new Intent(getContext(), Login.class));
                getActivity().onBackPressed();

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
                        nombres = jsonObject1.getString("nombres");
                        txtFechaRegistro.setText("Fecha de registro: "+jsonObject1.getString("created_at"));
                        rol = jsonObject1.getString("rol");
                    }
                        if(rol.equals("Administrador")){
                            txtNombres.setText("Hola, "+nombres+"_admin");
                        }else if(rol.equals("Usuario")){
                            txtNombres.setText("Hola, "+nombres);
                        }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
        requestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
            @Override
            public void onRequestFinished(Request<Object> request) {
                requestQueue.getCache().clear();
            }
        });
    }
}