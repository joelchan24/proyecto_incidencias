package com.example.joel.proyecto_incidencias;

import android.Manifest;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
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
public class GenerarIncidenciaFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener,com.android.volley.Response.Listener<JSONObject>, com.android.volley.Response.ErrorListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    int PLACE_PICKER_REQUEST = 1;
    private GoogleApiClient mGoogleApiClient;
    RequestQueue requestQueue;
    JsonObjectRequest jsonObject;
    int id_usuario;
    String dat;
    String zona_string;
    String comentario_straing;
    GoogleMap ngogle;
    MapView mapa;
    Marker marcadorgay;
    double lo;
    double latitud;
    double longitud;
    String foto_string;
    int   id_insidencia;
    ProgressDialog dialog;
    Button guardar;
// static final int REQUEST_IMAGE_CAPTURE = 123;
    String acciones;
TextView zona;
EditText comentario;
Uri direcionimagen;
String photoPath;
File photo;
int iglogbal;
int id_incidenciaaa;
    ImageButton foto;
Bitmap ima;
int iconovalor=R.drawable.baches;
Spinner spinner;
    private final String ruta_fotos = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/todopro/";

    private File file = new File(ruta_fotos);

    CharSequence[] array={"4","5","6","7","8","9","10","11"};
    int [] array_posiciones={0,1,2,3,4,5,6,7};


    String valor ;
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
checkCameraPermission();
//
      View vista_crearincidencias=inflater.inflate(R.layout.fragment_generar_incidencia, container, false);

      // sspiner

        //
        zona=(TextView)vista_crearincidencias.findViewById(R.id.txt_dadada);
        comentario=(EditText) vista_crearincidencias.findViewById(R.id.txt_comentario);
        foto=(ImageButton)vista_crearincidencias.findViewById(R.id.btn_foto);
        guardar= (Button)vista_crearincidencias.findViewById(R.id.btn_guardar);
     //   Bundle datos=getActivity().getIntent().getExtras();
        id_usuario = getArguments().getInt("id");

