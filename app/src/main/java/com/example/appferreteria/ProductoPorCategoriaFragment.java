package com.example.appferreteria;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import mockdata.productoFakeData;
import models.producto;

public class ProductoPorCategoriaFragment extends Fragment {

    private static final String ARG_ID_CATEGORIA = "idCategoria";

    private int idCategoria;
    private RecyclerView recyclerView;
    private  ProductoAdapter adapter;

    public static ProductoPorCategoriaFragment newInstance(int idCategoria){
        ProductoPorCategoriaFragment fragment = new ProductoPorCategoriaFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_ID_CATEGORIA, idCategoria);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idCategoria = getArguments().getInt(ARG_ID_CATEGORIA);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_inventario, container, false); // Reutiliza el layout

        recyclerView = view.findViewById(R.id.recyclerViewProducts);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<producto> productosFiltrados = obtenerProductosPorCategoria(idCategoria);
        adapter = new ProductoAdapter(productosFiltrados, producto -> {
            ProductoDetalleDialogFragment dialog = ProductoDetalleDialogFragment.newInstance(producto);
            dialog.show(getParentFragmentManager(), "DetalleProducto");
        });

        recyclerView.setAdapter(adapter);
        return view;
    }

    private List<producto> obtenerProductosPorCategoria(int categoriaId) {
        List<producto> todos = productoFakeData.getProductos();
        List<producto> filtrados = new ArrayList<>();
        for (producto p : todos) {
            if (p.getCategoria() == categoriaId) {
                filtrados.add(p);
            }
        }
        return filtrados;
    }

}
