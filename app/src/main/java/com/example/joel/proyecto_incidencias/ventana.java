package com.example.joel.proyecto_incidencias;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.squareup.picasso.Picasso;

/**
 * Created by JOEL on 16/03/2018.
 */


public class ventana  implements GoogleMap.InfoWindowAdapter {
    private Context context;

    public ventana(Context ctx){
        context = ctx;
    }
    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = ((Activity)context).getLayoutInflater().inflate(R.layout.cuadro_mapa, null);
        TextView name_tv = view.findViewById(R.id.name);
        TextView details_tv = view.findViewById(R.id.details);
        datos_ventana datos_ventana=(datos_ventana)marker.getTag();
        ImageView img = view.findViewById(R.id.pic);
        name_tv.setText(marker.getTitle());
//        details_tv.setText(datos_ventana.getHotel());

//        String rutaimagen=datos_ventana.getImage();
        Picasso.get().load(R.drawable.baches).into(img);
       return  view;
    }
}
