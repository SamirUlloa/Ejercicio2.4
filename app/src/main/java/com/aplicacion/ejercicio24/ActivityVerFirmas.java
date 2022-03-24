package com.aplicacion.ejercicio24;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.aplicacion.ejercicio24.adapters.Adaptador;
import com.aplicacion.ejercicio24.configuraciones.SQLiteConexion;
import com.aplicacion.ejercicio24.configuraciones.Transacciones;
import com.aplicacion.ejercicio24.configuraciones.Signaturess;

import java.util.ArrayList;
import java.util.List;

public class ActivityVerFirmas extends AppCompatActivity {

    SQLiteConexion conexion;
    RecyclerView recyclerView;
    List<Signaturess> signaturessList;
    List<String> AlmacListString;

    Button btnRegresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_firmas);

        conexion = new SQLiteConexion(this, Transacciones.NAME_DATABASE, null, 1);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewVista);
        signaturessList = new ArrayList<>();
        AlmacListString = new ArrayList<>();

        //Regresa al Main Activity
        btnRegresar = (Button) findViewById(R.id.btnRegreFirma);
        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ActivityVerFirmas.this, MainActivity.class);
                startActivity(i);
            }
        });

        SQLiteDatabase db = conexion.getReadableDatabase();
        Signaturess signaturess = null;
        Cursor cursor = db.rawQuery("SELECT * FROM "+ Transacciones.NAME_TABLE,null);

        while (cursor.moveToNext()){
            signaturess = new Signaturess();

            signaturess.id = cursor.getInt(0);
            signaturess.descripcion = cursor.getString(1);
            signaturess.imagen = cursor.getString(2);

            signaturessList.add(signaturess);
            AlmacListString.add(signaturess.descripcion);
        }

        Adaptador adapter = new Adaptador(signaturessList);
        recyclerView.setAdapter(adapter);
    }
}