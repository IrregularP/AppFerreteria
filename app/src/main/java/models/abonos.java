package models;
import java.time.LocalDate;

public class abonos {
    private int idAbono;
    private int idCuentaPorCobrar;
    private LocalDate fecha;
    private double monto;

    public abonos(int idAbono, int idCuentaPorCobrar, LocalDate fecha, double monto){
        this.idAbono = idAbono;
        this.idCuentaPorCobrar = idCuentaPorCobrar;
        this.fecha = fecha;
        this.monto = monto;
    }

    public int getIdAbono(){
        return idAbono;
    }

    public int getIdCuentaPorCobrar(){
        return idCuentaPorCobrar;
    }

    public LocalDate getFecha(){
        return fecha;
    }

    public double getMonto(){
        return monto;
    }

    public void setIdAbono(int idAbono) {
        this.idAbono = idAbono;
    }

    public void setIdCuentaPorCobrar(int idCuentaPorCobrar) {
        this.idCuentaPorCobrar = idCuentaPorCobrar;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }
}
