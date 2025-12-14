package com.example.kmoveteste.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kmoveteste.R;
import com.example.kmoveteste.models.Corrida;
import com.example.kmoveteste.utils.FormatUtils;

import java.util.List;

public class CorridaAdapter extends RecyclerView.Adapter<CorridaAdapter.ViewHolder> {

    private List<Corrida> lista;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Corrida corrida);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public CorridaAdapter(List<Corrida> lista){
        this.lista = lista;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_corrida, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        Corrida c = lista.get(position);
        holder.txtOrigemDestino.setText(c.getOrigem() + " → " + c.getDestino());
        holder.txtValor.setText(FormatUtils.moeda(c.getValor()));
        holder.txtKm.setText(String.format("%.1f km", c.getKm()));

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onItemClick(c);
        });
    }

    @Override
    public int getItemCount(){
        return lista.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtOrigemDestino, txtValor, txtKm;
        ViewHolder(View v){
            super(v);
            txtOrigemDestino = v.findViewById(R.id.txtOrigemDestino);
            txtValor = v.findViewById(R.id.txtValorCorrida);
            txtKm = v.findViewById(R.id.txtKmCorrida);
        }
    }
}
