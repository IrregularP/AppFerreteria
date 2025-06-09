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
}
