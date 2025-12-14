package com.example.kmoveteste.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kmoveteste.R;
import com.example.kmoveteste.database.MetaDAO;
import com.example.kmoveteste.models.Meta;

public class CadastrarMetasActivity extends AppCompatActivity {

    private EditText inputMetaDescricao, inputMetaValor;
    private Button btnSalvarMeta;
    private MetaDAO dao;
    private int usuarioId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_metas);

        usuarioId = getIntent().getIntExtra("usuario_id", -1);

        inputMetaDescricao = findViewById(R.id.inputMetaDescricao);
        inputMetaValor = findViewById(R.id.inputMetaValor);
        btnSalvarMeta = findViewById(R.id.btnSalvarMeta);

        dao = new MetaDAO(this);

        btnSalvarMeta.setOnClickListener(v -> {
            String titulo = inputMetaDescricao.getText().toString().trim();
            String sValor = inputMetaValor.getText().toString().trim();

            if (titulo.isEmpty() || sValor.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                return;
            }

            double valor = Double.parseDouble(sValor.replace(',', '.'));

            Meta meta = new Meta();
            meta.setUsuarioId(usuarioId);
            meta.setTitulo(titulo);
            meta.setValor(valor);   // ← CORRIGIDO
            meta.setValorAtual(0);
            meta.setVencimento("");

            long id = dao.inserir(meta);

            if (id > 0) {
                Toast.makeText(this, "Meta salva com sucesso!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Erro ao salvar meta", Toast.LENGTH_SHORT).show();
            }
        });
        Button btnVoltar = findViewById(R.id.btnVoltar);
        btnVoltar.setOnClickListener(v -> finish());

    }
}
