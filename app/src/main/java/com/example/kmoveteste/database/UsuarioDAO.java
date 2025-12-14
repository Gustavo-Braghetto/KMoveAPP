package com.example.kmoveteste.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.kmoveteste.models.Usuario;

public class UsuarioDAO {

    private SQLiteDatabase db;

    public UsuarioDAO(Context context) {
        DBHelper helper = new DBHelper(context);
        db = helper.getWritableDatabase();
    }

    public long inserir(Usuario usuario) {
        ContentValues cv = new ContentValues();
        cv.put("nome", usuario.getNome());
        cv.put("email", usuario.getEmail());
        cv.put("senha", usuario.getSenha());
        cv.put("consumo", usuario.getConsumo());
        cv.put("preco_gasolina", usuario.getPrecoGasolina());
        cv.put("preco_alcool", usuario.getPrecoAlcool());
        cv.put("preco_diesel", usuario.getPrecoDiesel()); // NOVO
        return db.insert("usuarios", null, cv);
    }

    public Usuario autenticar(String email, String senha) {
        Cursor c = db.rawQuery(
                "SELECT * FROM usuarios WHERE email=? AND senha=?",
                new String[]{email, senha});

        if (c.moveToFirst()) {
            Usuario u = new Usuario();
            u.setId(c.getInt(0));
            u.setNome(c.getString(1));
            u.setEmail(c.getString(2));
            u.setSenha(c.getString(3));
            u.setConsumo(c.getDouble(4));
            u.setPrecoGasolina(c.getDouble(5));
            u.setPrecoAlcool(c.getDouble(6));
            u.setPrecoDiesel(c.getDouble(7)); // NOVO
            return u;
        }
        return null;
    }

    public Usuario buscarPorId(int id) {
        Cursor c = db.rawQuery("SELECT * FROM usuarios WHERE id=?",
                new String[]{String.valueOf(id)});

        if (c.moveToFirst()) {
            Usuario u = new Usuario();
            u.setId(c.getInt(0));
            u.setNome(c.getString(1));
            u.setEmail(c.getString(2));
            u.setSenha(c.getString(3));
            u.setConsumo(c.getDouble(4));
            u.setPrecoGasolina(c.getDouble(5));
            u.setPrecoAlcool(c.getDouble(6));
            u.setPrecoDiesel(c.getDouble(7)); // NOVO
            return u;
        }
        return null;
    }

    public int atualizarPerfil(Usuario usuario) {
        ContentValues cv = new ContentValues();
        cv.put("nome", usuario.getNome());
        cv.put("email", usuario.getEmail());
        cv.put("senha", usuario.getSenha());
        cv.put("consumo", usuario.getConsumo());
        cv.put("preco_gasolina", usuario.getPrecoGasolina());
        cv.put("preco_alcool", usuario.getPrecoAlcool());
        cv.put("preco_diesel", usuario.getPrecoDiesel()); // NOVO

        return db.update("usuarios", cv, "id=?",
                new String[]{String.valueOf(usuario.getId())});
    }
}
