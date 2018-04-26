package com.example.joel.proyecto_incidencias;

import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

//import android.support.v4.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MisdatosFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class MisdatosFragment extends Fragment  {

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
        EditText correo=(EditText)vista.findViewById(R.id.txt_correo_mis_datos);
        EditText nombre=(EditText)vista.findViewById(R.id.txt_nombre_usuario);
        EditText contraseña=(EditText)vista.findViewById(R.id.txx_contraseña_datos);
        EditText contraseña2=(EditText)vista.findViewById(R.id.txt_conbtras2);
        ImageView foto=(ImageView) vista.findViewById(R.id.img_mis_datos);

correo.setText(getArguments().getString("cor"));
        nombre.setText(getArguments().getString("nom"));
        contraseña2.setText(getArguments().getString("con"));
        contraseña.setText(getArguments().getString("con"));
        Picasso.get().load(getArguments().getString("fot")).into(foto);
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

    public void mostrar() {
        // Create an instance of the dialog fragment and show it
        DialogFragment dialog = new ejemplo();
        dialog.show(getFragmentManager(), "not");
    }

}
