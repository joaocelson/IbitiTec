package com.ibititec.ldapp.utilidade_publica.transporte;

import android.os.Bundle;
import android.support.annotation.NonNull;
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

public class BassamarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bassamar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayList<Transporte> utilidadeArray = getHorariosBassamar();

        TransporteAdapter transporteAdater = new TransporteAdapter(this, utilidadeArray, this);
        final ListView listView = (ListView) findViewById(R.id.listview_vimara);
        listView.setAdapter(transporteAdater);
        UIHelper.setListViewHeightBasedOnChildren(listView);
    }

    @NonNull
    private ArrayList<Transporte> getHorariosBassamar() {
        ArrayList<Transporte> utilidadeArray = new ArrayList<Transporte>();
        utilidadeArray.add(new Transporte("Lima Duarte x Juiz de Fora", "Dia.: 05:15/07:00/09:00/10:30", "12:30/14:30/17:00/19:00"));
        utilidadeArray.add(new Transporte("Lima Duarte x Juiz de Fora", "Seg 06:00", "Dom: 21:30"));
        utilidadeArray.add(new Transporte("Juiz de Fora x Lima Duarte", "Dia: 06:00/07:30/10:00/11:30", "12:30/16:15/17:30/19:00"));
        utilidadeArray.add(new Transporte("Juiz de Fora x Lima Duarte", "Sex/Sab/Dom: 14:00", "Sex: 19:05/Dom: 23:00"));
        return utilidadeArray;
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

