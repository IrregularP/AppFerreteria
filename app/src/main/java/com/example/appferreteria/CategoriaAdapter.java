package com.example.appferreteria;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.appferreteria.R;
import java.util.List;
import models.categoria;

public class CategoriaAdapter extends RecyclerView.Adapter<CategoriaAdapter.ViewHolder> {

    private List<categoria> categorias;
    private OnCategoriaClickListener listener;

    public interface OnCategoriaClickListener {
        void onCategoriaClick(categoria categoria);
    }

    public CategoriaAdapter(List<categoria> categorias, OnCategoriaClickListener listener) {
        this.categorias = categorias;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_categorias, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        categoria item = categorias.get(position);
        holder.categoriaText.setText(item.getNombre());

        holder.cardView.setOnClickListener(v -> {
            if (listener != null) listener.onCategoriaClick(item);
        });
    }

    @Override
    public int getItemCount() {
        return categorias.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView categoriaText;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoriaText = itemView.findViewById(R.id.categoriaText);
            cardView = (CardView) itemView;
        }
    }
}

