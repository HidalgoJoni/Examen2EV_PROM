package com.example.examen2ev;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdministracionActivity extends AppCompatActivity {
    private Button btnInsertar, btnModificar, btnBorrar, btnVolver;
    private QuimicaSQLiteHelper dbQuimica;
    private EditText nombre, simbolo, numAtomico, estado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administracion);

        // encontramos todos los ids

        btnInsertar = findViewById(R.id.btnInsertar);
        btnModificar = findViewById(R.id.btnModificar);
        btnBorrar = findViewById(R.id.btnBorrar);
        btnVolver = findViewById(R.id.btnVolver);

        nombre = findViewById(R.id.nombre);
        simbolo = findViewById(R.id.simbolo);
        numAtomico = findViewById(R.id.numAtomico);
        estado = findViewById(R.id.estado);

        dbQuimica = new QuimicaSQLiteHelper(this);

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnInsertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validarDatos()){ // Validamos los datos
                    if (!dbQuimica.existeElemento(nombre.getText().toString().trim())){
                        // Si no existe un elemento con ese mismo nombre procedemos a insertarlo
                        Elemento nuevoElemento = new Elemento(nombre.getText().toString(), simbolo.getText().toString() ,Integer.parseInt(numAtomico.getText().toString()), estado.getText().toString());
                        Toast.makeText(AdministracionActivity.this, "Elemento insertado", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AdministracionActivity.this, "El elemento ya existe", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    boolean validarDatos(){
        String errores="";

        if (nombre.getText().toString().isEmpty()){
            errores+="El nombre no puede estar vacio \n";
        }

        if (simbolo.getText().toString().length() > 2){
            errores+="El simbolo no puede contener mas de dos caracteres\n";
        }

        try {
            int numero = Integer.parseInt(numAtomico.getText().toString());
            if (numero <= 0) {
                errores+="El numero atomico debe ser positivo\n";
            }
        } catch (NumberFormatException e) {
            errores += "El numero atomico debe ser un número válido.\n";
        }

        if (!estado.getText().toString().toLowerCase().equals("solido") && !estado.getText().toString().toLowerCase().equals("gas") && !estado.getText().toString().toLowerCase().equals("liquido")){
            errores += "El elemento solo puede ser solido, liquido o gas";
        }

        if (errores.isEmpty()){
            return true;
        } else {
            new AlertDialog.Builder(AdministracionActivity.this)
                    .setTitle("ERRORES")
                    .setMessage(errores)
                    .setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }

                    })
                    .show();
            return false;
        }
    }
}