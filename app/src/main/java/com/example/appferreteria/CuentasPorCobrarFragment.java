package com.example.appferreteria;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

import java.util.List;

import models.venta;
import models.cliente;
import models.cuentasPorCobrar;
import models.usuario;

import mockdata.ventaFakeData;
import mockdata.clienteFakeData;
import mockdata.cuentasPorCobrarFakeData;

public class CuentasPorCobrarFragment extends Fragment {

    GridLayout gridLayout;
    LayoutInflater globalInflater;
    List<cuentasPorCobrar> ListadeCuentas;
    List<venta> ListaDeVentas;
    List<cliente> ListaDeClientes;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_cuentasporcobrar, container, false);

        globalInflater = inflater;
        gridLayout = view.findViewById(R.id.cuentas);

        ListadeCuentas = obtenerCuentas();
        ListaDeVentas = obtenerVentas();
        ListaDeClientes = obtenerClientes();

        renderizarCuentas();

        return view;
    }

    private List<venta> obtenerVentas(){
        //CAMBIAR POR LO DEL API
        return ventaFakeData.getVentas();
    }

    private List<cliente> obtenerClientes(){
        //CAMBIAR POR LO DEL API
        return clienteFakeData.getClientes();
    }

    private List<cuentasPorCobrar> obtenerCuentas(){
        //CAMBIAR POR LO DEL API
        return cuentasPorCobrarFakeData.getCuentas();
    }


    private void renderizarCuentas(){
        gridLayout.removeAllViews();

        usuario UsuarioActual = SessionManager.getUsuario(getContext());
        boolean esAdmin = UsuarioActual != null && UsuarioActual.getRol().equalsIgnoreCase("administrador");

        for (cuentasPorCobrar cuenta : ListadeCuentas){
            View card = globalInflater.inflate(R.layout.item_card_cuentas, gridLayout, false);

            TextView NoCuenta = card.findViewById(R.id.numCuenta);
            TextView EstadoCuenta = card.findViewById(R.id.EstadoCuenta);
            TextView ClienteNombre = card.findViewById(R.id.ClienteDeLaCuenta);
            Button VerInfo = card.findViewById(R.id.btnVerInfo);
            Button BorrarCuenta = card.findViewById(R.id.BorrarCuenta);

            //Mostrar numero de cuenta
            NoCuenta.setText("Cuenta #" + cuenta.getIdCuenta());

            //Mostar estado
            EstadoCuenta.setText(cuenta.getEstado());
            if(cuenta.getEstado().equalsIgnoreCase("pendiente")){
                EstadoCuenta.setTextColor(Color.RED);
            }else if(cuenta.getEstado().equalsIgnoreCase("pagado")){
                EstadoCuenta.setTextColor(Color.GREEN);
            }

            //Buscar venta relacionada
            venta VentaRelacionada = null;
            for(venta ventaObj : ListaDeVentas){
                if(ventaObj.getIdVenta() == cuenta.getIdVenta()){
                    VentaRelacionada = ventaObj;
                    break;
                }
            }

            //Buscar cliente a partir de la venta
            String NombreCliente = "Cliente Desconocido";
            if(VentaRelacionada != null){
                for (cliente Cliente : ListaDeClientes){
                    if(Cliente.idCliente == VentaRelacionada.getIdCliente()){
                        NombreCliente = Cliente.getNombre();
                        break;
                    }
                }
            }

            ClienteNombre.setText(NombreCliente);

            String finalNombreCliente = NombreCliente;
            VerInfo.setOnClickListener(v -> {
                InfoCuentaDialogFragment dialog = InfoCuentaDialogFragment.newInstance(
                        cuenta.getIdCuenta(),
                        cuenta.getIdVenta(),
                        cuenta.getFechaInicio().toString(),
                        finalNombreCliente);
                dialog.show(getParentFragmentManager(), "infoCuentaDialog");
            });

            if (esAdmin){
                BorrarCuenta.setVisibility(View.VISIBLE);
                BorrarCuenta.setOnClickListener(v -> {
                    ListadeCuentas.remove(cuenta);
                    renderizarCuentas();
                });
            }else{
                BorrarCuenta.setVisibility(View.GONE);
            }

            gridLayout.addView(card);
        }
    }
}
