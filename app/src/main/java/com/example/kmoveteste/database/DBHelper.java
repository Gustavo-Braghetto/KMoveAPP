package com.example.kmoveteste.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "kmove.db";
    private static final int VERSAO = 7; // Aumentei para 7 para corrigir corridas

    public DBHelper(Context context) {
        super(context, NOME_BANCO, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE IF NOT EXISTS usuarios (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nome TEXT, " +
                "email TEXT UNIQUE, " +
                "senha TEXT, " +
                "consumo REAL, " +
                "preco_gasolina REAL DEFAULT 0, " +
                "preco_alcool REAL DEFAULT 0, " +
                "preco_diesel REAL DEFAULT 0)");

        db.execSQL("CREATE TABLE IF NOT EXISTS corridas (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "usuario_id INTEGER, " +
                "origem TEXT, " +
                "destino TEXT, " +
                "km REAL, " +
                "valor REAL, " +
                "data TEXT, " +
                "tipo_combustivel TEXT DEFAULT 'Gasolina')"); // ← nova coluna

        db.execSQL("CREATE TABLE IF NOT EXISTS despesas (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "usuario_id INTEGER, " +
                "valor REAL, " +
                "descricao TEXT, " +
                "data TEXT)");

        db.execSQL("CREATE TABLE IF NOT EXISTS metas (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "usuario_id INTEGER, " +
                "titulo TEXT, " +
                "valor REAL, " +
                "valor_atual REAL DEFAULT 0, " +
                "vencimento TEXT DEFAULT '')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (oldVersion < 5) {

            db.execSQL("ALTER TABLE usuarios ADD COLUMN preco_diesel REAL DEFAULT 0");
        }

        if (oldVersion < 6) {

            db.execSQL("ALTER TABLE metas ADD COLUMN valor_atual REAL DEFAULT 0");
            db.execSQL("ALTER TABLE metas ADD COLUMN vencimento TEXT DEFAULT ''");
        }

        if (oldVersion < 7) {

            db.execSQL("ALTER TABLE corridas ADD COLUMN tipo_combustivel TEXT DEFAULT 'Gasolina'");
        }
    }
}
