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
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.ibititec.campeonatold.MainActivity;
import com.ibititec.campeonatold.R;
import com.ibititec.campeonatold.aovivo.PartidaTempoRealActivity;
import com.ibititec.campeonatold.modelo.Noticia;

import java.io.Serializable;
import java.util.List;

/**
 * Created by JOAOCELSON on 04/05/2016.
 */
public class AdapterNoticia extends BaseAdapter {
    private Activity activity;
    private List<Noticia> noticiaList = null;

    public AdapterNoticia(Activity activityParam, List<Noticia> noticiaList) {
        this.noticiaList = noticiaList;
        this.activity = activityParam;
    }

    public AdapterNoticia() {
    }

    @Override

    public int getCount() {
        return noticiaList.size();
    }

    @Override
    public Object getItem(int position) {
        return noticiaList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            final Noticia noticia = noticiaList.get(position);
            Log.i(MainActivity.TAG, "Vai setar o Adapter, número de registro: " + noticiaList.size() + " Position: " + position + " - Titulo Noticia " + noticia.getTitulo());

            LayoutInflater inflater = (LayoutInflater) activity.getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.adapter_noticia, null);

            TextView titulo = (TextView) layout.findViewById(R.id.txtTituloFeedNoticias);
            TextView Corpo = (TextView) layout.findViewById(R.id.txtCorpoFeedNoticias);


            titulo.setText(noticia.getTitulo());
            Corpo.setText(noticia.getCorpo() + " - " + noticia.getDataNoticia());

            Uri imageUri = Uri.parse(MainActivity.PATH_FOTOS + noticia.getTime().getEscudoPequeno() + ".jpg");
            SimpleDraweeView draweeView = (SimpleDraweeView) layout.findViewById(R.id.imageView2);
            draweeView.setImageURI(imageUri);

//            Button btnTempoReal = (Button) layout.findViewById(R.id.btnTempoRealPartida1);
//            btnTempoReal.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    startarActivityPalpite(v, noticia);
//                }
//            });

            //Appodeal.show(activity, Appodeal.BANNER_BOTTOM);
            return layout;

        } catch (Exception e) {
            Log.i(MainActivity.TAG, "Erro ao preecnher o getView: " + e.getMessage());

        }
        return convertView;
    }

    private void startarActivityPalpite(View v, Noticia noticia) {
        Intent intent = new Intent(activity.getApplication(), PartidaTempoRealActivity.class);
        intent.putExtra("noticia", (Serializable) noticia);
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
