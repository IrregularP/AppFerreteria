package com.example.appferreteria;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    GridLayout gridLayout;
    BottomNavigationView navBottom;
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
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitymain);
        gridLayout = findViewById(R.id.menus);
        navBottom = findViewById(R.id.nav_bottom);

        LayoutInflater inflater = LayoutInflater.from(this);

        for(int i = 0; i < nombres.length; i++){
            View card = inflater.inflate(R.layout.item_card, gridLayout, false);

            TextView textView = card.findViewById(R.id.textView);
            ImageView imageView = card.findViewById(R.id.imageView);

            textView.setText(nombres[i]);
            imageView.setImageResource(imagenes[i]);

            gridLayout.addView(card);
        }

        navBottom.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.nav_home){
                return true;
            }else if(item.getItemId() == R.id.nav_user){
                Intent intent = new Intent(MainActivity.this, UsuarioActivity.class);
                startActivity(intent);
                return true;
            }else{
                return false;
            }
        });

    }
}
