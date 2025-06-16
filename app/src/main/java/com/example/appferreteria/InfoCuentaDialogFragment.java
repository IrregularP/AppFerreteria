package com.example.appferreteria;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;


import org.jspecify.annotations.NonNull;

import java.time.LocalDate;

public class InfoCuentaDialogFragment extends DialogFragment {

    private static final String ARG_ID_CUENTA ="id_cuenta";
    private static final String ARG_FECHA_INICIO = "fecha";

    public static InfoCuentaDialogFragment newInstance(int idCuenta, String fecha){
        InfoCuentaDialogFragment fragment = new InfoCuentaDialogFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_ID_CUENTA, idCuenta);
        args.putString(ARG_FECHA_INICIO, fecha);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        int idCuenta = getArguments().getInt(ARG_ID_CUENTA);
        String fecha = getArguments().getString(ARG_FECHA_INICIO);

        return new AlertDialog.Builder(requireContext()).setTitle("Información")
                .setMessage("Info de la Cuenta #"+ idCuenta + "\nFecha de Inicio: " + fecha)
                .setPositiveButton("Cerrar", (dialog, which) -> dialog.dismiss()).create();
    }
}
