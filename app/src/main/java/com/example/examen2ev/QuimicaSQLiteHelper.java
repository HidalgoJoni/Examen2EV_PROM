package com.example.examen2ev;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class QuimicaSQLiteHelper extends SQLiteOpenHelper {
    public QuimicaSQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
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
        db.execSQL(
                "CREATE TABLE Elementos (" +
                        "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "Nombre TEXT NOT NULL," +
                        "Simbolo TEXT," +
                        "NumAtomico INTEGER," +
                        "Estado TEXT)");
        db.execSQL("INSERT INTO Elementos VALUES ('HELIO', 'He', '2', 'GAS'), ('HIERRO', 'Fe', '26', 'SOLIDO'), ('MERCURIO', 'Hg', '80', 'LIQUIDO')");
    }
}
