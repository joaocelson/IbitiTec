package com.ibititec.ldapp.Ibitipoca;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ibititec.ldapp.R;

public class IbitipocaMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ibitipoca_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String[] values = new String[] { "Pousadas", "Casas", "Parque",
                "Vila", "Restaurantes" };
        final ListView lvIbitipoca = (ListView) findViewById(R.id.lv_ibitipoca_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, values);
        lvIbitipoca.setAdapter(adapter);

        lvIbitipoca.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> myAdapter, View myView, int position, long mylng) {
                try {
                    abrirTelaIbitipoca(lvIbitipoca, position);
                } catch (Exception ex) {
                    ex.getMessage();
                }
            }
        });
    }

    private void abrirTelaIbitipoca(ListView lvUtilidadePublica, int position) {
        String getListName = lvUtilidadePublica.getItemAtPosition(position).toString();

        if(getListName=="Pousadas"){
            Intent intent = new Intent(this, PousadasChalesActivity.class);
            startActivity(intent);
        }else   if(getListName=="Casas") {
            Intent intent = new Intent(this, CasasActivity.class);
            startActivity(intent);
        }else  if(getListName=="Parque") {
            Intent intent = new Intent(this, ParqueActivity.class);
            startActivity(intent);
        }else  if(getListName=="Restaurantes") {
            Intent intent = new Intent(this, RestaurantesActivity.class);
            startActivity(intent);
        }else  if(getListName=="Vila") {
            Intent intent = new Intent(this, VilaActivity.class);
            startActivity(intent);
        }
    }

}
