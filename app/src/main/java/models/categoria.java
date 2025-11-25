package models;

import com.google.gson.annotations.SerializedName; // <--- 1. IMPORTANTE: Agrega esta importación

public class categoria {

    // 2. IMPORTANTE: Esta etiqueta le dice a Java:
    // "Cuando recibas 'id_categoria' del servidor, guárdalo en esta variable"
    @SerializedName("id_categoria")
    private int idCategoria;

    @SerializedName("nombre") // Buena práctica ponerlo también aquí, aunque coincida
    private String nombre;

    // Constructor vacío (necesario a veces para GSON)
    public categoria() {}

    public categoria(int idCategoria, String nombre){
        this.idCategoria = idCategoria;
        this.nombre = nombre;
    }

    public int getIdCategoria(){
        return idCategoria;
    }

    public String getNombre(){
        return nombre;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}