package com.aplicacion.ejercicio24;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aplicacion.ejercicio24.configuraciones.SQLiteConexion;
import com.aplicacion.ejercicio24.configuraciones.Transacciones;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {

    Button btnGuardar;
    Button btnRegresar;
    EditText txtNombreFirma;
    SQLiteConexion conexion;
    Dibujable dibujable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        conexion = new SQLiteConexion(this, Transacciones.NAME_DATABASE,null,1);

        btnGuardar = (Button) findViewById(R.id.btnSalvar);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(dibujable.getVacio()){
                    Toast.makeText(MainActivity.this, "Firma Vacia", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(txtNombreFirma.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, "Ingrese un Nombre", Toast.LENGTH_SHORT).show();
                    return;
                }

                SQLiteDatabase db = conexion.getWritableDatabase();

                try {
                    ContentValues valores = new ContentValues();
                    valores.put(Transacciones.KEY_DESCRIPCION, txtNombreFirma.getText().toString());
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(10480);
                    Bitmap bitmap = Bitmap.createBitmap(dibujable.getWidth(), dibujable.getHeight(), Bitmap.Config.ARGB_8888);

                    Canvas canvas = new Canvas(bitmap);
                    dibujable.draw(canvas);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100 , byteArrayOutputStream);
                    byte[] bytes = byteArrayOutputStream.toByteArray();
                    String img = Base64.encodeToString(bytes,Base64.DEFAULT);

                    valores.put(Transacciones.KEY_IMAGEN, img);

                    Long result = db.insert(Transacciones.NAME_TABLE, Transacciones.KEY_ID, valores);

                    if (result > 0){
                        Toast.makeText(MainActivity.this, "Firma Guardada", Toast.LENGTH_SHORT).show();
                        dibujable.nuevaFirma();
                        txtNombreFirma.setText(null);
                    }else{
                        Toast.makeText(MainActivity.this, "Acción Imposible", Toast.LENGTH_SHORT).show();
                    }
                    db.close();
                }catch (Exception e){
                    Toast.makeText(MainActivity.this, "Acción Imposible", Toast.LENGTH_SHORT).show();
                    db.close();
                }
            }
        });

        txtNombreFirma = (EditText) findViewById(R.id.txtNombreFirma);

        dibujable = (Dibujable) findViewById(R.id.recuadroFirma);

        btnRegresar = (Button) findViewById(R.id.btnVerFirmas);
        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, ActivityVerFirmas.class);
                startActivity(i);
            }
        });
    }
}