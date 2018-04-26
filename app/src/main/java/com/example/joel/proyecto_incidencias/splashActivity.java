package com.example.joel.proyecto_incidencias;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.cloudinary.android.MediaManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class splashActivity extends Activity {
    JSONArray jsonArray;
    String nombre;
    String correo;
    String foto;
    String contraseña;
    int id;
    SharedPreferences preferencias_usuario;

    public static final String MyFRERERNCES = "MyPreferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //

        //
        MediaManager.init(this, Configuracion());
        preferencias_usuario = getSharedPreferences(MyFRERERNCES, Context.MODE_PRIVATE);
        final String correopre = preferencias_usuario.getString("correo_pre", "");
        final String contraseñapre = preferencias_usuario.getString("contra_pre", "");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (preferencias_usuario != null) {
                    Thread hilo = new Thread() {
                        @Override
                        public void run() {
                            final String resp = enviarpost(correopre, contraseñapre);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    int con = OBJJson(resp);
                                    if (con > 0) {


                                        Toast.makeText(getApplicationContext(), "Bienvenido " + correopre, Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(splashActivity.this, MainActivityMenu.class);
                                        intent.putExtra("nombre", nombre);
                                        intent.putExtra("cor", correo);
                                        intent.putExtra("foto", foto);
                                        intent.putExtra("ID", id);
                                        intent.putExtra("con", contraseña);

                                        startActivity(intent);


                                    } else {
                                        Intent intent = new Intent(splashActivity.this, Login.class);
                                        startActivity(intent);
                                        Toast.makeText(getApplicationContext(), "no hay datos", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }

                    };
                    hilo.start();

                }


            }
        }, 4000);
    }

    public int OBJJson(String respu) {
        int respuesta_si_existe = 0;

        try {
            jsonArray = new JSONArray(respu);
            if (jsonArray.length() > 0) {
                respuesta_si_existe = 1;
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject row = jsonArray.getJSONObject(i);
                    correo = row.getString("Correo");
                    nombre = row.getString("Nombre");
                    foto = row.getString("foto");
                    id = row.getInt("ID");
                    contraseña = row.getString("Contraseña");
                }
            }


        } catch (Exception e) {

        }


        return respuesta_si_existe;


    }

    public String enviarpost(String correo, String contraseña) {
        URL url = null;
        String linea = "";
        int respuesta = 0;
        StringBuilder resul = null;
        try {
            url = new URL("http://proyectoinciencias.gearhostpreview.com/sos_service.asmx/login?correo=" + correo + "&contraseña=" + contraseña);
            HttpURLConnection conec = (HttpURLConnection) url.openConnection();
            respuesta = conec.getResponseCode();
            resul = new StringBuilder();
            if (respuesta == HttpURLConnection.HTTP_OK) {
                InputStream in = new BufferedInputStream(conec.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                while ((linea = reader.readLine()) != null) {
                    resul.append(linea);
                }

            }
        } catch (Exception e) {
        }
        return resul.toString();

    }

    public static Map Configuracion() {
        Map Config = new HashMap();
        Config.put("cloud_name", "dlyngnwmw");
        Config.put("api_key", "362846149476767");
        Config.put("api_secret", "x8gH0p8MD_4hTCJ0aR6xZWq8mo0");
        return Config;
    }



}
