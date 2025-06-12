package com.example.appferreteria;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity{
    BottomNavigationView navBottom;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        /*navBottom = findViewById(R.id.nav_bottom);
        fragmentManager = getSupportFragmentManager();

        if(savedInstanceState == null){
            loadFragment(new UsuarioFragment());
            navBottom.setSelectedItemId(R.id.nav_user);
        }

        navBottom.setOnItemSelectedListener(item -> {
            Fragment fragment = null;

            if(item.getItemId() == R.id.nav_home){
                fragment = new HomeFragment();
            }else if(item.getItemId() == R.id.nav_user){
                fragment = new UsuarioFragment();
            }

            if(fragment != null){
                loadFragment(fragment);
                return true;
            }
            return false;
        });*/
    }

    private void loadFragment(Fragment fragment){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }
}