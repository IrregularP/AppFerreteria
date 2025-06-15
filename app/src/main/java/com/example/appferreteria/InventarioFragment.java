package com.example.appferreteria;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;
import models.producto;

public class InventarioFragment extends Fragment implements ProductoAdapter.ProductoClickListener {

    private RecyclerView recyclerView;
    private ProductoAdapter adapter;
    private List<producto> productoList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_inventario, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewProducts);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Puedes reemplazar este método con la llamada a tu API.
        productoList = obtenerProductos();
        adapter = new ProductoAdapter(productoList, this);
        recyclerView.setAdapter(adapter);

        return view;
    }

    // Método simulado para obtener productos. Reemplázalo por la llamada a tu API.
    private List<producto> obtenerProductos() {
        List<producto> list = new ArrayList<>();
        list.add(new producto(1, "Martillo", "Martillo de acero", "123456789", 50.0, 60.0, 20.0, 12.0, 15, 1, 101));
        list.add(new producto(2, "Taladro", "Taladro inalámbrico", "987654321", 150.0, 180.0, 20.0, 12.0, 10, 2, 102));
        return list;
    }

    @Override
    public void onDetalleClick(producto producto) {
        // Crea y muestra el modal con los detalles del producto.
        ProductoDetalleDialogFragment dialog = ProductoDetalleDialogFragment.newInstance(producto);
        dialog.show(getParentFragmentManager(), "ProductoDetalleDialogFragment");
    }
}