package com.example.appferreteria;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import models.producto;
import models.usuario;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import models.ApiService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductoDetalleDialogFragment extends DialogFragment {

    private OnProductoActualizadoListener listener;
    ApiService apiService;

    // IMPORTANTE: Usa la IP de tu servidor (PC), no localhost
    private static final String BASE_URL = "http://192.168.1.140:5000/";

    // Constantes de argumentos
    private static final String ARG_ID = "idProducto";
    private static final String ARG_NOMBRE = "nombre";
    private static final String ARG_DESCRIPCION = "descripcion";
    private static final String ARG_CODIGO_BARRAS = "codigoBarras";
    private static final String ARG_PRECIO_SIN_IVA = "precioSinIva";
    private static final String ARG_PRECIO_UNITARIO = "precioUnitario";
    private static final String ARG_PORCENTAJE_GANANCIA = "porcentajeGanancia";
    private static final String ARG_IVA = "iva";
    private static final String ARG_STOCK = "stock";
    private static final String ARG_CATEGORIA = "categoria";
    private static final String ARG_ID_PROVEEDOR = "idProveedor";

    public interface OnProductoActualizadoListener {
        void onProductoActualizado();
    }

    public void setOnProductoActualizadoListener(OnProductoActualizadoListener listener) {
        this.listener = listener;
    }

    public static ProductoDetalleDialogFragment newInstance(producto producto) {
        ProductoDetalleDialogFragment fragment = new ProductoDetalleDialogFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_ID, producto.getIdProducto());
        args.putString(ARG_NOMBRE, producto.getNombre());
        args.putString(ARG_DESCRIPCION, producto.getDescripcion());
        args.putString(ARG_CODIGO_BARRAS, producto.getCodigoBarras());
        args.putDouble(ARG_PRECIO_SIN_IVA, producto.getPrecioSinIva());
        args.putDouble(ARG_PRECIO_UNITARIO, producto.getPrecioUnitario());
        args.putDouble(ARG_PORCENTAJE_GANANCIA, producto.getPorcentajeGanancia());
        args.putDouble(ARG_IVA, producto.getIva());
        args.putInt(ARG_STOCK, producto.getStock());
        args.putInt(ARG_CATEGORIA, producto.getCategoria());
        args.putInt(ARG_ID_PROVEEDOR, producto.getIdProveedor());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null && getDialog().getWindow() != null) {
            int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.95);
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            getDialog().getWindow().setLayout(width, height);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // Configuración correcta de Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL) // Usamos la URL raíz
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);

        View view = inflater.inflate(R.layout.dialog_producto_detalle, container, false);

        // Vinculación de vistas
        TextView tvNombre = view.findViewById(R.id.tvDetalleNombre);
        TextView tvDescripcion = view.findViewById(R.id.tvDetalleDescripcion);
        TextView tvCodigoBarras = view.findViewById(R.id.tvDetalleCodigoBarras);
        TextView tvPrecioSinIva = view.findViewById(R.id.tvDetallePrecioSinIva);
        TextView tvPrecioUnitario = view.findViewById(R.id.tvDetallePrecioUnitario);
        TextView tvPorcentajeGanancia = view.findViewById(R.id.tvDetallePorcentajeGanancia);
        TextView tvIva = view.findViewById(R.id.tvDetalleIva);
        TextView tvStock = view.findViewById(R.id.tvDetalleStock);
        TextView tvCategoria = view.findViewById(R.id.tvDetalleCategoria);
        TextView tvIdProveedor = view.findViewById(R.id.tvDetalleIdProveedor);
        Button btnEliminarProducto = view.findViewById(R.id.btnEliminarProducto);
        Button btnModificarStock = view.findViewById(R.id.btnModificarStock);

        Bundle args = getArguments();
        if (args != null) {
            tvNombre.setText("Nombre: " + args.getString(ARG_NOMBRE));
            tvDescripcion.setText("Descripción: " + args.getString(ARG_DESCRIPCION));
            tvCodigoBarras.setText("Cód. Barras: " + args.getString(ARG_CODIGO_BARRAS));
            tvPrecioSinIva.setText(String.format("Precio s/IVA: $%.2f", args.getDouble(ARG_PRECIO_SIN_IVA)));
            tvPrecioUnitario.setText(String.format("Precio Unitario: $%.2f", args.getDouble(ARG_PRECIO_UNITARIO)));
            tvPorcentajeGanancia.setText(String.format("Ganancia: %.2f%%", args.getDouble(ARG_PORCENTAJE_GANANCIA)));
            tvIva.setText(String.format("IVA: %.2f%%", args.getDouble(ARG_IVA)));
            tvStock.setText("Stock: " + args.getInt(ARG_STOCK));
            tvCategoria.setText("Categoría ID: " + args.getInt(ARG_CATEGORIA));
            tvIdProveedor.setText("Proveedor ID: " + args.getInt(ARG_ID_PROVEEDOR));

            // Listeners
            btnModificarStock.setOnClickListener(v -> mostrarDialogoModificarStock(args.getInt(ARG_ID)));
            btnEliminarProducto.setOnClickListener(v -> eliminarProductoConfirmado(args.getInt(ARG_ID)));
        }

        // Control de permisos (Rol)
        usuario usuarioActual = SessionManager.getUsuario(getContext());
        if (usuarioActual != null && !usuarioActual.getRol().equalsIgnoreCase("administrador")) {
            btnEliminarProducto.setVisibility(View.GONE);
            // Centrar botón si solo queda uno
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) btnModificarStock.getLayoutParams();
            params.gravity = Gravity.CENTER_HORIZONTAL;
            btnModificarStock.setLayoutParams(params);
        } else {
            btnEliminarProducto.setVisibility(View.VISIBLE);
        }

        return view;
    }

    private void mostrarDialogoModificarStock(int idProducto) {
        EditText input = new EditText(getContext());
        input.setHint("Ingrese la nueva cantidad");
        input.setInputType(android.text.InputType.TYPE_CLASS_NUMBER); // Solo números

        new AlertDialog.Builder(getContext())
                .setTitle("Modificar Stock")
                .setView(input)
                .setPositiveButton("Actualizar", (dialog, which) -> {
                    String valor = input.getText().toString();
                    if(valor.isEmpty()) return;

                    int nuevoStock = Integer.parseInt(valor);

                    // Creamos el mapa con la clave "stock"
                    Map<String, Integer> body = new HashMap<>();
                    body.put("stock", nuevoStock);

                    apiService.actualizarStock(idProducto, body).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if(response.isSuccessful()){
                                Toast.makeText(getContext(), "Stock actualizado correctamente", Toast.LENGTH_SHORT).show();
                                if (listener != null) listener.onProductoActualizado();
                                dismiss();
                            } else {
                                Toast.makeText(getContext(), "Error al actualizar: " + response.code(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(getContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
                        }
                    });
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private void eliminarProductoConfirmado(int idProducto) {
        new AlertDialog.Builder(getContext())
                .setTitle("Confirmar eliminación")
                .setMessage("¿Estás seguro de que deseas eliminar este producto? Esta acción es irreversible.")
                .setPositiveButton("Eliminar", (dialog, which) -> {
                    apiService.eliminarProducto(idProducto).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(getContext(), "Producto eliminado", Toast.LENGTH_SHORT).show();
                                if (listener != null) listener.onProductoActualizado();
                                dismiss();
                            } else {
                                Toast.makeText(getContext(), "No se pudo eliminar: " + response.code(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(getContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
                        }
                    });
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }
}