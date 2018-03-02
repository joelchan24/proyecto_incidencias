package com.example.joel.proyecto_incidencias;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Locale;

public class mapas_incidentes extends FragmentActivity implements GoogleMap.OnMarkerDragListener,OnMapReadyCallback,GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private  Marker prueva_mar,Maracador_moviendodatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapas_incidentes);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng Mexico = new LatLng(23.634501, -102.55278399999997);
        mMap.addMarker(new MarkerOptions().position(Mexico).draggable(true).title("Mexico").snippet("marcador alv......").icon(BitmapDescriptorFactory.fromResource(R.drawable.poweroutage)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Mexico));

        LatLng Merida = new LatLng(20.9673702, -89.59258569999997);
        mMap.addMarker(new MarkerOptions().position(Merida).title("Merida").snippet("marcador alv......").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Merida));

        LatLng prueva= new LatLng(20.9169054,-89.94771109999999);
        prueva_mar =googleMap.addMarker(new MarkerOptions().position(prueva).title("kinchil").draggable(true).snippet("valores").icon(BitmapDescriptorFactory.fromResource(R.drawable.poweroutage)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(prueva));
        //
        LatLng Marcador_drag= new LatLng(40.7127837,-74.00594130000002);
        Maracador_moviendodatos =googleMap.addMarker(new MarkerOptions().position(Marcador_drag).title("new york").draggable(true).snippet("valores").icon(BitmapDescriptorFactory.fromResource(R.drawable.poweroutage)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Marcador_drag));
        //le dicimos a la varibkle gogle que va a escuvar los eventos de los marcadores al hacer clokc
        googleMap.setOnMarkerClickListener(this);
        googleMap.setOnMarkerDragListener(this);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        //if o swith
        if(marker.equals(prueva_mar))
        {
            String lat,log;
            lat=Double.toString(marker.getPosition().latitude);
            log=Double.toString(marker.getPosition().longitude);
            Toast.makeText(getApplicationContext(),"latitud = "+lat+" longitud = "+log,Toast.LENGTH_LONG).show();
        }
        return false;
    }

    @Override
    public void onMarkerDragStart(Marker marker) {
if(marker.equals(Maracador_moviendodatos))
{
    Toast.makeText(this,"marcadore gay inicia",Toast.LENGTH_LONG).show();
}
    }

    @Override
    public void onMarkerDrag(Marker marker) {
if(marker.equals(Maracador_moviendodatos))
        {
            String nuevotitoi=String.format(Locale.getDefault(),getString(R.string.marker_delait_lating),marker.getPosition().latitude,marker.getPosition().longitude);
            setTitle(nuevotitoi);
        }
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        if(marker.equals(Maracador_moviendodatos))
        {
            Toast.makeText(this,"acabaste" +marker.getPosition().latitude+" "+marker.getPosition().longitude,Toast.LENGTH_LONG).show();
        }
    }
}
