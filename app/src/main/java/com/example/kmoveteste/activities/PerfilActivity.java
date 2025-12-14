
package com.example.kmoveteste.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kmoveteste.R;
import com.example.kmoveteste.database.UsuarioDAO;
import com.example.kmoveteste.models.Usuario;

public class PerfilActivity extends AppCompatActivity {

    private EditText editNome, editEmail, editSenhaAtual, editSenhaNova, editConsumo;
    private EditText edtValorGasolina, edtValorAlcool, edtValorDiesel;
    private Button btnSalvarPerfil, btnVoltar;
    private TextView textMostrarTutorial;
    private LinearLayout layoutTutorial;

    private UsuarioDAO usuarioDAO;
    private Usuario usuarioLogado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        usuarioDAO = new UsuarioDAO(this);
        int userId = getSharedPreferences("APP", MODE_PRIVATE).getInt("user_id", -1);
        usuarioLogado = usuarioDAO.buscarPorId(userId);

        editNome = findViewById(R.id.editNome);
        editEmail = findViewById(R.id.editEmail);
        editSenhaAtual = findViewById(R.id.editSenhaAtual);
        editSenhaNova = findViewById(R.id.editSenhaNova);
        editConsumo = findViewById(R.id.editConsumo);

        edtValorGasolina = findViewById(R.id.edtValorGasolina);
        edtValorAlcool = findViewById(R.id.edtValorAlcool);
        edtValorDiesel = findViewById(R.id.edtValorDiesel);

        textMostrarTutorial = findViewById(R.id.textMostrarTutorial);
        layoutTutorial = findViewById(R.id.layoutTutorial);

        btnSalvarPerfil = findViewById(R.id.btnSalvarPerfil);
        btnVoltar = findViewById(R.id.btnVoltar);

        carregarDados();

        textMostrarTutorial.setOnClickListener(v -> {
            layoutTutorial.setVisibility(
                    layoutTutorial.getVisibility() == View.GONE ? View.VISIBLE : View.GONE
            );
        });

        btnSalvarPerfil.setOnClickListener(v -> salvarPerfil());

        btnVoltar.setOnClickListener(v -> {
            Intent i = new Intent(PerfilActivity.this, TelaPrincipalActivity.class);
            i.putExtra("usuario_id", usuarioLogado.getId());
            startActivity(i);
            finish();
        });
    }

    private void carregarDados() {
        if (usuarioLogado != null) {
            editNome.setText(usuarioLogado.getNome());
            editEmail.setText(usuarioLogado.getEmail());
            editConsumo.setText(String.valueOf(usuarioLogado.getConsumo()));
            edtValorGasolina.setText(String.valueOf(usuarioLogado.getPrecoGasolina()));
            edtValorAlcool.setText(String.valueOf(usuarioLogado.getPrecoAlcool()));
            edtValorDiesel.setText(String.valueOf(usuarioLogado.getPrecoDiesel()));
        }
    }

    private void salvarPerfil() {
        String senhaAtual = editSenhaAtual.getText().toString();

        if (!senhaAtual.equals(usuarioLogado.getSenha())) {
            Toast.makeText(this, "Senha atual incorreta!", Toast.LENGTH_SHORT).show();
            return;
        }

        String nome = editNome.getText().toString();
        String email = editEmail.getText().toString();
        String novaSenha = editSenhaNova.getText().toString();
        String consumoStr = editConsumo.getText().toString();
        String precoGasolinaStr = edtValorGasolina.getText().toString();
        String precoAlcoolStr = edtValorAlcool.getText().toString();
        String precoDieselStr = edtValorDiesel.getText().toString();

        if (nome.isEmpty() || email.isEmpty() || consumoStr.isEmpty() ||
                precoGasolinaStr.isEmpty() || precoAlcoolStr.isEmpty() || precoDieselStr.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            return;
        }

        usuarioLogado.setNome(nome);
        usuarioLogado.setEmail(email);
        usuarioLogado.setConsumo(Double.parseDouble(consumoStr));
        usuarioLogado.setPrecoGasolina(Double.parseDouble(precoGasolinaStr));
        usuarioLogado.setPrecoAlcool(Double.parseDouble(precoAlcoolStr));
        usuarioLogado.setPrecoDiesel(Double.parseDouble(precoDieselStr));

        if (!novaSenha.isEmpty()) {
            usuarioLogado.setSenha(novaSenha);
        }


        usuarioDAO.atualizarPerfil(usuarioLogado);


        SharedPreferences prefsAlerta = getSharedPreferences("KMOVE_PREFS", MODE_PRIVATE);
        prefsAlerta.edit()
                .putFloat("consumoCarro", Float.parseFloat(consumoStr))
                .putFloat("precoGasolina", Float.parseFloat(precoGasolinaStr))
                .apply();


        SharedPreferences prefs = getSharedPreferences("APP_PREFS", MODE_PRIVATE);
        prefs.edit()
                .putString("preco_gasolina", precoGasolinaStr)
                .putString("preco_alcool", precoAlcoolStr)
                .putString("preco_diesel", precoDieselStr)
                .apply();

        Toast.makeText(this, "Perfil atualizado com sucesso!", Toast.LENGTH_SHORT).show();
    }
}
