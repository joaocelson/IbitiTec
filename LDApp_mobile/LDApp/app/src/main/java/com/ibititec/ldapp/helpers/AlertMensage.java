package com.ibititec.ldapp.helpers;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by pedro on 11/02/16.
 */
public class AlertMensage {
    public static void setMessageAlert(String mensage, Activity activity, String tipoMensagem) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(mensage)
                .setTitle(tipoMensagem);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
