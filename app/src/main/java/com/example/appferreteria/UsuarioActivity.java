package com.example.appferreteria;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class UsuarioActivity extends AppCompatActivity {

    BottomNavigationView navBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usuariolayout);

        navBottom = findViewById(R.id.nav_bottom);
        navBottom.setSelectedItemId(R.id.nav_user);

        navBottom.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.nav_home){
                Intent intent = new Intent(UsuarioActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                return true;
            }else if(item.getItemId() == R.id.nav_user) {
                return true;
            }else{
                return false;
            }
        });

    }
}
