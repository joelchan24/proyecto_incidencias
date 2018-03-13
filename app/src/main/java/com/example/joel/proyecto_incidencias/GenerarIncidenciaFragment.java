package com.example.joel.proyecto_incidencias;

import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;

//import android.support.v4.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GenerarIncidenciaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GenerarIncidenciaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GenerarIncidenciaFragment extends Fragment implements OnMapReadyCallback,GoogleMap.OnMarkerDragListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    int id_usuario;
    String dat;
    GoogleMap ngogle;
    MapView mapa;
    Marker marcadorgay;
    double lo;
    double latitud;
TextView zona,comentario,algo;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public GenerarIncidenciaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GenerarIncidenciaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GenerarIncidenciaFragment newInstance(String param1, String param2) {
        GenerarIncidenciaFragment fragment = new GenerarIncidenciaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      View vista_crearincidencias=inflater.inflate(R.layout.fragment_generar_incidencia, container, false);
         id_usuario = getArguments().getInt("id");
         zona=(TextView)vista_crearincidencias.findViewById(R.id.txt_zona);
         comentario=(TextView)vista_crearincidencias.findViewById(R.id.txt_comentario);
         algo=(TextView)vista_crearincidencias.findViewById(R.id.txt_algo);
      String id=Integer.toString(id_usuario);
       
        Button guardar= (Button)vista_crearincidencias.findViewById(R.id.btn_guardar);
         guardar.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Toast.makeText(getActivity(),""+id_usuario,Toast.LENGTH_LONG).show();
                 Agregar_incidencia(lo,latitud,algo.getText().toString(),id_usuario,4,comentario.getText().toString());
             }
         });
      Button button=vista_crearincidencias.findViewById(R.id.btn_ejem);
      button.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {

             DialogFragment d = new ejemplo(); //Instanciamos la clase con el dialogo
             // d.setCancelable(false);//Hacemos que no se pueda saltar el dialogo (opcional)
              d.show(getFragmentManager(), "NEWUSER");
          }
      });
        mapa=vista_crearincidencias.findViewById(R.id.mvp_mapita);
        if(mapa!=null)
        {
            mapa.onCreate(null);
            mapa.onResume();
            mapa.getMapAsync(this);
        }
        return vista_crearincidencias;
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
        MapsInitializer.initialize(getContext());
        ngogle=googleMap;
        ngogle.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        Double latitud=20.9673702;
                double longitud=-89.59258569999997;
                        String nombrelugar="sss";
        LatLng LA=new LatLng(latitud,longitud);
     marcadorgay=   ngogle.addMarker(new MarkerOptions().position(LA).title(nombrelugar).snippet("ssss").draggable(true));

        ngogle.moveCamera(CameraUpdateFactory.newLatLng(LA));
        googleMap.setOnMarkerDragListener(this);
    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        if(marker.equals(marcadorgay))
        {
 latitud =marker.getPosition().longitude;
 lo=marker.getPosition().latitude;
            Toast.makeText(getActivity(),""+lo+"  "+latitud,Toast.LENGTH_LONG).show();
            zona.setText(Double.toString(latitud));

            comentario.setText(Double.toString(lo));
        }

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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    public AlertDialog createLoginDialogo(){
//infkar el dialogo

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflador = getActivity().getLayoutInflater();
        View vista_ejemplo=inflador.inflate(R.layout.ejemplo, null);

        builder.setView(vista_ejemplo);
        /////////////////////////////////////////////77
        Button butotoas=(Button)vista_ejemplo.findViewById(R.id.btn_toas);


        return builder.create();
    }
    public  void Agregar_incidencia(double longi,double lat,String zona,int id_usuario,int id_incidencia,String come)  {
        String urldefait="nota";

        URL url=null;
        String linea="";
        int respuesta=0;
        StringBuilder resul=null;
        try {
            url=new URL("http://incidenciaspro.gearhostpreview.com/sos_service.asmx/Guardar_puntos?longitud="+longi+"&latitud="+lat+"&Zona="+zona+"&id_usuario="+id_usuario+"&id_peligro="+id_incidencia+"&url="+urldefait+"&comentario="+come);
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
                dat=resul.toString();
            }
        }catch (Exception e)
        {}


    }

}