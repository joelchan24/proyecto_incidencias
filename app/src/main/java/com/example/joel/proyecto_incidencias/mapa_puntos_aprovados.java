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
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;


import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

//import android.support.v4.app.Fragment;


public class mapa_puntos_aprovados extends Fragment implements OnMapReadyCallback {

    public String respueta;
    datos_ventana datos_ventana =new datos_ventana();
    GoogleMap nmap;
    MapView mapView;
    View vista;
    SharedPreferences mapa_preferencias;
    String respuesta;
String baches;
    Marker marker;
    private Spinner spinner;
    int num=4;

    private OnFragmentInteractionListener mListener;

    public mapa_puntos_aprovados() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        vista= inflater.inflate(R.layout.fragment_mapa_puntos_aprovados, container, false);
        List<SocialNetwork> items = new ArrayList<SocialNetwork>(9);
        items.add(new SocialNetwork(getString(R.string.todo), R.drawable.logosos));
        items.add(new SocialNetwork(getString(R.string.baches), R.drawable.baches));
        items.add(new SocialNetwork(getString(R.string.Maltra), R.drawable.maltrato));
        items.add(new SocialNetwork(getString(R.string.lotes), R.drawable.lotes));
        items.add(new SocialNetwork(getString(R.string.van), R.drawable.vandalismo));
        items.add(new SocialNetwork(getString(R.string.Robo), R.drawable.robo));
        items.add(new SocialNetwork(getString(R.string.Quema), R.drawable.quema));
        items.add(new SocialNetwork(getString(R.string.acci), R.drawable.accidentes));
        items.add(new SocialNetwork(getString(R.string.otr), R.drawable.otros));

