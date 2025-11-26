package com.example.appferreteria;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import models.ApiService;
import models.proveedor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProveedoresFragment extends Fragment {

    private RecyclerView recyclerView;
    private static final String BASE_URL = "http://192.168.1.140:5000/"; // Tu IP real

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_proveedores, container, false);

        recyclerView = view.findViewById(R.id.recyclerProveedores);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        cargarProveedores();

        return view;
    }

    private void cargarProveedores() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        // Llamamos al endpoint creado en el Paso 2
        apiService.getProveedores().enqueue(new Callback<List<proveedor>>() {
            @Override
            public void onResponse(Call<List<proveedor>> call, Response<List<proveedor>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<proveedor> lista = response.body();

                    if (lista.isEmpty()) {
                        Toast.makeText(getContext(), "No hay proveedores registrados", Toast.LENGTH_SHORT).show();
                    }

                    ProveedorAdapter adapter = new ProveedorAdapter(lista, itemProveedor -> {
                        // Al hacer click en "Detalles", abrimos el Dialog
                        ProveedorDetalleDialog dialog = ProveedorDetalleDialog.newInstance(itemProveedor);
                        dialog.show(getParentFragmentManager(), "DetalleProveedor");
                    });

                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(getContext(), "Error del servidor: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<proveedor>> call, Throwable t) {
                Toast.makeText(getContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }
}