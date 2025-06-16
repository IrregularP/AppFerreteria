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
import java.util.ArrayList;
import java.util.List;
import models.cuentasPorCobrar;
import models.usuario;
import mockdata.cuentasPorCobrarFakeData;

public class CuentasPorCobrarFragment extends Fragment {

    GridLayout gridLayout;
    LayoutInflater globalInflater;
    List<cuentasPorCobrar> listaCuentas;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_cuentasporcobrar, container, false);

        globalInflater = inflater;
        gridLayout = view.findViewById(R.id.cuentas);

        listaCuentas = new ArrayList<>(obtenerCuentas());
        renderizarCuentas();

        return view;

    }

    private List<cuentasPorCobrar> obtenerCuentas(){
       //CAMBIAR POR LO DE LA API
       return cuentasPorCobrarFakeData.getCuentas();
    }

    private void renderizarCuentas(){
        gridLayout.removeAllViews();

        usuario UsuarioActual = SessionManager.getUsuario(getContext());
        boolean esAdmin = UsuarioActual != null && UsuarioActual.getRol().equalsIgnoreCase("administrador");

        for (cuentasPorCobrar cuenta : listaCuentas){
            View card = globalInflater.inflate(R.layout.item_card_cuentas, gridLayout, false);

            TextView NoCuenta = card.findViewById(R.id.numCuenta);
            TextView EstadoCuenta = card.findViewById(R.id.EstadoCuenta);
            Button VerInfo = card.findViewById(R.id.btnVerInfo);
            Button BorrarCuenta = card.findViewById(R.id.BorrarCuenta);

            NoCuenta.setText("Cuenta #" + cuenta.getIdCuenta());
            EstadoCuenta.setText(cuenta.getEstado());

            if(cuenta.getEstado().equalsIgnoreCase("pendiente")){
                EstadoCuenta.setTextColor(Color.RED);
            }else if(cuenta.getEstado().equalsIgnoreCase("pagado")){
                EstadoCuenta.setTextColor(Color.GREEN);
            }

            VerInfo.setOnClickListener(v -> {
                InfoCuentaDialogFragment dialog = InfoCuentaDialogFragment.newInstance(
                        cuenta.getIdCuenta(), cuenta.getFechaInicio().toString());
                dialog.show(getParentFragmentManager(), "infoCuentaDialog");
            });

            if (esAdmin){
                BorrarCuenta.setVisibility(View.VISIBLE);
                BorrarCuenta.setOnClickListener(v -> {
                    listaCuentas.remove(cuenta);
                    renderizarCuentas();
                });
            }else{
                BorrarCuenta.setVisibility(View.GONE);
            }

            gridLayout.addView(card);
        }
    }
}