//spiner
        List<SocialNetwork> items = new ArrayList<SocialNetwork>(9);

        items.add(new SocialNetwork(getString(R.string.baches), R.drawable.baches));
        items.add(new SocialNetwork(getString(R.string.Maltra), R.drawable.maltrato));
        items.add(new SocialNetwork(getString(R.string.lotes), R.drawable.lotes));
        items.add(new SocialNetwork(getString(R.string.van), R.drawable.vandalismo));
        items.add(new SocialNetwork(getString(R.string.Robo), R.drawable.robo));
        items.add(new SocialNetwork(getString(R.string.Quema), R.drawable.quema));
        items.add(new SocialNetwork(getString(R.string.acci), R.drawable.accidentes));
        items.add(new SocialNetwork(getString(R.string.otr), R.drawable.otros));

        spinner = (Spinner)vista_crearincidencias.findViewById(R.id.spiner_generar);
        spinner.setAdapter(new SocialNetworkSpinnerAdapter(getActivity(),items));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView <?> adapterView, View view, int i, long l) {
                valor=array[i].toString();
                id_insidencia=Integer.parseInt(valor);
                id_incidenciaaa=array_posiciones[i];
              //  id_incidenciaaa=Integer.parseInt(array[i].toString());

                    iglogbal = i;

                //    Toast.makeText(getActivity()," "+((SocialNetwork)adapterView.getItemAtPosition(i)).getNombre() +" "+id_insidencia,Toast.LENGTH_LONG).show();
                latitud = 20.9673702;
                longitud = -89.59258569999997;
              if(getArguments().get("id_inciden")!=null)
              {
                  latitud = getArguments().getDouble("lat");
                  longitud = getArguments().getDouble("lot");
              }


                LatLng LA=new LatLng(latitud,longitud);
                ngogle.clear();
                switch (iglogbal)
                {

                    case 0 :
                        marcadorgay = ngogle.addMarker(new MarkerOptions().position(LA).title("Tocar al marcador para seleccionar tu la Ubicación").icon(BitmapDescriptorFactory.fromResource(R.drawable.baches)));
                        marcadorgay.showInfoWindow();

                        break;
                    case 1 :
                        marcadorgay = ngogle.addMarker(new MarkerOptions().position(LA).title("Tocar al marcador para seleccionar tu la Ubicación").icon(BitmapDescriptorFactory.fromResource(R.drawable.maltrato)));
                        marcadorgay.showInfoWindow();

                        break;
                    case 2 :
                        marcadorgay = ngogle.addMarker(new MarkerOptions().position(LA).title("Tocar al marcador para seleccionar tu la Ubicación").icon(BitmapDescriptorFactory.fromResource(R.drawable.lotes)));
                        marcadorgay.showInfoWindow();
                        break;
                    case 3 :
                        marcadorgay = ngogle.addMarker(new MarkerOptions().position(LA).title("Tocar al marcador para seleccionar tu la Ubicación").icon(BitmapDescriptorFactory.fromResource(R.drawable.vandalismo)));
                        marcadorgay.showInfoWindow();
                        break;
                    case 4 :
                        marcadorgay = ngogle.addMarker(new MarkerOptions().position(LA).title("Tocar al marcador para seleccionar tu la Ubicación").icon(BitmapDescriptorFactory.fromResource(R.drawable.robo)));
                        marcadorgay.showInfoWindow();
                        break;
                    case 5 :
                        marcadorgay = ngogle.addMarker(new MarkerOptions().position(LA).title("Tocar al marcador para seleccionar tu la Ubicación").icon(BitmapDescriptorFactory.fromResource(R.drawable.quema)));
                        marcadorgay.showInfoWindow();
                        break;
                    case 6 :
                        marcadorgay = ngogle.addMarker(new MarkerOptions().position(LA).title("Tocar al marcador para seleccionar tu la Ubicación").icon(BitmapDescriptorFactory.fromResource(R.drawable.accidentes)));
                        marcadorgay.showInfoWindow();
                        break;
                    case 7 :
                        marcadorgay = ngogle.addMarker(new MarkerOptions().position(LA).title("Tocar al marcador para seleccionar tu la Ubicación").icon(BitmapDescriptorFactory.fromResource(R.drawable.otros)));
                        marcadorgay.showInfoWindow();
                        break;

                }

                CameraUpdate miubicacion=CameraUpdateFactory.newLatLng(LA);
                // ngogle.moveCamera(miubicacion);
                ngogle.moveCamera(CameraUpdateFactory.newLatLngZoom(LA,12));

            }

            @Override
            public void onNothingSelected(AdapterView <?> adapterView) {

            }
        });
        //



        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final  CharSequence[] arreglo = {"Cámara", "Galería"};
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
        if(getArguments().get("ID")!=null)
        {
            id_insidencia=Integer.parseInt(getArguments().getString("id_inciden"));
            comentario.setText(getArguments().getString("comen"));
            guardar.setText("Editar");
            foto_string=getArguments().getString("fot");
            zona_string=zona.getText().toString();
Picasso.get().load(getArguments().getString("fot")).resize(100,150).centerCrop().into(foto);
            zona.setText(getArguments().getString("zona"));
            switch (id_insidencia)
            {
                case 4:
                    id_incidenciaaa=0;
                    break;
                    case 5:
                        id_incidenciaaa=1;
                break;
                case 6:
                    id_incidenciaaa=2;
                break;
                case 7:
                    id_incidenciaaa=3;
                break;
                case 8:
                    id_incidenciaaa=4;
                break;
                case 9:
                    id_incidenciaaa=5;
                break;
                case 10:
                    id_incidenciaaa=6;
                break;
                case 11:
                    id_incidenciaaa=7;
                    break;

            }

            spinner.setSelection(id_incidenciaaa);

        }

