package com.example.examen2ev;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button btnQuimico, btnConsultar, btnSalir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // encontramos los ids de los botones

        btnConsultar = findViewById(R.id.btnConsultar);
        btnQuimico = findViewById(R.id.btnQuimico);
        btnSalir = findViewById(R.id.btnSalir);

        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, ConsultaActivity.class);
                startActivity(i);
            }
        });

        btnQuimico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("INFORMACIÓN")
                        .setMessage("Esperamos verle pronto de nuevo.")
                        .setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }

                        })
                        .show();
            }
        });
    }
}