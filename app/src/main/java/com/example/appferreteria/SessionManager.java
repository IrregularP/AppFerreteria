package com.example.appferreteria;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import models.usuario;

public class SessionManager {

    private static final String name_session = "mi_sesion";
    private static final String key_user = "usuario_actual";

    public static void guardarUsuario(Context context, usuario user){
        SharedPreferences prefs = context.getSharedPreferences(name_session, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        Gson gson = new Gson();
        String userJson = gson.toJson(user);

        editor.putString(key_user, userJson);
        editor.apply();
    }

    public static usuario getUsuario(Context context){
        SharedPreferences prefs = context.getSharedPreferences(name_session, Context.MODE_PRIVATE);
        String userJson = prefs.getString(key_user, null);

        if(userJson != null){
            Gson gson = new Gson();
            return gson.fromJson(userJson, usuario.class);
        }

        return  null;
    }

    public static void clearSesion(Context context){
        SharedPreferences prefs = context.getSharedPreferences(name_session, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();
    }
}