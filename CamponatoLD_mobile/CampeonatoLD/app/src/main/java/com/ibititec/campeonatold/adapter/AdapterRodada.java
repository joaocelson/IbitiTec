package com.ibititec.campeonatold.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ibititec.campeonatold.MainActivity;
import com.ibititec.campeonatold.R;
import com.ibititec.campeonatold.modelo.Rodada;

import java.util.List;

public class AdapterRodada extends BaseAdapter {

    private List<Rodada> rodadaList= null;
    private Activity activity;

    public  AdapterRodada(Activity activityParam, List<Rodada> rodadaListParam){
        this.rodadaList = rodadaListParam;
        this.activity = activityParam;
    }

    public  AdapterRodada(){}

    @Override
    public int getCount() {
        return rodadaList.size();
    }

    @Override
    public Object getItem(int position) {
        return rodadaList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            final Rodada rodadaObj = rodadaList.get(position);
            Log.i(MainActivity.TAG, "Vai setar o Adapter, número de registro: " + rodadaList.size() + " Position: " + position + " - Nome Rodada " + rodadaObj.getNumero());

            LayoutInflater inflater = (LayoutInflater) activity.getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.adapter_rodada, null);

            TextView rodada = (TextView) layout.findViewById(R.id.txtRodada);
            TextView Data = (TextView) layout.findViewById(R.id.txtData);
            TextView Campo = (TextView) layout.findViewById(R.id.txtCampo);
            TextView hrJogo1 = (TextView) layout.findViewById(R.id.txtHrJogo1);
            TextView jogo1 = (TextView) layout.findViewById(R.id.txtJogo1);
            TextView hrJogo2 = (TextView) layout.findViewById(R.id.txtHrJogo2);
            TextView jogo2 = (TextView) layout.findViewById(R.id.txtJogo2);

            rodada.setText("RODADA: " + rodadaObj.getNumero() + " - ");
            Data.setText("DATA: " + rodadaObj.getData());
            Campo.setText("CAMPO: " + rodadaObj.getCampo());
            hrJogo1.setText("Hr: " + rodadaObj.getHoraJogo1());
            jogo1.setText(rodadaObj.getJogo1());
            hrJogo2.setText("Hr: " + rodadaObj.getHoraJogo2());
            jogo2.setText(rodadaObj.getJogo2());

            //Appodeal.show(activity, Appodeal.BANNER_BOTTOM);
            return layout;

        } catch (Exception e) {
            Log.i(MainActivity.TAG, "Erro ao preecnher o getView: " + e.getMessage());

        }
        return convertView;
    }
}
