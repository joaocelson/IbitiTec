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
import com.ibititec.campeonatold.modelo.Classificacao;

import java.util.List;

public class AdapterClassificacao extends BaseAdapter {
    private List<Classificacao> classificacaoList = null;
    private Activity activity;

    public AdapterClassificacao(Activity activityParam, List<Classificacao> classificacaoList){
        this.classificacaoList = classificacaoList;
        this.activity = activityParam;
    }

    public AdapterClassificacao(){}

    @Override
    public int getCount() {
        return classificacaoList.size();
    }

    @Override
    public Object getItem(int position) {
        return classificacaoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View layout = null;
        try {
            final Classificacao classificacao = classificacaoList.get(position);
            Log.i(MainActivity.TAG, "Vai setar o Adapter, número de registro: " + classificacaoList.size() + " Position: " + position + " - Nome Rodada " + classificacao.getTime());

            LayoutInflater inflater = (LayoutInflater) activity.getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = inflater.inflate(R.layout.adapter_classificacao, null);

            TextView posicao = (TextView) layout.findViewById(R.id.txtPosicao);
            TextView pontos = (TextView) layout.findViewById(R.id.txtPontos);
            TextView time = (TextView) layout.findViewById(R.id.txtTime);

            posicao.setText(classificacao.getPosicao());
            pontos.setText(classificacao.getPontos());
            time.setText(classificacao.getTime());
            return layout;
        } catch (Exception e) {
            Log.i(MainActivity.TAG, "Erro ao preecnher o getView: " + e.getMessage());
            return  convertView;
        }
    }
}
