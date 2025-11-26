package com.example.appferreteria;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import models.ApiService;
import models.categoria; // Asegúrate de que tu modelo se llame así
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CategoriaFragment extends Fragment {

    RecyclerView recyclerView;
    CategoriaAdapter adapter;
    List<categoria> listaCategorias;

    // Ajusta la IP a la de tu servidor
    private static final String BASE_URL = "http://192.168.1.140:5000/";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_categoria, container, false);

        recyclerView = view.findViewById(R.id.categorias);

        // Configurar Grid de 2 columnas
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);

        listaCategorias = new ArrayList<>();

        // Llamada a la API
        cargarCategorias();

        return view;
    }

    private void cargarCategorias(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<List<categoria>> call = apiService.getCategorias(); // Asumiendo que agregaste este método en ApiService

        call.enqueue(new Callback<List<categoria>>() {
            @Override
            public void onResponse(Call<List<categoria>> call, Response<List<categoria>> response) {
                if(response.isSuccessful() && response.body() != null){
                    listaCategorias = response.body();

                    // AQUÍ ESTÁ LA CLAVE: Inicializamos el adapter con el listener
                    adapter = new CategoriaAdapter(listaCategorias, new CategoriaAdapter.OnCategoriaClickListener() {
                        @Override
                        public void onCategoriaClick(categoria categoriaItem) {
                            // Al hacer clic, abrimos el fragmento de productos pasando el ID
                            abrirProductosPorCategoria(categoriaItem.getIdCategoria());
                        }
                    });

                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(getContext(), "Error al cargar categorías", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<categoria>> call, Throwable t) {
                Toast.makeText(getContext(), "Fallo de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void abrirProductosPorCategoria(int idCategoria) {
        // Creamos la instancia del fragmento nuevo pasando el ID
        ProductoPorCategoriaFragment fragment = ProductoPorCategoriaFragment.newInstance(idCategoria);

        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null) // Importante para poder regresar con el botón atrás
                .commit();
    }
}