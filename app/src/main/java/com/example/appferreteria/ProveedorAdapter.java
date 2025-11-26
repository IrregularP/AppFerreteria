package com.example.appferreteria;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import models.proveedor;

public class ProveedorAdapter extends RecyclerView.Adapter<ProveedorAdapter.ViewHolder> {

    private List<proveedor> listaProveedores;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onDetallesClick(proveedor p);
    }

    public ProveedorAdapter(List<proveedor> lista, OnItemClickListener listener) {
        this.listaProveedores = lista;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_card_proveedor, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        proveedor item = listaProveedores.get(position);
        holder.tvNombre.setText(item.getNombre());
        holder.tvTelefono.setText("Tel: " + item.getTelefono());

        holder.btnDetalles.setOnClickListener(v -> {
            if (listener != null) listener.onDetallesClick(item);
        });
    }

    @Override
    public int getItemCount() {
        return listaProveedores.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvTelefono;
        Button btnDetalles;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvProvNombre);
            tvTelefono = itemView.findViewById(R.id.tvProvTelefono);
            btnDetalles = itemView.findViewById(R.id.btnProvDetalles);
        }
    }
}