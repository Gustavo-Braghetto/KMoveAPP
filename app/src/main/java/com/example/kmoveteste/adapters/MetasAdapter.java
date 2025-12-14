package com.example.kmoveteste.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kmoveteste.R;
import com.example.kmoveteste.models.Meta;

import java.util.List;

public class MetasAdapter extends RecyclerView.Adapter<MetasAdapter.ViewHolder> {

    public interface OnMetaClickListener {
        void onMetaClick(Meta meta);
    }

    private List<Meta> lista;
    private OnMetaClickListener listener;

    public MetasAdapter(List<Meta> lista, OnMetaClickListener listener) {
        this.lista = lista;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_meta, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Meta m = lista.get(position);

        holder.txtTitulo.setText(m.getTitulo());
        holder.txtProgresso.setText(
                String.format("R$ %.2f / R$ %.2f", m.getValorAtual(), m.getValor())
        );

        int pct = (int) ((m.getValorAtual() / (m.getValor() == 0 ? 1 : m.getValor())) * 100);
        holder.progress.setProgress(Math.min(pct, 100));

        holder.itemView.setOnClickListener(v -> listener.onMetaClick(m));
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitulo, txtProgresso;
        ProgressBar progress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitulo = itemView.findViewById(R.id.txtTituloMeta);
            txtProgresso = itemView.findViewById(R.id.txtProgressoMeta);
            progress = itemView.findViewById(R.id.progressMeta);
        }
    }
}
