package com.ibititec.ldapp.helpers;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.ibititec.ldapp.ListComercianteActivity;
import com.ibititec.ldapp.R;
import com.ibititec.ldapp.models.Comerciante;

import java.util.ArrayList;

/**
 * Created by JOAOCELSON on 03/03/2016.
 */
public class ComercianteAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Comerciante> listaComerciantes;
    public  ArrayList<String> listComerciantes = new ArrayList<String>();
    public ComercianteAdapter(Context context, ArrayList<Comerciante> listaComerciantes) {
        this.context = context;
        this.listaComerciantes = listaComerciantes;
    }

    @Override
    public int getCount() {
        return listaComerciantes.size();
    }

    @Override
    public Object getItem(int position) {
        return listaComerciantes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        try {
            Log.i(ListComercianteActivity.TAG, "Vai setar o Adapter, número de registro " + listaComerciantes.size() + " Position" + position);

            Comerciante comerciante = listaComerciantes.get(position);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.comerciante_adapter, null);

            TextView nome = (TextView) layout.findViewById(R.id.txtNomeComerciante);
            nome.setText(comerciante.getNome());

            TextView txtTelefoneComerciante = (TextView) layout.findViewById(R.id.txtTelefoneComerciante);
            txtTelefoneComerciante.setText("Telefone: " + comerciante.getTelefones().get(0).getNumero());

            /* ImageView ivCasa = (ImageView) layout.findViewById(R.id.ivComerciante);
            ivCasa.setImageResource(comerciante.getComercianteImage(position));*/

            Uri imageUri = Uri.parse(comerciante.getNomeFoto());
            SimpleDraweeView draweeView = (SimpleDraweeView) layout.findViewById(R.id.my_image_view);
            draweeView.setImageURI(imageUri);
            listComerciantes.add(comerciante.getNome());

            return layout;

        } catch (Exception e) {
            Log.i(ListComercianteActivity.TAG, "Erro ao preecnher o getView: " + e.getMessage());
            return null;
        }
    }
}
