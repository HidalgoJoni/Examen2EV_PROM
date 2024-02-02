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

        // Controlamos el set on click del boton de volver, que en su primera pulsacion actuara como si fuese un boton de reiniciar campos y botones, despues de su segunda pulsacion se saldra a la pantalla principal
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nombre.setActivated(true);
                simbolo.setActivated(true);
                numAtomico.setActivated(true);
                estado.setActivated(true);

                nombre.setText("");
                simbolo.setText("");
                numAtomico.setText("");
                estado.setText("");

                btnInsertar.setEnabled(true);
                btnModificar.setEnabled(true);
                btnBorrar.setEnabled(true);

                btnVolver.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
            }
        });

        // Controlamos el set on click del boton de insertar, primero validamos los datos y que no exista el elemento en la tabla y despues procedemos a su insercion
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


        // Controlamos el set on click del boton modificar, primero miramos si existe el elemento y despues le hacemos repetir los cambios que quiere hacer (como una verificacion de que de verdad quiere hacer eso), tambien desactivamos el resto de botones para que solo pueda actualizar o volver y procedemos con la actualizacion despues de verificar los datos
        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dbQuimica.existeElemento(nombre.getText().toString())){
                    nombre.setActivated(false);
                    simbolo.setText("");
                    numAtomico.setText("");
                    estado.setText("");

                    btnInsertar.setEnabled(false);
                    btnBorrar.setEnabled(false);

                    Toast.makeText(AdministracionActivity.this, "Introduce los cambios que necesites hacer en ese elemento", Toast.LENGTH_LONG).show();
                    btnModificar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Elemento nuevoElemento = new Elemento(nombre.getText().toString(), simbolo.getText().toString() ,Integer.parseInt(numAtomico.getText().toString()), estado.getText().toString());
                            if (validarDatos()){
                                dbQuimica.actualizarElemento(nuevoElemento);
                                Toast.makeText(AdministracionActivity.this, "Elemento actualizado", Toast.LENGTH_SHORT).show();

                                nombre.setActivated(true);

                                nombre.setText("");
                                simbolo.setText("");
                                numAtomico.setText("");
                                estado.setText("");

                                btnInsertar.setEnabled(true);
                                btnBorrar.setEnabled(true);
                            }
                        }
                    });
                } else {
                    Toast.makeText(AdministracionActivity.this, "No puedes modificar un elemento que todavia no esta en la tabla", Toast.LENGTH_LONG).show();
                }
            }
        });


        // Controlamos el set on click del boton de borrar, practicamente casi igual al de actualizar, pero en vez de desactivar solo los botones desactivamos todo menos el nombre, tambien para que tenga que insertarlo dos veces como verificacion
        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dbQuimica.existeElemento(nombre.getText().toString())){
                    nombre.setText("");

                    // Desactivamos y dejamos vacios los campos y botones (excepto volver y borrar) para que vuelva a introducir el nombre del elemento que desea borrar
                    numAtomico.setText("");
                    numAtomico.setActivated(false);
                    estado.setText("");
                    estado.setActivated(false);
                    simbolo.setText("");
                    simbolo.setActivated(false);

                    btnInsertar.setEnabled(false);
                    btnModificar.setEnabled(false);

                    Toast.makeText(AdministracionActivity.this, "Vuelve a introducir el nombre que deseas BORRAR de la tabla", Toast.LENGTH_LONG).show();
                    btnBorrar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Elemento elementoABorrar = new Elemento(nombre.getText().toString(), simbolo.getText().toString() ,Integer.parseInt(numAtomico.getText().toString()), estado.getText().toString());
                            dbQuimica.borrarElemento(elementoABorrar);
                            Toast.makeText(AdministracionActivity.this, "Elemento eliminado correctamente", Toast.LENGTH_SHORT).show();

                            simbolo.setActivated(true);
                            numAtomico.setActivated(true);
                            estado.setActivated(true);

                            nombre.setText("");
                            simbolo.setText("");
                            numAtomico.setText("");
                            estado.setText("");

                            btnInsertar.setEnabled(true);
                            btnModificar.setEnabled(true);
                        }
                    });
                } else {
                    Toast.makeText(AdministracionActivity.this, "No puedes borrar un elemento que todavia no esta en la tabla", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    // Validamos todos los datos antes de insertarlos o actualizarlos en la tabla
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