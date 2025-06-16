package mockdata;

import java.util.ArrayList;
import java.util.List;
import models.cliente;

public class clienteFakeData {

    public static List<cliente> getClientes(){
        List<cliente> clientes = new ArrayList<>();
        clientes.add(new cliente(1, "Pedro", "Calle 1", "1243343", "Pedro@gmail.com"));
        clientes.add(new cliente(2, "Juan", "Calle Miguel", "4492121232", "juanito123@gmail.com"));
        return clientes;
    }
}
