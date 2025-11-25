package mockdata;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import models.venta;

public class ventaFakeData {

    public static List<venta> getVentas(){
        List<venta> ventas = new ArrayList<>();
        ventas.add(new venta(1234, LocalDate.of(2024, 8, 12),1, 1));
        ventas.add(new venta(5678, LocalDate.of(2025, 8, 12),2, 1));
        ventas.add(new venta(9101, LocalDate.of(2014, 1, 26), 3, 1));
        return ventas;
    }
}
