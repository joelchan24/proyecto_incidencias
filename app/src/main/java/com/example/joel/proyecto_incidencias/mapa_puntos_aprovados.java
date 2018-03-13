package com.example.joel.proyecto_incidencias;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.app.Fragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class mapa_puntos_aprovados extends Fragment implements OnMapReadyCallback {

    public String respueta;

    GoogleMap nmap;
    MapView mapView;
    View vista;
    SharedPreferences mapa_preferencias;
    String respuesta;



    private OnFragmentInteractionListener mListener;

    public mapa_puntos_aprovados() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        vista= inflater.inflate(R.layout.fragment_mapa_puntos_aprovados, container, false);
        //como le asigne la bariable respuets
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
                                    Toast.makeText(getActivity(),"jdjdj"+longitud,Toast.LENGTH_LONG).show();
                                    nmap.moveCamera(CameraUpdateFactory.newLatLng(LA));
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }



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
