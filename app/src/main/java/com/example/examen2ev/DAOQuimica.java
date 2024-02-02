package com.example.examen2ev;

import android.database.sqlite.SQLiteDatabase;

public class DAOQuimica {
    public boolean nuevoLibro(QuimicaSQLiteHelper helper, String nombre, String simbolo, int num, String estado) {
        SQLiteDatabase db = helper.getWritableDatabase();
        if (db != null){
            db.execSQL("INSERT INTO Elementos " +
                    " VALUES ('"+nombre+"','"+simbolo+"','"+num+"','"+estado+"') ");
        }
        db.close();
        return true;
    }
}
