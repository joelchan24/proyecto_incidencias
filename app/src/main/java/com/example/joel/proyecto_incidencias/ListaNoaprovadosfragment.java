package com.example.joel.proyecto_incidencias;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.joel.proyecto_incidencias.clases.datos_incidencia_;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

//import android.support.v4.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListaNoaprovadosfragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListaNoaprovadosfragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListaNoaprovadosfragment extends Fragment implements com.android.volley.Response.Listener<JSONObject>, com.android.volley.Response.ErrorListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView recyclerView_datos;
    ArrayList<datos_incidencia_> lista_datos;

    int id;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    JsonObjectRequest jsonObjectRequest;

    View vista;
    private OnFragmentInteractionListener mListener;

    public ListaNoaprovadosfragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListaNoaprovadosfragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListaNoaprovadosfragment newInstance(String param1, String param2) {
        ListaNoaprovadosfragment fragment = new ListaNoaprovadosfragment();
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
        // Inflate the layout for this fragment
        vista= inflater.inflate(R.layout.fragment_lista_noaprovadosfragment, container, false);
        id = getArguments().getInt("id");
        lista_datos = new ArrayList <>();
        requestQueue = Volley.newRequestQueue(getContext());
        CargarWebserivice(id);
        return vista;
    }

    private void CargarWebserivice(int id) {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("consultadoi");
        progressDialog.show();
        String urll = "http://proyectoinciencias.gearhostpreview.com/sos_service.asmx/filtros_de_incidencias_porusuario_NO_aprovados_cardview?id_usuario=" + id;

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urll, null, this, this);
        requestQueue.add(jsonObjectRequest);
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
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getContext(),""+error,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        progressDialog.hide();
      //  Toast.makeText(getContext(), "" + response, Toast.LENGTH_LONG).show();
        JSONArray jsonArray = response.optJSONArray("Table");

        for (int i = 0; i <= jsonArray.length(); i++) {
            try {
                JSONObject row = jsonArray.getJSONObject(i);
                lista_datos.add(new datos_incidencia_(row.getString("Peligro"), row.getString("comentadmin"), row.getString("imagen"),row.getDouble("Latitud"),row.getDouble("Longitud"),row.getString("Zona"),row.getString("ID1"),row.getString("comentario"),row.getInt("ID")));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        recyclerView_datos = (RecyclerView) vista.findViewById(R.id.recycle_noaprovados);
        recyclerView_datos.setLayoutManager(new LinearLayoutManager(getContext()));


//llenar_lista();

//ultimo mandar le adaptador y al recliview
        adaptador_datosrecycleview ada = new adaptador_datosrecycleview(lista_datos);
        recyclerView_datos.setAdapter(ada);
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
}
