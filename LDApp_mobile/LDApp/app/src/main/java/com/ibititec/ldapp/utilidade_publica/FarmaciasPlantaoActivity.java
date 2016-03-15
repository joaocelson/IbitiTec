package com.ibititec.ldapp.utilidade_publica;

import android.os.Bundle;
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

public class FarmaciasPlantaoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmacias_plantao);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ArrayList<UtilidadePublica> utilidadeArray = new ArrayList<UtilidadePublica>();
        utilidadeArray.add(new UtilidadePublica("01-03 Pharmavida", "(32) 3281-0943"));
        utilidadeArray.add(new UtilidadePublica("02-03 Pharmavida", "(32) 3215-1109"));
        utilidadeArray.add(new UtilidadePublica("03-03 Pharmavida", "(32) 3276-1125"));
        utilidadeArray.add(new UtilidadePublica("04-03 Pharmavida", "(32) 3281-0943"));
        utilidadeArray.add(new UtilidadePublica("05-03 Pharmavida", "(32) 3215-1109"));
        utilidadeArray.add(new UtilidadePublica("06-03 Pharmavida", "(32) 3276-1125"));
        utilidadeArray.add(new UtilidadePublica("07-03 Pharmavida", "(32) 3281-0943"));
        utilidadeArray.add(new UtilidadePublica("08-03 Pharmavida", "(32) 3215-1109"));
        utilidadeArray.add(new UtilidadePublica("09-03 Pharmavida", "(32) 3276-1125"));
        utilidadeArray.add(new UtilidadePublica("10-03 Rodrigues ", "(32) 3281-0943"));
        utilidadeArray.add(new UtilidadePublica("11-03 Pharmavida", "(32) 3215-1109"));
        utilidadeArray.add(new UtilidadePublica("12-03 Pharmavida", "(32) 3276-1125"));
        utilidadeArray.add(new UtilidadePublica("13-03 Pharmavida", "(32) 3281-0943"));
        utilidadeArray.add(new UtilidadePublica("14-03 Pharmavida", "(32) 3215-1109"));
        utilidadeArray.add(new UtilidadePublica("15-03 Pharmavida", "(32) 3276-1125"));
        utilidadeArray.add(new UtilidadePublica("16-03 Pharmavida", "(32) 3281-0943"));
        utilidadeArray.add(new UtilidadePublica("17-03 Pharmavida", "(32) 3215-1109"));
        utilidadeArray.add(new UtilidadePublica("18-03 Pharmavida", "(32) 3276-1125"));
        utilidadeArray.add(new UtilidadePublica("19-03 Pharmavida", "(32) 3281-0943"));
        utilidadeArray.add(new UtilidadePublica("20-03 Pharmavida", "(32) 3215-1109"));
        utilidadeArray.add(new UtilidadePublica("21-03 Pharmavida", "(32) 3276-1125"));
        utilidadeArray.add(new UtilidadePublica("22-03 Pharmavida", "(32) 3281-0943"));
        utilidadeArray.add(new UtilidadePublica("23-03 Pharmavida", "(32) 3215-1109"));
        utilidadeArray.add(new UtilidadePublica("24-03 Pharmavida", "(32) 3276-1125"));
        utilidadeArray.add(new UtilidadePublica("25-03 Pharmavida", "(32) 3276-1125"));
        utilidadeArray.add(new UtilidadePublica("26-03 Pharmavida", "(32) 3281-0943"));
        utilidadeArray.add(new UtilidadePublica("27-03 Pharmavida", "(32) 3215-1109"));
        utilidadeArray.add(new UtilidadePublica("28-03 Pharmavida", "(32) 3276-1125"));
        utilidadeArray.add(new UtilidadePublica("29-03 Pharmavida", "(32) 3215-1109"));
        utilidadeArray.add(new UtilidadePublica("30-03 Pharmavida", "(32) 3276-1125"));
        utilidadeArray.add(new UtilidadePublica("31-03 Pharmavida", "(32) 3215-1109"));

        UtilidadeAdapter utilidadeAdater = new UtilidadeAdapter(this, utilidadeArray, this);
        final ListView listView = (ListView) findViewById(R.id.listview_utilidades_farmacia_plantao);
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
