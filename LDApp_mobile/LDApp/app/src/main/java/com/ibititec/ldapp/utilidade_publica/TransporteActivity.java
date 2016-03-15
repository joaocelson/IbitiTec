package com.ibititec.ldapp.utilidade_publica;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ibititec.ldapp.R;
import com.ibititec.ldapp.adapter.UtilidadeAdapter;
import com.ibititec.ldapp.helpers.UIHelper;
import com.ibititec.ldapp.models.UtilidadePublica;
import com.ibititec.ldapp.utilidade_publica.transporte.BassamarActivity;
import com.ibititec.ldapp.utilidade_publica.transporte.SertanejaActivity;
import com.ibititec.ldapp.utilidade_publica.transporte.VimaraActivity;

import java.util.ArrayList;

public class TransporteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transporte);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ArrayList<UtilidadePublica> utilidadeArray = new ArrayList<UtilidadePublica>();
        utilidadeArray.add(new UtilidadePublica("Vimara", "(32) 3281-1390"));
        utilidadeArray.add(new UtilidadePublica("Bassamar", "(32) 3215-1109"));
        utilidadeArray.add(new UtilidadePublica("Sertaneja", "(32) 3276-1125"));
        utilidadeArray.add(new UtilidadePublica("Taxi", "(32) 3281-1200"));

        UtilidadeAdapter utilidadeAdater = new UtilidadeAdapter(this, utilidadeArray, this);
        final ListView listView = (ListView) findViewById(R.id.listview_utilidades_transporte_lista  );
        listView.setAdapter(utilidadeAdater);
        UIHelper.setListViewHeightBasedOnChildren(listView);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                abrirTelaTranposrte(listView,position);
            }
        });
    }

    private void abrirTelaTranposrte(ListView listView, int position) {
        UtilidadePublica getListName =(UtilidadePublica) listView.getItemAtPosition(position);

        if (getListName.getNomeUtilidade() == "Vimara") {
            Intent intent = new Intent(this, VimaraActivity.class);
            startActivity(intent);
        } else if (getListName.getNomeUtilidade() == "Bassamar") {
            Intent intent = new Intent(this, BassamarActivity.class);
            startActivity(intent);
        } else if (getListName.getNomeUtilidade() == "Sertaneja") {
            Intent intent = new Intent(this, SertanejaActivity.class);
            startActivity(intent);
        }
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
