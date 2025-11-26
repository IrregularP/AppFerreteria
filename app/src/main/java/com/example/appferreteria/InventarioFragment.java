package com.example.appferreteria;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;
import models.producto;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import models.ApiService;
import retrofit2.converter.gson.GsonConverterFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class InventarioFragment extends Fragment implements ProductoAdapter.ProductoClickListener {

    private RecyclerView recyclerView;
    private ProductoAdapter adapter;

    // IMPORTANTE: Usa la misma IP que te funcionó en las otras pantallas
    private static final String BASE_URL = "http://192.168.1.99:5000/";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_inventario, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewProducts);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Cargamos los datos al iniciar la pantalla
        cargarInventario();

        return view;
    }

    // Método reutilizable para cargar (o recargar) la lista
    private void cargarInventario() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        // Llamamos al endpoint getDatos() que trae TODOS los productos
        Call<List<producto>> call = apiService.getDatos();

        call.enqueue(new Callback<List<producto>>() {
            @Override
            public void onResponse(Call<List<producto>> call, Response<List<producto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<producto> list = response.body();

                    if(list.isEmpty()){
                        mostrarMensaje("El inventario está vacío");
                    }

                    // Configurar el adaptador pasando 'this' como listener
                    adapter = new ProductoAdapter(list, InventarioFragment.this);
                    recyclerView.setAdapter(adapter);
                } else {
                    mostrarMensaje("Error al cargar datos: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<producto>> call, Throwable t) {
                Log.e("API", "Error al obtener datos", t);
                mostrarMensaje("Error de conexión");
            }
        });
    }

    @Override
    public void onDetalleClick(producto producto) {
        // 1. Crear el diálogo con el producto seleccionado
        ProductoDetalleDialogFragment dialog = ProductoDetalleDialogFragment.newInstance(producto);

        // 2. IMPORTANTE: Aquí conectamos el listener para escuchar cambios
        dialog.setOnProductoActualizadoListener(new ProductoDetalleDialogFragment.OnProductoActualizadoListener() {
            @Override
            public void onProductoActualizado() {
                // 3. Si el diálogo nos avisa que hubo cambios (editar/borrar),
                // volvemos a descargar la lista entera.
                cargarInventario();
                mostrarMensaje("Inventario actualizado");
            }
        });

        dialog.show(getParentFragmentManager(), "ProductoDetalleDialogFragment");
    }

    private void mostrarMensaje(String msg) {
        if (getContext() != null) {
            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
        }
    }
}