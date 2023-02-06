package com.example.epapa_coli;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hanks.passcodeview.PasscodeView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PinCodeVerify extends AppCompatActivity {

    PasscodeView passcodeView;
    String user;
    int codigo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_code_verify);
        user =  Preferences.obtenerPreferenceString(getApplicationContext(), Preferences.PREFERENCE_USUARIO_LOGIN);
        passcodeView = findViewById(R.id.passcodeview);
        obtenerCodigo();

    }

    private void obtenerCodigo() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://devtesis.com/tesis-epapacoli/obtenerCodigo.php?correo="+user, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray jsonArray = jsonObject.getJSONArray("codigo");

                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        codigo = jsonObject1.getInt("codigo");
                        passcodeView.setPasscodeLength(6)
                                // to set pincode or passcode
                                .setLocalPasscode(String.valueOf(codigo))

                                // to set listener to it to check whether
                                // passwords has matched or failed
                                .setListener(new PasscodeView.PasscodeViewListener() {
                                    @Override
                                    public void onFail() {
                                        // to show message when Password is incorrect
                                        Toast.makeText(PinCodeVerify.this, "Password is wrong!", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onSuccess(String number) {
                                        // here is used so that when password
                                        // is correct user will be
                                        // directly navigated to next activity
                                        Intent intent_passcode = new Intent(PinCodeVerify.this, HomeUser.class);
                                        startActivity(intent_passcode);
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

}