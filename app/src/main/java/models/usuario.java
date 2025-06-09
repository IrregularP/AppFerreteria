package models;

public class usuario {
    private String usuario;
    private String contraseña;
    private String rol;

    public usuario(String usuario, String contraseña, String rol){
        this.usuario = usuario;
        this.contraseña = contraseña;
        this.rol = rol;
    }

    public String getUsuario(){
        return usuario;
    }

    public String getContraseña(){
        return contraseña;
    }

    public String getRol(){
        return rol;
    }
}
