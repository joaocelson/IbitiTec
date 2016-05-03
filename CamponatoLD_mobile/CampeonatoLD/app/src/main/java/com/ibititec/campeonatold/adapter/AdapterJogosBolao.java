package com.ibititec.campeonatold.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.ibititec.campeonatold.MainActivity;
import com.ibititec.campeonatold.R;
import com.ibititec.campeonatold.bolao.PalpitePorJogoActivity;
import com.ibititec.campeonatold.modelo.Partida;

import java.io.Serializable;
import java.util.List;

/**
 * Created by JOAOCELSON on 29/04/2016.
 */
public class AdapterJogosBolao extends BaseAdapter {

    private Activity activity;
    private List<Partida> bolaoList = null;

    public AdapterJogosBolao(Activity activityParam, List<Partida> bolaoList) {
        this.bolaoList = bolaoList;
        this.activity = activityParam;
    }

    public AdapterJogosBolao() {
    }

    @Override

    public int getCount() {
        return bolaoList.size();
    }

    @Override
    public Object getItem(int position) {
        return bolaoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Partida rodadaObj = bolaoList.get(position);
        try {
           Log.i(MainActivity.TAG, "Vai setar o Adapter, número de registro: " + bolaoList.size() + " Position: " + position + " - ID PARTIDA " + rodadaObj.getId());

            LayoutInflater inflater = (LayoutInflater) activity.getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.adapter_jogos_bolao, null);


            TextView vsJogo2 = (TextView) layout.findViewById(R.id.txtversus_Bolao);
            vsJogo2.setText(rodadaObj.getGolMandante() + " X " + rodadaObj.getGolVisitante());
            //TextView jogo2 = (TextView) layout.findViewById(R.id.txtJogo2);

//            rodada.setText("RODADA: " + rodadaObj.getNumero() + " - ");
//            Data.setText("DATA: " + rodadaObj.getData());
//            Campo.setText("CAMPO: " + rodadaObj.getCampo());
//            hrJogo1.setText("Hr: " + rodadaObj.getHoraJogo1());
//            // jogo1.setText(rodadaObj.getJogo1());
//            hrJogo2.setText("Hr: " + rodadaObj.getHoraJogo2());
//            // jogo2.setText(rodadaObj.getJogo2());
//
//            //setando nome da imagem a ser exibida
//            String[] jogo1Array = splitString(rodadaObj.getJogo1());
//            String[] jogo2Array = splitString(rodadaObj.getJogo2());
//
//            if(jogo1Array == null){
//                jogo1Array[0] = rodadaObj.getJogo1().trim();
//                jogo1Array[1] =rodadaObj.getJogo1().trim();
//                jogo1Array[2] =rodadaObj.getJogo1().trim();
//
//            }
//            if(jogo2Array == null){
//                jogo2Array[0] = rodadaObj.getJogo2().trim();
//                jogo2Array[1] = rodadaObj.getJogo2().trim();
//                jogo2Array[2] = rodadaObj.getJogo2().trim();
//            }

//            vsJogo1.setText(jogo1Array[1]);
//            vsJogo2.setText(jogo2Array[1]);
//
//            Log.i(MainActivity.TAG, "URL position: " + position + " - "+ MainActivity.PATH_FOTOS + jogo1Array[0].trim()+".jpg");
//            Log.i(MainActivity.TAG, "jogo1Array[0]: " +jogo1Array[0]);
//            Log.i(MainActivity.TAG, "jogo1Array[1]: " +jogo1Array[1]);
//            Log.i(MainActivity.TAG, "jogo2Array[0]: " +jogo2Array[0]);
//            Log.i(MainActivity.TAG, "jogo2Array[1]: " +jogo2Array[1]);
//
            Uri imageUriMandante = Uri.parse(MainActivity.PATH_FOTOS + rodadaObj.getEscudoPequenoMandante().trim());
            SimpleDraweeView draweeViewMandante = (SimpleDraweeView) layout.findViewById(R.id.img_time_mandante_bolao);
            draweeViewMandante.setImageURI(imageUriMandante);

            Uri imageUriVisistante = Uri.parse(MainActivity.PATH_FOTOS + rodadaObj.getEscudoPequenoVisitante().trim());
            SimpleDraweeView draweeViewVisistante = (SimpleDraweeView) layout.findViewById(R.id.img_time_visitante_bolao);
            draweeViewVisistante.setImageURI(imageUriVisistante);

            Button btnPalpite = (Button) layout.findViewById(R.id.btnPalpiteJogoBolao);
            btnPalpite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startarActivityPalpite(v, rodadaObj);
                }
            });
//
//            Uri imageUri3 = Uri.parse(MainActivity.PATH_FOTOS + jogo2Array[0].trim()+".jpg");
//            SimpleDraweeView draweeView3 = (SimpleDraweeView) layout.findViewById(R.id.imageView4);
//            draweeView3.setImageURI(imageUri3);
//
//            Uri imageUri4 = Uri.parse(MainActivity.PATH_FOTOS + jogo2Array[2].trim()+".jpg");
//            SimpleDraweeView draweeView4 = (SimpleDraweeView) layout.findViewById(R.id.imageView5);
//            draweeView4.setImageURI(imageUri4);

            //Appodeal.show(activity, Appodeal.BANNER_BOTTOM);
            return layout;

        } catch (Exception e) {
            Log.i(MainActivity.TAG, "Erro ao preecnher o getView: " + e.getMessage());
            Log.i(MainActivity.TAG, "Valor da variável: " + rodadaObj.getId() + " - " + rodadaObj.toString());
        }
        return convertView;
    }

    private void startarActivityPalpite(View v, Partida partida) {
        Intent intent = new Intent(activity.getApplication(), PalpitePorJogoActivity.class);
        intent.putExtra("jogo_bolao", (Serializable) partida);
        activity.startActivity(intent);
    }

    public String[] splitString(String param) {
        try {
            return param.split("-");
        } catch (Exception ex) {
            ex.getMessage();
            return null;
        }
    }
}
