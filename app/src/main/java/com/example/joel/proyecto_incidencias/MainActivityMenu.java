package com.example.joel.proyecto_incidencias;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudinary.android.MediaManager;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class MainActivityMenu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener ,mapa_puntos_aprovados.OnFragmentInteractionListener,EstadisticasFragment.OnFragmentInteractionListener,GpuntosFragment.OnFragmentInteractionListener,misIncidenciasFragment.OnFragmentInteractionListener,MisdatosFragment.OnFragmentInteractionListener,GenerarIncidenciaFragment.OnFragmentInteractionListener{
    SharedPreferences preferencias_puntos;
    SharedPreferences preferencias_usuarios;
    public  static  final  String MyFRERERNCES="MyPreferences";
    int id_usuairo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MediaManager.init(this,Configuracion());
        //mandar preferencia de los mapas
        preferencias_usuarios=getSharedPreferences(MyFRERERNCES,Context.MODE_PRIVATE);
        preferencias_puntos = getApplicationContext().getSharedPreferences("puntos", Context.MODE_PRIVATE);
        setContentView(R.layout.activity_main_menu);
        NavigationView navigationView=(NavigationView)findViewById(R.id.nav_view);
        View view =navigationView.getHeaderView(0);
        TextView nom = (TextView) view.findViewById(R.id.txv_nombre);
        ImageView imagenusuario=(ImageView)view.findViewById(R.id.img_imagenusuario);
        TextView cor = (TextView) view.findViewById(R.id.txv_correo);
        Bundle datos_traidos= getIntent().getExtras();
        if(datos_traidos!=null) {
            String nombre_usuario = (String) datos_traidos.get("nombre");
            String correo_usuario = (String) datos_traidos.get("cor");
            String fot=(String)datos_traidos.get("foto");
        id_usuairo=(int)datos_traidos.get("ID");
            cor.setText(correo_usuario);
            nom.setText(nombre_usuario);
            Picasso.get().load(fot).into(imagenusuario);

            Toast.makeText(getApplicationContext(),nom.getText().toString(),Toast.LENGTH_SHORT).show();
        }
        navigationView.setNavigationItemSelectedListener(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                //R.string.navigation_drawer_open, R.string.navigation_drawer_close
                this, drawer, toolbar, R.string.title_activity_maps, R.string.title_activity_maps);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

      /*  NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);*/
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

            CargarDatosLugares();
            mapa_puntos_aprovados conte= new mapa_puntos_aprovados();
         /*   Bundle bundle=new Bundle();
            String ba=enviar_Baches().toString();
            bundle.putString("baches",ba);
            conte.setArguments(bundle);*/
            FragmentTransaction fragmentTransaction= getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.contenedor,conte).commit();

            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            Bundle bundle= new Bundle();
            bundle.putInt("id",id_usuairo);
            misIncidenciasFragment gpuntosFragment=new misIncidenciasFragment();
            FragmentTransaction fragmentTransaction= getFragmentManager().beginTransaction();
            gpuntosFragment.setArguments(bundle);
            fragmentTransaction.replace(R.id.contenedor,gpuntosFragment).commit();
            //fragmentTransaction.addToBackStack(null);


        } else if (id == R.id.nav_slideshow) {
            Bundle bundle = new Bundle();
            bundle.putInt("id",id_usuairo);
GenerarIncidenciaFragment generarIncidenciaFragment= new GenerarIncidenciaFragment();
generarIncidenciaFragment.setArguments(bundle);
          //  getSupportFragmentManager().beginTransaction().add(R.id.contenedor,generarIncidenciaFragment).commit();
            FragmentTransaction fragmentTransaction= getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.contenedor,generarIncidenciaFragment).commit();
        } else if (id == R.id.nav_manage) {
EstadisticasFragment estadisticasFragment= new EstadisticasFragment();
            FragmentTransaction fragmentTransaction= getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.contenedor,estadisticasFragment).commit();
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {
MisdatosFragment misdatosFragment= new MisdatosFragment();
            FragmentTransaction fragmentTransaction= getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.contenedor,misdatosFragment).commit();
        }else if(id==R.id.nav_send1){
SharedPreferences.Editor editor=preferencias_usuarios.edit();
editor.clear().commit();
            Intent intent= new Intent(getApplicationContext(),Login.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public static Map Configuracion()
    {
        Map Config = new HashMap();
        Config.put("cloud_name", "dlyngnwmw");
        Config.put("api_key", "362846149476767");
        Config.put("api_secret", "x8gH0p8MD_4hTCJ0aR6xZWq8mo0");
        return Config;
    }
    @Override
    public void onFragmentInteraction(Uri uri) {
        
    }
    public void CargarDatosLugares(){
        Thread hilo = new Thread(){
            @Override
            public void run() {
                final String   resp = enviarpost();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int con=OBJJsonS(resp);
                        if(con>0)
                        {
                            String respondiendo = resp;
                            SharedPreferences.Editor editor = preferencias_puntos.edit();
                            editor.putString("respuesta_mapa",respondiendo);
                            editor.commit();

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
    public  String enviarpost()  {
        URL url=null;
        String linea="";
        int respuesta=0;
        StringBuilder resul=null;
        try {
            //http://fhkuku182-001-site1.atempurl.com/Grantour.asmx/CargarLugares
            url=new URL("http://proyectoinciencias.gearhostpreview.com/sos_service.asmx/mostrar_puntos_aprovados");
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
        {

        }
        return  resul.toString();

    }
    public  String enviar_Baches()  {
        URL url=null;
        String linea="";
        int respuesta=0;
        StringBuilder resul=null;
        try {
            //http://fhkuku182-001-site1.atempurl.com/Grantour.asmx/CargarLugares
            url=new URL("http://incidencias2.gearhostpreview.com/sos_service.asmx/filtros_de_niveles?id_peligro=4");
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
        {

        }
        return  resul.toString();

    }
    public  int OBJJsonS(String respu)
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
  /*  public  String Agregar_usuairo(Double longitud,Double latitud,String zona,int id_usuario,int id_indencia,String urlno,String comentario)  {
        String urldefait="nota";
        urlno=urldefait;
        URL url=null;
        String linea="";
        int respuesta=0;
        StringBuilder resul=null;
        try {
            url=new URL("http://incidenciaspro.gearhostpreview.com/sos_service.asmx/Guardar_puntos?longitud="+longitud+"&latitud="+latitud+"&Zona="+zona+"&id_usuario="+id_usuario+"&id_peligro="+id_indencia+"&url="+urlno+"&comentario="+comentario);
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

    }*/
}
