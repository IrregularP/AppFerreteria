package com.example.appferreteria;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import org.jspecify.annotations.NonNull;

import java.util.List;

import models.cliente;
import models.venta;

import mockdata.ventaFakeData;
import mockdata.clienteFakeData;

public class InfoCuentaDialogFragment extends DialogFragment {

    private static final String ARG_ID_CUENTA = "idCuenta";
    private static final String ARG_ID_VENTA = "idVenta";
    private static final String ARG_FECHA_INICIO = "fechaInicio";
    private static final String ARG_CLIENTE_NOMBRE = "nombreCliente";


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

        int idCuenta = args != null ? args.getInt(ARG_ID_CUENTA) : -1;
        int idVenta = args != null ? args.getInt(ARG_ID_VENTA) : -1;
        String fechaInicio = args != null ? args.getString(ARG_FECHA_INICIO) : "Sin Fecha";
        String nombreCliente = args != null ? args.getString(ARG_CLIENTE_NOMBRE) : "Sin Cliente";

        String mensaje = "Cliente: " + nombreCliente +
                         "\nID de Cuenta: " + idCuenta +
                         "\nID de Venta: " + idVenta +
                         "\nFecha de Inicio: " + fechaInicio;

        //Buscar venta
        venta VentaEncontrada = null;
        for(venta Venta : obtenerVentas()){
            if(Venta.getIdVenta() == idVenta){
                VentaEncontrada = Venta;
                break;
            }
        }

        //Buscar cliente
        String NombreCliente = "Desconocido";
        if(VentaEncontrada != null){
            int idCliente = VentaEncontrada.getIdCliente();
            for(cliente Cliente : obtenerClientes()){
                if(Cliente.idCliente == idCliente){
                    NombreCliente = Cliente.nombre;
                    break;
                }
            }
        }

        return new AlertDialog.Builder(requireContext())
                .setTitle("Información de Cuenta")
                .setMessage(mensaje)
                .setPositiveButton("Cerrar", (dialog, which) -> dialog.dismiss())
                .create();
    }

    private List<venta> obtenerVentas(){
        //CAMBIAR POR LO DEL API
        return ventaFakeData.getVentas();
    }

    private List<cliente> obtenerClientes(){
        //CAMBIAR POR LO DEL API
        return clienteFakeData.getClientes();
    }
}
