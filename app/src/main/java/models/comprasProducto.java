package models;

import java.time.LocalDate;

public class comprasProducto {
    private int idCompra;
    private LocalDate fechaCompra;
    private int idProducto;
    private int idProveedor;
    private int cantidadAdquirida;
    private double precioCompra;

    public comprasProducto(int idCompra, LocalDate fechaCompra, int idProducto, int idProveedor, int cantidadAdquirida, double precioCompra){
        this.idCompra = idCompra;
        this.fechaCompra = fechaCompra;
        this.idProducto = idProducto;
        this.idProveedor = idProveedor;
        this.cantidadAdquirida = cantidadAdquirida;
        this.precioCompra = precioCompra;
    }

    public int getIdCompra(){
        return idCompra;
    }

    public LocalDate getFechaCompra(){
        return fechaCompra;
    }

    public int getIdProducto(){
        return idProducto;
    }

    public int getIdProveedor(){
        return idProveedor;
    }

    public int getCantidadAdquirida(){
        return cantidadAdquirida;
    }

    public double getPrecioCompra(){
        return precioCompra;
    }

    public void setIdCompra(int idCompra) {
        this.idCompra = idCompra;
    }

    public void setFechaCompra(LocalDate fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public void setPrecioCompra(double precioCompra) {
        this.precioCompra = precioCompra;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public void setCantidadAdquirida(int cantidadAdquirida) {
        this.cantidadAdquirida = cantidadAdquirida;
    }
}
