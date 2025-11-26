package com.example.appferreteria;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import models.ApiService;
import models.cliente;
import models.cuentasPorCobrar;
import models.usuario;
import models.venta;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CuentasPorCobrarFragment extends Fragment {

    GridLayout gridLayout;
    LayoutInflater globalInflater;

    // Listas donde guardaremos los datos reales del API
    List<cuentasPorCobrar> ListadeCuentas = new ArrayList<>();
    List<venta> ListaDeVentas = new ArrayList<>();
    List<cliente> ListaDeClientes = new ArrayList<>();

    // CONFIGURACIÓN API
    private static final String BASE_URL = "http://192.168.1.140:5000/";
    private ApiService apiService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_cuentasporcobrar, container, false);

        globalInflater = inflater;
        gridLayout = view.findViewById(R.id.cuentas);

        // Inicializar Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);

        // Iniciar la carga de datos en cadena
        cargarDatosDelServidor();

        return view;
    }

    // --- LÓGICA DE CARGA EN CASCADA ---
    // Cargamos 1 por 1 para asegurar que al pintar tengamos toda la info para buscar nombres

    private void cargarDatosDelServidor() {
        // Paso 1: Cuentas
        apiService.getCuentasPorCobrar().enqueue(new Callback<List<cuentasPorCobrar>>() {
            @Override
            public void onResponse(Call<List<cuentasPorCobrar>> call, Response<List<cuentasPorCobrar>> response) {
                if(response.isSuccessful() && response.body() != null){
                    ListadeCuentas = response.body();
                    cargarVentas(); // Siguiente eslabón
                } else {
                    mostrarMensaje("Error al obtener cuentas");
                }
            }
            @Override
            public void onFailure(Call<List<cuentasPorCobrar>> call, Throwable t) {
                mostrarMensaje("Fallo de conexión (Cuentas)");
            }
        });
    }

    private void cargarVentas() {
        // Paso 2: Ventas
        apiService.getVentas().enqueue(new Callback<List<venta>>() {
            @Override
            public void onResponse(Call<List<venta>> call, Response<List<venta>> response) {
                if(response.isSuccessful() && response.body() != null){
                    ListaDeVentas = response.body();
                    cargarClientes(); // Siguiente eslabón
                } else {
                    mostrarMensaje("Error al obtener ventas");
                }
            }
            @Override
            public void onFailure(Call<List<venta>> call, Throwable t) {
                mostrarMensaje("Fallo de conexión (Ventas)");
            }
        });
    }

    private void cargarClientes() {
        // Paso 3: Clientes y Renderizado Final
        apiService.getClientes().enqueue(new Callback<List<cliente>>() {
            @Override
            public void onResponse(Call<List<cliente>> call, Response<List<cliente>> response) {
                if(response.isSuccessful() && response.body() != null){
                    ListaDeClientes = response.body();
                    renderizarCuentas(); // ¡Ya tenemos todo! Pintamos la pantalla.
                } else {
                    mostrarMensaje("Error al obtener clientes");
                }
            }
            @Override
            public void onFailure(Call<List<cliente>> call, Throwable t) {
                mostrarMensaje("Fallo de conexión (Clientes)");
            }
        });
    }

    private void renderizarCuentas(){
        if(getContext() == null) return;
        gridLayout.removeAllViews();

        usuario UsuarioActual = SessionManager.getUsuario(getContext());
        boolean esAdmin = UsuarioActual != null && "administrador".equalsIgnoreCase(UsuarioActual.getRol());

        if(ListadeCuentas.isEmpty()){
            mostrarMensaje("No hay cuentas por cobrar pendientes");
        }

        for (cuentasPorCobrar cuenta : ListadeCuentas){
            View card = globalInflater.inflate(R.layout.item_card_cuentas, gridLayout, false);

            TextView NoCuenta = card.findViewById(R.id.numCuenta);
            TextView EstadoCuenta = card.findViewById(R.id.EstadoCuenta);
            TextView ClienteNombre = card.findViewById(R.id.ClienteDeLaCuenta);
            Button VerInfo = card.findViewById(R.id.btnVerInfo);
            Button BorrarCuenta = card.findViewById(R.id.BorrarCuenta);

            NoCuenta.setText("Cuenta #" + cuenta.getIdCuenta());

            // Estado y Color
            EstadoCuenta.setText(cuenta.getEstado());
            if("pendiente".equalsIgnoreCase(cuenta.getEstado())){
                EstadoCuenta.setTextColor(Color.RED);
            } else if("pagado".equalsIgnoreCase(cuenta.getEstado())){
                EstadoCuenta.setTextColor(Color.GREEN);
            }

            // --- CRUCE DE DATOS PARA BUSCAR NOMBRE ---
            String NombreCliente = "Cliente Desconocido";

            // 1. Buscar la venta asociada a esta cuenta
            venta VentaRelacionada = null;
            for(venta v : ListaDeVentas){
                if(v.getIdVenta() == cuenta.getIdVenta()){
                    VentaRelacionada = v;
                    break;
                }
            }

            // 2. Si encontramos venta, buscar el cliente asociado a esa venta
            if(VentaRelacionada != null){
                for(cliente c : ListaDeClientes){
                    // OJO: Verifica si en tu modelo cliente es getIdCliente() o getClienteId()
                    if(c.getIdCliente() == VentaRelacionada.getIdCliente()){
                        NombreCliente = c.getNombre();
                        break;
                    }
                }
            }

            ClienteNombre.setText(NombreCliente);
            // ------------------------------------------

            String finalNombreCliente = NombreCliente;
            VerInfo.setOnClickListener(v -> {
                InfoCuentaDialogFragment dialog = InfoCuentaDialogFragment.newInstance(
                        cuenta.getIdCuenta(),
                        cuenta.getIdVenta(),
                        cuenta.getFechaInicio(), // String directo
                        finalNombreCliente);
                dialog.show(getParentFragmentManager(), "infoCuentaDialog");
            });

            if (esAdmin){
                BorrarCuenta.setVisibility(View.VISIBLE);
                BorrarCuenta.setOnClickListener(v -> {
                    eliminarCuentaApi(cuenta.getIdCuenta());
                });
            } else {
                BorrarCuenta.setVisibility(View.GONE);
            }

            gridLayout.addView(card);
        }
    }

    private void eliminarCuentaApi(int idCuenta) {
        apiService.eliminarCuenta(idCuenta).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    mostrarMensaje("Cuenta eliminada");
                    cargarDatosDelServidor(); // Recargamos para actualizar la vista
                } else {
                    mostrarMensaje("Error al eliminar");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                mostrarMensaje("Error de red al eliminar");
            }
        });
    }

    private void mostrarMensaje(String msg){
        if(getContext() != null) Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }
}