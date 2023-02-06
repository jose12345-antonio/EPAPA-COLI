package com.example.epapa_coli;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.util.Log;
import android.view.View;
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

import java.io.IOException;
import java.nio.charset.Charset;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Arrays;
import java.util.concurrent.Executor;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class VerifySession extends AppCompatActivity {

    CardView credencialSession, biometricSession, pinSession;
    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;
    public static final int BIOMETRIC_STRONG = 15;
    public static final int BIOMETRIC_WEAK = 255;
    public static final int DEVICE_CREDENTIAL = 32768;
    KeyStore keyStore;
    KeyGenerator keyGenerator;
    int codeCantidad;
    String user;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_session);
        getSupportActionBar().hide();
        user =  Preferences.obtenerPreferenceString(getApplicationContext(), Preferences.PREFERENCE_USUARIO_LOGIN);
        Variable();
        verificacionBiometrica();
        executor= ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(VerifySession.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(getApplicationContext(), "Error de autenticación"+errString, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                String plaintext="huellaDactilar";
                byte[] encryptedInfo = new byte[0];
                try {
                    encryptedInfo = result.getCryptoObject().getCipher().doFinal(
                            // plaintext-string text is whatever data the developer would like
                            // to encrypt. It happens to be plain-text in this example, but it
                            // can be anything
                            plaintext.getBytes(Charset.defaultCharset()));
                } catch (BadPaddingException e) {
                    e.printStackTrace();
                } catch (IllegalBlockSizeException e) {
                    e.printStackTrace();
                }
                Log.d("MY_APP_TAG", "Encrypted information: " +
                        Arrays.toString(encryptedInfo));
                Toast.makeText(getApplicationContext(), "Verificación correcta", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), HomeUser.class));
                finish();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getApplicationContext(), "Fallo en la autenticación", Toast.LENGTH_SHORT).show();
            }
        });

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                generateSecretKey(new KeyGenParameterSpec.Builder(
                        "huellaDactilar",
                        KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                        .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                        .setUserAuthenticationRequired(true)
                        // Invalidate the keys if the user has registered a new biometric
                        // credential, such as a new fingerprint. Can call this method only
                        // on Android 7.0 (API level 24) or higher. The variable
                        // "invalidatedByBiometricEnrollment" is true by default.
                        .setInvalidatedByBiometricEnrollment(true)
                        .build());
            }
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }

        promptInfo= new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Autenticación Biométrica")
                .setSubtitle("Inicio de sesión mediante Huella dactilar")
                .setAllowedAuthenticators(BIOMETRIC_STRONG | DEVICE_CREDENTIAL)
                .build();

        biometricSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cipher cipher = null;
                try {
                    cipher = getCipher();
                } catch (NoSuchPaddingException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                SecretKey secretKey = null;
                try {
                    secretKey = getSecretKey();
                } catch (KeyStoreException e) {
                    e.printStackTrace();
                } catch (CertificateException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (UnrecoverableKeyException e) {
                    e.printStackTrace();
                }
                try {
                    cipher.init(Cipher.ENCRYPT_MODE, secretKey);
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                }


                biometricPrompt.authenticate(promptInfo, new BiometricPrompt.CryptoObject(cipher));
                System.out.println("Generator "+keyGenerator);

                //System.out.println(keyStore.getKey());

            }
        });
    }

    private void Variable(){
        credencialSession = findViewById(R.id.credencialSession);
        credencialSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });
        biometricSession = findViewById(R.id.cardHuella);
        pinSession = findViewById(R.id.passcode);
        pinSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerCode();
            }
        });
    }

    @SuppressLint("WrongConstant")
    public void verificacionBiometrica(){
        BiometricManager biometricManager = BiometricManager.from(this);
        switch (biometricManager.canAuthenticate(BIOMETRIC_STRONG | DEVICE_CREDENTIAL)) {
            case BiometricManager.BIOMETRIC_SUCCESS:
                //Log.d("MY_APP_TAG", "La aplicación puede autenticarse usando datos biométricos.");
                break;
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                Log.e("MY_APP_TAG", "No hay funciones biométricas disponibles en este dispositivo.");
                break;
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                //Log.e("MY_APP_TAG", "Las funciones biométricas no están disponibles actualmente.");
                break;
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                // Prompts the user to create credentials that your app accepts.
                Log.e("MY_APP_TAG", "Necesita registrar al menos una huella dactilar.");
                final Intent enrollIntent = new Intent(Settings.ACTION_BIOMETRIC_ENROLL);
                enrollIntent.putExtra(Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                        BIOMETRIC_STRONG | DEVICE_CREDENTIAL);
                startActivityForResult(enrollIntent, 1);
                break;
            default:
                Log.e("MY_APP_TAG", "Necesita registrar al menos una huella dactilar.");
                break;
        }
    }
    private void generateSecretKey(KeyGenParameterSpec keyGenParameterSpec) throws NoSuchProviderException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        keyGenerator = KeyGenerator.getInstance(
                KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            keyGenerator.init(keyGenParameterSpec);
        }
        keyGenerator.generateKey();
    }
    private SecretKey getSecretKey() throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException, UnrecoverableKeyException {
        keyStore = KeyStore.getInstance("AndroidKeyStore");

        // Before the keystore can be accessed, it must be loaded.
        keyStore.load(null);
        return ((SecretKey)keyStore.getKey("huellaDactilar", null));
    }
    private Cipher getCipher() throws NoSuchPaddingException, NoSuchAlgorithmException {
        return Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/"
                + KeyProperties.BLOCK_MODE_CBC + "/"
                + KeyProperties.ENCRYPTION_PADDING_PKCS7);
    }

    public void obtenerCode(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://devtesis.com/tesis-epapacoli/obtenerCodigo.php?correo="+user, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray jsonArray = jsonObject.getJSONArray("codigo");
                    codeCantidad = jsonArray.length();

                    if(codeCantidad==0){
                        //startActivity(new Intent(getApplicationContext(), PinCodeVerify.class));
                        //Toast.makeText(getApplicationContext(), "No tiene registrado código", Toast.LENGTH_SHORT).show();
                        mostrarMensaje();

                    }else if(codeCantidad==1){
                                startActivity(new Intent(getApplicationContext(), PinCodeVerify.class));
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

    public void mostrarMensaje(){
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
        dialogo1.setTitle("NO REGISTRO PIN");
        dialogo1.setMessage("¿Desea registrar pin de acceso para mayor seguridad?");
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                Bundle code = new Bundle();
                code.putString("correo", user);
                Intent i = new Intent(VerifySession.this, RegisterCode.class);
                i.putExtras(code);
                startActivity(i);
            }
        });
        dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                dialogo1.cancel();
            }
        });
        dialogo1.show();
    }

}