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
        db.execSQL("INSERT INTO Elementos VALUES (1, 'HELIO', 'He', 2, 'GAS'), (2, 'HIERRO', 'Fe', 26, 'SOLIDO'), (3, 'MERCURIO', 'Hg', 80, 'LIQUIDO')");
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

    // Método para recuperar un elemento
    public Elemento obtenerElemento(String nombre) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query("Elementos", new String[] {"Simbolo", "NumAtomico", "Estado"}, "nombre = ?",
                new String[] { nombre }, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Elemento elemento = new Elemento(nombre, cursor.getString(0), Integer.parseInt(cursor.getString(1)), cursor.getString(2));
        cursor.close();
        return elemento;
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

    // Metodo para saber si existe un elemento en especifico
    public boolean existeElemento (String nombre){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT 1 FROM Elementos WHERE nombre = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{nombre});
        boolean existe = cursor.getCount() > 0;
        return existe;
    }

    // Metodo para borrar un elemento
    public void borrarElemento(String nombre){
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete("Elementos", "Nombre = ?", new String[]{nombre});
        db.close();
    }
}
