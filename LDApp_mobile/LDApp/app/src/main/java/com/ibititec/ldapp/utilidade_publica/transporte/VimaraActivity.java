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

public class VimaraActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vimara);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayList<Transporte> utilidadeArray = getHorariosVimara();

        TransporteAdapter transporteAdater = new TransporteAdapter(this, utilidadeArray, this);
        final ListView listView = (ListView) findViewById(R.id.listview_vimara);
        listView.setAdapter(transporteAdater);
        UIHelper.setListViewHeightBasedOnChildren(listView);
    }

    @NonNull
    private ArrayList<Transporte> getHorariosVimara() {
        ArrayList<Transporte> utilidadeArray = new ArrayList<Transporte>();
        utilidadeArray.add(new Transporte("Lima Duarte x Ibitipoca", "Seg a Sex: 06:30/15:15", "Sab/Dom/Fer: 7:30/15:15"));
        utilidadeArray.add(new Transporte("Ibitipoca x Lima Duarte", "Seg a Sex: 08:30/17:00", "Sab/Dom/Fer: 9:30/17:00"));
        utilidadeArray.add(new Transporte("Lima Duarte x Divisa", "Seg a Sex: 06:30/15:15", "Sab/Dom/Fer: 9:30/17:00"));
        utilidadeArray.add(new Transporte("Divisa x Lima Duarte", "Seg a Sex: 06:30/15:15", "Sab/Dom/Fer: 9:30/17:00"));
        utilidadeArray.add(new Transporte("Lima Duarte x Lopes", "Seg a Sex: 06:30/15:15", "Sab/Dom/Fer: 7:30h/15:15"));
        utilidadeArray.add(new Transporte("Lopes x Lima Duarte", "Seg a Sex: 06:30/15:15", "Sab/Dom/Fer: 10:00h/17:30"));
        utilidadeArray.add(new Transporte("LD x São Domingos (Olaria)", "Seg a Sex: 6:30h/16:30", ""));
        utilidadeArray.add(new Transporte("São Domingos (Olaria) x LD", "Seg a Sex: 8:00/18:00", ""));
        utilidadeArray.add(new Transporte("Lima Duarte x Várzea do Brumado", "Seg a Sex: 6:30/16:00", ""));
        utilidadeArray.add(new Transporte("Várzea do Brumado x Lima Duarte", "Seg a Sex: 08:00/17:30", ""));
        utilidadeArray.add(new Transporte("Lima Duarte x Orvalho", "Seg a Sex: ", ""));
        utilidadeArray.add(new Transporte("Orvalho x Lima Duarte", "Seg a Sex: ", "Sab/Dom/Fer: "));
        utilidadeArray.add(new Transporte("Vila Cruzeiro x Centro", "Seg a Sex: ", ""));
        utilidadeArray.add(new Transporte("Centro x Vila Cruzeiro", "Seg a Sex: ", "Sab/Dom/Fer: "));
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