package models;

import com.google.gson.annotations.SerializedName;

public class cuentasPorCobrar {

    @SerializedName("id_cuenta")
    private int idCuenta;

    @SerializedName("id_venta")
    private int idVenta;

    @SerializedName("fecha_inicio")
    private String fechaInicio;

    @SerializedName("estado")
    private String estado;

    // Constructor vacío
    public cuentasPorCobrar() {}

    // Getters y Setters
    public int getIdCuenta() { return idCuenta; }
    public void setIdCuenta(int idCuenta) { this.idCuenta = idCuenta; }

    public int getIdVenta() { return idVenta; }
    public void setIdVenta(int idVenta) { this.idVenta = idVenta; }

    public String getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(String fechaInicio) { this.fechaInicio = fechaInicio; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}