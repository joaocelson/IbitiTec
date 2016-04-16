package com.ibititec.lffa.adapter;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.ibititec.lffa.MainActivity;
import com.ibititec.lffa.R;
import com.ibititec.lffa.modelo.Classificacao;

import java.util.List;

public class AdapterClassificacao extends BaseAdapter {
    private List<Classificacao> classificacaoList = null;
    private Activity activity;
    private boolean exibirPontuacao;

    public AdapterClassificacao(Activity activityParam, List<Classificacao> classificacaoList, boolean exibirPontuacao) {
        this.classificacaoList = classificacaoList;
        this.activity = activityParam;
        this.exibirPontuacao = exibirPontuacao;
    }

    public AdapterClassificacao() {
    }

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
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            final Classificacao classificacao = classificacaoList.get(position);
            Log.i(MainActivity.TAG, "Vai setar o Adapter, número de registro: " + classificacaoList.size() + " Position: " + position + " - Nome Rodada " + classificacao.getTime());

            LayoutInflater inflater = (LayoutInflater) activity.getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.adapter_classificacao, null);

            //TextView posicao = (TextView) layout.findViewById(R.id.txtPosicao);
            TextView pontos = (TextView) layout.findViewById(R.id.txtPontos);
            TextView jogos = (TextView) layout.findViewById(R.id.txtJogos);
            TextView vitoria = (TextView) layout.findViewById(R.id.txtVitorias);
            TextView derrotas = (TextView) layout.findViewById(R.id.txtDerrotas);
            TextView empates = (TextView) layout.findViewById(R.id.txtEmpates);

            TextView pontospp = (TextView) layout.findViewById(R.id.txtPP);
            TextView pontospc = (TextView) layout.findViewById(R.id.txtPC);
            //TextView time = (TextView) layout.findViewById(R.id.txtTime);

            if (classificacao.getTime().equals("GRUPOA") || classificacao.getTime().equals("GRUPOB")) {
            //    posicao.setText("");
//                pontos.setText("");
//                jogos.setText("");
//                vitoria.setText("");
//                derrotas.setText("");
//                empates.setText("");

                //time.setText(classificacao.getTime());
            }else if(exibirPontuacao){
             //   posicao.setText(classificacao.getPosicao());
                pontos.setText(classificacao.getPontos());
                jogos.setText(classificacao.getJogos());
                vitoria.setText(classificacao.getVitorias());
                derrotas.setText(classificacao.getDerrotas());
                empates.setText(classificacao.getEmpate());
                pontospp.setText(classificacao.getPontosPC());
                pontospc.setText(classificacao.getPontosPC());
                Uri imageUri = Uri.parse(MainActivity.PATH_FOTOS + classificacao.getTime() + "_escudo.png");
                SimpleDraweeView draweeView = (SimpleDraweeView) layout.findViewById(R.id.ivClassificacao);
                draweeView.setImageURI(imageUri);
            }


            if(!exibirPontuacao){
                Uri imageUri = Uri.parse(MainActivity.PATH_FOTOS + classificacao.getTime() + ".png");
                SimpleDraweeView draweeView = (SimpleDraweeView) layout.findViewById(R.id.ivClassificacao);
                draweeView.setImageURI(imageUri);
                pontos.setVisibility(View.INVISIBLE);
                jogos.setVisibility(View.INVISIBLE);
                vitoria.setVisibility(View.INVISIBLE);
                derrotas.setVisibility(View.INVISIBLE);
                empates.setVisibility(View.INVISIBLE);
                pontospc.setVisibility(View.INVISIBLE);
                pontospp.setVisibility(View.INVISIBLE);
                ViewGroup.LayoutParams params=draweeView.getLayoutParams();
                params.width= ActionBar.LayoutParams.MATCH_PARENT;
                draweeView.setLayoutParams(params);
            }
            //Appodeal.show(activity, Appodeal.BANNER_BOTTOM);
            return layout;
        } catch (Exception e) {
            Log.i(MainActivity.TAG, "Erro ao preecnher o getView: " + e.getMessage());
        }
        return convertView;
    }
}
