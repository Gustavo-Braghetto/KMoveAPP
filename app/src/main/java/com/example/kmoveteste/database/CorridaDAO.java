package com.example.kmoveteste.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.kmoveteste.models.Corrida;

import java.util.ArrayList;
import java.util.List;

public class CorridaDAO extends DBHelper {

    public CorridaDAO(Context context) {
        super(context);
    }

    public long inserir(Corrida c) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("usuario_id", c.getUsuarioId());
        cv.put("origem", c.getOrigem());
        cv.put("destino", c.getDestino());
        cv.put("valor", c.getValor());
        cv.put("km", c.getKm());
        cv.put("data", c.getData());
        cv.put("tipo_combustivel", c.getTipoCombustivel()); // NOVO
        long id = db.insert("corridas", null, cv);
        return id;
    }

    public List<Corrida> listarTodos() {
        List<Corrida> lista = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM corridas ORDER BY id DESC", null);

        while (cursor.moveToNext()) {
            Corrida c = new Corrida();
            c.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            c.setUsuarioId(cursor.getInt(cursor.getColumnIndexOrThrow("usuario_id")));
            c.setOrigem(cursor.getString(cursor.getColumnIndexOrThrow("origem")));
            c.setDestino(cursor.getString(cursor.getColumnIndexOrThrow("destino")));
            c.setValor(cursor.getDouble(cursor.getColumnIndexOrThrow("valor")));
            c.setKm(cursor.getDouble(cursor.getColumnIndexOrThrow("km")));
            c.setData(cursor.getString(cursor.getColumnIndexOrThrow("data")));
            c.setTipoCombustivel(cursor.getString(cursor.getColumnIndexOrThrow("tipo_combustivel"))); // NOVO
            lista.add(c);
        }
        cursor.close();
        return lista;
    }

    public List<Corrida> listarPorUsuario(int usuarioId) {
        List<Corrida> lista = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM corridas WHERE usuario_id = ? ORDER BY id DESC",
                new String[]{String.valueOf(usuarioId)});

        while (cursor.moveToNext()) {
            Corrida c = new Corrida();
            c.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            c.setUsuarioId(cursor.getInt(cursor.getColumnIndexOrThrow("usuario_id")));
            c.setOrigem(cursor.getString(cursor.getColumnIndexOrThrow("origem")));
            c.setDestino(cursor.getString(cursor.getColumnIndexOrThrow("destino")));
            c.setValor(cursor.getDouble(cursor.getColumnIndexOrThrow("valor")));
            c.setKm(cursor.getDouble(cursor.getColumnIndexOrThrow("km")));
            c.setData(cursor.getString(cursor.getColumnIndexOrThrow("data")));
            c.setTipoCombustivel(cursor.getString(cursor.getColumnIndexOrThrow("tipo_combustivel"))); // NOVO
            lista.add(c);
        }
        cursor.close();
        return lista;
    }

    public boolean excluir(int id) {
        SQLiteDatabase db = getWritableDatabase();
        int linhas = db.delete("corridas", "id = ?", new String[]{String.valueOf(id)});
        return linhas > 0;
    }

    public double somarTotalPorUsuario(int usuarioId) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(valor) AS total FROM corridas WHERE usuario_id = ?",
                new String[]{String.valueOf(usuarioId)});
        double total = 0;
        if (cursor.moveToFirst()) {
            try { total = cursor.getDouble(cursor.getColumnIndexOrThrow("total")); }
            catch (Exception e) { total = 0; }
        }
        cursor.close();
        return total;
    }

    public double somarKmPorUsuario(int usuarioId) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(km) AS total_km FROM corridas WHERE usuario_id = ?",
                new String[]{String.valueOf(usuarioId)});
        double totalKm = 0;
        if (cursor.moveToFirst()) {
            try { totalKm = cursor.getDouble(cursor.getColumnIndexOrThrow("total_km")); }
            catch (Exception e) { totalKm = 0; }
        }
        cursor.close();
        return totalKm;
    }
}
