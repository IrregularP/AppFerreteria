package com.example.appferreteria;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import mockdata.usuariosFakeData;
import models.usuario;

public class LoginActivity extends AppCompatActivity {

    EditText editUsuario, editContraseña;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        editUsuario = findViewById(R.id.TextUsuario);
        editContraseña = findViewById(R.id.TextContraseña);
        btnLogin = findViewById(R.id.btnLogin);

        usuario UsuarioActivo = SessionManager.getUsuario(getApplicationContext());

        if(UsuarioActivo != null){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }

        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String userInput = editUsuario.getText().toString().trim();
                String passInput = editContraseña.getText().toString().trim();

                if(validarUsuario(userInput, passInput)){
                    Toast.makeText(LoginActivity.this, "Inicio de Sesión Exitoso", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(LoginActivity.this, "Usuario o Contraseña Incorrectos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validarUsuario(String usuarioInput, String contraseñaInput){
        //PONER LA LOGICA DEL API
        List<usuario> listUsuarios = usuariosFakeData.getUsuarios();

        for(usuario u : listUsuarios){
            if(u.getUsuario().equals(usuarioInput) && u.getContraseña().equals(contraseñaInput)){
                SessionManager.guardarUsuario(getApplicationContext(), u);
                return true;
            }
        }
        return false;
    }
}
