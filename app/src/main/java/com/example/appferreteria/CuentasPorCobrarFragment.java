package com.example.appferreteria;

import android.content.Intent;
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
import models.cuentasPorCobrar;
import models.usuario;

import mockdata.cuentasPorCobrarFakeData;

public class CuentasPorCobrarFragment extends Fragment {

    GridLayout gridLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_cuentasporcobrar, container, false);

        gridLayout = view.findViewById(R.id.cuentas);
        Button btnEliminarCuenta = view.findViewById(R.id.BtnEliminarCuenta);
        usuario usuarioActual = SessionManager.getUsuario(getContext());

        if(usuarioActual != null && !usuarioActual.getRol().equalsIgnoreCase("administrador")){
            btnEliminarCuenta.setVisibility(View.GONE);
        }else{
            btnEliminarCuenta.setVisibility(View.VISIBLE);
        }

        List <cuentasPorCobrar> listaCuentas = obtenerCuentas();

        for(cuentasPorCobrar cuenta : listaCuentas){
            View card = inflater.inflate(R.layout.item_card_cuentas, gridLayout, false);
            
            Button VerInfo = card.findViewById(R.id.btnVerInfo);
            TextView ClienteCuenta = card.findViewById(R.id.ClienteDeLaCuenta);
            TextView NoCuenta = card.findViewById(R.id.numCuenta);
            TextView EstadoCuenta = card.findViewById(R.id.EstadoCuenta);

            NoCuenta.setText("Cuenta #" + cuenta.getIdCuenta());
            EstadoCuenta.setText(cuenta.getEstado());

            if (cuenta.getEstado().equalsIgnoreCase("pendiente")){
                EstadoCuenta.setTextColor(Color.RED);
            }else if (cuenta.getEstado().equalsIgnoreCase("pagado")){
                EstadoCuenta.setTextColor(Color.GREEN);
            }

            gridLayout.addView(card);
        }

        return view;
    }

    private List<cuentasPorCobrar> obtenerCuentas(){
       //CAMBIAR POR LO DE LA API
       return cuentasPorCobrarFakeData.getCuentas();
    }

}
