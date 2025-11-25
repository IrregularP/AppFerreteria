package mockdata;

import models.producto;

import java.util.Arrays;
import java.util.List;

public class productoFakeData {

    public static List<producto> getProductos(){
        return Arrays.asList(
                /*new producto(1, "Pintura Roja", "Rojo intenso", "1234", 100, 120, 20, 16, 50, 5, 101),
                new producto(2, "Pintura Azul", "Azul marino", "5678", 90, 108, 20, 16, 40, 5, 102),
                new producto(3, "Llave Inglesa", "Herramienta", "9876", 60, 72, 20, 16, 30, 8, 103)*/
        );
    }
}
