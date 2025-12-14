package com.example.kmoveteste.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kmoveteste.R;
import com.example.kmoveteste.models.Despesa;

import java.util.List;

public class DespesaAdapter extends RecyclerView.Adapter<DespesaAdapter.ViewHolder>{
    private List<Despesa> lista;
    private OnItemClickListener listener;

    public DespesaAdapter(List<Despesa> lista){ this.lista = lista; }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_despesa, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        Despesa d = lista.get(position);
        holder.txtDescricao.setText(d.getDescricao());
        holder.txtValor.setText(String.format("R$ %.2f", d.getValor()));
        holder.txtData.setText(d.getData());

        holder.itemView.setOnClickListener(v -> {
            if(listener != null) listener.onItemClick(d);
        });
    }

    @Override
    public int getItemCount(){ return lista.size(); }

    public void remover(Despesa d){
        int pos = lista.indexOf(d);
        if(pos != -1){
            lista.remove(pos);
            notifyItemRemoved(pos);
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public interface OnItemClickListener{
        void onItemClick(Despesa d);
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtDescricao, txtValor, txtData;
        ViewHolder(View v){
            super(v);
            txtDescricao = v.findViewById(R.id.txtDescricaoDespesaItem);
            txtValor = v.findViewById(R.id.txtValorDespesaItem);
            txtData = v.findViewById(R.id.txtDataDespesaItem);
        }
    }
}
