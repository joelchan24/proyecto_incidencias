package com.example.joel.proyecto_incidencias;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Estadisticas33Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class Estadisticas33Fragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public Estadisticas33Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista33= inflater.inflate(R.layout.fragment_estadisticas33, container, false);
        PieChart pieChart=(PieChart)vista33.findViewById(R.id.piechar);

        ArrayList<Entry> entries=new ArrayList <>();
      //  entries.add(new Entry(4, 0));
                entries.add(new Entry(getArguments().getInt("0"), 0));
                entries.add(new Entry(getArguments().getInt("1"), 1));
                entries.add(new Entry(getArguments().getInt("2"), 2));
                entries.add(new Entry(getArguments().getInt("3"), 3));
                entries.add(new Entry(getArguments().getInt("4"), 4));
        entries.add(new Entry(getArguments().getInt("5"), 5));
        entries.add(new Entry(getArguments().getInt("6"), 6));
        entries.add(new Entry(getArguments().getInt("7"), 7));
        PieDataSet dataset= new PieDataSet(entries,"datos");
dataset.setColors(ColorTemplate.COLORFUL_COLORS);


ArrayList<String>labels=new ArrayList <>();
        labels.add("Accidente");
        labels.add("Baches");
        labels.add("Incendio");
        labels.add("Lotes Baldíos");
        labels.add("Maltrato animal");
        labels.add("Otros");
        labels.add("Robo");
        labels.add("Vandalismo");
        /*  labels.add(getArguments().getString("8"));
        labels.add(getArguments().getString("9"));
        labels.add(getArguments().getString("10"));
        labels.add(getArguments().getString("11"));
        labels.add(getArguments().getString("12"));
        labels.add(getArguments().getString("13"));
        labels.add(getArguments().getString("14"));
        labels.add(getArguments().getString("15"));*/

        PieData data = new PieData(labels, dataset);
        pieChart.setData(data);

        return  vista33;
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
}
