package com.example.joel.proyecto_incidencias;
import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

//import android.support.v4.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Estadisticas22Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Estadisticas22Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Estadisticas22Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    String respuesta;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Estadisticas22Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Estadisticas22Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Estadisticas22Fragment newInstance(String param1, String param2) {
        Estadisticas22Fragment fragment = new Estadisticas22Fragment();
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
     View vista_2esta=    inflater.inflate(R.layout.fragment_estadisticas22, container, false);
        HorizontalBarChart barChart=(HorizontalBarChart) vista_2esta.findViewById(R.id.barchar);

        ArrayList<BarEntry> barEntries=new ArrayList <>();
        barEntries.add(new BarEntry(getArguments().getInt("0"),0,"aaaa"));
        barEntries.add(new BarEntry(getArguments().getInt("1"),1,"aaaa"));
        barEntries.add(new BarEntry(getArguments().getInt("2"),2,"aaaa"));
        barEntries.add(new BarEntry(getArguments().getInt("3"),3,"aaaa"));
        barEntries.add(new BarEntry(getArguments().getInt("4"),4,"aaaa"));
        barEntries.add(new BarEntry(getArguments().getInt("5"),5,"aaaa"));
        barEntries.add(new BarEntry(getArguments().getInt("6"),6,"aaaa"));
        barEntries.add(new BarEntry(getArguments().getInt("7"),7,"aaaa"));

        BarDataSet barDataSet= new BarDataSet(barEntries,"datos");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        barDataSet.setDrawValues(true);
            ArrayList<String> fechas=new ArrayList <>();
        fechas.add("Accidente");
        fechas.add("Baches");
        fechas.add("Incendio");
        fechas.add("Lotes Baldíos");
        fechas.add("Maltrato animal");
        fechas.add("Otros");
        fechas.add("Robo");
        fechas.add("Vandalismo");











        BarData barData= new BarData(fechas,barDataSet);

        barChart.setData(barData);

 barChart.setDragEnabled(true);
// barChart.setTouchEnabled(true);
 //barChart.setScaleEnabled(true);

        ;
   return  vista_2esta;
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
    public  String enviarpost()  {
        URL url=null;
        String linea="";
        int respuesta=0;
        StringBuilder resul=null;
        try {
            //http://fhkuku182-001-site1.atempurl.com/Grantour.asmx/CargarLugares
            url=new URL("http://proyectoinciencias.gearhostpreview.com/sos_service.asmx/estadisticas");
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
}
