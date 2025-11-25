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
    @GET("http://192.168.1.99:5000/productos")
    Call<List<producto>> getDatos();

    @GET("http://192.168.1.99:5000/categorias")
    Call<List<categoria>> getCategorias();

    @GET("productos/categoria/{id}")
    Call<List<producto>> getProductosPorCategoria(@Path("id") int idCategoria);
    @PUT("http://10.0.2.2:5000/productos/{id}/stock")
    Call<Void> actualizarStock(@Path("id") int idProducto, @Body Map<String, Integer> nuevoStock);

    @DELETE("{id}")
    Call<Void> eliminarProducto(@Path("id") int idProducto);
}
