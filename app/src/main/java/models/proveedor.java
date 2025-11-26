package models;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable; // Para poder pasarlo al Dialog fácilmente

public class proveedor implements Serializable {

    @SerializedName("id_proveedor")
    private int idProveedor;

    @SerializedName("nombre")
    private String nombre;

    @SerializedName("direccion")
    private String direccion;

    @SerializedName("telefono")
    private String telefono;

    @SerializedName("correo")
    private String correo;

    // Constructor vacío
    public proveedor() {}

    // Getters
    public int getIdProveedor() { return idProveedor; }
    public String getNombre() { return nombre; }
    public String getDireccion() { return direccion; }
    public String getTelefono() { return telefono; }
    public String getCorreo() { return correo; }
}