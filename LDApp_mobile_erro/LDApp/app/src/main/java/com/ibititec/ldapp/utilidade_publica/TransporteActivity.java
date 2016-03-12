package com.ibititec.ldapp.utilidade_publica;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;

import com.ibititec.ldapp.R;
import com.ibititec.ldapp.adapter.UtilidadeAdapter;
import com.ibititec.ldapp.helpers.UIHelper;
import com.ibititec.ldapp.models.UtilidadePublica;

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
        final ListView listView = (ListView) findViewById(R.id.listview_utilidades_transporte  );
        listView.setAdapter(utilidadeAdater);
        UIHelper.setListViewHeightBasedOnChildren(listView);
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
