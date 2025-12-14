package com.example.kmoveteste.activities;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import com.example.kmoveteste.models.Usuario;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kmoveteste.R;
import com.example.kmoveteste.database.UsuarioDAO;
import com.google.android.material.button.MaterialButton;

public class TelaCadastroActivity extends AppCompatActivity {
    private EditText editNome, editEmailCadastro, editSenhaCadastro, editConfirmarSenha;
    private MaterialButton btnCadastrar, btnVoltarLogin;
    private UsuarioDAO dao;

    @Override protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState); setContentView(R.layout.activity_telacadastro);
        dao = new UsuarioDAO(this);
        editNome = findViewById(R.id.editNome); editEmailCadastro = findViewById(R.id.editEmailCadastro); editSenhaCadastro = findViewById(R.id.editSenhaCadastro); editConfirmarSenha = findViewById(R.id.editConfirmarSenha);
        btnCadastrar = findViewById(R.id.btnCadastrar); btnVoltarLogin = findViewById(R.id.btnVoltarLogin);
        btnCadastrar.setOnClickListener(v -> {
            String nome = editNome.getText().toString().trim(); String email = editEmailCadastro.getText().toString().trim(); String senha = editSenhaCadastro.getText().toString().trim(); String conf = editConfirmarSenha.getText().toString().trim();
            if(nome.isEmpty() || email.isEmpty() || senha.isEmpty()){ Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show(); return; }
            if(!senha.equals(conf)){ Toast.makeText(this, "Senhas não conferem", Toast.LENGTH_SHORT).show(); return; }
            Usuario u = new Usuario(); u.setNome(nome); u.setEmail(email); u.setSenha(senha);
            long id = dao.inserir(u);
            if(id>0){ Toast.makeText(this, "Cadastro efetuado", Toast.LENGTH_SHORT).show(); finish(); } else { Toast.makeText(this, "Erro ao cadastrar (email talvez já exista)", Toast.LENGTH_SHORT).show(); }
        });
        btnVoltarLogin.setOnClickListener(v -> finish());
    }
}
