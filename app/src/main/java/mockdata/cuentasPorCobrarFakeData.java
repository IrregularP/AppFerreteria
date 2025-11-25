package mockdata;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import models.cuentasPorCobrar;

public class cuentasPorCobrarFakeData {

    public static List<cuentasPorCobrar> getCuentas(){
        List<cuentasPorCobrar> cuentas = new ArrayList<>();

        cuentas.add(new cuentasPorCobrar(159, 1234, LocalDate.of(2015, 8, 13), "Pendiente"));
        cuentas.add(new cuentasPorCobrar(160, 5678, LocalDate.of(2012, 12, 7), "Pagado"));
        cuentas.add(new cuentasPorCobrar(161, 9101, LocalDate.of(2010, 8, 15), "Pendiente"));

        return cuentas;
    }

    public static void eliminarCuenta(int idCuenta){
        List<cuentasPorCobrar> cuentas = getCuentas();
        cuentas.removeIf(cuenta -> cuenta.getIdCuenta() == idCuenta);
    }
}
