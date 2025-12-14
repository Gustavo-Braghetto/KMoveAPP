package com.example.kmoveteste.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kmoveteste.R;
import com.example.kmoveteste.adapters.CorridaAdapter;
import com.example.kmoveteste.database.CorridaDAO;
import com.example.kmoveteste.database.UsuarioDAO;
import com.example.kmoveteste.models.Corrida;
import com.example.kmoveteste.models.Usuario;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;

public class CorridasActivity extends AppCompatActivity {

    private RecyclerView recyclerCorridas;
    private TextView textSemCorridas;
    private CorridaDAO dao;
    private CorridaAdapter adapter;
    private Corrida corridaSelecionada = null;

    private MaterialButton btnVoltarCorridas, btnExcluirCorrida, btnVerInfoCorrida;
    private Usuario usuarioLogado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corridas);

        recyclerCorridas = findViewById(R.id.recyclerCorridas);
        textSemCorridas = findViewById(R.id.textSemCorridas);
        btnVoltarCorridas = findViewById(R.id.btnVoltarCorridas);
        btnExcluirCorrida = findViewById(R.id.btnExcluirCorrida);
        btnVerInfoCorrida = findViewById(R.id.btnVerInfoCorrida);

        dao = new CorridaDAO(this);
        UsuarioDAO usuarioDAO = new UsuarioDAO(this);

        int usuarioId = getIntent().getIntExtra("usuario_id", -1);
        usuarioLogado = usuarioDAO.buscarPorId(usuarioId);

        List<Corrida> lista = dao.listarPorUsuario(usuarioId);
        textSemCorridas.setVisibility(lista.isEmpty() ? View.VISIBLE : View.GONE);

        adapter = new CorridaAdapter(lista);
        recyclerCorridas.setLayoutManager(new LinearLayoutManager(this));
        recyclerCorridas.setAdapter(adapter);

        adapter.setOnItemClickListener(corrida -> {
            corridaSelecionada = corrida;
            btnExcluirCorrida.setVisibility(View.VISIBLE);
            btnVerInfoCorrida.setVisibility(View.VISIBLE);
        });

        btnExcluirCorrida.setOnClickListener(v -> {
            if (corridaSelecionada != null) {
                dao.excluir(corridaSelecionada.getId());
                lista.remove(corridaSelecionada);
                adapter.notifyDataSetChanged();

                corridaSelecionada = null;
                btnExcluirCorrida.setVisibility(View.GONE);
                btnVerInfoCorrida.setVisibility(View.GONE);

                Toast.makeText(this, "Corrida excluída!", Toast.LENGTH_SHORT).show();

                if (lista.isEmpty())
                    textSemCorridas.setVisibility(View.VISIBLE);
            }
        });

        btnVerInfoCorrida.setOnClickListener(v -> {
            if (corridaSelecionada != null) {

                String origem = corridaSelecionada.getOrigem();
                String destino = corridaSelecionada.getDestino();
                double km = corridaSelecionada.getKm();
                double valor = corridaSelecionada.getValor();
                String tipoCombustivel = corridaSelecionada.getTipoCombustivel();

                SharedPreferences prefs = getSharedPreferences("APP_PREFS", MODE_PRIVATE);
                double preco;

                switch (tipoCombustivel) {
                    case "Gasolina":
                        preco = Double.parseDouble(prefs.getString("preco_gasolina", "5.50"));
                        break;
                    case "Álcool":
                        preco = Double.parseDouble(prefs.getString("preco_alcool", "4.00"));
                        break;
                    case "Diesel":
                        preco = Double.parseDouble(prefs.getString("preco_diesel", "5.00"));
                        break;
                    default:
                        preco = 0;
                }

                double litrosGastos = km / usuarioLogado.getConsumo();
                double gasto = litrosGastos * preco;

                String msg = "Origem → Destino: " + origem + " → " + destino +
                        "\nDistância: " + km + " km" +
                        "\nValor recebido: R$ " + String.format("%.2f", valor) +
                        "\nCombustível: " + tipoCombustivel +
                        "\nLitros gastos: " + String.format("%.2f", litrosGastos) + " L" +
                        "\nGasto total: R$ " + String.format("%.2f", gasto);


                new MaterialAlertDialogBuilder(CorridasActivity.this, R.style.ThemeOverlay_MyAlertDialog)
                        .setTitle("Mais Informações")
                        .setMessage(msg)
                        .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                        .show();
            }
        });

        btnVoltarCorridas.setOnClickListener(v -> finish());
    }
}
