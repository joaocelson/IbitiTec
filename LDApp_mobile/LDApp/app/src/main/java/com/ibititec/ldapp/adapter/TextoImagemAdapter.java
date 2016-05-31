package com.ibititec.ldapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ibititec.ldapp.ListComercianteActivity;
import com.ibititec.ldapp.R;
import com.ibititec.ldapp.models.ObjetoTextoImagem;

import java.util.ArrayList;

public class TextoImagemAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<ObjetoTextoImagem> listaItensCidade;
    public ArrayList<String> listaItensCidadeString;

    public TextoImagemAdapter(Context context, ArrayList<ObjetoTextoImagem> listaItensCidade) {
        this.context = context;
        this.listaItensCidade = listaItensCidade;
        this.listaItensCidadeString = new ArrayList<String>();
    }

    @Override
    public int getCount() {
        return listaItensCidade.size();
    }

    @Override
    public Object getItem(int position) {
        return listaItensCidade.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        try {
            Log.i(ListComercianteActivity.TAG, "Vai setar o Adapter, número de registro " + listaItensCidade.size() + " Position" + position);

            ObjetoTextoImagem cidade = listaItensCidade.get(position);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.adapter_cidade, null);

            TextView nome = (TextView) layout.findViewById(R.id.txt_cidade_adapter);
            nome.setText(cidade.getTexto());
            nome.setPadding(10,0,0,0);

            ImageView ivCasa = (ImageView) layout.findViewById(R.id.img_cidade);
            ivCasa.setImageResource(cidade.getFoto());

            return layout;
        } catch (Exception e) {
            Log.i(ListComercianteActivity.TAG, "Erro ao preecnher o getView: " + e.getMessage());
        }
        return convertView;
    }
}
