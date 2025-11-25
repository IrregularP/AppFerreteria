package mockdata;

import java.util.Arrays;
import java.util.List;
import models.categoria;
public class categoriaFakeData {

    public static List<categoria> getCategorias(){
        return Arrays.asList(
                new categoria(1, "Taladros"),
                new categoria(2, "Martillos"),
                new categoria(3, "Clavos"),
                new categoria(4, "Llaves"),
                new categoria(5, "Pintura"),
                new categoria(6, "Aceites en Polvo"),
                new categoria(7, "Pulidoras"),
                new categoria(8, "Llave"),
                new categoria(9, "Lijas"),
                new categoria(10, "Sierras")
        );
    }
}
