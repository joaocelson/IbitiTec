package com.ibititec.ldapp;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;

import com.ibititec.ldapp.adapter.TextoImagemAdapter;
import com.ibititec.ldapp.helpers.UIHelper;
import com.ibititec.ldapp.models.ObjetoTextoImagem;

import java.util.ArrayList;

public class CidadeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cidade);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayList<ObjetoTextoImagem> cidades = getTextoCidade();

        TextoImagemAdapter cidadeAdapter = new TextoImagemAdapter(this, cidades);
        final ListView listView = (ListView) findViewById(R.id.listview_cidade);
        listView.setAdapter(cidadeAdapter);
        UIHelper.setListViewHeightBasedOnChildren(listView);

        //Appodeal.show(this, Appodeal.BANNER_BOTTOM);
    }

    private ArrayList<ObjetoTextoImagem> getTextoCidade(){
        ArrayList<ObjetoTextoImagem> cidades = new ArrayList<ObjetoTextoImagem>();
        cidades.add(new ObjetoTextoImagem(getResources().getString(R.string.txtLimaDuarte_1),R.drawable.limaduarte));
        cidades.add(new ObjetoTextoImagem(getResources().getString(R.string.txtLimaDuarte_2),R.drawable.limaduarte_2));
        cidades.add(new ObjetoTextoImagem(getResources().getString(R.string.txtLimaDuarte_3),R.drawable.limaduarte_3));
        cidades.add(new ObjetoTextoImagem(getResources().getString(R.string.txtLimaDuarte_4),R.drawable.limaduarte_4));
        cidades.add(new ObjetoTextoImagem(getResources().getString(R.string.txtLimaDuarte_5),R.drawable.limaduarte_5));
        return cidades;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();
        switch (id) {
            // Id correspondente ao botão Up/Home da actionbar
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
