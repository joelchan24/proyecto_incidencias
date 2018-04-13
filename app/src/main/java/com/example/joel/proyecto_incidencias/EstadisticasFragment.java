package com.example.joel.proyecto_incidencias;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

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

//import android.support.v4.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EstadisticasFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class EstadisticasFragment extends Fragment implements com.android.volley.Response.Listener<JSONObject>, com.android.volley.Response.ErrorListener{

    private OnFragmentInteractionListener mListener;
    String respuesta;


    View vista_frag;

    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    JsonObjectRequest jsonObjectRequest;
    JSONObject jsonObject;
    final Number[] val = new Integer[8];
    final Number[] domainLabels = {1, 2, 3, 6, 7, 8, 9, 10, 13, 14};
clase_traedatos clase_traedatos=new clase_traedatos();
    public EstadisticasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         vista_frag=inflater.inflate(R.layout.fragment_estadisticas, container, false);
    //    FragmentTabHost host=(FragmentTabHost)vista_frag.findViewById(R.id.tabhost);
     //  host.setup(getActivity(),getSupportFra,R.id.tabhost);
        //web service
      // requestQueue= Volley.newRequestQueue(getContext());
     //    CargarWebserivice();
     /*   val[0]=getArguments().getInt("0");
        val[1]=getArguments().getInt("1");
        val[2]=getArguments().getInt("2");
        val[3]=getArguments().getInt("3");
        val[4]=getArguments().getInt("4");
        val[5]=getArguments().getInt("5");
        val[6]=getArguments().getInt("6");
        val[7]=getArguments().getInt("7");*/
        LineChart lineChart= (LineChart)vista_frag.findViewById(R.id.puntos);
        ArrayList<Entry> entries=new ArrayList <>();
        entries.add(new Entry(getArguments().getInt("0"),0));
        entries.add(new Entry(getArguments().getInt("1"),1));
        entries.add(new Entry(getArguments().getInt("2"),2));
        entries.add(new Entry(getArguments().getInt("3"),3));
        entries.add(new Entry(getArguments().getInt("4"),4));
        entries.add(new Entry(getArguments().getInt("5"),5));
        entries.add(new Entry(getArguments().getInt("6"),6));
        entries.add(new Entry(getArguments().getInt("7"),7));
       LineDataSet lineDataSet= new LineDataSet(entries,"d");
       lineChart.setBorderColor(Color.BLUE);

       ArrayList<String> labels=new ArrayList <>();
        labels.add("Accidente");
        labels.add("Baches");
        labels.add("Incendio");
        labels.add("Lotes Baldíos");
        labels.add("Maltrato animal");
        labels.add("Otros");
        labels.add("Robo");
        labels.add("Vandalismo");
        lineDataSet.setDrawFilled(true);
        lineDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        LineData lineData= new LineData(labels,lineDataSet);
        XAxis xAxis=lineChart.getXAxis();
        xAxis.setLabelsToSkip(0);
        xAxis.setLabelRotationAngle(20);
        xAxis.setTextSize(9);

        lineChart.setData(lineData);
        lineChart.setHorizontalScrollBarEnabled(true
        );
      /*  grafica=(XYPlot)vista_frag.findViewById(R.id.grafica1);
      //  final Number[] domainLabels = {1, 2, 3, 6, 7, 8, 9, 10, 13, 14};

       Number [] daros={1,4,6,6,7,4,5,6};

        int val1;



*//*for(int i=0;i<=val.length;i++)
{
    Toast.makeText(getActivity(),val[i].toString(),Toast.LENGTH_LONG).show();
}*//*

       XYSeries serie1=new SimpleXYSeries(Arrays.asList(daros), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY,"serie1");

        XYSeries serie2=new SimpleXYSeries(Arrays.asList(val), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY,"serie1");


        //modificamos color primera serie
       LineAndPointFormatter serie1format= new LineAndPointFormatter(  Color.rgb(0,200,0),//color de la linea
                Color.rgb(200,0,0),null,null
        );
        LineAndPointFormatter serie1format2= new LineAndPointFormatter(
                Color.rgb(0,200,0),//color de la linea
                Color.rgb(0,0,200),null,null


        );
        grafica.addSeries(serie2,serie1format2);
        grafica.addSeries(serie1,serie1format);
      *//*  grafica.getGraph().getLineLabelStyle(XYGraphWidget.Edge.BOTTOM).setFormat(new Format() {
            @Override
            public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
                int i = Math.round(((Number) obj).floatValue());
                return toAppendTo.append(domainLabels[i]);
            }
            @Override
            public Object parseObject(String source, ParsePosition pos) {
                return null;
            }
        });*//*
        serie1format2.setInterpolationParams(
                new CatmullRomInterpolator.Params(20, CatmullRomInterpolator.Type.Centripetal));
        serie1format.getLinePaint().setPathEffect(new DashPathEffect(new float[] {

                // always use DP when specifying pixel sizes, to keep things consistent across devices:
                PixelUtils.dpToPix(20),
                PixelUtils.dpToPix(15)}, 0));
        //  Integer[] val = new Integer[5];*/
        //




        //



        return  vista_frag;
    }

    private void CargarWebserivice() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("consultadoi");
        progressDialog.show();
        String urll="http://proyectoinciencias.gearhostpreview.com/sos_service.asmx/estadisticas";

        jsonObjectRequest= new JsonObjectRequest(Request.Method.GET,urll,null,this,this);
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
        progressDialog.hide();

        Toast.makeText(getContext(),""+error,Toast.LENGTH_LONG).show();
        Toast.makeText(getContext(),""+jsonObject,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(JSONObject response) {
    //   jsonObject=response;
progressDialog.hide();
//Toast.makeText(getContext(),"ahuevo "+response,Toast.LENGTH_LONG).show();
JSONArray jsonArray=response.optJSONArray("Table");
jsonObject=null;
for (int i=0;i<=jsonArray.length();i++) {
    try {
        jsonObject = jsonArray.getJSONObject(i);
        val[i] = jsonObject.getInt("total");
        if (i == 2) {
            clase_traedatos.setDato1(jsonObject.getInt("total"));
        }
        Toast.makeText(getActivity(), "" + val[i], Toast.LENGTH_LONG).show();
    } catch (JSONException e) {
        e.printStackTrace();
    }


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
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View vista = inflater.inflate(R.layout.ejemplo, null);

        builder.setView(vista);


      /*  Button signup = (Button) v.findViewById(R.id.crear_boton);
        Button signin = (Button) v.findViewById(R.id.entrar_boton);

        signup.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Crear Cuenta...
                        dismiss();
                    }
                }
        );

        signin.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Loguear...
                        dismiss();
                    }
                }

        );*/

        return builder.create();
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
