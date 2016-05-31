package com.ibititec.ldapp.adapter;

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
    public ArrayList<String> listComerciantes;

    public ComercianteAdapter(Context context, ArrayList<Comerciante> listaComerciantes) {
        this.context = context;
        this.listaComerciantes = listaComerciantes;
        this.listComerciantes = new ArrayList<String>();
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
            View layout = inflater.inflate(R.layout.adapter_comerciante, null);

            TextView nome = (TextView) layout.findViewById(R.id.txtNomeComerciante);
            nome.setText(comerciante.getNome());
            if(comerciante.getNome().equals("Alternativa Bar")) {
                String teste = "";
            }
            Log.i(ListComercianteActivity.TAG, comerciante.getNome());
            nome.setPadding(10,0,0,0);
            TextView txtTelefoneComerciante = (TextView) layout.findViewById(R.id.txtTelefoneComerciante);

            if(comerciante.getTelefones() != null && comerciante.getTelefones().size() > 0
                    && comerciante.getTelefones().get(0).getNumero() != null)
                txtTelefoneComerciante.setText("Telefone: " + comerciante.getTelefones().get(0).getNumero());
            else
                txtTelefoneComerciante.setText("Telefone: ");

            txtTelefoneComerciante.setPadding(10,0,0,0);

            Uri imageUri = Uri.parse(comerciante.getNomeFoto());
            SimpleDraweeView draweeView = (SimpleDraweeView) layout.findViewById(R.id.my_image_view);
            draweeView.setImageURI(imageUri);

            return layout;

        } catch (Exception e) {
            Log.i(ListComercianteActivity.TAG, "Erro ao preecnher o getView: " + e.getMessage());
        }
        return convertView;
    }
}
