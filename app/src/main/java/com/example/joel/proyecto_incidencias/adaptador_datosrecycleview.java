package com.example.joel.proyecto_incidencias;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.joel.proyecto_incidencias.clases.*;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by JOEL on 11/04/2018.
 */

public class adaptador_datosrecycleview extends RecyclerView.Adapter<adaptador_datosrecycleview.ViewHolderDatos> {
    ArrayList<datos_incidencia_> lista_datos;
    ImageView fot;
    String urlFoto;
    /// 2 se crear el contructor que llame esa lista
    public adaptador_datosrecycleview(ArrayList <datos_incidencia_> lista_datos) {
        this.lista_datos = lista_datos;
    }

    //1 ya instalado la estructura del lista se crear el arraylist

    @Override
    public ViewHolderDatos onCreateViewHolder(ViewGroup parent, int viewType) {
        //aqui se va vincular el itemlist
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recicleview,null,false);
        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderDatos holder, int position) {
//ahora llamaos el
        holder.incidet.setText(lista_datos.get(position).getIncidencia());
        holder.comentario.setText(lista_datos.get(position).getCometario_admin());
        urlFoto=lista_datos.get(position).getFoto();
        //holder.fot.set(lista_datos.get(position).getFoto());
        Picasso.get().load(urlFoto).into(fot);
    }

    @Override
    public int getItemCount() {
        //4 el tamaño
        return lista_datos.size();
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {
        //3 vinculas el holder con el item xml

        TextView comentario,incidet;
        public ViewHolderDatos(View itemView) {
            super(itemView);

            fot=(ImageView)itemView.findViewById(R.id.id_img_holder);
            comentario=(TextView)itemView.findViewById(R.id.id_datos_holder);
            incidet=(TextView)itemView.findViewById(R.id.id_titulo_holder);
        }
    }
}
