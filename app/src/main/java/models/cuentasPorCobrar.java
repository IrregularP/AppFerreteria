package models;


import java.time.LocalDate;

public class cuentasPorCobrar {
    private int idCuenta;
    private int idVenta;
    private LocalDate fechaInicio;
    private String estado;

    public cuentasPorCobrar(int idCuenta, int idVenta, LocalDate fechaInicio, String estado){
        this.idCuenta = idCuenta;
        this.idVenta = idVenta;
        this.fechaInicio = fechaInicio;
        this.estado = estado;
    }

    public int getIdCuenta(){
        return idCuenta;
    }

    public int getIdVenta(){
        return idVenta;
    }

    public LocalDate getFechaInicio(){
        return fechaInicio;
    }

    public String getEstado(){
        return estado;
    }

    public void setIdCuenta(int idCuenta){
        this.idCuenta = idCuenta;
    }

    public void setIdVenta(int idVenta){
        this.idVenta = idVenta;
    }

    public void setFechaInicio(LocalDate fechaInicio){
        this.fechaInicio = fechaInicio;
    }

    public void setEstado(String estado){
        this.estado = estado;
    }

}
