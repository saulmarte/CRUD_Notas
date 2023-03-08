package com.cdp.agenda.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.cdp.agenda.entidades.Notas;

import java.util.ArrayList;

public class DbNotas extends DbHelper {

    Context context;

    public DbNotas(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertarNota(String titulo, String descripcion) {

        long id = 0;

        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("titulo", titulo);
            values.put("descripcion", descripcion);

            id = db.insert(TABLE_NOTAS, null, values);
        } catch (Exception ex) {
            ex.toString();
        }

        return id;
    }

    public ArrayList<Notas> mostrarNotas() {

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Notas> listaNotas = new ArrayList<>();
        Notas nota;
        Cursor cursorNotas;

        cursorNotas = db.rawQuery("SELECT * FROM " + TABLE_NOTAS + " ORDER BY nombre ASC", null);

        if (cursorNotas.moveToFirst()) {
            do {
                nota = new Notas();
                nota.setId(cursorNotas.getInt(0));
                nota.setTitulo(cursorNotas.getString(1));
                nota.setDescripcion(cursorNotas.getString(2));
                listaNotas.add(nota);
            } while (cursorNotas.moveToNext());
        }

        cursorNotas.close();

        return listaNotas;
    }

    public Notas verNotas(int id) {

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Notas nota = null;
        Cursor cursorNotas;

        cursorNotas = db.rawQuery("SELECT * FROM " + TABLE_NOTAS + " WHERE id = " + id + " LIMIT 1", null);

        if (cursorNotas.moveToFirst()) {
            nota = new Notas();
            nota.setId(cursorNotas.getInt(0));
            nota.setTitulo(cursorNotas.getString(1));
            nota.setDescripcion(cursorNotas.getString(2));
        }

        cursorNotas.close();

        return nota;
    }

    public boolean editarNotas(int id, String titulo, String nota) {

        boolean correcto = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("UPDATE " + TABLE_NOTAS + " SET titulo = '" + titulo + "', nota = '" + nota + "', WHERE id='" + id + "' ");
            correcto = true;
        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }

    public boolean eliminarNotas(int id) {

        boolean correcto = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("DELETE FROM " + TABLE_NOTAS + " WHERE id = '" + id + "'");
            correcto = true;
        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }
}
