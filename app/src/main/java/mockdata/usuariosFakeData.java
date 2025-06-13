package mockdata;

import java.util.ArrayList;
import java.util.List;
import models.usuario;

public class usuariosFakeData {

    public static List<usuario> getUsuarios(){
        List<usuario> usuarios = new ArrayList<>();

        usuarios.add(new usuario("admin", "1234", "Administrador"));
        usuarios.add(new usuario("juan", "abcd", "Administrador"));
        usuarios.add(new usuario("maria", "5678", "Vendedor"));
        usuarios.add(new usuario("lucia", "pass", "Vendedor"));

        return usuarios;
    }
}
