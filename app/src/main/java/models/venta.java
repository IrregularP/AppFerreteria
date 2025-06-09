package models;

import java.time.LocalDate;

public class venta {
    private int idVenta;
    private LocalDate fecha;
    private int idCliente;
    private int idTipoPago;

    public venta(int idVenta, LocalDate fecha, int idCliente, int idTipoPago) {
        this.idVenta = idVenta;
        this.fecha = fecha;
        this.idCliente = idCliente;
        this.idTipoPago = idTipoPago;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public int getIdTipoPago() {
        return idTipoPago;
    }
}
