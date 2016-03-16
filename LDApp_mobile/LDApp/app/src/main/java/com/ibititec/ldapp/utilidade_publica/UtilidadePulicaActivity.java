package com.ibititec.ldapp.utilidade_publica;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
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


        String[] values = new String[] { "Telefones Prefeitura", "Telefones Santa Casa", "Telefones Polícia",
                "Transporte", "Telefone Câmara", "Farmácias Plantão" };
        final ListView lvUtilidadePublica = (ListView) findViewById(R.id.lv_utilidade_publica);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
        }else  if(getListName=="Farmácias Plantão") {
            Intent intent = new Intent(this, FarmaciasPlantaoActivity.class);
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
