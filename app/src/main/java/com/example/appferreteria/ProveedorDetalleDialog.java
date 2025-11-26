package com.example.appferreteria;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import models.proveedor;

public class ProveedorDetalleDialog extends DialogFragment {

    private static final String ARG_PROVEEDOR = "proveedorObj";

    public static ProveedorDetalleDialog newInstance(proveedor p) {
        ProveedorDetalleDialog fragment = new ProveedorDetalleDialog();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PROVEEDOR, p);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        proveedor p = (proveedor) getArguments().getSerializable(ARG_PROVEEDOR);

        String mensaje = "Sin información";
        String titulo = "Detalles";

        if (p != null) {
            titulo = p.getNombre();
            mensaje = "ID: " + p.getIdProveedor() + "\n\n" +
                    "Teléfono: " + p.getTelefono() + "\n" +
                    "Correo: " + p.getCorreo() + "\n" +
                    "Dirección: " + p.getDireccion();
        }

        return new AlertDialog.Builder(requireContext())
                .setTitle(titulo)
                .setMessage(mensaje)
                .setPositiveButton("Cerrar", (dialog, which) -> dialog.dismiss())
                .create();
    }
}