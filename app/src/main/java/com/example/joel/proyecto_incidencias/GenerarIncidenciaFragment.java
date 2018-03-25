package com.example.joel.proyecto_incidencias;

import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

//import android.support.v4.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GenerarIncidenciaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GenerarIncidenciaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GenerarIncidenciaFragment extends Fragment implements OnMapReadyCallback,GoogleMap.OnMarkerDragListener, com.android.volley.Response.Listener<JSONObject>, com.android.volley.Response.ErrorListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RequestQueue requestQueue;
    JsonObjectRequest jsonObject;
    int id_usuario;
    String dat;
    GoogleMap ngogle;
    MapView mapa;
    Marker marcadorgay;
    double lo;
    double latitud;
    String foto_string;
    int   id_insidencia;
// static final int REQUEST_IMAGE_CAPTURE = 123;
    String acciones;
TextView zona,comentario;
Uri direcionimagen;
String photoPath;
File photo;
    ImageButton foto;
Bitmap ima;
    private final String ruta_fotos = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/todopro/";

    private File file = new File(ruta_fotos);
    CharSequence[] arreglo={"BACHES","MALTRATO ANIMAL","LOTES BALDÍOS","VANDALISMO ","ROBO","QUEMA DE BASURA","ACCIDENTES AUTOMOVILÍSTICOS","OTROS"};
    CharSequence[] array={"4","5","6","7","8","9","10","11"};

    String datogayner;
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
        Fragment FRA=this;

//
      View vista_crearincidencias=inflater.inflate(R.layout.fragment_generar_incidencia, container, false);
         id_usuario = getArguments().getInt("id");
         zona=(TextView)vista_crearincidencias.findViewById(R.id.txt_dadada);
         comentario=(TextView)vista_crearincidencias.findViewById(R.id.txt_comentario);
         foto=(ImageButton)vista_crearincidencias.findViewById(R.id.btn_foto);
        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final  CharSequence[] arreglo = {"Camara", "Galeria"};
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity());
                builder.setTitle("Selecciona");
                builder.setSingleChoiceItems(arreglo, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if(which == 0)
                        {
                            acciones = "CAMARA";
                           dispatchTakePictureIntent();
                        }
                        else
                        {
                            acciones="GALERIA";
                           SelectGaleria();
                        }
                        dialog.cancel();
                    }
                });
                android.support.v7.app.AlertDialog alertDialog = builder.create();
                alertDialog.show();
             /*   String state= Environment.getExternalStorageState();
                if(Environment.MEDIA_MOUNTED.equals(state)){
                    long captureTime=System.currentTimeMillis();
                    photoPath = Environment.getExternalStorageDirectory() + "/DCIM/Camera/Point" + captureTime + ".jpg";
                    try{
                        Intent intent=new Intent("android.media.action.IMAGE_CAPTURE");
                        photo=new File(photoPath);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
                        startActivityForResult(Intent.createChooser(intent, "Capture una foto"), REQUEST_IMAGE_CAPTURE);

                    }
                    catch (Exception e){

                    }

                }*/
            }
        });
      String id=Integer.toString(id_usuario);

requestQueue= Volley.newRequestQueue(getContext());
        Button guardar= (Button)vista_crearincidencias.findViewById(R.id.btn_guardar);
         guardar.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Toast.makeText(getActivity(),""+dat+" "+id_usuario,Toast.LENGTH_LONG).show();
