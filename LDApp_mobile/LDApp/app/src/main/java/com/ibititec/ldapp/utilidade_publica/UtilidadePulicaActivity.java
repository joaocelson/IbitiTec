package com.ibititec.ldapp.utilidade_publica;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ibititec.ldapp.R;

public class UtilidadePulicaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utilidade_pulica);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        String[] values = new String[] { "Telefones Prefeitura", "Telefones Santa Casa", "Telefones Polícia",
                "Transporte", "Telefone Câmara" };
        final ListView lvUtilidadePublica = (ListView) findViewById(R.id.lv_utilidade_publica);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, values);
        lvUtilidadePublica.setAdapter(adapter);

        lvUtilidadePublica.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> myAdapter, View myView, int position, long mylng) {
                try {
                  abrirTelautilidade(lvUtilidadePublica, position);
                } catch (Exception ex) {
                    ex.getMessage();
                }
            }
        });
    }

    private void abrirTelautilidade(ListView lvUtilidadePublica, int position ) {

       String getListName = lvUtilidadePublica.getItemAtPosition(position).toString();

        if(getListName=="Telefones Prefeitura"){
            Intent intent = new Intent(this, PrefeituraActivity.class);
            startActivity(intent);
        }else   if(getListName=="Telefones Santa Casa") {
            Intent intent = new Intent(this, SantaCasaActivity.class);
            startActivity(intent);
        }else  if(getListName=="Telefones Polícia") {
            Intent intent = new Intent(this, PoliciaActivity.class);
            startActivity(intent);
        }else  if(getListName=="Transporte") {
            Intent intent = new Intent(this, TransporteActivity.class);
            startActivity(intent);
        }else  if(getListName=="Telefone Câmara") {
            Intent intent = new Intent(this, CamaraActivity.class);
            startActivity(intent);
        }
    }
}
