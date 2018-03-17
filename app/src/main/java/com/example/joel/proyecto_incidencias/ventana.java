package com.example.joel.proyecto_incidencias;

import android.view.LayoutInflater;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by JOEL on 16/03/2018.
 */


public class ventana implements GoogleMap.InfoWindowAdapter {
    private static final String TAG = "CustomInfoWindowAdapter";
    private LayoutInflater inflater;

    public ventana(LayoutInflater inflater){
        this.inflater = inflater;
    }
    @Override
    public View getInfoWindow(Marker marker) {



        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {

        return null;
    }
}
