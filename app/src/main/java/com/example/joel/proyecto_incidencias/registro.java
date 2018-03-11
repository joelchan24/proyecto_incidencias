package com.example.joel.proyecto_incidencias;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class registro extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener {
    EditText camponombre,campoapellido,campotelefono,campocorreo,campocontra,campofecha,camposexo;
    Button botonagregar;
    ProgressDialog progreso;
    RequestQueue reques;
    JsonObjectRequest jsonObjectRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        camponombre=(EditText)findViewById(R.id.txtnombreR);
        campoapellido=(EditText)findViewById(R.id.txtapellidoR);
        campocorreo=(EditText)findViewById(R.id.txtcorreoR);
        campocontra=(EditText)findViewById(R.id.txtcontraseñaR);
        botonagregar=(Button)findViewById(R.id.buttonregiR);
        reques= Volley.newRequestQueue(registro.this);
        botonagregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarwebservice();
                Intent i = new Intent(registro.this,Login.class);
                startActivity(i);

            }
        });



    }
    public void cargarwebservice() {
        progreso=new ProgressDialog(registro.this);
        progreso.setMessage("Cargando.....");
        progreso.show();

        String url="http://incidenciaspro.gearhostpreview.com/sos_service.asmx/Guardar_usuario?nombre="+camponombre.getText().toString()+
                "&contraseña="+campocontra.getText().toString()+"&fecha=10/10/10"+"&telefono=9991095546"+"&correo="+campocorreo.getText().toString()+
                "&apellido="+campoapellido.getText().toString()+"&sexo=Sin definir";





        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        reques.add(jsonObjectRequest);
    }

    public void onErrorResponse(VolleyError error) {
        progreso.hide();
        // Toast.makeText(RegistroUsuarios.this,"No se han registrado los datos"+error.toString(),Toast.LENGTH_SHORT).show();
        //Log.i("ERROR",error.toString());
        Toast.makeText(registro.this,"se ha registrado exitosamente",Toast.LENGTH_SHORT).show();

    }
    @Override
    public void onResponse(JSONObject response) {
        Toast.makeText(registro.this,"se ha registrado exitosamente",Toast.LENGTH_SHORT).show();
        camponombre.setText("");
        campoapellido.setText("");
        campotelefono.setText("");
        campofecha.setText("");
        campocontra.setText("");
        campocorreo.setText("");
        camposexo.setText("");

    }

}
