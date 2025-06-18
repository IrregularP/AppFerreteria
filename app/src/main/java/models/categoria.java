package models;

public class categoria {
    private int idCategoria;
    private String nombre;

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
