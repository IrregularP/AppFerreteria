package models;

import com.google.gson.annotations.SerializedName;

public class producto {

    @SerializedName("id_producto")
    private int idProducto;

    @SerializedName("nombre")
    private String nombre;

    @SerializedName("descripcion")
    private String descripcion;

    @SerializedName("codigo_barras")
    private String codigoBarras;

    @SerializedName("precio_sin_iva")
    private String precioSinIva;

    @SerializedName("precio_unitario")
    private String precioUnitario;

    @SerializedName("porcentaje_ganancia")
    private String porcentajeGanancia;

    @SerializedName("iva")
    private String iva;

    @SerializedName("stock")
    private int stock;

    @SerializedName("categoria")
    private int categoria;

    @SerializedName("id_proveedor")
    private int idProveedor;

    // Getters
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
        return parseDouble(precioSinIva);
    }

    public double getPrecioUnitario() {
        return parseDouble(precioUnitario);
    }

    public double getPorcentajeGanancia() {
        return parseDouble(porcentajeGanancia);
    }

    public double getIva() {
        return parseDouble(iva);
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

    // Utilidad para convertir String a double de forma segura
    private double parseDouble(String valor) {
        try {
            return Double.parseDouble(valor);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
}