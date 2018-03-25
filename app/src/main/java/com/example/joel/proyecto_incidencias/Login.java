package com.example.joel.proyecto_incidencias;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Login extends AppCompatActivity {
    EditText txt_correo,txt_contra;
    Button btn_entrar,btn_registro;
    JSONArray jsonArray;
    String nombre;
    String correo;
    String foto;
    int id;
    public  static  final  String MyFRERERNCES="MyPreferences";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txt_contra=(EditText)findViewById(R.id.txt_contraseña);
        txt_correo=(EditText)findViewById(R.id.txt_correo);
        btn_entrar=(Button)findViewById(R.id.btn_login);
       sharedPreferences=getSharedPreferences(MyFRERERNCES, Context.MODE_PRIVATE);

      /*  if(sharedPreferences!=null)
        {
            final String correopre=sharedPreferences.getString("correo_pre","");
            final String contraseñapre=sharedPreferences.getString("contra_pre","");
            Thread hilo = new Thread(){
                @Override
                public void run() {
                    final String   resp = enviarpost(correopre,contraseñapre);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            int con=OBJJson(resp);
                            if(con>0)
                            {
                                //intaciamos prefrencias
                               SharedPreferences.Editor editor=sharedPreferences.edit();

                                Toast.makeText(getApplicationContext(),"Bienvenido "+correopre,Toast.LENGTH_SHORT).show();
                                Intent intent= new Intent(Login.this,MainActivityMenu.class);
                                intent.putExtra("nombre",nombre);
                                intent.putExtra("cor",correo);
                                intent.putExtra("foto",foto);
                                intent.putExtra("ID",id);
                            //   editor.putString("correo_pre",correo);
                         //     editor.putString("contra_pre",txt_correo.getText().toString());
                             //   editor.commit();

                              //  editor
                                startActivity(intent);


                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"Correo o contraseña incorrectos",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            };
            hilo.start();



        }*/
        Button btnRegistro =(Button)findViewById(R.id.btnregistrar);
        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(Login.this,registro.class);
                startActivity(intent);
            }
        });

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
                                    //intaciamos prefrencias
                                   SharedPreferences.Editor editor=sharedPreferences.edit();

                                    Toast.makeText(getApplicationContext(),"Bienvenido "+nombre,Toast.LENGTH_SHORT).show();
                                  Intent intent= new Intent(Login.this,MainActivityMenu.class);
                                  intent.putExtra("nombre",nombre);
                                  intent.putExtra("cor",correo);
                                    intent.putExtra("foto",foto);
                                    intent.putExtra("ID",id);
                                    editor.putString("correo_pre",correo);
                                    editor.putString("contra_pre",txt_contra.getText().toString());
                                    editor.commit();
                                    Toast.makeText(getApplicationContext(),"Bienvenido "+nombre,Toast.LENGTH_SHORT).show();
                                  Toast.makeText(getApplicationContext(),"prerencisass "+sharedPreferences.getString("correo_pre","")+sharedPreferences.getString("contra_pre",""),Toast.LENGTH_SHORT).show();
                                    startActivity(intent);


                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(),"Correo o contraseña incorrectos",Toast.LENGTH_SHORT).show();
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
        StringBuilder resul=null;
        try {
            url=new URL("http://proyectoinciencias.gearhostpreview.com/sos_service.asmx/login?correo="+correo+"&contraseña="+contraseña);
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
             jsonArray=new JSONArray(respu);
            if (jsonArray.length()>0)
            {
                respuesta_si_existe=1;
                for(int i=0;i<jsonArray.length();i++)
                {
                    JSONObject row = jsonArray.getJSONObject(i);
                    correo=row.getString("Correo");
                    nombre=row.getString("Nombre");
                    foto=row.getString("foto");
                    id=row.getInt("ID");
                }
            }


        }catch (Exception e)
        {

        }




        return  respuesta_si_existe;



    }

}