//enviarpost();
                 cargar_webservice(marcadorgay.getPosition().longitude,marcadorgay.getPosition().latitude,zona.getText().toString(),id_usuario,id_insidencia,comentario.getText().toString(),foto_string);
              //   Toast.makeText(getActivity(),""+id_usuario,Toast.LENGTH_LONG).show();
             }
         });
      Button button=vista_crearincidencias.findViewById(R.id.btn_ejem);
      button.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              seleccionar(view);

         //    DialogFragment d = new ejemplo(); //Instanciamos la clase con el dialogo
             // d.setCancelable(false);//Hacemos que no se pueda saltar el dialogo (opcional)
          //    d.show(getFragmentManager(), "NEWUSER");
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



    private static final  int SELECT_FILE  =1;
    protected void SelectGaleria()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Seleccione una imagen"), SELECT_FILE);
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            long captureTime = System.currentTimeMillis();
            photoPath = Environment.getExternalStorageDirectory() + "/DCIM/Camera/Point" + captureTime + ".jpg";
            try {
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                photo = new File(photoPath);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
                startActivityForResult(Intent.createChooser(intent, "Capture una foto"), REQUEST_IMAGE_CAPTURE);

            } catch (Exception e) {

            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && acciones =="CAMARA") {
            File imgfile = new File(photoPath);
            Bitmap bitmap = BitmapFactory.decodeFile(imgfile.getAbsolutePath());

            direcionimagen = Uri.fromFile(photo);
            Picasso.get().load(direcionimagen).into(foto);
            // foto.setImageBitmap(bitmap);

            String RequestID = MediaManager.get().upload(direcionimagen).callback(new UploadCallback() {
                @Override
                public void onStart(String requestId) {

                }

                @Override
                public void onProgress(String requestId, long bytes, long totalBytes) {

                }

                @Override
                public void onSuccess(String requestId, Map resultData) {
                    //Aqui te da la URL de la imagen
                    String URLRESULTADO = resultData.get("url").toString();
                    foto_string=URLRESULTADO;
                    Toast.makeText(getActivity(), foto_string, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(String requestId, ErrorInfo error) {

                }

                @Override
                public void onReschedule(String requestId, ErrorInfo error) {

                }
            }).dispatch();
        }
        else
        {
            if(resultCode == RESULT_OK && acciones =="GALERIA")
            {
                Uri selectedImage = data.getData();
                Log.d("uri", selectedImage.toString());
                InputStream is;
                try
                {
                    is = getActivity().getContentResolver().openInputStream(selectedImage);
                    BufferedInputStream bis = new BufferedInputStream(is);
                    Bitmap bitmap = BitmapFactory.decodeStream(bis);
                    String resID = MediaManager.get().upload(selectedImage).callback(new UploadCallback() {
                        @Override
                        public void onStart(String requestId) {

                        }

                        @Override
                        public void onProgress(String requestId, long bytes, long totalBytes) {

                        }

                        @Override
                        public void onSuccess(String requestId, Map resultData) {
                            String uri=resultData.get("url").toString();
                            foto_string=uri;
                            Toast.makeText(getActivity(),foto_string,Toast.LENGTH_SHORT).show();
                          //  Log.d("Resultado", resultData.get("url").toString());
                        }

                        @Override
                        public void onError(String requestId, ErrorInfo error) {

                        }

                        @Override
                        public void onReschedule(String requestId, ErrorInfo error) {

                        }
                    }).dispatch();
                    Log.d("Resultado", resID);
                    foto.setImageBitmap(bitmap);

                } catch (FileNotFoundException e) {
                    System.out.print("Ups algo salio mal");
                    e.printStackTrace();
                }

            }
            else
            {
                Toast.makeText(getActivity(), "Salio de la galeria", Toast.LENGTH_SHORT).show();
            }
        }

/*Fragment FRA=this;
            if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {

                Bitmap fotito = (Bitmap) data.getExtras().get("data");
                foto.setImageBitmap(fotito);
            }*/
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
        boolean success = googleMap.setMapStyle(new MapStyleOptions(getResources()
                .getString(R.string.style_json)));
        MapsInitializer.initialize(getContext());
        ngogle=googleMap;
        ngogle.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        Double latitud=20.9673702;
                double longitud=-89.59258569999997;
                        String nombrelugar="sss";
        LatLng LA=new LatLng(latitud,longitud);
     marcadorgay=   ngogle.addMarker(new MarkerOptions().position(LA).title(nombrelugar).snippet("ssss").draggable(true));
        CameraUpdate miubicacion=CameraUpdateFactory.newLatLngZoom(LA,15);
        ngogle.moveCamera(miubicacion);
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
          //  zona.setText(Double.toString(latitud));

           // comentario.setText(Double.toString(lo));
        }

    }

    public  void cargar_webservice(double longi,double lat,String zona,int id_usuario,int id_incidencia,String come ,String fto)
    {

       String urldafa="kkdkd";
        String url="http://proyectoinciencias.gearhostpreview.com/sos_service.asmx/Guardar_puntos?longitud="+longi+"&latitud="+lat+"&zona="+zona+"&id_usuario="+id_usuario+"&id_peligro="+id_incidencia+"&url="+urldafa+"&comentario="+come+"&foto="+fto;
        url.replace(" ","%20");
        jsonObject = new JsonObjectRequest(com.android.volley.Request.Method.GET,url,null,this,this);
        requestQueue.add(jsonObject);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getContext(),"error",Toast.LENGTH_LONG).show();

    }

    @Override
    public void onResponse(JSONObject response) {
        Toast.makeText(getContext(),"guardado",Toast.LENGTH_LONG).show();
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
    public  void Agregar_incidencia(double longi,double lat,String zona,int id_usuario,int id_incidencia,String come )  {
        String urldefait="nota";

        URL url=null;
        String fto="http://res.cloudinary.com/dlyngnwmw/image/upload/v1521600790/IMG_20180320_205047_pnck08.jpg";
        String linea="";
        int respuesta=0;
        StringBuilder resul=null;
        try {
            //"http://proyectoinciencias.gearhostpreview.com/sos_service.asmx/Guardar_puntos?longitud="+longi+"&latitud="+lat+"&zona="+zona+"&id_usuario="+id_usuario+"&id_peligro="+id_incidencia+"&url="+urldefait+"&comentario="+come+"&foto="+fto
            //http://proyectoinciencias.gearhostpreview.com/sos_service.asmx/Guardar_puntos?longitud=-89.64037809008789&latitud=20.966094900664228&zona=hgghg&id_usuario=3033&id_peligro=4&url=ksksk&comentario=jksjskj&foto=http://res.cloudinary.com/dlyngnwmw/image/upload/v1521600790/IMG_20180320_205047_pnck08.jpg
            url=new URL("http://proyectoinciencias.gearhostpreview.com//sos_service.asmx/Guardar_puntos?longitud=-89.64037809008789&latitud=20.966094900664228&zona=hghghghghghg&id_usuario=3033&id_peligro=4&url=ksksk&comentario=hyhyhyhyhyhyhyhyyy&foto=http://res.cloudinary.com/dlyngnwmw/image/upload/v1521600790/IMG_20180320_205047_pnck08.jpg");
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

    public  void enviarpost()  {
        URL url=null;
        String linea="";
        int respuesta=0;
        StringBuilder resul=null;
        try {
            url=new URL("http://proyectoinciencias.gearhostpreview.com//sos_service.asmx/Guardar_puntos?longitud=-89.64037809008789&latitud=20.966094900664228&zona=hghghghghghg&id_usuario=3033&id_peligro=4&url=ksksk&comentario=hyhyhyhyhyhyhyhyyy&foto=http://res.cloudinary.com/dlyngnwmw/image/upload/v1521600790/IMG_20180320_205047_pnck08.jpg");
            HttpURLConnection conec=(HttpURLConnection)url.openConnection();

           // respuesta=conec.getResponseCode();
          //  resul=new StringBuilder();
           /* if(respuesta==HttpURLConnection.HTTP_OK)
            {
                InputStream in= new BufferedInputStream(conec.getInputStream());
                BufferedReader reader= new BufferedReader(new InputStreamReader(in));

                while ((linea=reader.readLine())!=null)
                {
                    resul.append(linea);
                }

            }*/
        }catch (Exception e)
        {}


    }
    public void seleccionar(View view)
    {
        AlertDialog.Builder ale=new AlertDialog.Builder(getActivity());
        ale.setTitle("Cual es su incidencia ");
        ale.setSingleChoiceItems(arreglo, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                datogayner=array[i].toString();
                id_insidencia=Integer.parseInt(datogayner);
                dialogInterface.cancel();
            }
        });
        // CharSequence[] arreglo={}
AlertDialog alert=ale.create();
alert.show();
    }
}
