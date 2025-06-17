package com.example.appferreteria;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import models.usuario;

public class UsuarioFragment extends Fragment {

    TextView UsuarioConectado, RolUsuario;
    Button CerrarSesion;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_usuario, container, false);

        UsuarioConectado = view.findViewById(R.id.UsuarioConectado);
        RolUsuario = view.findViewById(R.id.RolUsuario);
        CerrarSesion = view.findViewById(R.id.CerrarSesion);

        usuario user = SessionManager.getUsuario(requireContext());

        if(user != null){
            UsuarioConectado.setText(user.getUsuario());
            RolUsuario.setText(user.getRol());
        }

        CerrarSesion.setOnClickListener(v ->{
            SessionManager.clearSesion(requireContext());

            Intent intent = new Intent(requireActivity(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        return view;
    }
}
