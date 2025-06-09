package models;

public class cliente {
    public int idCliente;
    public String nombre;
    public String direccion;
    public String telefono;
    public String email;

    public cliente(int idCliente, String nombre, String direccion, String telefono, String email){
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
    }

    public int getIdCliente(){
        return idCliente;
    }

    public String getNombre(){
        return nombre;
    }

    public String getDireccion(){
        return direccion;
    }

    public String getTelefono(){
        return telefono;
    }

    public String getEmail(){
        return email;
    }
}
