package mockdata;

import java.time.LocalDate;
import java.util.List;
import models.cuentasPorCobrar;

public class cuentasPorCobrarFakeData {

    public static List<cuentasPorCobrar> getCuentas(){
        return List.of(
                new cuentasPorCobrar(1, 1, LocalDate.of(2024, 12, 12), "Pendiente"),
                new cuentasPorCobrar(2, 2, LocalDate.of(2023, 4, 13), "Pagado")
        );
    }

    public static void eliminarCuenta(int idCuenta){
        List<cuentasPorCobrar> cuentas = getCuentas();
        cuentas.removeIf(cuenta -> cuenta.getIdCuenta() == idCuenta);
    }
}
