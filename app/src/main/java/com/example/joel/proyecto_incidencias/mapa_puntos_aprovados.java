package com.example.joel.proyecto_incidencias;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

//import android.support.v4.app.Fragment;


public class mapa_puntos_aprovados extends Fragment implements OnMapReadyCallback {

    public String respueta;

    GoogleMap nmap;
    MapView mapView;
    View vista;
    SharedPreferences mapa_preferencias;
    String respuesta;
String baches;


    private OnFragmentInteractionListener mListener;

    public mapa_puntos_aprovados() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        vista= inflater.inflate(R.layout.fragment_mapa_puntos_aprovados, container, false);

      //  ImageView icon=new ImageView(getActivity());
        /*icon.setImageResource(R.drawable.logosos);
  FloatingActionButton btn=(FloatingActionButton)vista.findViewById(R.id.btn);

        SubActionButton.Builder builder=new SubActionButton.Builder(getActivity());

        ImageView deleteIcon=new ImageView(getActivity());
        deleteIcon.setImageResource(R.drawable.logosos);
        SubActionButton deleteBtn=builder.setContentView(deleteIcon).build();

        ImageView removeIcon=new ImageView(getActivity());
        removeIcon.setImageResource(R.drawable.logosos);
        SubActionButton removeBtn=builder.setContentView(removeIcon).build();

        ImageView addIcon=new ImageView(getActivity());
        addIcon.setImageResource(R.drawable.logosos);
        SubActionButton addBtn=builder.setContentView(addIcon).build();

        final FloatingActionMenu fam=new FloatingActionMenu.Builder(getActivity())
                .addSubActionView(addBtn)
                .addSubActionView(removeBtn)
                .addSubActionView(deleteBtn)
                .attachTo(btn)
                .build();

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Add Clicked", Toast.LENGTH_SHORT).show();
                fam.close(true);
            }
        });
        removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Remove Clicked", Toast.LENGTH_SHORT).show();
                fam.close(true);
            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Delete Clicked", Toast.LENGTH_SHORT).show();
                fam.close(true);
            }
        });*/
      //  ScrollView scrollView=(ScrollView)vista.findViewById(R.id.sss);
       /* final Button btn_prueva=(Button)vista.findViewById(R.id.btm);
        btn_prueva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu= new PopupMenu(getActivity(),btn_prueva);
                popupMenu.getMenuInflater().inflate(R.menu.activity_ventana_ejem,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        Toast.makeText(getActivity(),"dat"+menuItem.getTitle().toString(),Toast.LENGTH_LONG).show();
                        return true;
                    }
                });


            }
        });*/

       /* btn_prueva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/

        //como le asigne la bariable respuets
        //baches
      //  baches = getArguments().get("baches").toString();
        final FloatingActionButton baches=(FloatingActionButton) vista.findViewById(R.id.btn_gay);
baches.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Thread hilo = new Thread() {
            @Override
            public void run() {
                respuesta = enviar_filtro_incidencia(5);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int con = OBJJson(respuesta);
                        if (con > 0) {
                            try {
                                nmap.clear();
                                JSONArray array = new JSONArray(respuesta);
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject row = array.getJSONObject(i);
                                    Double latitud = row.getDouble("Latitud");
                                    Double longitud = row.getDouble("Longitud");
                                    String nombrelugar = row.getString("Zona");
                                    LatLng LA = new LatLng(latitud, longitud);
                                    nmap.addMarker(new MarkerOptions().position(new LatLng(latitud, longitud)).title(nombrelugar));
                                  //    Toast.makeText(getActivity(),"jdjdj"+longitud,Toast.LENGTH_LONG).show();
                                    nmap.moveCamera(CameraUpdateFactory.newLatLng(LA));
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {

                        }
                    }
                });
            }


            //  Toast.makeText(getActivity(),"gay",Toast.LENGTH_SHORT).show();
        };
    hilo.start();
    }

});
        // fin baches

this.respuesta=mapa_preferencias.getString("respuesta_mapa","");
        return  vista;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView=vista.findViewById(R.id.mapa_aprovados);
        if(mapView!=null)
        {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
        mapa_preferencias=context.getSharedPreferences("puntos",Context.MODE_PRIVATE);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());
        nmap=googleMap;
        nmap.setMapType(GoogleMap.MAP_TYPE_NORMAL);






                            try {
                                JSONArray array = new JSONArray(respuesta);
                                for (int i =0; i<array.length(); i++){
                                    JSONObject row = array.getJSONObject(i);
                                Double    latitud = row.getDouble("Latitud");
                                Double    longitud = row.getDouble("Longitud");
                                 String   nombrelugar = row.getString("Zona");
LatLng LA=new LatLng(latitud,longitud);
                                    nmap.addMarker(new MarkerOptions().position(new LatLng(latitud, longitud)).title(nombrelugar));
                               //     Toast.makeText(getActivity(),"jdjdj"+longitud,Toast.LENGTH_LONG).show();
                                    nmap.moveCamera(CameraUpdateFactory.newLatLng(LA));
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }



                    }




    public  String enviar_filtro_incidencia(int res)  {
        URL url=null;
        String linea="";
        int respuesta=0;
        StringBuilder resul=null;
        try {
            //http://fhkuku182-001-site1.atempurl.com/Grantour.asmx/CargarLugares
            url=new URL("http://incidencias2.gearhostpreview.com/sos_service.asmx/filtros_de_niveles?id_peligro="+res);
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

     /*   LatLng sydney = new LatLng(-34, 151);
        nmap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        nmap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        nmap.addMarker(new MarkerOptions().position(new LatLng(20.9169054,-89.94771109999999)).title("kimchil"));*/



    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
