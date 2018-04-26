package com.example.joel.proyecto_incidencias;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.example.joel.proyecto_incidencias.clases.spiner_ejem;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

public class registro extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener {
    EditText camponombre,campoapellido,campotelefono,campocorreo,campocontra,campofecha,camposexo;
    Button botonagregar;
    ProgressDialog progreso;
    RequestQueue reques;
    String acciones;
    JsonObjectRequest jsonObjectRequest;
    Uri direcionimagen;
    String foto_string;
    String photoPath;
    File photo;
    ProgressDialog dialog;
    ImageButton foto;
    String[] sexo1={  "Hombre","Mujer"
};
    Spinner sexo_spiner ;
    //private static final int SELECT_FILE=1;
    private final String ruta_fotos = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/todopro/";

    private File file = new File(ruta_fotos);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        sexo_spiner=(Spinner)findViewById(R.id.sexo);
        ArrayList<spiner_ejem> Lis= new ArrayList <>();
        Lis.add(new spiner_ejem("mujer"));
        Lis.add(new spiner_ejem("Hombre"));

       // ArrayAdapter<spiner_ejem> adapter=new ArrayAdapter <spiner_ejem>(this,R.layout.support_simple_spinner_dropdown_item,Lis);
       // adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        sexo_spiner.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,sexo1));
//sexo_spiner.setAdapter(adapter);
        sexo_spiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView <?> parent, View view, int position, long id) {
                ( (TextView)parent.getChildAt(0)).setTextColor(Color.BLACK);
            }

            @Override
            public void onNothingSelected(AdapterView <?> parent) {

            }
        });
         foto=(ImageButton)findViewById(R.id.btngaleria);
        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCameraPermission();
                abrir();
            }
        });
ImageButton imageButton=(ImageButton)findViewById(R.id.ima);
imageButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        mostrar_ventana().show();
    }
});
        camponombre=(EditText)findViewById(R.id.txtnombreR);
        campoapellido=(EditText)findViewById(R.id.txtapellidoR);
        campocorreo=(EditText)findViewById(R.id.txtcorreoR);
        campocontra=(EditText)findViewById(R.id.txtcontraseñaR);
        botonagregar=(Button)findViewById(R.id.buttonregiR);
        campofecha=(EditText)findViewById(R.id.txtfechaR);
        reques= Volley.newRequestQueue(registro.this);

        botonagregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(camponombre.getText().toString().trim())||TextUtils.isEmpty(campoapellido.getText().toString().trim())||  TextUtils.isEmpty(campocorreo.getText().toString().trim())||TextUtils.isEmpty(campocontra.getText().toString().trim())||TextUtils.isEmpty(campofecha.getText().toString().trim()))
                {
                    mostrar_ventana().show();
                    camponombre.setError("Agregar tu nombre");
                    campoapellido.setError("Agregar tus Apellidos");
                    campocorreo.setError("Agregar tu Correo");
                    campocontra.setError("Agrega tu Contraseña");
                    campofecha.setError("Agrega tu fecha de nacimineto");
                } else
                {
                    cargarwebservice();
                    Intent i = new Intent(registro.this, Login.class);
                    startActivity(i);



                    
                }



            }
        });


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

    protected void onActivityResult(int requesCode,int resultCode,Intent data){

        if (requesCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && acciones == "CAMARA") {

            File imgfile = new File(photoPath);
            Bitmap bitmap = BitmapFactory.decodeFile(imgfile.getAbsolutePath());

            direcionimagen = Uri.fromFile(photo);
            Picasso.get().load(direcionimagen).into(foto);
            dialog = new ProgressDialog(this);
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
                    is = this.getContentResolver().openInputStream(selectedImage);
                    BufferedInputStream bis = new BufferedInputStream(is);
                    Bitmap bitmap = BitmapFactory.decodeStream(bis);

                    foto.setImageBitmap(bitmap);
                    dialog = new ProgressDialog(this);
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
                            dialog.hide();
                            Toast.makeText(getApplicationContext(), foto_string, Toast.LENGTH_SHORT).show();
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


                } catch (FileNotFoundException e) {
                    System.out.print("Ups algo salio mal");
                    e.printStackTrace();
                }

            }
            //   Toast.makeText(getActivity(), "Salio de la galeria", Toast.LENGTH_SHORT).show();
        }



    }
    public void cargarwebservice() {
        progreso=new ProgressDialog(registro.this);
        progreso.setMessage("Cargando.....");
        progreso.show();
        String url;
if (foto_string==null)
{
    foto_string="https://res.cloudinary.com/dyhowxkye/image/upload/v1523834686/%C3%ADndice.jpg";
     url="http://proyectoinciencias.gearhostpreview.com/sos_service.asmx/Guardar_usuario?Nombre="+camponombre.getText().toString()+"&Contraseña="+campocontra.getText().toString()+"&Fecha=11/11/1998&Telefono=98898987&correo="+campocorreo.getText().toString()+"&apellido="+campoapellido.getText().toString()+"&sexo=masculino&foto="+foto_string;
}
else
{
     url="http://proyectoinciencias.gearhostpreview.com/sos_service.asmx/Guardar_usuario?Nombre="+camponombre.getText().toString()+"&Contraseña="+campocontra.getText().toString()+"&Fecha=11/11/1998&Telefono=98898987&correo="+campocorreo.getText().toString()+"&apellido="+campoapellido.getText().toString()+"&sexo=masculino&foto="+foto_string;
}




        url=url.replace(" ","%20");

        jsonObjectRequest=new JsonObjectRequest(com.android.volley.Request.Method.GET,url,null,this,this);
        reques.add(jsonObjectRequest);
    }

    public void onErrorResponse(VolleyError error) {
        progreso.hide();
        // Toast.makeText(RegistroUsuarios.this,"No se han registrado los datos"+error.toString(),Toast.LENGTH_SHORT).show();
        //Log.i("ERROR",error.toString());
        Toast.makeText(registro.this,"se ha registrado exitosamente1",Toast.LENGTH_SHORT).show();

    }
    @Override
    public void onResponse(JSONObject response) {
        Toast.makeText(registro.this,"se ha registrado exitosamente",Toast.LENGTH_SHORT).show();
        camponombre.setText("");
        campoapellido.setText("");
       // campotelefono.setText("");
        campofecha.setText("");
        campocontra.setText("");
        campocorreo.setText("");
        //camposexo.setText("");

    }
    public void  abrir()
    {
        final  CharSequence[] arreglo = {"Cámara", "Galería"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    public android.app.AlertDialog mostrar_ventana()
    {
        final android.app.AlertDialog.Builder   builder= new android.app.AlertDialog.Builder(this);
        LayoutInflater inflater=this.getLayoutInflater();
        View  v=inflater.inflate(R.layout.datatime,null);
        DatePicker calendario=(DatePicker) v.findViewById(R.id.datatimepicker);
        Calendar calendar=  Calendar.getInstance();
        calendario.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
              //  Toast.makeText(getApplicationContext(),datePicker.getDayOfMonth()+"-"+datePicker.getMonth()+"-"+datePicker.getYear(),Toast.LENGTH_LONG).show();
                campofecha.setText(datePicker.getDayOfMonth()+"-"+datePicker.getMonth()+"-"+datePicker.getYear());

            }
        });
        builder.setView(v)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });





        return  builder.create();
    }
    private void checkCameraPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(
                this, Manifest.permission.CAMERA);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            Log.i("Mensaje", "No se tiene permiso para la camara!.");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 225);
        } else {
            Log.i("Mensaje", "Tienes permiso para usar la camara.");
        }
    }
}
