package com.ibititec.ldapp.Ibitipoca;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;

import com.ibititec.ldapp.R;
import com.ibititec.ldapp.adapter.TextoImagemAdapter;
import com.ibititec.ldapp.helpers.UIHelper;
import com.ibititec.ldapp.models.ObjetoTextoImagem;

import java.util.ArrayList;

public class ParqueActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parque);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayList<ObjetoTextoImagem> parque = getTextoParque();

        TextoImagemAdapter parqueAdapter = new TextoImagemAdapter(this, parque);
        final ListView listView = (ListView) findViewById(R.id.listview_parque);
        listView.setAdapter(parqueAdapter);
        UIHelper.setListViewHeightBasedOnChildren(listView);


    }

    private ArrayList<ObjetoTextoImagem> getTextoParque(){
        ArrayList<ObjetoTextoImagem> parque = new ArrayList<ObjetoTextoImagem>();
        parque.add(new ObjetoTextoImagem(getResources().getString(R.string.txtIbitipoca_pq_1),R.drawable.parque));
        parque.add(new ObjetoTextoImagem(getResources().getString(R.string.txtIbitipoca_pq_2),R.drawable.parque_2));
        parque.add(new ObjetoTextoImagem(getResources().getString(R.string.txtIbitipoca_pq_3),R.drawable.parque_3));
        parque.add(new ObjetoTextoImagem(getResources().getString(R.string.txtIbitipoca_pq_4),R.drawable.parque_4));
        return parque;
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
