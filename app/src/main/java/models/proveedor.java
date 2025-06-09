package models;

public class proveedor {
    private int idProveedor;
    private String nombre;
    private String direccion;
    private String telefono;
    private String correo;

    public proveedor(int idProveedor, String telefono, String correo, String direccion, String nombre) {
        this.idProveedor = idProveedor;
        this.telefono = telefono;
        this.correo = correo;
        this.direccion = direccion;
        this.nombre = nombre;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCorreo() {
        return correo;
    }
}
