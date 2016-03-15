package com.ibititec.ldapp.utilidade_publica.transporte;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;

import com.ibititec.ldapp.R;
import com.ibititec.ldapp.adapter.TransporteAdapter;
import com.ibititec.ldapp.helpers.UIHelper;
import com.ibititec.ldapp.models.Transporte;

import java.util.ArrayList;

public class VimaraActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vimara);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayList<Transporte> utilidadeArray = new ArrayList<Transporte>();
        utilidadeArray.add(new Transporte("Lima Duarte x Ibitipoca", "Seg a Sex: 06:30/15:15", "Seg a Sex: 06:30/15:15"));
        utilidadeArray.add(new Transporte("Ibitipoca x Lima Duarte", "Seg a Sex: 06:30/15:15", "Seg a Sex: 06:30/15:15"));
        utilidadeArray.add(new Transporte("Lima Duarte x Divisa", "Seg a Sex: 06:30/15:15", "Seg a Sex: 06:30/15:15"));
        utilidadeArray.add(new Transporte("Divisa x Lima Duarte", "Seg a Sex: 06:30/15:15", "Seg a Sex: 06:30/15:15"));
        utilidadeArray.add(new Transporte("Lima Duarte x Lopes", "Seg a Sex: 06:30/15:15", "Seg a Sex: 06:30/15:15"));
        utilidadeArray.add(new Transporte("Lopes x Lima Duarte", "Seg a Sex: 06:30/15:15", "Seg a Sex: 06:30/15:15"));
        utilidadeArray.add(new Transporte("LD x São Domingos (Olaria)", "Seg a Sex: 06:30/15:15", "Seg a Sex: 06:30/15:15"));
        utilidadeArray.add(new Transporte("São Domingos (Olaria) x LD", "Seg a Sex: 06:30/15:15", "Seg a Sex: 06:30/15:15"));
        utilidadeArray.add(new Transporte("Lima Duarte x Rancharia", "Seg a Sex: 06:30/15:15", "Seg a Sex: 06:30/15:15"));
        utilidadeArray.add(new Transporte("Rancharia x Lima Duarte", "Seg a Sex: 06:30/15:15", "Seg a Sex: 06:30/15:15"));

        TransporteAdapter transporteAdater = new TransporteAdapter(this, utilidadeArray, this);
        final ListView listView = (ListView) findViewById(R.id.listview_vimara);
        listView.setAdapter(transporteAdater);
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