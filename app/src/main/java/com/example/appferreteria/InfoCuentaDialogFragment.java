package com.example.appferreteria;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import org.jspecify.annotations.NonNull;

public class InfoCuentaDialogFragment extends DialogFragment {

    private static final String ARG_ID_CUENTA = "idCuenta";
    private static final String ARG_ID_VENTA = "idVenta";
    private static final String ARG_FECHA_INICIO = "fechaInicio";
    private static final String ARG_CLIENTE_NOMBRE = "nombreCliente";

    // Mantenemos el newInstance igual, ya que recibe los datos listos desde el Fragment padre
    public static InfoCuentaDialogFragment newInstance(int idCuenta, int idVenta, String fechaInicio, String nombreCliente){
        InfoCuentaDialogFragment fragment = new InfoCuentaDialogFragment();
        Bundle args = new Bundle();

        args.putInt(ARG_ID_CUENTA, idCuenta);
        args.putInt(ARG_ID_VENTA, idVenta);
        args.putString(ARG_FECHA_INICIO, fechaInicio);
        args.putString(ARG_CLIENTE_NOMBRE, nombreCliente);

        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){

        Bundle args = getArguments();

        // Recuperamos los datos que nos pasó CuentasPorCobrarFragment
        int idCuenta = args != null ? args.getInt(ARG_ID_CUENTA) : -1;
        int idVenta = args != null ? args.getInt(ARG_ID_VENTA) : -1;
        String fechaInicio = args != null ? args.getString(ARG_FECHA_INICIO) : "Sin Fecha";
        String nombreCliente = args != null ? args.getString(ARG_CLIENTE_NOMBRE) : "Desconocido";

        // Construimos el mensaje directamente.
        // NO es necesario volver a buscar en listas porque 'nombreCliente' ya viene calculado con datos reales.
        String mensaje = "Cliente: " + nombreCliente +
                "\nID de Cuenta: " + idCuenta +
                "\nID de Venta: " + idVenta +
                "\nFecha de Inicio: " + fechaInicio;

        return new AlertDialog.Builder(requireContext())
                .setTitle("Información de Cuenta")
                .setMessage(mensaje)
                .setPositiveButton("Cerrar", (dialog, which) -> dialog.dismiss())
                .create();
    }

    // Se eliminaron los métodos 'obtenerVentas' y 'obtenerClientes'
    // y sus imports porque ya no se necesitan.
}