package models;

public class tipoPago {
    private int idTipoPago;
    private String nombre;
    private String descripcion;

    public tipoPago(int idTipoPago, String nombre, String descripcion) {
        this.idTipoPago = idTipoPago;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public int getIdTipoPago() {
        return idTipoPago;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
