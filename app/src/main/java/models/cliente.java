package models;

import com.google.gson.annotations.SerializedName; // <--- NO OLVIDAR

public class cliente {

    @SerializedName("id_cliente") // <--- SI ESTE FALTA, EL ID ES 0
    private int idCliente;

    @SerializedName("nombre")
    private String nombre;

    @SerializedName("direccion")
    private String direccion;

    // Constructor vacío
    public cliente() {}

    // Getters
    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}