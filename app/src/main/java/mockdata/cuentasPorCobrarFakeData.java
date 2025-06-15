package mockdata;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import models.cuentasPorCobrar;
import models.venta;

public class cuentasPorCobrarFakeData {

    public static List<cuentasPorCobrar> getCuentas(){
        List<cuentasPorCobrar> cuentas = new ArrayList<>();

        cuentas.add(new cuentasPorCobrar(1, 1000, LocalDate.of(2024, 8, 18), "Pendiente"));
        cuentas.add(new cuentasPorCobrar(2, 1001, LocalDate.of(2023, 12, 12), "Pagado"));
        cuentas.add(new cuentasPorCobrar(3, 1002, LocalDate.of(2022, 4, 15), "Pendiente"));
        cuentas.add(new cuentasPorCobrar(4, 1003, LocalDate.of(2025, 1, 12), "Pagado"));
        cuentas.add(new cuentasPorCobrar(5, 1004, LocalDate.of(2024, 12, 23), "Pendiente"));
        cuentas.add(new cuentasPorCobrar(6, 1005, LocalDate.of(2024, 8, 18), "Pendiente"));
        cuentas.add(new cuentasPorCobrar(7, 1006, LocalDate.of(2023, 12, 12), "Pagado"));
        cuentas.add(new cuentasPorCobrar(8, 1007, LocalDate.of(2022, 4, 15), "Pendiente"));
        cuentas.add(new cuentasPorCobrar(9, 1008, LocalDate.of(2025, 1, 12), "Pagado"));
        cuentas.add(new cuentasPorCobrar(10, 1009, LocalDate.of(2024, 12, 23), "Pendiente"));

        return cuentas;
    }
}
