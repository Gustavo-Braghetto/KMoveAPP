package com.example.kmoveteste.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.kmoveteste.database.DespesaDAO;


import com.example.kmoveteste.R;
import com.example.kmoveteste.models.Despesa;
import com.google.android.material.button.MaterialButton;

import java.util.Calendar;
import java.util.Locale;

public class CadastrarDespesaActivity extends AppCompatActivity {

    private EditText editDescricaoDespesa, editValorDespesa, editDataDespesa;
    private MaterialButton btnSalvarDespesa, btnCancelarDespesa;
    private int usuarioId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_despesa);

        usuarioId = getIntent().getIntExtra("usuario_id", -1);

        editDescricaoDespesa = findViewById(R.id.inputDescricaoDespesa);
        editValorDespesa = findViewById(R.id.inputValorDespesa);
        editDataDespesa = findViewById(R.id.inputDataDespesa);
        btnSalvarDespesa = findViewById(R.id.btnSalvarDespesa);
        btnCancelarDespesa = findViewById(R.id.btnCancelarDespesa);


        editDataDespesa.setOnClickListener(v -> {
            final Calendar c = Calendar.getInstance();
            int ano = c.get(Calendar.YEAR);
            int mes = c.get(Calendar.MONTH);
            int dia = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dpd = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
                month += 1; // Janeiro = 0
                String data = String.format(Locale.getDefault(), "%02d/%02d/%04d", dayOfMonth, month, year);
                editDataDespesa.setText(data);
            }, ano, mes, dia);
            dpd.show();
        });

        btnSalvarDespesa.setOnClickListener(v -> salvarDespesa());
        btnCancelarDespesa.setOnClickListener(v -> finish());
    }

    private void salvarDespesa() {
        String descricao = editDescricaoDespesa.getText().toString().trim();
        String valorStr = editValorDespesa.getText().toString().trim();
        String data = editDataDespesa.getText().toString().trim();

        if (descricao.isEmpty() || valorStr.isEmpty() || data.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            return;
        }

        double valor = Double.parseDouble(valorStr.replace(',', '.'));

        Despesa despesa = new Despesa();
        despesa.setDescricao(descricao);
        despesa.setValor(valor);
        despesa.setData(data);
        despesa.setUsuarioId(usuarioId);

        DespesaDAO dao = new DespesaDAO(this);
        dao.inserir(despesa);

        Toast.makeText(this, "Despesa salva com sucesso!", Toast.LENGTH_SHORT).show();
        finish();
    }
}
