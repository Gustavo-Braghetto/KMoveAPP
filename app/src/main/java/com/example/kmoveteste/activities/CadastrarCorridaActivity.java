package com.example.kmoveteste.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kmoveteste.R;
import com.example.kmoveteste.database.CorridaDAO;
import com.example.kmoveteste.database.MetaDAO;
import com.example.kmoveteste.database.UsuarioDAO;
import com.example.kmoveteste.models.Corrida;
import com.example.kmoveteste.models.Usuario;
import com.google.android.material.button.MaterialButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CadastrarCorridaActivity extends AppCompatActivity {

    private EditText editOrigem, editDestino, editValor, editKm, editPrecoCombustivel;
    private Spinner spinnerCombustivel;
    private MaterialButton btnCadastrarCorrida, btnCancelar;

    private CorridaDAO corridaDAO;
    private UsuarioDAO usuarioDAO;
    private int usuarioId;
    private Usuario usuarioLogado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_corrida);

        usuarioId = getIntent().getIntExtra("usuario_id", -1);
        corridaDAO = new CorridaDAO(this);
        usuarioDAO = new UsuarioDAO(this);
        usuarioLogado = usuarioDAO.buscarPorId(usuarioId);

        editOrigem = findViewById(R.id.editOrigem);
        editDestino = findViewById(R.id.editDestino);
        editValor = findViewById(R.id.editValor);
        editKm = findViewById(R.id.editKm);
        editPrecoCombustivel = findViewById(R.id.editPrecoCombustivel);
        spinnerCombustivel = findViewById(R.id.spinnerCombustivel);
        btnCadastrarCorrida = findViewById(R.id.btnCadastrarCorrida);
        btnCancelar = findViewById(R.id.btnCancelar);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                new String[]{"Gasolina", "Álcool", "Diesel"}
        );
        spinnerCombustivel.setAdapter(adapter);

        spinnerCombustivel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                carregarPrecoCombustivel();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        btnCadastrarCorrida.setOnClickListener(v -> cadastrarCorrida());
        btnCancelar.setOnClickListener(v -> finish());
    }

    private void carregarPrecoCombustivel() {
        String tipo = spinnerCombustivel.getSelectedItem().toString();
        String preco = "";
        switch (tipo) {
            case "Gasolina": preco = String.valueOf(usuarioLogado.getPrecoGasolina()); break;
            case "Álcool": preco = String.valueOf(usuarioLogado.getPrecoAlcool()); break;
            case "Diesel": preco = String.valueOf(usuarioLogado.getPrecoDiesel()); break;
        }
        editPrecoCombustivel.setText(preco);
    }

    private void cadastrarCorrida() {
        String origem = editOrigem.getText().toString().trim();
        String destino = editDestino.getText().toString().trim();
        String sValor = editValor.getText().toString().trim();
        String sKm = editKm.getText().toString().trim();
        String sPreco = editPrecoCombustivel.getText().toString().trim();

        if (origem.isEmpty() || destino.isEmpty() || sValor.isEmpty() || sKm.isEmpty() || sPreco.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double valor = Double.parseDouble(sValor.replace(',', '.'));
            double km = Double.parseDouble(sKm.replace(',', '.'));
            double precoCombustivel = Double.parseDouble(sPreco.replace(',', '.'));
            String tipoCombustivel = spinnerCombustivel.getSelectedItem().toString();

            SharedPreferences prefs = getSharedPreferences("APP_PREFS", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            switch (tipoCombustivel) {
                case "Gasolina": editor.putString("preco_gasolina", sPreco); break;
                case "Álcool": editor.putString("preco_alcool", sPreco); break;
                case "Diesel": editor.putString("preco_diesel", sPreco); break;
            }
            editor.apply();

            double consumoKmL = usuarioLogado.getConsumo();
            double litrosGastos = km / consumoKmL;
            double gastoEmReais = litrosGastos * precoCombustivel;

            Corrida corrida = new Corrida();
            corrida.setUsuarioId(usuarioId);
            corrida.setOrigem(origem);
            corrida.setDestino(destino);
            corrida.setValor(valor);
            corrida.setKm(km);
            corrida.setTipoCombustivel(tipoCombustivel);
            String data = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
            corrida.setData(data);

            long id = corridaDAO.inserir(corrida);
            if (id > 0) {
                MetaDAO metaDAO = new MetaDAO(this);
                metaDAO.atualizarProgressoDeTodas(usuarioId, valor);

                Toast.makeText(this,
                        "Corrida cadastrada!\nGasto estimado: R$ " + String.format("%.2f", gastoEmReais),
                        Toast.LENGTH_LONG).show();
                finish();
            } else {
                Toast.makeText(this, "Erro ao salvar corrida", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            Toast.makeText(this, "Valores inválidos!", Toast.LENGTH_SHORT).show();
        }
    }
}