        spinner = (Spinner)vista.findViewById(R.id.spinner);
        spinner.setAdapter(new SocialNetworkSpinnerAdapter(getActivity(),items));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView <?> adapterView, View view, int i, long l) {
                Toast.makeText(adapterView.getContext(), ((SocialNetwork) adapterView.getItemAtPosition(i)).getNombre()+i, Toast.LENGTH_SHORT).show();
                int posicion =i;
                switch (i)
                {
                    case 1:
                        Thread hilo = new Thread() {
                            @Override
                            public void run() {
                                respuesta = enviar_filtro_incidencia(4);
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
                                                    nmap.addMarker(new MarkerOptions().position(new LatLng(latitud, longitud)).title(nombrelugar).icon(BitmapDescriptorFactory.fromResource(R.drawable.baches)));
                                                    //    Toast.makeText(getActivity(),"jdjdj"+longitud,Toast.LENGTH_LONG).show();
                                                    nmap.moveCamera(CameraUpdateFactory.newLatLng(LA));
                                                }

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        } else {
                                            Toast.makeText(getActivity(),"ss",Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }


                            //  Toast.makeText(getActivity(),"gay",Toast.LENGTH_SHORT).show();
                        };
                        hilo.start();

                        break;
                    case 2:
                        Thread hilo1 = new Thread() {
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
                                                    nmap.addMarker(new MarkerOptions().position(new LatLng(latitud, longitud)).title(nombrelugar).icon(BitmapDescriptorFactory.fromResource(R.drawable.maltrato)));
                                                    //    Toast.makeText(getActivity(),"jdjdj"+longitud,Toast.LENGTH_LONG).show();
                                                    nmap.moveCamera(CameraUpdateFactory.newLatLng(LA));
                                                }

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        } else {
Toast.makeText(getActivity(),"ss",Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }


                            //  Toast.makeText(getActivity(),"gay",Toast.LENGTH_SHORT).show();
                        };
                        hilo1.start();


                        break;
                    case 3:
                        Thread hilo2 = new Thread() {
                            @Override
                            public void run() {
                                respuesta = enviar_filtro_incidencia(6);
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
                                                    nmap.addMarker(new MarkerOptions().position(new LatLng(latitud, longitud)).title(nombrelugar).icon(BitmapDescriptorFactory.fromResource(R.drawable.lotes)));
                                                    //    Toast.makeText(getActivity(),"jdjdj"+longitud,Toast.LENGTH_LONG).show();
                                                    nmap.moveCamera(CameraUpdateFactory.newLatLng(LA));
                                                }

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        } else {
                                            Toast.makeText(getActivity(),"ss",Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }


                            //  Toast.makeText(getActivity(),"gay",Toast.LENGTH_SHORT).show();
                        };
                        hilo2.start();

                        break;
                    case 4:
                        Thread hilo3 = new Thread() {
                            @Override
                            public void run() {
                                respuesta = enviar_filtro_incidencia(7);
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        int con = OBJJson(respuesta);
                                        nmap.clear();
                                        if (con > 0) {
                                            try {

                                                JSONArray array = new JSONArray(respuesta);
                                                for (int i = 0; i < array.length(); i++) {
                                                    JSONObject row = array.getJSONObject(i);
                                                    Double latitud = row.getDouble("Latitud");
                                                    Double longitud = row.getDouble("Longitud");
                                                    String nombrelugar = row.getString("Zona");
                                                    LatLng LA = new LatLng(latitud, longitud);
                                                    nmap.addMarker(new MarkerOptions().position(new LatLng(latitud, longitud)).title(nombrelugar).icon(BitmapDescriptorFactory.fromResource(R.drawable.vandalismo)));
                                                    //    Toast.makeText(getActivity(),"jdjdj"+longitud,Toast.LENGTH_LONG).show();
                                                    nmap.moveCamera(CameraUpdateFactory.newLatLng(LA));
                                                }

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        } else {
                                            Toast.makeText(getActivity(),"ss",Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }


                            //  Toast.makeText(getActivity(),"gay",Toast.LENGTH_SHORT).show();
                        };
                        hilo3.start();

                        break;
                    case 5:
                        Thread hilo4 = new Thread() {
                            @Override
                            public void run() {
                                respuesta = enviar_filtro_incidencia(8);
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        int con = OBJJson(respuesta);
                                        nmap.clear();
                                        if (con > 0) {
                                            try {

                                                JSONArray array = new JSONArray(respuesta);
                                                for (int i = 0; i < array.length(); i++) {
                                                    JSONObject row = array.getJSONObject(i);
                                                    Double latitud = row.getDouble("Latitud");
                                                    Double longitud = row.getDouble("Longitud");
                                                    String nombrelugar = row.getString("Zona");
                                                    LatLng LA = new LatLng(latitud, longitud);
                                                    nmap.addMarker(new MarkerOptions().position(new LatLng(latitud, longitud)).title(nombrelugar).icon(BitmapDescriptorFactory.fromResource(R.drawable.robo)));
                                                    //    Toast.makeText(getActivity(),"jdjdj"+longitud,Toast.LENGTH_LONG).show();
                                                    nmap.moveCamera(CameraUpdateFactory.newLatLng(LA));
                                                }

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        } else {
                                            Toast.makeText(getActivity(),"ss",Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }


                            //  Toast.makeText(getActivity(),"gay",Toast.LENGTH_SHORT).show();
                        };
                        hilo4.start();

                        break;
                    case 6:
                        Thread hilo5 = new Thread() {
                            @Override
                            public void run() {
                                respuesta = enviar_filtro_incidencia(9);
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        int con = OBJJson(respuesta);
                                        nmap.clear();
                                        if (con > 0) {
                                            try {

                                                JSONArray array = new JSONArray(respuesta);
                                                for (int i = 0; i < array.length(); i++) {
                                                    JSONObject row = array.getJSONObject(i);
                                                    Double latitud = row.getDouble("Latitud");
                                                    Double longitud = row.getDouble("Longitud");
                                                    String nombrelugar = row.getString("Zona");
                                                    LatLng LA = new LatLng(latitud, longitud);
                                                    nmap.addMarker(new MarkerOptions().position(new LatLng(latitud, longitud)).title(nombrelugar).icon(BitmapDescriptorFactory.fromResource(R.drawable.quema)));
                                                    //    Toast.makeText(getActivity(),"jdjdj"+longitud,Toast.LENGTH_LONG).show();
                                                    nmap.moveCamera(CameraUpdateFactory.newLatLng(LA));
                                                }

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        } else {
                                            Toast.makeText(getActivity(),"ss",Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }


                            //  Toast.makeText(getActivity(),"gay",Toast.LENGTH_SHORT).show();
                        };
                        hilo5.start();
                        break;
                    case 7:
                        Thread hilo6 = new Thread() {
                            @Override
                            public void run() {
                                respuesta = enviar_filtro_incidencia(10);
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        int con = OBJJson(respuesta);
                                        nmap.clear();
                                        if (con > 0) {
                                            try {

                                                JSONArray array = new JSONArray(respuesta);
                                                for (int i = 0; i < array.length(); i++) {
                                                    JSONObject row = array.getJSONObject(i);
                                                    Double latitud = row.getDouble("Latitud");
                                                    Double longitud = row.getDouble("Longitud");
                                                    String nombrelugar = row.getString("Zona");
                                                    LatLng LA = new LatLng(latitud, longitud);
                                                    nmap.addMarker(new MarkerOptions().position(new LatLng(latitud, longitud)).title(nombrelugar).icon(BitmapDescriptorFactory.fromResource(R.drawable.accidentes)));
                                                    //    Toast.makeText(getActivity(),"jdjdj"+longitud,Toast.LENGTH_LONG).show();
                                                    nmap.moveCamera(CameraUpdateFactory.newLatLng(LA));
                                                }

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        } else {
                                            Toast.makeText(getActivity(),"ss",Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }


                            //  Toast.makeText(getActivity(),"gay",Toast.LENGTH_SHORT).show();
                        };
                        hilo6.start();

                        break;
                    case 8:
                        Thread hilo7 = new Thread() {
                            @Override
                            public void run() {
                                respuesta = enviar_filtro_incidencia(11);
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        int con = OBJJson(respuesta);
                                        nmap.clear();
                                        if (con > 0) {
                                            try {

                                                JSONArray array = new JSONArray(respuesta);
                                                for (int i = 0; i < array.length(); i++) {
                                                    JSONObject row = array.getJSONObject(i);
                                                    Double latitud = row.getDouble("Latitud");
                                                    Double longitud = row.getDouble("Longitud");
                                                    String nombrelugar = row.getString("Zona");
                                                    LatLng LA = new LatLng(latitud, longitud);
                                                    nmap.addMarker(new MarkerOptions().position(new LatLng(latitud, longitud)).title(nombrelugar).icon(BitmapDescriptorFactory.fromResource(R.drawable.otros)));
                                                    //    Toast.makeText(getActivity(),"jdjdj"+longitud,Toast.LENGTH_LONG).show();
                                                    nmap.moveCamera(CameraUpdateFactory.newLatLng(LA));
                                                }

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        } else {
                                            Toast.makeText(getActivity(),"ss",Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }


                            //  Toast.makeText(getActivity(),"gay",Toast.LENGTH_SHORT).show();
                        };
                        hilo7.start();

                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView <?> adapterView) {

            }
        });



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
                               // nmap.clear();
                               // String vaaaa=enviar_filtro_incidencia();
                                JSONArray array = new JSONArray(respuesta);
                                for (int i =0; i<array.length(); i++){
                                    JSONObject row = array.getJSONObject(i);
                                Double    latitud = row.getDouble("Latitud");
                                Double    longitud = row.getDouble("Longitud");
                                 String   nombrelugar = row.getString("Zona");
                                 int incidente= row.getInt("id_peligro");

datos_ventana.setHotel(nombrelugar.toString());
                                    ventana vetana=new ventana(getActivity());
                                    nmap.setInfoWindowAdapter(vetana);
LatLng LA=new LatLng(latitud,longitud);
MarkerOptions markerOptions= new MarkerOptions();

switch (incidente)
{
    case 4:
        nmap.addMarker(markerOptions.position(new LatLng(latitud, longitud)).title(nombrelugar).icon(BitmapDescriptorFactory.fromResource(R.drawable.baches)));
     /*   marker=nmap.addMarker(markerOptions);
        datos_ventana.setHotel(nombrelugar);
        marker.setTag(datos_ventana);
        marker.showInfoWindow();*/
        break;
    case 5:
        nmap.addMarker(markerOptions.position(new LatLng(latitud, longitud)).title(nombrelugar).icon(BitmapDescriptorFactory.fromResource(R.drawable.maltrato)));
       /* marker=nmap.addMarker(markerOptions);datos_ventana.setHotel(nombrelugar);

        marker.setTag(datos_ventana);
        marker.showInfoWindow();*/
        break;
    case 6:
        nmap.addMarker(markerOptions.position(new LatLng(latitud, longitud)).title(nombrelugar).icon(BitmapDescriptorFactory.fromResource(R.drawable.lotes)));
      /*  marker=nmap.addMarker(markerOptions);
        datos_ventana.setHotel(nombrelugar);
        marker.setTag(datos_ventana);
        marker.showInfoWindow();*/
        break;
    case 7:
        nmap.addMarker(markerOptions.position(new LatLng(latitud, longitud)).title(nombrelugar).icon(BitmapDescriptorFactory.fromResource(R.drawable.vandalismo)));
    /*    marker=nmap.addMarker(markerOptions);
        datos_ventana.setHotel(nombrelugar);
        marker.setTag(datos_ventana);
        marker.showInfoWindow();*/
        break;
    case 8:
        nmap.addMarker(markerOptions.position(new LatLng(latitud, longitud)).title(nombrelugar).icon(BitmapDescriptorFactory.fromResource(R.drawable.robo)));
  /*      marker=nmap.addMarker(markerOptions);
        datos_ventana.setHotel(nombrelugar);
        marker.setTag(datos_ventana);
        marker.showInfoWindow();*/
        break;
    case 9:
        nmap.addMarker(markerOptions.position(new LatLng(latitud, longitud)).title(nombrelugar).icon(BitmapDescriptorFactory.fromResource(R.drawable.quema)));
        /*marker=nmap.addMarker(markerOptions);
        datos_ventana.setHotel(nombrelugar);
        marker.setTag(datos_ventana);
        marker.showInfoWindow();*/
        break;
    case 10:
        nmap.addMarker(markerOptions.position(new LatLng(latitud, longitud)).title(nombrelugar).icon(BitmapDescriptorFactory.fromResource(R.drawable.accidentes)));
        marker=nmap.addMarker(markerOptions);
      /*  datos_ventana.setHotel(nombrelugar);
        marker.setTag(datos_ventana);
        marker.showInfoWindow();*/
        break;
    case 11:
        nmap.addMarker(markerOptions.position(new LatLng(latitud, longitud)).title(nombrelugar).icon(BitmapDescriptorFactory.fromResource(R.drawable.otros)));

        break;


}
                                    marker=nmap.addMarker(markerOptions);
                                    datos_ventana.setHotel(nombrelugar);
                                    marker.setTag(datos_ventana);
                                    marker.showInfoWindow();
                               //     Toast.makeText(getActivity(),"jdjdj"+longitud,Toast.LENGTH_LONG).show();
                                    Double latitudj=20.9673702;
                                    double longitudj=-89.59258569999997;
//Toast.makeText(getActivity(),""+incidente,Toast.LENGTH_LONG).show();
                                    LatLng LAj=new LatLng(latitudj,longitudj);
                                    CameraUpdate miubicacion=CameraUpdateFactory.newLatLngZoom(LAj,12);
                                    nmap.moveCamera(miubicacion);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }



                    }




    public  String enviar_filtro_incidencia(int va)  {
        URL url=null;
        String linea="";
        int respuesta=0;
        StringBuilder resul=null;
        try {
            //http://fhkuku182-001-site1.atempurl.com/Grantour.asmx/CargarLugares
            url=new URL("http://incidencias2.gearhostpreview.com/sos_service.asmx/filtros_de_niveles?id_peligro="+va);
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
