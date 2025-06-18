package com.example.appferreteria;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;

import models.categoria;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jspecify.annotations.Nullable;

import mockdata.categoriaFakeData;

import java.util.List;

public class CategoriaFragment extends Fragment {

    RecyclerView recyclerView;
    CategoriaAdapter adapter;
    LayoutInflater globalInflater;
    List<categoria> listaCategorias;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_categoria, container, false);

        globalInflater = inflater;
        recyclerView = view.findViewById(R.id.categorias);

        listaCategorias = obtenerCategorias();
        adapter = new CategoriaAdapter(listaCategorias, categoria -> {

        });

        recyclerView.setAdapter(adapter);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);

        return view;
    }

    private List<categoria> obtenerCategorias(){
        //Futuramente cambiar por el API
        return categoriaFakeData.getCategorias();
    }
}
