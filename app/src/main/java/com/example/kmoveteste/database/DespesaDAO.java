package com.example.kmoveteste.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.kmoveteste.models.Despesa;

import java.util.ArrayList;
import java.util.List;

public class DespesaDAO extends DBHelper {

    public DespesaDAO(Context context) {
        super(context);
    }

    public long inserir(Despesa d) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("usuario_id", d.getUsuarioId());
        cv.put("valor", d.getValor());
        cv.put("descricao", d.getDescricao());
        cv.put("data", d.getData());
        return db.insert("despesas", null, cv);
    }

    public List<Despesa> listarPorUsuario(int usuarioId) {
        List<Despesa> lista = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM despesas WHERE usuario_id=?", new String[]{String.valueOf(usuarioId)});
        if (cursor.moveToFirst()) {
            do {
                Despesa d = new Despesa();
                d.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                d.setUsuarioId(cursor.getInt(cursor.getColumnIndexOrThrow("usuario_id")));
                d.setValor(cursor.getDouble(cursor.getColumnIndexOrThrow("valor")));
                d.setDescricao(cursor.getString(cursor.getColumnIndexOrThrow("descricao")));
                d.setData(cursor.getString(cursor.getColumnIndexOrThrow("data")));
                lista.add(d);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return lista;
    }

    public int excluir(int id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete("despesas", "id=?", new String[]{String.valueOf(id)});
    }

    public double somarTotalPorUsuario(int usuarioId) {
        double total = 0;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(valor) as total FROM despesas WHERE usuario_id=?", new String[]{String.valueOf(usuarioId)});
        if (cursor.moveToFirst()) {
            total = cursor.getDouble(cursor.getColumnIndexOrThrow("total"));
        }
        cursor.close();
        return total;
    }
}
