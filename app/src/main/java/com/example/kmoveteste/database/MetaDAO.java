package com.example.kmoveteste.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.kmoveteste.models.Meta;

import java.util.ArrayList;
import java.util.List;

public class MetaDAO extends DBHelper {

    public MetaDAO(Context context) {
        super(context);
    }


    public long inserir(Meta meta) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("usuario_id", meta.getUsuarioId());
        cv.put("titulo", meta.getTitulo());
        cv.put("valor", meta.getValor());
        cv.put("valor_atual", meta.getValorAtual());
        cv.put("vencimento", meta.getVencimento());
        return db.insert("metas", null, cv);
    }


    public int atualizar(Meta meta) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("titulo", meta.getTitulo());
        cv.put("valor", meta.getValor());
        cv.put("valor_atual", meta.getValorAtual());
        cv.put("vencimento", meta.getVencimento());
        return db.update("metas", cv, "id=?", new String[]{String.valueOf(meta.getId())});
    }


    public List<Meta> listarMetasPorUsuario(int usuarioId) {
        List<Meta> lista = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM metas WHERE usuario_id=? ORDER BY id DESC",
                new String[]{String.valueOf(usuarioId)}
        );

        if (cursor.moveToFirst()) {
            do {
                Meta meta = new Meta();
                meta.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                meta.setUsuarioId(cursor.getInt(cursor.getColumnIndexOrThrow("usuario_id")));
                meta.setTitulo(cursor.getString(cursor.getColumnIndexOrThrow("titulo")));
                meta.setValor(cursor.getDouble(cursor.getColumnIndexOrThrow("valor")));
                meta.setValorAtual(cursor.getDouble(cursor.getColumnIndexOrThrow("valor_atual")));
                meta.setVencimento(cursor.getString(cursor.getColumnIndexOrThrow("vencimento")));
                lista.add(meta);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return lista;
    }


    public Meta buscarMetaPorUsuario(int usuarioId) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM metas WHERE usuario_id=? ORDER BY id DESC LIMIT 1",
                new String[]{String.valueOf(usuarioId)}
        );

        if (cursor.moveToFirst()) {
            Meta meta = new Meta();
            meta.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            meta.setUsuarioId(cursor.getInt(cursor.getColumnIndexOrThrow("usuario_id")));
            meta.setTitulo(cursor.getString(cursor.getColumnIndexOrThrow("titulo")));
            meta.setValor(cursor.getDouble(cursor.getColumnIndexOrThrow("valor")));
            meta.setValorAtual(cursor.getDouble(cursor.getColumnIndexOrThrow("valor_atual")));
            meta.setVencimento(cursor.getString(cursor.getColumnIndexOrThrow("vencimento")));
            cursor.close();
            return meta;
        }

        cursor.close();
        return null;
    }


    public Meta buscarPorId(int id) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM metas WHERE id=?",
                new String[]{String.valueOf(id)}
        );

        if (cursor.moveToFirst()) {
            Meta meta = new Meta();
            meta.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            meta.setUsuarioId(cursor.getInt(cursor.getColumnIndexOrThrow("usuario_id")));
            meta.setTitulo(cursor.getString(cursor.getColumnIndexOrThrow("titulo")));
            meta.setValor(cursor.getDouble(cursor.getColumnIndexOrThrow("valor")));
            meta.setValorAtual(cursor.getDouble(cursor.getColumnIndexOrThrow("valor_atual")));
            meta.setVencimento(cursor.getString(cursor.getColumnIndexOrThrow("vencimento")));
            cursor.close();
            return meta;
        }

        cursor.close();
        return null;
    }


    public int excluir(int id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete("metas", "id=?", new String[]{String.valueOf(id)});
    }


    public void atualizarProgressoDeTodas(int usuarioId, double valorGanhos) {
        SQLiteDatabase db = getWritableDatabase();

        db.execSQL(
                "UPDATE metas SET valor_atual = valor_atual + ? WHERE usuario_id = ?",
                new Object[]{valorGanhos, usuarioId}
        );

        db.close();
    }

}
