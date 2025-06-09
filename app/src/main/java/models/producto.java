package models;

public class producto {
    private int idProducto;
    private String nombre;
    private String descripcion;
    private String codigoBarras;
    private double precioSinIva;
    private double precioUnitario;
    private double porcentajeGanancia;
    private double iva;
    private int stock;
    private int categoria;
    private int idProveedor;

    public producto(int idProducto, String nombre, String descripcion, String codigoBarras, double precioSinIva, double precioUnitario, double porcentajeGanancia, double iva, int stock, int categoria, int idProveedor){
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.codigoBarras = codigoBarras;
        this.precioSinIva = precioSinIva;
        this.precioUnitario = precioUnitario;
        this.porcentajeGanancia = porcentajeGanancia;
        this.iva = iva;
        this.stock = stock;
        this.categoria = categoria;
        this.idProveedor = idProveedor;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public double getPrecioSinIva() {
        return precioSinIva;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public double getPorcentajeGanancia() {
        return porcentajeGanancia;
    }

    public double getIva() {
        return iva;
    }

    public int getStock() {
        return stock;
    }

    public int getCategoria() {
        return categoria;
    }

    public int getIdProveedor() {
        return idProveedor;
    }
}
