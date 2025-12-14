package com.example.kmoveteste.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.kmoveteste.R;
import com.example.kmoveteste.database.CorridaDAO;
import com.example.kmoveteste.database.MetaDAO;
import com.example.kmoveteste.database.DespesaDAO;
import com.example.kmoveteste.database.UsuarioDAO;
import com.example.kmoveteste.models.Meta;
import com.example.kmoveteste.models.Usuario;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;

public class TelaPrincipalActivity extends AppCompatActivity {

    private TextView txtTotalCorridas, txtDespesas, txtLucroTotal, txtMetas;
    private CardView cardTotalCorridas, cardLucroTotal, cardDespesas, cardMetas;
    private int usuarioId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telaprincipal);


        usuarioId = getIntent().getIntExtra("usuario_id", -1);


        txtTotalCorridas = findViewById(R.id.txtTotalCorridas);
        txtDespesas = findViewById(R.id.txtDespesas);
        txtLucroTotal = findViewById(R.id.txtLucroTotal);
        txtMetas = findViewById(R.id.txtMetas);


        cardTotalCorridas = findViewById(R.id.cardTotalCorridas);
        cardLucroTotal = findViewById(R.id.cardLucroTotal);
        cardDespesas = findViewById(R.id.cardDespesas);
        cardMetas = findViewById(R.id.cardMetas);


        findViewById(R.id.btnCadastrarCorrida).setOnClickListener(v -> {
            Intent i = new Intent(this, CadastrarCorridaActivity.class);
            i.putExtra("usuario_id", usuarioId);
            startActivity(i);
        });

        findViewById(R.id.btnCadastrarDespesa).setOnClickListener(v -> {
            Intent i = new Intent(this, CadastrarDespesaActivity.class);
            i.putExtra("usuario_id", usuarioId);
            startActivity(i);
        });

        findViewById(R.id.btnMetas).setOnClickListener(v -> {
            Intent i = new Intent(this, CadastrarMetasActivity.class);
            i.putExtra("usuario_id", usuarioId);
            startActivity(i);
        });


        cardTotalCorridas.setOnClickListener(v -> {
            Intent i = new Intent(this, CorridasActivity.class);
            i.putExtra("usuario_id", usuarioId);
            startActivity(i);
        });

        cardDespesas.setOnClickListener(v -> {
            Intent i = new Intent(this, DespesasActivity.class);
            i.putExtra("usuario_id", usuarioId);
            startActivity(i);
        });

        cardMetas.setOnClickListener(v -> {
            Intent i = new Intent(this, MetasActivity.class);
            i.putExtra("usuario_id", usuarioId);
            startActivity(i);
        });

        cardLucroTotal.setOnClickListener(v -> {
            Intent i = new Intent(this, LucroActivity.class);
            i.putExtra("usuario_id", usuarioId);
            startActivity(i);
        });


        findViewById(R.id.btnPerfil).setOnClickListener(v -> {
            Intent i = new Intent(TelaPrincipalActivity.this, PerfilActivity.class);
            i.putExtra("usuario_id", usuarioId);
            startActivity(i);
        });


        findViewById(R.id.btnSair).setOnClickListener(v -> {
            Intent i = new Intent(TelaPrincipalActivity.this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            finish();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        atualizarDashboard();
        checarDadosDoCarro();
    }

    private void atualizarDashboard() {
        CorridaDAO corridaDAO = new CorridaDAO(this);
        DespesaDAO despesaDAO = new DespesaDAO(this);
        MetaDAO metaDAO = new MetaDAO(this);

        double totalCorridas = corridaDAO.somarTotalPorUsuario(usuarioId);
        double totalDespesas = despesaDAO.somarTotalPorUsuario(usuarioId);
        double totalKm = corridaDAO.somarKmPorUsuario(usuarioId);

        double custoCombustivel = totalKm * 0.5;
        double lucro = totalCorridas - totalDespesas - custoCombustivel;

        List<Meta> metas = metaDAO.listarMetasPorUsuario(usuarioId);

        double totalMetas = 0;
        if (metas != null && !metas.isEmpty()) {
            for (Meta m : metas) {
                totalMetas += m.getValor();
            }
        }

        txtTotalCorridas.setText(String.format("R$ %.2f", totalCorridas));
        txtDespesas.setText(String.format("R$ %.2f", totalDespesas));
        txtLucroTotal.setText(String.format("R$ %.2f", lucro));
        txtMetas.setText(metas.isEmpty() ? "Nenhuma meta" : String.format("R$ %.2f", totalMetas));
    }

    private void checarDadosDoCarro() {
        UsuarioDAO usuarioDAO = new UsuarioDAO(this);
        Usuario usuario = usuarioDAO.buscarPorId(usuarioId);

        if (usuario != null && usuario.getConsumo() <= 0.0) {

            txtTotalCorridas.post(this::mostrarAlertaPreencherDados);
        }
    }

    private void mostrarAlertaPreencherDados() {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        builder.setTitle("Atenção")
                .setMessage("Para garantir cálculos precisos, é necessário informar o consumo do veículo nas configurações do perfil.")
                .setCancelable(false)
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss());

        AlertDialog alertDialog = builder.create();
        alertDialog.show();


        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setAllCaps(false);
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(android.R.color.white));
    }


}
