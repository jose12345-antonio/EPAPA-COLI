package com.example.epapa_coli;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.epapa_coli.Model.Logear_usuario;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONObject;

public class Login extends AppCompatActivity {

    EditText edtCorreo, edtPassword;
    Button btnIniciarSesion;
    TextView txtReset, txtRegistrar;
    CheckBox session;
    int estado;
    private boolean isActivateRadioButton;
    private AsyncHttpClient usuario_clien;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        Variable();
        usuario_clien = new AsyncHttpClient();

        if(Preferences.obtenerPreferenceBoolean(this,Preferences.PREFERENCE_ESTADO_BUTTON_SESION)) {
            if (Preferences.obtenerPreferenceStringRol(this, Preferences.PREFERENCE_ROL_LOGIN).equals("administrador")) {
                Intent intent = new Intent(getApplicationContext(), HomeAdmin.class);
                startActivity(intent);
                Login.super.onBackPressed();
            }else if(Preferences.obtenerPreferenceStringRol(this,Preferences.PREFERENCE_ROL_LOGIN).equals("usuario")){
                Intent intent = new Intent(getApplicationContext(),VerifySession.class);
                startActivity(intent);
                Login.super.onBackPressed();
            }
        }


        botonLogin();
    }

    private void Variable(){
        edtCorreo = findViewById(R.id.edtCorreoInicio);
        edtPassword = findViewById(R.id.edtPasswordInicio);

        btnIniciarSesion = findViewById(R.id.btnInicioSesion);

        txtReset = findViewById(R.id.txtReset);
        txtReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ResetPassword.class));
            }
        });
        txtRegistrar = findViewById(R.id.txtRegistrarse);
        txtRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), VerifyCedula.class));
            }
        });
        //Método para indicar si la sesión se la desea mantener o no.
        session = findViewById(R.id.sesionlogin);
        isActivateRadioButton = session.isChecked();
        session.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isActivateRadioButton) {
                    session.setChecked(false);
                }
                isActivateRadioButton = session.isChecked();
            }
        });
    }

    private void botonLogin(){
        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (edtCorreo.getText().toString().isEmpty() || edtPassword.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Hay Campos vacíos", Toast.LENGTH_SHORT).show();
                } else {
                    String usuario = edtCorreo.getText().toString().replace(" ", "%20");
                    String password = edtPassword.getText().toString().replace(" ", "%20");

                    String url = "https://epapa-coli.es/tesis-epapacoli/logear.php?email="+usuario+"&password="+password;
                    //Se sincroniza con la web
                    usuario_clien.post(url, new AsyncHttpResponseHandler() {
                        //almacenar mediante el método onSuccess
                        @Override
                        public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                            //Solicitud a la web
                            if (statusCode == 200) {
                                String respuesta = new String(responseBody);
                                if (respuesta.equalsIgnoreCase("null")) {
                                    toastError();
                                    edtPassword.setText("");
                                } else {
                                    try {

                                        //Se crea un objeto a la función de conversión a JSONObject
                                        JSONObject jsonObj = new JSONObject(respuesta);
                                        //Se crea un objeto a la clase de LogearUsuario
                                        Logear_usuario user = new Logear_usuario();
                                        //Establecer a la variable de la clase Logear_Usuario
                                        //todo lo que se obtiene de la clase JSONObj
                                        user.setId(jsonObj.getInt("id_usuario"));
                                        user.setPassword(jsonObj.getString("contrasena"));
                                        user.setRol(jsonObj.getInt("id_rol"));
                                        user.setNombre_rol(jsonObj.getString("id_rol"));
                                        estado = jsonObj.getInt("estado");
                                        if(estado!=0) {
                                            Intent i = null;
                                            toastVerify();
                                            switch (user.getRol()) {
                                                case 1:
                                                    user.setNombre_rol("Administrador");
                                                    Preferences.guardarPreferenceBoolean(Login.this, session.isChecked(), Preferences.PREFERENCE_ESTADO_BUTTON_SESION);
                                                    Preferences.guardarPreferenceString(Login.this, edtCorreo.getText().toString(), Preferences.PREFERENCE_USUARIO_LOGIN);
                                                    Preferences.guardarPreferenceStringRol(Login.this, "administrador", Preferences.PREFERENCE_ROL_LOGIN);
                                                    //if(Preferences.obtenerPreferenceBoolean(MainActivity.this,Preferences.PREFERENCE_ESTADO_BUTTON_SESION)) {
                                                    startActivity(new Intent(Login.this, HomeAdmin.class));
                                                    Login.super.onBackPressed();
                                                    //}
                                                    break;
                                                case 2:
                                                    user.setNombre_rol(("Usuario"));
                                                    Preferences.guardarPreferenceBoolean(Login.this, session.isChecked(), Preferences.PREFERENCE_ESTADO_BUTTON_SESION);
                                                    Preferences.guardarPreferenceString(Login.this, edtCorreo.getText().toString(), Preferences.PREFERENCE_USUARIO_LOGIN);
                                                    Preferences.guardarPreferenceStringRol(Login.this, "usuario", Preferences.PREFERENCE_ROL_LOGIN);
                                                    //if(Preferences.obtenerPreferenceBoolean(MainActivity.this,Preferences.PREFERENCE_ESTADO_BUTTON_SESION)) {
                                                    startActivity(new Intent(Login.this, HomeUser.class));
                                                    Login.super.onBackPressed();
                                                    // }
                                                    break;

                                                default:
                                                    Toast.makeText(getApplicationContext(), "PROBLEMA CON EL ROL", Toast.LENGTH_SHORT).show();
                                                    break;
                                            }
                                        }else{
                                            Toast.makeText(getApplicationContext(), "El usuario no se encuentra activo", Toast.LENGTH_LONG).show();
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                        }

                        @Override
                        public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                            Toast.makeText( Login.this, "Error Desconocido. Intentelo De Nuevo!!", Toast.LENGTH_SHORT).show();
                            edtPassword.setText("");
                        }
                    });
                }
            }


        });
    } // C


    public void toastError() {
        Toast toast = new Toast(this);
        View toast_layout = getLayoutInflater().inflate(R.layout.toast_error, (ViewGroup) findViewById(R.id.lytLayout));
        toast.setView(toast_layout);
        TextView textView = (TextView) toast_layout.findViewById(R.id.toastMessage);
        textView.setText("Correo y/o contraseña incorrecta");
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

    public void toastVerify() {
        Toast toast = new Toast(this);

        View toast_layout = getLayoutInflater().inflate(R.layout.toast_verify, (ViewGroup) findViewById(R.id.lytLayoutverify));
        toast.setView(toast_layout);
        TextView textView = (TextView) toast_layout.findViewById(R.id.toastMessageSession);
        textView.setText("Inicio de sesión completada");
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }
}