requestQueue= Volley.newRequestQueue(getContext());

         guardar.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {


              //      Toast.makeText(getActivity(),getArguments().geti""+zona.getText().toString()+longitud+" "+latitud+"  "+id_usuario+"  "+comentario.getText()+" "+foto_string,Toast.LENGTH_LONG).show();

                     if (TextUtils.isEmpty(comentario.getText().toString())) {
                         comentario.setError("ingrese un comentario");
                     } else {
                         comentario_straing = comentario.getText().toString();
                         cargar_webservice(longitud, latitud, zona.getText().toString(), id_usuario, id_insidencia, comentario_straing.toString(), foto_string);
                     }

                 /*
                 * if (getArguments().get("id_inciden") != null) {

                    Toast.makeText(getActivity(),""+zona.getText().toString()+longitud+" "+latitud+"  "+id_usuario+"  "+comentario.getText()+" "+foto_string,Toast.LENGTH_LONG).show();
                 } else {
                     if (TextUtils.isEmpty(comentario.getText().toString())) {
                         comentario.setError("ingrese un comentario");
                     } else {
                         comentario_straing = comentario.getText().toString();
                         cargar_webservice(longitud, latitud, zona_string.toString(), id_usuario, id_insidencia, comentario_straing.toString(), foto_string);
                     }
                 }*/
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
        if (requestCode == PLACE_PICKER_REQUEST && resultCode == RESULT_OK && acciones == "mapa") {

            Place place = PlacePicker.getPlace(getActivity(), data);
          //  String adrees = String.format("place  :" + place.getAddress() + " longitud : " + place.getLatLng().longitude + " latitud: " + place.getLatLng().latitude);
            latitud = place.getLatLng().latitude;
            longitud = place.getLatLng().longitude;
            zona.setText(place.getAddress());
            zona_string = place.getAddress().toString();
            //Toast.makeText(getActivity(), adrees, Toast.LENGTH_LONG).show();
            LatLng LA = new LatLng(latitud, longitud);
            ngogle.clear();
            switch (id_insidencia)
            {
                case 4 :
                    marcadorgay = ngogle.addMarker(new MarkerOptions().position(LA).title(zona.getText().toString()).snippet("ssss").icon(BitmapDescriptorFactory.fromResource(R.drawable.baches)));
                    break;
                case 5 :
                    marcadorgay = ngogle.addMarker(new MarkerOptions().position(LA).icon(BitmapDescriptorFactory.fromResource(R.drawable.maltrato)));
                    break;
                case 6 :
                    marcadorgay = ngogle.addMarker(new MarkerOptions().position(LA).icon(BitmapDescriptorFactory.fromResource(R.drawable.lotes)));
                    break;
                case 7 :
                    marcadorgay = ngogle.addMarker(new MarkerOptions().position(LA).icon(BitmapDescriptorFactory.fromResource(R.drawable.vandalismo)));
                    break;
                case 8 :
                    marcadorgay = ngogle.addMarker(new MarkerOptions().position(LA).icon(BitmapDescriptorFactory.fromResource(R.drawable.robo)));
                    break;
                case 9 :
                    marcadorgay = ngogle.addMarker(new MarkerOptions().position(LA).icon(BitmapDescriptorFactory.fromResource(R.drawable.quema)));
                    break;
                case 10 :
                    marcadorgay = ngogle.addMarker(new MarkerOptions().position(LA).icon(BitmapDescriptorFactory.fromResource(R.drawable.accidentes)));
                    break;
                case 11 :
                    marcadorgay = ngogle.addMarker(new MarkerOptions().position(LA).icon(BitmapDescriptorFactory.fromResource(R.drawable.otros)));
                    break;
            }

            CameraUpdate miubicacion = CameraUpdateFactory.newLatLngZoom(LA, 15);
            ngogle.moveCamera(miubicacion);


        } else {


            if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && acciones == "CAMARA") {

                File imgfile = new File(photoPath);

                Bitmap bitmap = BitmapFactory.decodeFile(imgfile.getAbsolutePath());
Bitmap.createScaledBitmap(bitmap,150,150,true);
                direcionimagen = Uri.fromFile(photo);
              //  Picasso.get().load(direcionimagen).into(foto);
                dialog = new ProgressDialog(getContext());
                dialog.setMessage("Guardando Imagen");
                dialog.show();
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
                        foto_string = URLRESULTADO;
                      //  Toast.makeText(getActivity(), foto_string, Toast.LENGTH_SHORT).show();
                        Picasso.get().load(foto_string).resize(120,120).centerCrop().into(foto);
                        dialog.hide();
                    }

                    @Override
                    public void onError(String requestId, ErrorInfo error) {

                    }

                    @Override
                    public void onReschedule(String requestId, ErrorInfo error) {

                    }
                }).dispatch();
            } else {
                if (resultCode == RESULT_OK && acciones == "GALERIA") {
                    Uri selectedImage = data.getData();
                    Log.d("uri", selectedImage.toString());
                    InputStream is;
                    try {
                        is = getActivity().getContentResolver().openInputStream(selectedImage);
                        BufferedInputStream bis = new BufferedInputStream(is);

                        Bitmap bitmap = BitmapFactory.decodeStream(bis);
Bitmap.createScaledBitmap(bitmap,150,150,true);
                      //  foto.setImageBitmap(bitmap);
                        dialog = new ProgressDialog(getContext());
                        dialog.setMessage("Guardando Imagen");
                        dialog.show();
                        String resID = MediaManager.get().upload(selectedImage).callback(new UploadCallback() {
                            @Override
                            public void onStart(String requestId) {

                            }

                            @Override
                            public void onProgress(String requestId, long bytes, long totalBytes) {

                            }

                            @Override
                            public void onSuccess(String requestId, Map resultData) {
                                String uri = resultData.get("url").toString();
                                foto_string = uri;
                                Picasso.get().load(foto_string).resize(120,120).centerCrop().into(foto);
                                dialog.hide();
                              //  Toast.makeText(getActivity(), foto_string, Toast.LENGTH_SHORT).show();
                                //  Log.d("Resultado", resultData.get("url").toString());
                            }

                            @Override
                            public void onError(String requestId, ErrorInfo error) {
                                dialog.hide();

                                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onReschedule(String requestId, ErrorInfo error) {

                            }
                        }).dispatch();
                        Log.d("Resultado", resID);


                    } catch (FileNotFoundException e) {
                        System.out.print("Ups algo salio mal");
                        e.printStackTrace();
                    }

                }
                //   Toast.makeText(getActivity(), "Salio de la galeria", Toast.LENGTH_SHORT).show();
            }
        }

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


    // marcadorgay=   ngogle.addMarker(new MarkerOptions().position(LA).title("Click al marcador para seleccionar tu la Ubicación").draggable(true));


        googleMap.setOnMarkerClickListener(this);
    }




    public  void cargar_webservice(double longi,double lat,String zona,int id_usuario,int id_incidencia,String come ,String fto)
    {
        if(getArguments().get("id_inciden")!=null)
        {
     int       id_del_incidente=getArguments().getInt("ID");
            String url;
            String urldafa="kkdkd";
            if(fto==null) {
                fto="https://res.cloudinary.com/dyhowxkye/image/upload/v1521322391/image_placeholder.jpg";
                url = "http://proyectoinciencias.gearhostpreview.com/sos_service.asmx/editar_puntos?longitud=" + longi + "&latitud=" + lat + "&zona=" + zona + "&id_peligro=" + id_incidencia + "&comentario=" + come + "&foto=" + fto + "&id_inci="+id_del_incidente;
            }
            else
            {
                url = "http://proyectoinciencias.gearhostpreview.com/sos_service.asmx/editar_puntos?longitud=" + longi + "&latitud=" + lat + "&zona=" + zona + "&id_peligro=" + id_incidencia + "&comentario=" + come + "&foto=" + fto + "&id_inci="+id_del_incidente;

            }

            url=url.replace(" ","%20");
            jsonObject = new JsonObjectRequest(com.android.volley.Request.Method.GET,url,null,this,this);
            requestQueue.add(jsonObject);
           // Toast.makeText(getActivity(),"C",Toast.LENGTH_SHORT).show();
        }
        else{
            String url;
            String urldafa="kkdkd";
            if(fto==null)
            {
                fto="https://res.cloudinary.com/dyhowxkye/image/upload/v1521322391/image_placeholder.jpg";
                url="http://proyectoinciencias.gearhostpreview.com/sos_service.asmx/Guardar_puntos?longitud="+longi+"&latitud="+lat+"&zona="+zona+"&id_usuario="+id_usuario+"&id_peligro="+id_incidencia+"&url="+urldafa+"&comentario="+come+"&foto="+fto;

            }else
            {
                url="http://proyectoinciencias.gearhostpreview.com/sos_service.asmx/Guardar_puntos?longitud="+longi+"&latitud="+lat+"&zona="+zona+"&id_usuario="+id_usuario+"&id_peligro="+id_incidencia+"&url="+urldafa+"&comentario="+come+"&foto="+fto;

            }
            url=url.replace(" ","%20");
            jsonObject = new JsonObjectRequest(com.android.volley.Request.Method.GET,url,null,this,this);
            requestQueue.add(jsonObject);
        }

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getContext(),"Guardado con Exito su punto esta en espera de ser aprobado",Toast.LENGTH_LONG).show();
        comentario.setText("");
        zona.setText("");
        latitud = 20.9673702;
        longitud = -89.59258569999997;
        foto.setImageResource(R.drawable.camera);
        //spinner.setSelection();


    }

    @Override
    public void onResponse(JSONObject response) {
        Toast.makeText(getContext(),"guardado",Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if(marker.equals(marcadorgay))
        {
            PlacePicker.IntentBuilder builder=new PlacePicker.IntentBuilder();
            Intent intent;
            try {
                intent=builder.build(getActivity());
                acciones="mapa";
                startActivityForResult(intent,PLACE_PICKER_REQUEST);

            } catch (GooglePlayServicesRepairableException e) {
                e.printStackTrace();
            } catch (GooglePlayServicesNotAvailableException e) {
                e.printStackTrace();
            }
        }
        return false;
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
    private void checkCameraPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(
                getActivity(), Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            Log.i("Mensaje", "No se tiene permiso para la camara!.");
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 225);
        } else {
            Log.i("Mensaje", "Tienes permiso para usar la camara.");
        }
    }





}
