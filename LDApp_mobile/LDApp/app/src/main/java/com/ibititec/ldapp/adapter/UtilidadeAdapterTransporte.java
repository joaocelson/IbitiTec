package com.ibititec.ldapp.adapter;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ibititec.ldapp.ListComercianteActivity;
import com.ibititec.ldapp.R;
import com.ibititec.ldapp.models.UtilidadePublica;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UtilidadeAdapterTransporte extends BaseAdapter {

    public static final String TAG = "UTILIDADE";
    private Context context;
    private Activity activity;

    private ArrayList<UtilidadePublica> listaUtilidades;
    private final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 12;

    public UtilidadeAdapterTransporte(Context context, ArrayList<UtilidadePublica> listaUtilidades, Activity activity) {
        this.context = context;
        this.listaUtilidades = listaUtilidades;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return listaUtilidades.size();
    }

    @Override
    public Object getItem(int position) {
        return listaUtilidades.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        try {
            final UtilidadePublica utilidade = listaUtilidades.get(position);
            Log.i(ListComercianteActivity.TAG, "Vai setar o Adapter, número de registro: " + listaUtilidades.size() + " Position: " + position + " - Nome Utilidade " + utilidade.getNomeUtilidade());

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.adapter_utilidade_transporte, null);

            TextView nome = (TextView) layout.findViewById(R.id.txtNomeUtilidade);
            nome.setText(utilidade.getNomeUtilidade());

            TextView txtTelefoneUtilidade = (TextView) layout.findViewById(R.id.txtTelefoneUtilidade);
            txtTelefoneUtilidade.setText("Telefone: " + utilidade.getTelefoneUtilidade());

            if(nome.getText().equals("Taxi")) {
                TextView txt = (TextView) layout.findViewById(R.id.txt);
                txt.setVisibility(View.INVISIBLE);
            }

       //     ImageButton btnCall = (ImageButton) layout.findViewById(R.id.btn_image_button);
            ImageView ivCall = (ImageView) layout.findViewById(R.id.ivCall);

            ivCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    discar(v,utilidade);
                }
            });
            //Appodeal.show(activity, Appodeal.BANNER_BOTTOM);
            return layout;

        } catch (Exception e) {
            Log.i(UtilidadeAdapterTransporte.TAG, "Erro ao preecnher o getView: " + e.getMessage());

        }
        return convertView;
    }

    private void discar(View v, UtilidadePublica utilidade) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CALL_PHONE)) {
                showMessageOKCancel("Você precisa permitir acesso ao discador do telefone!",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(activity,new String[] {Manifest.permission.CALL_PHONE},
                                        MY_PERMISSIONS_REQUEST_CALL_PHONE);
                            }
                        });
                return;
            }
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CALL_PHONE},
                    MY_PERMISSIONS_REQUEST_CALL_PHONE);
            return;
        }
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + utilidade.getTelefoneUtilidade()));
        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(callIntent);

    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancelar", null)
                .create()
                .show();
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
       // activity.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CALL_PHONE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Map<String, Integer> perms = new HashMap<String, Integer>();
                    // Inicial
                    perms.put(Manifest.permission.CALL_PHONE, PackageManager.PERMISSION_GRANTED);
                } else {

                }
                return;
            }
        }
    }

}
