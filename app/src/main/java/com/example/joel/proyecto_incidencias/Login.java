package com.example.joel.proyecto_incidencias;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Login extends AppCompatActivity {
    EditText txt_correo,txt_contra;
    Button btn_entrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txt_contra=(EditText)findViewById(R.id.txt_contraseña);
        txt_correo=(EditText)findViewById(R.id.txt_correo);
        btn_entrar=(Button)findViewById(R.id.btn_login);


        btn_entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(getApplicationContext(),"gay",Toast.LENGTH_LONG).show();
                Thread hilo = new Thread(){
                    @Override
                    public void run() {
                        final String   resp = enviarpost(txt_correo.getText().toString(),txt_contra.getText().toString());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                int con=OBJJson(resp);
                                if(con>0)
                                {
                                   Intent intent= new Intent(getApplicationContext(),menu_principal.class);
                                    startActivity(intent);
                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(),"usuarios o password incorrectos",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }

                };
                hilo.start();
            }
        });

    }
    public  String enviarpost(String correo,String contraseña)  {
        URL url=null;
        String linea="";
        int respuesta=0;
        StringBuilder resul=null;try {
            url=new URL("http://sos.gearhostpreview.com/sos_service.asmx/login?correo="+correo+"&contraseña="+contraseña);
            HttpURLConnection conec=(HttpURLConnection)url.openConnection();
            respuesta=conec.getResponseCode();
            resul=new StringBuilder();
            if(respuesta==HttpURLConnection.HTTP_OK)
            {
                InputStream in= new BufferedInputStream(conec.getInputStream());
                BufferedReader reader= new BufferedReader(new InputStreamReader(in));

                while ((linea=reader.readLine())!=null)
                {
                    resul.append(linea);
                }

            }
        }catch (Exception e)
        {}
        return   resul.toString();

    }



    public  int OBJJson(String respu)
    {
        int respuesta_si_existe=0;

        try{
            JSONArray jsonArray=new JSONArray(respu);
            if (jsonArray.length()>0)
            {
                respuesta_si_existe=1;
            }


        }catch (Exception e)
        {

        }


        return  respuesta_si_existe;



    }
}
