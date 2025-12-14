package com.example.kmoveteste.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kmoveteste.R;
import com.example.kmoveteste.database.UsuarioDAO;
import com.example.kmoveteste.models.Usuario;
import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    private EditText editEmail, editSenha;
    private MaterialButton btnEntrar, btnIrCadastro;
    private UsuarioDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dao = new UsuarioDAO(this);
        editEmail = findViewById(R.id.editEmail);
        editSenha = findViewById(R.id.editSenha);
        btnEntrar = findViewById(R.id.btnEntrar);
        btnIrCadastro = findViewById(R.id.btnIrCadastro);

        // BOTÃO ENTRAR
        btnEntrar.setOnClickListener(v -> {
            String email = editEmail.getText().toString().trim();
            String senha = editSenha.getText().toString().trim();

            if (email.isEmpty() || senha.isEmpty()) {
                Toast.makeText(this, "Preencha email e senha", Toast.LENGTH_SHORT).show();
                return;
            }

            Usuario u = dao.autenticar(email, senha);

            if (u != null) {


                getSharedPreferences("APP", MODE_PRIVATE)
                        .edit()
                        .putInt("user_id", u.getId())
                        .apply();


                Intent it = new Intent(this, TelaPrincipalActivity.class);
                it.putExtra("usuario_id", u.getId());
                it.putExtra("usuario_nome", u.getNome());
                startActivity(it);
                finish();

            } else {
                Toast.makeText(this, "Credenciais inválidas", Toast.LENGTH_SHORT).show();
            }
        });


        btnIrCadastro.setOnClickListener(v ->
                startActivity(new Intent(this, TelaCadastroActivity.class))
        );
    }
}
