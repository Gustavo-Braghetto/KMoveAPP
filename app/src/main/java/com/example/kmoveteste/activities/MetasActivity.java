package com.example.kmoveteste.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kmoveteste.R;
import com.example.kmoveteste.adapters.MetasAdapter;
import com.example.kmoveteste.database.MetaDAO;
import com.example.kmoveteste.models.Meta;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class MetasActivity extends AppCompatActivity {

    private int usuarioId;

    private TextView txtMetaAtual, txtProgresso;
    private RecyclerView recyclerMetas;
    private MaterialButton btnCadastrarMeta, btnExcluirMeta, btnVoltarPrincipal;

    private Meta metaSelecionada = null;
    private MetasAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metas);

        usuarioId = getIntent().getIntExtra("usuario_id", -1);

        txtMetaAtual = findViewById(R.id.txtMetaAtual);
        txtProgresso = findViewById(R.id.txtProgresso);

        recyclerMetas = findViewById(R.id.recyclerMetas);
        recyclerMetas.setLayoutManager(new LinearLayoutManager(this));

        btnCadastrarMeta = findViewById(R.id.btnCadastrarMeta);
        btnExcluirMeta = findViewById(R.id.btnExcluirMeta);
        btnVoltarPrincipal = findViewById(R.id.btnVoltarPrincipal);

        carregarMetaAtual();
        carregarListaMetas();

        btnCadastrarMeta.setOnClickListener(v -> {
            Intent it = new Intent(this, CadastrarMetasActivity.class);
            it.putExtra("usuario_id", usuarioId);
            startActivity(it);
        });

        btnExcluirMeta.setOnClickListener(v -> {
            if (metaSelecionada != null) {
                MetaDAO dao = new MetaDAO(this);
                dao.excluir(metaSelecionada.getId());
                metaSelecionada = null;
                btnExcluirMeta.setVisibility(View.GONE);
                carregarListaMetas();
                carregarMetaAtual();
            }
        });

        btnVoltarPrincipal.setOnClickListener(v -> {
            finish();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarListaMetas();
        carregarMetaAtual();
    }

    private void carregarListaMetas() {
        MetaDAO dao = new MetaDAO(this);
        List<Meta> lista = dao.listarMetasPorUsuario(usuarioId);

        adapter = new MetasAdapter(lista, meta -> {
            metaSelecionada = meta;
            btnExcluirMeta.setVisibility(View.VISIBLE);
        });

        recyclerMetas.setAdapter(adapter);
    }

    private void carregarMetaAtual() {
        MetaDAO dao = new MetaDAO(this);
        List<Meta> metas = dao.listarMetasPorUsuario(usuarioId);

        Meta meta = metas.size() > 0 ? metas.get(0) : null;

        if (meta != null) {
            txtMetaAtual.setText(String.format("Meta atual: R$ %.2f", meta.getValor()));
            txtProgresso.setText(String.format("Progresso: %.2f%%", meta.getProgresso()));
        } else {
            txtMetaAtual.setText("Nenhuma meta cadastrada");
            txtProgresso.setText("");
        }
    }

}
