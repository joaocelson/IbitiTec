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

public class VilaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vila);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayList<ObjetoTextoImagem> vilas = getTextoCidade();

        TextoImagemAdapter vilaAdapter = new TextoImagemAdapter(this, vilas);
        final ListView listView = (ListView) findViewById(R.id.listview_vila);
        listView.setAdapter(vilaAdapter);
        UIHelper.setListViewHeightBasedOnChildren(listView);
    }

    private ArrayList<ObjetoTextoImagem> getTextoCidade(){
        ArrayList<ObjetoTextoImagem> vila = new ArrayList<ObjetoTextoImagem>();
        vila.add(new ObjetoTextoImagem(getResources().getString(R.string.txtIbitipoca_vila_1),R.drawable.limaduarte));
        vila.add(new ObjetoTextoImagem(getResources().getString(R.string.txtIbitipoca_vila_2),R.drawable.limaduarte_2));
        vila.add(new ObjetoTextoImagem(getResources().getString(R.string.txtIbitipoca_vila_1),R.drawable.limaduarte_3));
        vila.add(new ObjetoTextoImagem(getResources().getString(R.string.txtIbitipoca_vila_2),R.drawable.limaduarte_4));
        return vila;
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
