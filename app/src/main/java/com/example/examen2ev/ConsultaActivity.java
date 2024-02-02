package com.example.examen2ev;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ConsultaActivity extends AppCompatActivity {
    private TextView simbolo, numAtomico, estado;
    private QuimicaSQLiteHelper dbQuimica;
    private EditText nombre;
    private Button btnBuscar, btnCancelar;

    private int numConsultas = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);

        // encontramos los ids

        btnBuscar = findViewById(R.id.btnBuscar);
        btnCancelar = findViewById(R.id.btnCancelar);

        nombre = findViewById(R.id.nombre);

        simbolo = findViewById(R.id.simbolo);
        numAtomico = findViewById(R.id.numAtomico);
        estado = findViewById(R.id.estado);

        dbQuimica = new QuimicaSQLiteHelper(this);

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ConsultaActivity.this, MainActivity.class);
                i.putExtra("consultas", numConsultas);
                finish();
            }
        });

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dbQuimica.existeElemento(nombre.getText().toString())) {
                    Elemento elemento = dbQuimica.obtenerElemento(nombre.getText().toString());

                    simbolo.setText(elemento.getSimbolo());
                    numAtomico.setText(elemento.getNumAtomico());
                    estado.setText(elemento.getEstado());

                    simbolo.setVisibility(View.VISIBLE);
                    numAtomico.setVisibility(View.VISIBLE);
                    estado.setVisibility(View.VISIBLE);

                    numConsultas++;

                    btnBuscar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            nombre.setText("");
                            simbolo.setText("");
                            numAtomico.setText("");
                            estado.setText("");

                            simbolo.setVisibility(View.INVISIBLE);
                            numAtomico.setVisibility(View.INVISIBLE);
                            estado.setVisibility(View.INVISIBLE);
                        }
                    });
                } else {
                    Toast.makeText(ConsultaActivity.this, "El elemento no existe", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}