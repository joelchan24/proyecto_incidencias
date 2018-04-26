package com.example.joel.proyecto_incidencias;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Imagen extends AppCompatActivity {
    private static final int SELECT_FILE=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagen);


    }

    public  void abrirgaleria(View view){
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Seleccione una imagen"),SELECT_FILE);
    }
    protected void onActivityResult(int requesCode,int resultCode,Intent data){
        if (resultCode==RESULT_OK){

            Uri selectedImage=data.getData();
            InputStream is;
            try {
                is=getContentResolver().openInputStream(selectedImage);
                BufferedInputStream bis=new BufferedInputStream(is);
                Bitmap bitmap= BitmapFactory.decodeStream(bis);
               // ImageView iv=(ImageView)findViewById(R.id.b);
               // iv.setImageBitmap(bitmap);


            }catch (FileNotFoundException e){
                System.out.print("Fallo");
            }
        }else {
            Toast.makeText(Imagen.this,"Salio de la galeria",Toast.LENGTH_SHORT).show();
        }
}}
