package models;

public class detalleVenta {
    private int idDetalleVenta;
    private int idVenta;
    private int idProducto;
    private int cantidad;

    public detalleVenta(int idDetalleVenta, int idVenta, int idProducto, int cantidad){
        this.idDetalleVenta = idDetalleVenta;
        this.idVenta = idVenta;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
    }

    public int getIdDetalleVenta(){
        return idDetalleVenta;
    }

    public int getIdVenta(){
        return idVenta;
    }

    public int getIdProducto(){
        return idProducto;
    }

    public int getCantidad(){
        return cantidad;
    }

    public void setIdDetalleVenta(int idDetalleVenta) {
        this.idDetalleVenta = idDetalleVenta;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
