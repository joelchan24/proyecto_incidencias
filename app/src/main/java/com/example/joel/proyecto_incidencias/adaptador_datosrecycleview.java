package com.example.joel.proyecto_incidencias;

import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.joel.proyecto_incidencias.clases.datos_incidencia_;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by JOEL on 11/04/2018.
 */

public class adaptador_datosrecycleview extends RecyclerView.Adapter<adaptador_datosrecycleview.ViewHolderDatos> {
    ArrayList<datos_incidencia_> lista_datos;
    ImageView fot;
    String urlFoto;
    Context context;
    Button editar;
    int posicion;
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
    public void onBindViewHolder(ViewHolderDatos holder, final int position)  {
//ahora llamaos el
       // posicion=position;
        holder.incidet.setText(lista_datos.get(position).getIncidencia());
        holder.comentario.setText(lista_datos.get(position).getCometario_admin());
        urlFoto=lista_datos.get(position).getFoto();
        //holder.fot.set(lista_datos.get(position).getFoto());
        Picasso.get().load(urlFoto).resize(150,150).centerCrop().into(fot);
      /*  editar.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Toast.makeText(context,""+lista_datos.get(position).getIncidencia(),Toast.LENGTH_LONG).show();
                GenerarIncidenciaFragment generarIncidenciaFragment = new GenerarIncidenciaFragment();
             //   listaAprovadosFragment.setArguments(bundle);
                MainActivityMenu myActivity = (MainActivityMenu) v.getContext();
                FragmentTransaction fragmentTransaction = ((MainActivityMenu) v.getContext()).getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.contenedor, generarIncidenciaFragment).addToBackStack(null).commit();
            }
        });*/
        editar.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
              // Toast.makeText(context,""+lista_datos.get(position).getIncidencia()+" "+lista_datos.get(position).getId_incidencia()+" "+lista_datos.get(position).getDireccion()+" "+lista_datos.get(position).getLatitud()+" "+lista_datos.get(position).getLongitu()+" "+lista_datos.get(position).getCometario_admin(),Toast.LENGTH_LONG).show();
                GenerarIncidenciaFragment generarIncidenciaFragment = new GenerarIncidenciaFragment();
                //   listaAprovadosFragment.setArguments(bundle);
               MainActivityMenu myActivity = (MainActivityMenu) context;
              FragmentTransaction fragmentTransaction = myActivity.getFragmentManager().beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putInt("id",myActivity.id_usuairo);
                bundle.putString("id_inciden",lista_datos.get(position).getId_incidencia());
                bundle.putString("comen",lista_datos.get(position).getComentario());
                bundle.putString("zona",lista_datos.get(position).getDireccion());
                bundle.putDouble("lat",lista_datos.get(position).getLatitud());
                bundle.putDouble("lot",lista_datos.get(position).getLongitu());
                bundle.putString("fot",lista_datos.get(position).getFoto());
                bundle.putInt("ID",lista_datos.get(position).getId_punto());
                generarIncidenciaFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.contenedor, generarIncidenciaFragment).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        //4 el tamaño
        return lista_datos.size();
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {
        //3 vinculas el holder con el item xml

        TextView comentario,incidet;
        public ViewHolderDatos(final View itemView) {
            super(itemView);
context=itemView.getContext();
            fot=(ImageView)itemView.findViewById(R.id.id_img_holder);
            comentario=(TextView)itemView.findViewById(R.id.id_datos_holder);
            incidet=(TextView)itemView.findViewById(R.id.id_titulo_holder);
            editar=(Button)itemView.findViewById(R.id.btn_editar_incidente);

        }
    }
}
