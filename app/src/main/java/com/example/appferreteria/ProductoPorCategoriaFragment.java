package com.example.appferreteria;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import models.ApiService;
import models.producto;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductoPorCategoriaFragment extends Fragment {

    private static final String ARG_ID_CATEGORIA = "idCategoria";

    // IMPORTANTE: Asegúrate de usar la IP de tu servidor
    private static final String BASE_URL = "http://192.168.1.140:5000/";

    private int idCategoria;
    private RecyclerView recyclerView;
    private ProductoAdapter adapter;

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

        // Mantenemos tu layout 'fragment_inventario'
        View view = inflater.inflate(R.layout.fragment_inventario, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewProducts);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // En lugar de obtener datos fake, llamamos a la API
        cargarProductosDeApi(idCategoria);

        return view;
    }

    private void cargarProductosDeApi(int categoriaId) {
        // 1. Configurar Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        // 2. Llamar al endpoint que creamos previamente
        Call<List<producto>> call = apiService.getProductosPorCategoria(categoriaId);

        // 3. Ejecutar llamada en segundo plano
        call.enqueue(new Callback<List<producto>>() {
            @Override
            public void onResponse(Call<List<producto>> call, Response<List<producto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<producto> listaProductos = response.body();

                    if (listaProductos.isEmpty()) {
                        Toast.makeText(getContext(), "No hay productos en esta categoría", Toast.LENGTH_SHORT).show();
                    }

                    // 4. Configurar el adaptador con la lista REAL
                    // Mantenemos tu lógica del DialogFragment aquí
                    adapter = new ProductoAdapter(listaProductos, producto -> {
                        ProductoDetalleDialogFragment dialog = ProductoDetalleDialogFragment.newInstance(producto);
                        dialog.show(getParentFragmentManager(), "DetalleProducto");
                    });

                    recyclerView.setAdapter(adapter);

                } else {
                    Toast.makeText(getContext(), "Error del servidor: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<producto>> call, Throwable t) {
                Toast.makeText(getContext(), "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Se eliminó el método 'obtenerProductosPorCategoria' ya que usaba datos falsos.
}