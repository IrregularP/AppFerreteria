package models;

import com.google.gson.annotations.SerializedName; // <--- Importante

public class venta {

    @SerializedName("id_venta")
    private int idVenta;

    @SerializedName("fecha")
    private String fecha;

    @SerializedName("id_cliente")
    private int idCliente;

    @SerializedName("id_tipo_pago")
    private int idTipoPago;

    // Constructor vacío (necesario para Retrofit/GSON)
    public venta() {}

    public venta(int idVenta, String fecha, int idCliente, int idTipoPago) {
        this.idVenta = idVenta;
        this.fecha = fecha;
        this.idCliente = idCliente;
        this.idTipoPago = idTipoPago;
    }

    public int getIdVenta() { return idVenta; }
    public void setIdVenta(int idVenta) { this.idVenta = idVenta; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    public int getIdTipoPago() { return idTipoPago; }
    public void setIdTipoPago(int idTipoPago) { this.idTipoPago = idTipoPago; }
}