package com.example.kmoveteste.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kmoveteste.R;
import com.example.kmoveteste.database.CorridaDAO;
import com.example.kmoveteste.database.DespesaDAO;
import com.google.android.material.button.MaterialButton;

public class LucroActivity extends AppCompatActivity {

    private TextView textLucroTotal, textGanhos, textCustoCombustivel, textDespesas;
    private CorridaDAO corridaDAO;
    private DespesaDAO despesaDAO;
    private int usuarioId;
    private MaterialButton btnVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lucro);


        textLucroTotal = findViewById(R.id.textLucroTotal);
        textGanhos = findViewById(R.id.textGanhos);
        textCustoCombustivel = findViewById(R.id.textCustoCombustivel);
        textDespesas = findViewById(R.id.textDespesas);
        btnVoltar = findViewById(R.id.btnVoltarLucro);


        corridaDAO = new CorridaDAO(this);
        despesaDAO = new DespesaDAO(this);


        usuarioId = getIntent().getIntExtra("usuario_id", -1);


        atualizarLucro();


        btnVoltar.setOnClickListener(v -> {
            Intent it = new Intent(LucroActivity.this, TelaPrincipalActivity.class);
            startActivity(it);
            finish();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        atualizarLucro();
    }

    private void atualizarLucro() {
        double totalCorridas = corridaDAO.somarTotalPorUsuario(usuarioId);
        double totalKm = corridaDAO.somarKmPorUsuario(usuarioId);
        double totalDespesas = despesaDAO.somarTotalPorUsuario(usuarioId);

        double custoCombustivel = totalKm * 0.5;
        double lucro = totalCorridas - totalDespesas - custoCombustivel;

        textGanhos.setText(String.format("Ganhos com Corridas: R$ %.2f", totalCorridas));
        textCustoCombustivel.setText(String.format("Custo de Combustível: R$ %.2f", custoCombustivel));
        textDespesas.setText(String.format("Despesas Gerais: R$ %.2f", totalDespesas));
        textLucroTotal.setText(String.format("Lucro Líquido: R$ %.2f", lucro));
    }
}
