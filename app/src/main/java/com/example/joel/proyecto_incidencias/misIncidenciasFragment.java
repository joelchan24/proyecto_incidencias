package com.example.joel.proyecto_incidencias;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Fragment;
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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link misIncidenciasFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class misIncidenciasFragment extends Fragment implements OnMapReadyCallback {
Spinner spinner;
    GoogleMap nmap;
    MapView mapView;
    Marker marcador;
    String respuesta;
    int id_usuario;
    private OnFragmentInteractionListener mListener;

    public misIncidenciasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View vista=inflater.inflate(R.layout.fragment_mis_incidencias, container, false);
        id_usuario = getArguments().getInt("id");
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

        spinner = (Spinner)vista.findViewById(R.id.spinnermis);
        spinner.setAdapter(new SocialNetworkSpinnerAdapter(getActivity(),items));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView <?> adapterView, View view, int i, long l) {
              //  Toast.makeText(adapterView.getContext(), ((SocialNetwork) adapterView.getItemAtPosition(i)).getNombre()+i, Toast.LENGTH_SHORT).show();
                int posicion =i;
                switch (i)
                {case 0:
                    Thread hilotodo90 = new Thread() {
                        @Override
                        public void run() {
                            respuesta = incidencias_usuario(id_usuario);
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
                                                String imagen=row.getString("imagen");
                                                int incidente= row.getInt("id_peligro");
                                                LatLng LA = new LatLng(latitud, longitud);
                                                switch (incidente)
                                                {
                                                    case 4:
                                                        marcador=   nmap.addMarker( new MarkerOptions().position(new LatLng(latitud, longitud)).title(nombrelugar).icon(BitmapDescriptorFactory.fromResource(R.drawable.baches)));
     /*   marker=nmap.addMarker(markerOptions);
        datos_ventana.setHotel(nombrelugar);
        marker.setTag(datos_ventana);
        marker.showInfoWindow();*/
                                                        break;
                                                    case 5:
                                                        marcador= nmap.addMarker(new MarkerOptions().position(new LatLng(latitud, longitud)).title(nombrelugar).icon(BitmapDescriptorFactory.fromResource(R.drawable.maltrato)));
       /* marker=nmap.addMarker(markerOptions);datos_ventana.setHotel(nombrelugar);

        marker.setTag(datos_ventana);
        marker.showInfoWindow();*/
                                                        break;
                                                    case 6:
                                                        marcador=    nmap.addMarker(new MarkerOptions().position(new LatLng(latitud, longitud)).title(nombrelugar).icon(BitmapDescriptorFactory.fromResource(R.drawable.lotes)));
      /*  marker=nmap.addMarker(markerOptions);
        datos_ventana.setHotel(nombrelugar);
        marker.setTag(datos_ventana);
        marker.showInfoWindow();*/
                                                        break;
                                                    case 7:
                                                        marcador=    nmap.addMarker(new MarkerOptions().position(new LatLng(latitud, longitud)).title(nombrelugar).icon(BitmapDescriptorFactory.fromResource(R.drawable.vandalismo)));
    /*    marker=nmap.addMarker(markerOptions);
        datos_ventana.setHotel(nombrelugar);
        marker.setTag(datos_ventana);
        marker.showInfoWindow();*/
                                                        break;
                                                    case 8:
                                                        marcador=    nmap.addMarker(new MarkerOptions().position(new LatLng(latitud, longitud)).title(nombrelugar).icon(BitmapDescriptorFactory.fromResource(R.drawable.robo)));
  /*      marker=nmap.addMarker(markerOptions);
        datos_ventana.setHotel(nombrelugar);
        marker.setTag(datos_ventana);
        marker.showInfoWindow();*/
                                                        break;
                                                    case 9:
                                                        marcador=    nmap.addMarker(new MarkerOptions().position(new LatLng(latitud, longitud)).title(nombrelugar).icon(BitmapDescriptorFactory.fromResource(R.drawable.quema)));
        /*marker=nmap.addMarker(markerOptions);
        datos_ventana.setHotel(nombrelugar);
        marker.setTag(datos_ventana);
        marker.showInfoWindow();*/
                                                        break;
                                                    case 10:
                                                        marcador= nmap.addMarker(new MarkerOptions().position(new LatLng(latitud, longitud)).title(nombrelugar).icon(BitmapDescriptorFactory.fromResource(R.drawable.accidentes)));
                                                        // marker=nmap.addMarker(markerOptions);
      /*  datos_ventana.setHotel(nombrelugar);
        marker.setTag(datos_ventana);
        marker.showInfoWindow();*/
                                                        break;
                                                    case 11:
                                                        marcador=    nmap.addMarker(new MarkerOptions().position(new LatLng(latitud, longitud)).title(nombrelugar).icon(BitmapDescriptorFactory.fromResource(R.drawable.otros)));

                                                        break;


                                                }
                                                //    Toast.makeText(getActivity(),"jdjdj"+longitud,Toast.LENGTH_LONG).show();
                                                ventana ventana = new ventana(getContext());
                                                datos_ventana datos_ventana =new datos_ventana();
                                                datos_ventana.setDetalle(nombrelugar);
                                                datos_ventana.setNombre(nombrelugar);
                                                datos_ventana.setImage(imagen);
                                                marcador.setTag(datos_ventana);
                                                nmap.moveCamera(CameraUpdateFactory.newLatLng(LA));
                                            }

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    } else {
                                        Toast.makeText(getActivity(),"NO HAY INCIDENTES DE VACHES ",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }


                        //  Toast.makeText(getActivity(),"gay",Toast.LENGTH_SHORT).show();
                    };
                    hilotodo90.start();
                    break;
                    case 1:
                        Thread hilotodo = new Thread() {
                            @Override
                            public void run() {
                                respuesta = enviar_filtro_incidencia(4);
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
                                                    String imagen=row.getString("imagen");
                                                    LatLng LA = new LatLng(latitud, longitud);
                                                marcador=    nmap.addMarker(new MarkerOptions().position(new LatLng(latitud, longitud)).title(nombrelugar).icon(BitmapDescriptorFactory.fromResource(R.drawable.baches)));
                                                    //    Toast.makeText(getActivity(),"jdjdj"+longitud,Toast.LENGTH_LONG).show();
                                                    ventana ventana = new ventana(getContext());
                                                    datos_ventana datos_ventana =new datos_ventana();
                                                    datos_ventana.setDetalle(nombrelugar);
                                                    datos_ventana.setNombre(nombrelugar);
                                                    datos_ventana.setImage(imagen);
                                                    marcador.setTag(datos_ventana);
                                                    nmap.moveCamera(CameraUpdateFactory.newLatLng(LA));
                                                }

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        } else {
                                            Toast.makeText(getActivity(),"NO HAY INCIDENTES DE VACHES ",Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }


                            //  Toast.makeText(getActivity(),"gay",Toast.LENGTH_SHORT).show();
                        };
                        hilotodo.start();
                        break;
                    case 2:
                        Thread hilo = new Thread() {
                            @Override
                            public void run() {
                                respuesta = enviar_filtro_incidencia(5);
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
                                                    String imagen=row.getString("imagen");
                                                    LatLng LA = new LatLng(latitud, longitud);
                                              marcador=      nmap.addMarker(new MarkerOptions().position(new LatLng(latitud, longitud)).title(nombrelugar).icon(BitmapDescriptorFactory.fromResource(R.drawable.maltrato)));
                                                    //    Toast.makeText(getActivity(),"jdjdj"+longitud,Toast.LENGTH_LONG).show();
                                                    ventana ventana = new ventana(getContext());
                                                    datos_ventana datos_ventana =new datos_ventana();
                                                    datos_ventana.setDetalle(nombrelugar);
                                                    datos_ventana.setNombre(nombrelugar);
                                                    datos_ventana.setImage(imagen);
                                                    marcador.setTag(datos_ventana);
                                                    nmap.moveCamera(CameraUpdateFactory.newLatLng(LA));
                                                }

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        } else {
                                            Toast.makeText(getActivity(),"NO HAY INCIDENTES DE VACHES ",Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }


                            //  Toast.makeText(getActivity(),"gay",Toast.LENGTH_SHORT).show();
                        };
                        hilo.start();

                        break;
                    case 3:
                        Thread hilo1 = new Thread() {
                            @Override
                            public void run() {
                                respuesta = enviar_filtro_incidencia(6);
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
                                                    String imagen=row.getString("imagen");
                                                    LatLng LA = new LatLng(latitud, longitud);
                                                marcador=    nmap.addMarker(new MarkerOptions().position(new LatLng(latitud, longitud)).title(nombrelugar).icon(BitmapDescriptorFactory.fromResource(R.drawable.lotes)));
                                                    //    Toast.makeText(getActivity(),"jdjdj"+longitud,Toast.LENGTH_LONG).show();
                                                    ventana ventana = new ventana(getContext());
                                                    datos_ventana datos_ventana =new datos_ventana();
                                                    datos_ventana.setDetalle(nombrelugar);
                                                    datos_ventana.setNombre(nombrelugar);
                                                    datos_ventana.setImage(imagen);
                                                    marcador.setTag(datos_ventana);
                                                    nmap.moveCamera(CameraUpdateFactory.newLatLng(LA));
                                                }

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        } else {
                                            Toast.makeText(getActivity(),"NO HAY INCIDENTES DE MALTRATO ANIMAL ",Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }


                            //  Toast.makeText(getActivity(),"gay",Toast.LENGTH_SHORT).show();
                        };
                        hilo1.start();


                        break;
                    case 4:
                        Thread hilo2 = new Thread() {
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
                                                    String imagen=row.getString("imagen");
                                                    LatLng LA = new LatLng(latitud, longitud);
                                                 marcador=   nmap.addMarker(new MarkerOptions().position(new LatLng(latitud, longitud)).title(nombrelugar).icon(BitmapDescriptorFactory.fromResource(R.drawable.vandalismo)));
                                                    //    Toast.makeText(getActivity(),"jdjdj"+longitud,Toast.LENGTH_LONG).show();
                                                    ventana ventana = new ventana(getContext());
                                                    datos_ventana datos_ventana =new datos_ventana();
                                                    datos_ventana.setDetalle(nombrelugar);
                                                    datos_ventana.setNombre(nombrelugar);
                                                    datos_ventana.setImage(imagen);
                                                    marcador.setTag(datos_ventana);
                                                    nmap.moveCamera(CameraUpdateFactory.newLatLng(LA));
                                                }

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        } else {
                                            Toast.makeText(getActivity(),"NO HAY INCIDENTES DE LOTES VALDÍOS ",Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }


                            //  Toast.makeText(getActivity(),"gay",Toast.LENGTH_SHORT).show();
                        };
                        hilo2.start();

                        break;
                    case 5:
                        Thread hilo3 = new Thread() {
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
                                                    String imagen=row.getString("imagen");
                                                    LatLng LA = new LatLng(latitud, longitud);
                                                    marcador=nmap.addMarker(new MarkerOptions().position(new LatLng(latitud, longitud)).title(nombrelugar).icon(BitmapDescriptorFactory.fromResource(R.drawable.robo)));
                                                    //    Toast.makeText(getActivity(),"jdjdj"+longitud,Toast.LENGTH_LONG).show();
                                                    ventana ventana = new ventana(getContext());
                                                    datos_ventana datos_ventana =new datos_ventana();
                                                    datos_ventana.setDetalle(nombrelugar);
                                                    datos_ventana.setNombre(nombrelugar);
                                                    datos_ventana.setImage(imagen);
                                                    marcador.setTag(datos_ventana);
                                                    nmap.moveCamera(CameraUpdateFactory.newLatLng(LA));
                                                }

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        } else {
                                            Toast.makeText(getActivity(),"NO HAY INCIDENTES DE VANDALISMO ",Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }


                            //  Toast.makeText(getActivity(),"gay",Toast.LENGTH_SHORT).show();
                        };
                        hilo3.start();

                        break;
                    case 6:
                        Thread hilo4 = new Thread() {
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
                                                    String imagen=row.getString("imagen");
                                                    LatLng LA = new LatLng(latitud, longitud);
                                                    marcador=nmap.addMarker(new MarkerOptions().position(new LatLng(latitud, longitud)).title(nombrelugar).icon(BitmapDescriptorFactory.fromResource(R.drawable.quema)));
                                                    //    Toast.makeText(getActivity(),"jdjdj"+longitud,Toast.LENGTH_LONG).show();
                                                    ventana ventana = new ventana(getContext());
                                                    datos_ventana datos_ventana =new datos_ventana();
                                                    datos_ventana.setDetalle(nombrelugar);
                                                    datos_ventana.setNombre(nombrelugar);
                                                    datos_ventana.setImage(imagen);
                                                    marcador.setTag(datos_ventana);
                                                    nmap.moveCamera(CameraUpdateFactory.newLatLng(LA));
                                                }

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        } else {
                                            Toast.makeText(getActivity(),"NO HAY INCIDENTES DE ROBO ",Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }


                            //  Toast.makeText(getActivity(),"gay",Toast.LENGTH_SHORT).show();
                        };
                        hilo4.start();

                        break;
                    case 7:
                        Thread hilo5 = new Thread() {
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
                                                    String imagen=row.getString("imagen");
                                                    LatLng LA = new LatLng(latitud, longitud);
                                                    marcador=nmap.addMarker(new MarkerOptions().position(new LatLng(latitud, longitud)).title(nombrelugar).icon(BitmapDescriptorFactory.fromResource(R.drawable.accidentes)));
                                                    //    Toast.makeText(getActivity(),"jdjdj"+longitud,Toast.LENGTH_LONG).show();
                                                    ventana ventana = new ventana(getContext());
                                                    datos_ventana datos_ventana =new datos_ventana();
                                                    datos_ventana.setDetalle(nombrelugar);
                                                    datos_ventana.setNombre(nombrelugar);
                                                    datos_ventana.setImage(imagen);
                                                    marcador.setTag(datos_ventana);
                                                    nmap.moveCamera(CameraUpdateFactory.newLatLng(LA));
                                                }

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        } else {
                                            Toast.makeText(getActivity(),"NO HAY INCIDENTES DE QUEMA DE BASURA",Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }


                            //  Toast.makeText(getActivity(),"gay",Toast.LENGTH_SHORT).show();
                        };
                        hilo5.start();
                        break;
                    case 8:
                        Thread hilo6 = new Thread() {
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
                                                    String imagen=row.getString("imagen");
                                                    LatLng LA = new LatLng(latitud, longitud);
                                           marcador=         nmap.addMarker(new MarkerOptions().position(new LatLng(latitud, longitud)).title(nombrelugar).icon(BitmapDescriptorFactory.fromResource(R.drawable.otros)));
                                                    //    Toast.makeText(getActivity(),"jdjdj"+longitud,Toast.LENGTH_LONG).show();
                                                    ventana ventana = new ventana(getContext());
                                                    datos_ventana datos_ventana =new datos_ventana();
                                                    datos_ventana.setDetalle(nombrelugar);
                                                    datos_ventana.setNombre(nombrelugar);
                                                    datos_ventana.setImage(imagen);
                                                    marcador.setTag(datos_ventana);
                                                    nmap.moveCamera(CameraUpdateFactory.newLatLng(LA));
                                                }

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        } else {
                                            Toast.makeText(getActivity(),"NO HAY INCIDENTES DE ACIIDENTES AUTOMOVILISTICOS ",Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }


                            //  Toast.makeText(getActivity(),"gay",Toast.LENGTH_SHORT).show();
                        };
                        hilo6.start();

                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView <?> adapterView) {

            }
        });





       mapView =(MapView)vista.findViewById(R.id.mis_incidencias);








        if(mapView!=null)
        {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
        return vista;
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
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getActivity());
        nmap=googleMap;
        nmap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        LatLng LA=new LatLng(20.9673702,-89.59258569999997);
        CameraUpdate miubicacion= CameraUpdateFactory.newLatLngZoom(LA,12);
        ventana ventana = new ventana(getContext());
        //ventana vetana=new ventana(getActivity());
        nmap.setInfoWindowAdapter(ventana);
nmap.moveCamera(miubicacion);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public  String enviar_filtro_incidencia(int va)  {
        URL url=null;
        String linea="";
        int respuesta=0;
        StringBuilder resul=null;
        try {
            //http://fhkuku182-001-site1.atempurl.com/Grantour.asmx/CargarLugares
            url=new URL("http://proyectoinciencias.gearhostpreview.com/sos_service.asmx/filtros_de_incidencias_porusuario?id_peligro="+va+"&id_usuario="+id_usuario);
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
    public  String incidencias_usuario(int va)  {
        URL url=null;
        String linea="";
        int respuesta=0;
        StringBuilder resul=null;
        try {
            //http://fhkuku182-001-site1.atempurl.com/Grantour.asmx/CargarLugares
            url=new URL("http://proyectoinciencias.gearhostpreview.com/sos_service.asmx/mostrar_puntos_aprovados_usuario?id="+id_usuario);
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
