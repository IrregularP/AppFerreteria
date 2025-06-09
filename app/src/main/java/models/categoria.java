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
}
