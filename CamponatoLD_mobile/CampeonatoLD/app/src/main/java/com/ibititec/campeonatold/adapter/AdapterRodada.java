package com.ibititec.campeonatold.adapter;

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
            TextView vsJogo1 = (TextView) layout.findViewById(R.id.txtversus);
            //TextView jogo1 = (TextView) layout.findViewById(R.id.txtJogo1);
            TextView hrJogo2 = (TextView) layout.findViewById(R.id.txtHrJogo2);
            TextView vsJogo2 = (TextView) layout.findViewById(R.id.txtversus2);
            //TextView jogo2 = (TextView) layout.findViewById(R.id.txtJogo2);

            rodada.setText("RODADA: " + rodadaObj.getNumero() + " - ");
            Data.setText("DATA: " + rodadaObj.getData());
            Campo.setText("CAMPO: " + rodadaObj.getCampo());
            hrJogo1.setText("Hr: " + rodadaObj.getHoraJogo1());
           // jogo1.setText(rodadaObj.getJogo1());
            hrJogo2.setText("Hr: " + rodadaObj.getHoraJogo2());
           // jogo2.setText(rodadaObj.getJogo2());

            //setando nome da imagem a ser exibida
            String[] jogo1Array = splitString(rodadaObj.getJogo1());
            String[] jogo2Array = splitString(rodadaObj.getJogo2());

            if(jogo1Array == null){
                jogo1Array[0] = rodadaObj.getJogo1().trim();
                jogo1Array[1] =rodadaObj.getJogo1().trim();
                jogo1Array[2] =rodadaObj.getJogo1().trim();

            }
            if(jogo2Array == null){
                jogo2Array[0] = rodadaObj.getJogo2().trim();
                jogo2Array[1] = rodadaObj.getJogo2().trim();
                jogo2Array[2] = rodadaObj.getJogo2().trim();
            }

            vsJogo1.setText(jogo1Array[1]);
            vsJogo2.setText(jogo2Array[1]);

            Log.i(MainActivity.TAG, "URL position: " + position + " - "+ MainActivity.PATH_FOTOS + jogo1Array[0].trim()+".jpg");
            Log.i(MainActivity.TAG, "jogo1Array[0]: " +jogo1Array[0]);
            Log.i(MainActivity.TAG, "jogo1Array[1]: " +jogo1Array[1]);
            Log.i(MainActivity.TAG, "jogo2Array[0]: " +jogo2Array[0]);
            Log.i(MainActivity.TAG, "jogo2Array[1]: " +jogo2Array[1]);

            Uri imageUri = Uri.parse(MainActivity.PATH_FOTOS + jogo1Array[0].trim()+".jpg");
            SimpleDraweeView draweeView = (SimpleDraweeView) layout.findViewById(R.id.imageView2);
            draweeView.setImageURI(imageUri);

            Uri imageUri2 = Uri.parse(MainActivity.PATH_FOTOS + jogo1Array[2].trim()+".jpg");
            SimpleDraweeView draweeView2 = (SimpleDraweeView) layout.findViewById(R.id.imageView3);
            draweeView2.setImageURI(imageUri2);

            Uri imageUri3 = Uri.parse(MainActivity.PATH_FOTOS + jogo2Array[0].trim()+".jpg");
            SimpleDraweeView draweeView3 = (SimpleDraweeView) layout.findViewById(R.id.imageView4);
            draweeView3.setImageURI(imageUri3);

            Uri imageUri4 = Uri.parse(MainActivity.PATH_FOTOS + jogo2Array[2].trim()+".jpg");
            SimpleDraweeView draweeView4 = (SimpleDraweeView) layout.findViewById(R.id.imageView5);
            draweeView4.setImageURI(imageUri4);

            //Appodeal.show(activity, Appodeal.BANNER_BOTTOM);
            return layout;

        } catch (Exception e) {
            Log.i(MainActivity.TAG, "Erro ao preecnher o getView: " + e.getMessage());

        }
        return convertView;
    }

    public String[] splitString(String param){
        try{
            return param.split("-");
        }catch (Exception ex){
            ex.getMessage();
            return null;
        }
    }
}
