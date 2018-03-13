package com.example.joel.proyecto_incidencias;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

//import android.support.v4.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MisdatosFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class MisdatosFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
Button buttomntoas;
String dat;
    public MisdatosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista=inflater.inflate(R.layout.fragment_misdatos, container, false);
        buttomntoas=(Button)vista.findViewById(R.id.btn_toas);
        buttomntoas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
Agregar_usuairo();
                Toast.makeText(getActivity(),dat,Toast.LENGTH_LONG).show();
            }
        });
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
    public  void Agregar_usuairo()  {
        String urldefait="nota";

        URL url=null;
        String linea="";
        int respuesta=0;
        StringBuilder resul=null;
        try {
            url=new URL("http://incidenciaspro.gearhostpreview.com/sos_service.asmx/Guardar_puntos?longitud=-89.94771109999999&latitud=20.9169054&Zona=usa&id_usuario=3033&id_peligro=7&url=sjsj&comentario=purevagaycomen1hjhjhj");
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
