package models;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {
    @GET("http://192.168.1.140:5000/productos")
    Call<List<producto>> getDatos();

    @GET("http://192.168.1.140:5000/categorias")
    Call<List<categoria>> getCategorias();

    @GET("productos/categoria/{id}")
    Call<List<producto>> getProductosPorCategoria(@Path("id") int idCategoria);
    @PUT("productos/{id}")
    Call<Void> actualizarStock(@Path("id") int idProducto, @Body Map<String, Integer> data);

    // ELIMINAR PRODUCTO
    @DELETE("productos/{id}")
    Call<Void> eliminarProducto(@Path("id") int idProducto);

    @GET("cuentas") // Coincide con el url_prefix de app.py
    Call<List<cuentasPorCobrar>> getCuentasPorCobrar();

    @DELETE("cuentas/{id}")
    Call<Void> eliminarCuenta(@Path("id") int idCuenta);

    // --- Necesarios para cruzar la información ---
    @GET("ventas") // Asumiendo que tienes este endpoint
    Call<List<venta>> getVentas();

    @GET("clientes") // Asumiendo que tienes este endpoint
    Call<List<cliente>> getClientes();

    @GET("proveedores") // Asegúrate de haber registrado este blueprint en Flask
    Call<List<proveedor>> getProveedores();
}
