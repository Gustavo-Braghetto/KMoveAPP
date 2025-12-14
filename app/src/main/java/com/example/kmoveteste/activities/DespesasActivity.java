package com.example.kmoveteste.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kmoveteste.R;
import com.example.kmoveteste.adapters.DespesaAdapter;
import com.example.kmoveteste.database.DespesaDAO;
import com.example.kmoveteste.models.Despesa;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class DespesasActivity extends AppCompatActivity {

    private RecyclerView recyclerDespesas;
    private TextView textSemDespesas;
    private DespesaDAO dao;
    private int usuarioId;
    private MaterialButton btnExcluirDespesa, btnCadastrarDespesa, btnVoltarDespesas;
    private DespesaAdapter adapter;
    private Despesa despesaSelecionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despesas);


        recyclerDespesas = findViewById(R.id.recyclerDespesas);
        textSemDespesas = findViewById(R.id.textSemDespesas);
        btnExcluirDespesa = findViewById(R.id.btnExcluirDespesa);
        btnCadastrarDespesa = findViewById(R.id.btnCadastrarDespesa);
        btnVoltarDespesas = findViewById(R.id.btnVoltarDespesas);


        dao = new DespesaDAO(this);


        usuarioId = getIntent().getIntExtra("usuario_id", -1);


        List<Despesa> lista = dao.listarPorUsuario(usuarioId);


        adapter = new DespesaAdapter(lista);
        recyclerDespesas.setLayoutManager(new LinearLayoutManager(this));
        recyclerDespesas.setAdapter(adapter);


        textSemDespesas.setVisibility(lista.isEmpty() ? View.VISIBLE : View.GONE);


        btnCadastrarDespesa.setOnClickListener(v -> {
            Intent i = new Intent(this, CadastrarDespesaActivity.class);
            i.putExtra("usuario_id", usuarioId);
            startActivity(i);
        });


        btnVoltarDespesas.setOnClickListener(v -> finish());


        btnExcluirDespesa.setOnClickListener(v -> {
            if (despesaSelecionada != null) {
                dao.excluir(despesaSelecionada.getId());
                adapter.remover(despesaSelecionada);
                despesaSelecionada = null;
                btnExcluirDespesa.setVisibility(View.GONE);
                textSemDespesas.setVisibility(adapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
            }
        });


        adapter.setOnItemClickListener(d -> {
            despesaSelecionada = d;
            btnExcluirDespesa.setVisibility(View.VISIBLE);
        });
    }
}
