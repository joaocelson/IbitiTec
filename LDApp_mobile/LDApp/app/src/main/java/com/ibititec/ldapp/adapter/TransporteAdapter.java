package com.ibititec.ldapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ibititec.ldapp.ListComercianteActivity;
import com.ibititec.ldapp.R;
import com.ibititec.ldapp.models.Transporte;

import java.util.ArrayList;

public class TransporteAdapter extends BaseAdapter {

    public static final String TAG = "TRANPORTE_ADAPTER";
    private Context context;
    private Activity activity;

    private ArrayList<Transporte> listaTransporte;
    private final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 12;

    public TransporteAdapter(Context context, ArrayList<Transporte> listaTransporte, Activity activity) {
        this.context = context;
        this.listaTransporte = listaTransporte;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return listaTransporte.size();
    }

    @Override
    public Object getItem(int position) {
        return listaTransporte.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        try {
            Log.i(ListComercianteActivity.TAG, "Vai setar o Tranporte Adapter, número de registro " + listaTransporte.size() + " Position" + position);

            final Transporte transporte = listaTransporte.get(position);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.activity_transporte_adapter, null);

            TextView txtTituloTranporteAdapter = (TextView) layout.findViewById(R.id.txtTituloTranporteAdapter);
            txtTituloTranporteAdapter.setText(transporte.getNomeTranporte());

            TextView txtTranporteAdapter1 = (TextView) layout.findViewById(R.id.txtTranporteAdapter1);
            txtTranporteAdapter1.setText(transporte.getTextoSemanal());

            TextView txtTranporteAdapter2 = (TextView) layout.findViewById(R.id.txtTranporteAdapter2);
            txtTranporteAdapter2.setText(transporte.getTextFds());

            //Appodeal.show(activity, Appodeal.BANNER_BOTTOM);
            return layout;
        } catch (Exception e) {
            Log.i(UtilidadeAdapter.TAG, "Erro ao preecnher o getView: " + e.getMessage());

        }
        return convertView;
    }
}
