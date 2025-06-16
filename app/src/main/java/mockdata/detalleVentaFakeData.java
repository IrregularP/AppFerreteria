package mockdata;

import java.util.ArrayList;
import java.util.List;
import models.detalleVenta;

public class detalleVentaFakeData {

    public static List<detalleVenta> getDetalles(){
        List<detalleVenta> detalles = new ArrayList<>();
        detalles.add(new detalleVenta(1, 1000, 1, 2));
        detalles.add(new detalleVenta(2, 1030, 2, 1));
        return detalles;
    }
}
