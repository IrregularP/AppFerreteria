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
            "Inventario", "Proveedores", "Categorías",
            "Cuentas Por Cobrar", "Estadísticas"
    };

    int[] imagenes = {
            R.drawable.inventario, R.drawable.proveedor, R.drawable.categoria,
            R.drawable.cuentasporcobrar, R.drawable.estadisticas
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

            int seleccion = i;
            card.setOnClickListener(v ->{
                Fragment fragment = null;

                switch (seleccion){
                    case 0:
                        fragment = new InventarioFragment();
                        break;
                    case 1:
                        fragment = new ProveedoresFragment();
                        break;
                    case 2:
                        fragment = new CategoriaFragment();
                        break;
                    case 3:
                        fragment = new CuentasPorCobrarFragment();
                        break;
                    case 4:
                        fragment = new EstadisticasFragment();
                        break;
                }

                if(fragment != null){
                    requireActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
                }
            });

            gridLayout.addView(card);
        }
        return view;
    }
}
