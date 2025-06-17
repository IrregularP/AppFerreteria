package com.example.appferreteria;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import models.producto;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder> {

    private List<producto> productoList;
    private ProductoClickListener listener;

    // Define una interfaz para el clic del botón "Detalles"
    public interface ProductoClickListener {
        void onDetalleClick(producto producto);
    }

        public ProductoAdapter(List<producto> productoList, ProductoClickListener listener) {
        this.productoList = productoList;
        this.listener = listener;
    }

    public static class ProductoViewHolder extends RecyclerView.ViewHolder {
        public TextView tvProductName;
        public TextView tvStock;
        public TextView tvDetallePrecioUnitario;
        public Button btnDetalles;

        public ProductoViewHolder(View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvStock = itemView.findViewById(R.id.tvStock);
            tvDetallePrecioUnitario = itemView.findViewById(R.id.tvDetallePrecioUnitario);
            btnDetalles = itemView.findViewById(R.id.btnDetalles);
        }
    }

    @Override
    public ProductoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_card_producto, parent, false);
        return new ProductoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductoViewHolder holder, int position) {
        final producto prod = productoList.get(position);
        holder.tvProductName.setText(prod.getNombre());
        holder.tvStock.setText("Stock: " + prod.getStock());
        holder.tvDetallePrecioUnitario.setText("Precio: $" + prod.getPrecioUnitario());

        holder.btnDetalles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null){
                    listener.onDetalleClick(prod);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return productoList.size();
    }
}
