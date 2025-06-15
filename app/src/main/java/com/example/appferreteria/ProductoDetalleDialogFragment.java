package com.example.appferreteria;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import models.producto;
import models.usuario;


public class ProductoDetalleDialogFragment extends DialogFragment {

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null && getDialog().getWindow() != null) {
            int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.95); // 95% del ancho de pantalla
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            getDialog().getWindow().setLayout(width, height);
        }
    }

    // Constantes para los argumentos
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_producto_detalle, container, false);

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

        Bundle args = getArguments();
        if (args != null) {
            tvNombre.setText("Nombre: " + args.getString(ARG_NOMBRE));
            tvDescripcion.setText("Descripción: " + args.getString(ARG_DESCRIPCION));
            tvCodigoBarras.setText("Código de Barras: " + args.getString(ARG_CODIGO_BARRAS));
            tvPrecioSinIva.setText("Precio sin IVA: " + args.getDouble(ARG_PRECIO_SIN_IVA));
            tvPrecioUnitario.setText("Precio Unitario: " + args.getDouble(ARG_PRECIO_UNITARIO));
            tvPorcentajeGanancia.setText("Porcentaje Ganancia: " + args.getDouble(ARG_PORCENTAJE_GANANCIA));
            tvIva.setText("IVA: " + args.getDouble(ARG_IVA));
            tvStock.setText("Stock: " + args.getInt(ARG_STOCK));
            tvCategoria.setText("Categoría: " + args.getInt(ARG_CATEGORIA));
            tvIdProveedor.setText("ID Proveedor: " + args.getInt(ARG_ID_PROVEEDOR));
        }

        Button btnModificarStock = view.findViewById(R.id.btnModificarStock);
        Button btnEliminarProducto = view.findViewById(R.id.btnEliminarProducto);
        LinearLayout botonesContenedor = view.findViewById(R.id.botonesContenedor);

        usuario usuarioActual = SessionManager.getUsuario(getContext());

        if (usuarioActual != null && !usuarioActual.getRol().equalsIgnoreCase("administrador")) {
            btnEliminarProducto.setVisibility(View.GONE);

            // Centrar el botón único si no es admin
            LinearLayout.LayoutParams soloModificarParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            soloModificarParams.gravity = Gravity.CENTER_HORIZONTAL;
            btnModificarStock.setLayoutParams(soloModificarParams);
        } else {
            btnEliminarProducto.setVisibility(View.VISIBLE);
        }
        return view;
    }
}