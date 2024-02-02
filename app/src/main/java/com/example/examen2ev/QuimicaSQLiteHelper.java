package com.example.examen2ev;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class QuimicaSQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "agenda.db";
    private static final int DATABASE_VERSION = 1;

    public QuimicaSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Creacion BBDD de los elementos de la tabla periodica
        db.execSQL(
                "CREATE TABLE Elementos (" +
                        "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "Nombre TEXT NOT NULL," +
                        "Simbolo TEXT," +
                        "NumAtomico INTEGER," +
                        "Estado TEXT)");
        db.execSQL("INSERT INTO Elementos VALUES ('HELIO', 'He', '2', 'GAS'), ('HIERRO', 'Fe', '26', 'SOLIDO'), ('MERCURIO', 'Hg', '80', 'LIQUIDO')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS Elementos");
        onCreate(db);
    }

    // Metodo para añadir un elemento
    public void aniadirElemento(Elemento elemento) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Nombre", elemento.getNombre());
        values.put("Simbolo", elemento.getSimbolo());
        values.put("NumAtomico", elemento.getNumAtomico());
        values.put("Estado", elemento.getEstado());

        // No necesitamos poner el ID, SQLite lo autoincrementará automáticamente
        db.insert("Elementos", null, values);
        db.close();
    }

    // Metodo para actualizar un elemento
    public int actualizarElemento(Elemento elemento){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("Nombre", elemento.getNombre());
        values.put("Simbolo", elemento.getSimbolo());
        values.put("NumAtomico", elemento.getNumAtomico());
        values.put("Estado", elemento.getEstado());

        return db.update("Elementos", values, "ID = ?", new String[]{String.valueOf(elemento.getIdentificacion())});
    }

    public void borrarElemento(Elemento elemento){
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete("Elementos", "ID = ?", new String[]{String.valueOf(elemento.getIdentificacion())});
        db.close();
    }

    public List<Elemento> obtenerElementos(String nombre){
        List<Elemento> listaElementos = new ArrayList<Elemento>();

        String selectQuery = "SELECT * FROM Elementos WHERE Nombre = ?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{nombre});

        if (cursor.moveToFirst()) {
            do {
                Elemento elemento = new Elemento();
                elemento.setIdentificacion(Integer.parseInt(cursor.getString(0)));
                elemento.setNombre(cursor.getString(1));
                elemento.setSimbolo(cursor.getString(2));
                elemento.setNumAtomico(Integer.parseInt(cursor.getString(3)));
                elemento.setEstado(cursor.getString(4));

                // Añadiendo elemento a la lista
                listaElementos.add(elemento);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return listaElementos;
    }
}
