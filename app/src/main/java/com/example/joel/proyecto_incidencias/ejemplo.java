package com.example.joel.proyecto_incidencias;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

/**
 * Created by JOEL on 11/03/2018.
 */

public class ejemplo extends DialogFragment {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
     //   return super.onCreateDialog(savedInstanceState);

         View content = getActivity().getLayoutInflater().inflate(R.layout.ejemplo,null);

        content.findViewById(R.id.btn_toas);

        content.findViewById(R.id.rbt);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(content);
                builder.setMessage("jOEL")
                // Add action buttons
                .setPositiveButton("Joel 1"/*Mensaje para el botón positivo*/, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //Código para cuando se haga click en positiv
                        Toast.makeText(getActivity(),"gay",Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("Joel2"/*Mensaje para el botón negativo*/, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //Código para cuando se haga click en negativo
                    }
                });
        return builder.create();


    }

}
