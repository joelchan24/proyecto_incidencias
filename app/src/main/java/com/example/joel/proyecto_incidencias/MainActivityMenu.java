package com.example.joel.proyecto_incidencias;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
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

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivityMenu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener ,mapa_puntos_aprovados.OnFragmentInteractionListener,EstadisticasFragment.OnFragmentInteractionListener,GpuntosFragment.OnFragmentInteractionListener,misIncidenciasFragment.OnFragmentInteractionListener,MisdatosFragment.OnFragmentInteractionListener,GenerarIncidenciaFragment.OnFragmentInteractionListener,contenedorFragment.OnFragmentInteractionListener,Estadisticas22Fragment.OnFragmentInteractionListener,Estadisticas33Fragment.OnFragmentInteractionListener,datosappFragment.OnFragmentInteractionListener,ListaAprovadosFragment.OnFragmentInteractionListener,ListaNoaprovadosfragment.OnFragmentInteractionListener{
    SharedPreferences preferencias_puntos;
    SharedPreferences preferencias_usuarios;
    public  static  final  String MyFRERERNCES="MyPreferences";
    int id_usuairo;
    String respondiendo_car_aprovados;
    clase_traedatos clase_traedatos= new clase_traedatos();
    String respondiendo_no_aprovados;
    String nombre_usuario;
    String correo_usuario;
    String fot;
    String contra;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CargarDatosestadisticas1();
        mapa_puntos_aprovados mapa_puntos_aprovado1s= new mapa_puntos_aprovados();

        FragmentTransaction fragmentTransaction= getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.contenedor,mapa_puntos_aprovado1s).commit();
      //  MediaManager.init(this,Configuracion());
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
             nombre_usuario = (String) datos_traidos.get("nombre");
             correo_usuario = (String) datos_traidos.get("cor");
             fot=(String)datos_traidos.get("foto");
             contra=(String)datos_traidos.get("con");

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
            mapa_puntos_aprovados conte = new mapa_puntos_aprovados();
         /*   Bundle bundle=new Bundle();
            String ba=enviar_Baches().toString();
            bundle.putString("baches",ba);
            conte.setArguments(bundle);*/
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.contenedor, conte).commit();

            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            Bundle bundle = new Bundle();
            bundle.putInt("id", id_usuairo);
            misIncidenciasFragment gpuntosFragment = new misIncidenciasFragment();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            gpuntosFragment.setArguments(bundle);
            fragmentTransaction.replace(R.id.contenedor, gpuntosFragment).commit();
            //fragmentTransaction.addToBackStack(null);


        } else if (id == R.id.nav_slideshow) {
            Bundle bundle = new Bundle();
            bundle.putInt("id", id_usuairo);
            GenerarIncidenciaFragment generarIncidenciaFragment = new GenerarIncidenciaFragment();
            generarIncidenciaFragment.setArguments(bundle);
            //  getSupportFragmentManager().beginTransaction().add(R.id.contenedor,generarIncidenciaFragment).commit();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.contenedor, generarIncidenciaFragment).commit();
        } else if (id == R.id.list_aprovados) {
           // cargar_aprovados_car();
            ListaAprovadosFragment listaAprovadosFragment= new ListaAprovadosFragment();
            Bundle bundle= new Bundle();
            bundle.putString("aprobados",respondiendo_car_aprovados);
            bundle.putInt("id",id_usuairo)
            ;
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            listaAprovadosFragment.setArguments(bundle);
            fragmentTransaction.replace(R.id.contenedor, listaAprovadosFragment).commit();
        }else if (id == R.id.list_no) {
            Bundle bundle= new Bundle();
            bundle.putString("no_aprobados",respondiendo_no_aprovados);
            bundle.putInt("id",id_usuairo);
            ListaNoaprovadosfragment listaAprovadosFragment= new ListaNoaprovadosfragment();
            listaAprovadosFragment.setArguments(bundle);
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.contenedor, listaAprovadosFragment).commit();

        } else if (id == R.id.grafica2) {
            CargarDatosestadisticas1();
            Estadisticas22Fragment ususu = new Estadisticas22Fragment();
            Bundle bundle = new Bundle();
            bundle.putInt("0", clase_traedatos.getDato0());
            bundle.putInt("1", clase_traedatos.getDato1());
            bundle.putInt("2", clase_traedatos.getDato2());
            bundle.putInt("3", clase_traedatos.getDato3());
            bundle.putInt("4", clase_traedatos.getDato4());
            bundle.putInt("5", clase_traedatos.getDato5());
            bundle.putInt("6", clase_traedatos.getDato6());
            bundle.putInt("7", clase_traedatos.getDato7());
            ususu.setArguments(bundle);
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.contenedor, ususu).commit();

        } else if (id == R.id.datosapp){
            datosappFragment datosappFragment1 = new datosappFragment();
    FragmentTransaction fragmentTransaction= getFragmentManager().beginTransaction();
    fragmentTransaction.replace(R.id.contenedor,datosappFragment1).commit();


        } else if (id == R.id.nav_send) {
MisdatosFragment misdatosFragment= new MisdatosFragment();
Bundle bundle = new Bundle();
bundle.putString("nom",nombre_usuario);
bundle.putString("fot",fot);
bundle.putString("cor",correo_usuario);
bundle.putString("con",contra)
;
misdatosFragment.setArguments(bundle);
            FragmentTransaction fragmentTransaction= getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.contenedor,misdatosFragment).commit();
        }else if(id==R.id.grafica3){
            CargarDatosestadisticas1();
            Bundle bundle= new Bundle();
            Estadisticas33Fragment estadisticas33Fragment= new Estadisticas33Fragment();
            bundle.putInt("0",clase_traedatos.getDato0());
            bundle.putInt("1",clase_traedatos.getDato1());
            bundle.putInt("2",clase_traedatos.getDato2());
            bundle.putInt("3",clase_traedatos.getDato3());
            bundle.putInt("4",clase_traedatos.getDato4());
            bundle.putInt("5",clase_traedatos.getDato5());
            bundle.putInt("6",clase_traedatos.getDato6());
            bundle.putInt("7",clase_traedatos.getDato7());
            bundle.putString("8",clase_traedatos.getPeligro0());
            bundle.putString("9",clase_traedatos.getPeligro1());
            bundle.putString("10",clase_traedatos.getPeligro2());
            bundle.putString("11",clase_traedatos.getPeligro3());
            bundle.putString("12",clase_traedatos.getPeligro4());
            bundle.putString("13",clase_traedatos.getPeligro5());
            bundle.putString("14",clase_traedatos.getPeligro6());
            bundle.putString("15",clase_traedatos.getPeligro7());
            estadisticas33Fragment.setArguments(bundle);
            FragmentTransaction fragmentTransaction= getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.contenedor,estadisticas33Fragment).commit();
        }else if(id==R.id.nav_send1){
cerra();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
   /* public static Map Configuracion()
    {
        Map Config = new HashMap();
        Config.put("cloud_name", "dlyngnwmw");
        Config.put("api_key", "362846149476767");
        Config.put("api_secret", "x8gH0p8MD_4hTCJ0aR6xZWq8mo0");
        return Config;
    }*/
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
    /*public void cargar_aprovados_car(){
        Thread hilo = new Thread(){
            @Override
            public void run() {
                final String   resp = enviarcard_aprovados1();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int con=OBJJsonS(resp);
                        if(con>0)
                        {
                             respondiendo_car_aprovados = resp;


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
    }*/

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
    public void CargarDatosestadisticas1(){
        Thread hilo = new Thread(){
            @Override
            public void run() {
                final String   resp = enviarpost_esta1();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int con=OBJJsonS(resp);
                        if(con>0)
                        {

                            try {
                                JSONArray array = new JSONArray(resp);
for (int i=0;i<array.length();i++) {
    JSONObject jsonObject = array.getJSONObject(i);
    {
switch (i)
{
    case 0:
        clase_traedatos.setPeligro0(jsonObject.getString("Peligro"));
        clase_traedatos.setDato0(jsonObject.getInt("total"));
    break;
    case 1:
        clase_traedatos.setPeligro1(jsonObject.getString("Peligro"));
        clase_traedatos.setDato1(jsonObject.getInt("total"));
        break;
    case 2:
        clase_traedatos.setPeligro2(jsonObject.getString("Peligro"));
        clase_traedatos.setDato2(jsonObject.getInt("total"));
        break;
    case 3:
        clase_traedatos.setPeligro3(jsonObject.getString("Peligro"));
        clase_traedatos.setDato3(jsonObject.getInt("total"));
        break;

    case 4:
        clase_traedatos.setPeligro4(jsonObject.getString("Peligro"));
        clase_traedatos.setDato4(jsonObject.getInt("total"));
        break;
    case 5:
        clase_traedatos.setPeligro5(jsonObject.getString("Peligro"));
        clase_traedatos.setDato5(jsonObject.getInt("total"));
        break;
    case 6:
        clase_traedatos.setPeligro6(jsonObject.getString("Peligro"));
        clase_traedatos.setDato6(jsonObject.getInt("total"));
        break;
    case 7:
        clase_traedatos.setPeligro7(jsonObject.getString("Peligro"));
        clase_traedatos.setDato7(jsonObject.getInt("total"));
        break;

}



    }
}

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

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
    public  String enviarpost_esta1()  {
        URL url=null;
        String linea="";
        int respuesta=0;
        StringBuilder resul=null;
        try {
            //http://fhkuku182-001-site1.atempurl.com/Grantour.asmx/CargarLugares
            url=new URL("http://proyectoinciencias.gearhostpreview.com/sos_service.asmx/estadisticas1");
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
  public  void  cerra() {
      AlertDialog.Builder builder = new AlertDialog.Builder(this);
      builder.setMessage("¿Desea cerrar sesión?")
              .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                  public void onClick(DialogInterface dialog, int id) {
                      // FIRE ZE MISSILES!
                      SharedPreferences.Editor editor = preferencias_usuarios.edit();
                      editor.clear().commit();
                      Intent intent = new Intent(getApplicationContext(), Login.class);
                      startActivity(intent);
                  }
              })
              .setNegativeButton("No", new DialogInterface.OnClickListener() {
                  public void onClick(DialogInterface dialog, int id) {
                      // User cancelled the dialog
                  }
              });
      // Create the AlertDialog object and return it
      builder.create();
      builder.show();
  }
    public  String enviarcard_aprovados1()  {
        URL url=null;
        String linea="";
        int respuesta=0;
        StringBuilder resul=null;
        try {
            //http://fhkuku182-001-site1.atempurl.com/Grantour.asmx/CargarLugares
            url=new URL("http://proyectoinciencias.gearhostpreview.com/sos_service.asmx/filtros_de_incidencias_porusuario_aprovados_cardview?id_usuario="+id_usuairo);
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
    public  String enviarcard_NO_aprovados1()  {
        URL url=null;
        String linea="";
        int respuesta=0;
        StringBuilder resul=null;
        try {
            //http://fhkuku182-001-site1.atempurl.com/Grantour.asmx/CargarLugares
            url=new URL("http://proyectoinciencias.gearhostpreview.com/sos_service.asmx/filtros_de_incidencias_porusuario_NO_aprovados_cardview?id_usuario="+id_usuairo);
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
}
