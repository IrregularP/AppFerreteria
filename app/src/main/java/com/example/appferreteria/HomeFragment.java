package com.example.appferreteria;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {
    GridLayout gridLayout;

    String[] nombres = {
            "Venta", "Inventario", "Clientes", "Proveedores",
            "Cuentas Por Cobrar", "Ventas Realizadas", "Estadisticas"
    };

    int[] imagenes = {
            R.drawable.venta, R.drawable.inventario, R.drawable.cliente,
            R.drawable.proveedor, R.drawable.cuentasporcobrar, R.drawable.ventasrealizadas,
            R.drawable.estadisticas
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        gridLayout = view.findViewById(R.id.menus);

        for(int i = 0; i < nombres.length; i++){
            View card = inflater.inflate(R.layout.item_card, gridLayout, false);

            TextView textView = card.findViewById(R.id.textView);
            ImageView imageView = card.findViewById(R.id.imageView);

            textView.setText(nombres[i]);
            imageView.setImageResource(imagenes[i]);

            gridLayout.addView(card);
        }
        return view;
    }
}
