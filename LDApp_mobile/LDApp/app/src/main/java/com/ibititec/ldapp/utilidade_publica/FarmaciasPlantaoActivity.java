package com.ibititec.ldapp.utilidade_publica;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.ibititec.ldapp.R;
import com.ibititec.ldapp.adapter.ComercianteAdapter;
import com.ibititec.ldapp.adapter.UtilidadeAdapter;
import com.ibititec.ldapp.adapter.UtilidadeAdapterFarmacia;
import com.ibititec.ldapp.helpers.AlertMensage;
import com.ibititec.ldapp.helpers.HttpHelper;
import com.ibititec.ldapp.helpers.JsonHelper;
import com.ibititec.ldapp.helpers.UIHelper;
import com.ibititec.ldapp.models.Comerciante;
import com.ibititec.ldapp.models.UtilidadePublica;
import com.ibititec.ldapp.models.UtilidadePublicaFarmacia;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FarmaciasPlantaoActivity extends AppCompatActivity {
    private ListView lsViewFarmacia;
    private ProgressBar progressBar;
    private static final String TAG = "LOG - FARMACIA";
    ArrayList<Comerciante> comerciantesArray = new ArrayList<Comerciante>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmacia_plantao);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressBar = (ProgressBar) findViewById(R.id.progress_farmacias);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);

        setupFarmacias();

        lsViewFarmacia = (ListView) findViewById(R.id.listview_farmacia_plantao);

        lsViewFarmacia.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> myAdapter, View myView, int myItemInt, long mylng) {
                try {

                } catch (Exception ex) {
                    ex.getMessage();
                }
            }
        });
        //Appodeal.show(this, Appodeal.BANNER_BOTTOM);
    }

    public void setupFarmacias() {
        String json = leJsonFarmacias();

        if (json.isEmpty()) {
            Log.i(TAG, "Baixou comerciantes.");
            AtualizarFarmacias();
        } else {
            Log.i(TAG, "Carregou comerciantes salvos");
            preencherListFarmacias(json);
            //marcaPatios();
        }
    }

    private String leJsonFarmacias() {
        String json = PreferenceManager.getDefaultSharedPreferences(FarmaciasPlantaoActivity.this)
                .getString("farmacias.json", "");
        Log.i(TAG, "Lendo preferences: " + json);
        return json;
    }

    public void AtualizarFarmacias() {
        (new AsyncTask<String, Void, String>() {

            private ProgressDialog progressDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(FarmaciasPlantaoActivity.this, "Aguarde", "Atualizando dados");
            }

            @Override
            protected String doInBackground(String... params) {
                String json = null;
                try {
                    String url = params[0];
                    json = HttpHelper.downloadFromURL(url);
                    Log.i(TAG, json);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, String.format(getString(R.string.msg_erro_json), e.getMessage()));
                }

                return json;
            }

            @Override
            protected void onPostExecute(String json) {
                super.onPostExecute(json);

                progressDialog.dismiss();

                if (json == null) {
                    Log.w(TAG, "JSON veio nulo!");
                    return;
                }

                PreferenceManager.getDefaultSharedPreferences(FarmaciasPlantaoActivity.this).edit()
                        .putString("farmacias.json", json)
                        .apply();

                preencherListFarmacias(json);
                exibirMsgAtualizacao(String.format("%d Empresas atualizadas.", comerciantesArray.size()));
                //marcaPatios()
            }
        }).execute(getString(R.string.url_farmacias_plantao));
    }

    private void exibirMsgAtualizacao(String mensagem) {
        AlertMensage.setMessageAlert(mensagem, this, "Aviso");
    }

    private void preencherListFarmacias(String json) {
        try {
            List<Comerciante> comerciantesList = JsonHelper.getList(json, Comerciante[].class);
            comerciantesArray = new ArrayList<>(comerciantesList);
            ComercianteAdapter comercianteAdapter = new ComercianteAdapter(this, comerciantesArray);
            ListView listView = (ListView) findViewById(R.id.listview_farmacia_plantao);
            UIHelper.setListViewHeightBasedOnChildren(listView);

            ArrayList<UtilidadePublicaFarmacia> utilidadeArray = new ArrayList<UtilidadePublicaFarmacia>();

            for (int i = 0; i < comerciantesList.size(); i++) {
                String dia = null;
                String nome = null;
                if(comerciantesList.get(i).getNome() != null){
                    String[] nomes = comerciantesList.get(i).getNome().split("-");
                    dia = nomes[0] + "-"+ nomes[1] + " - "+ nomes[2];
                    nome = nomes[3].trim();
                }

                UtilidadePublicaFarmacia utilidadePublica = new UtilidadePublicaFarmacia(dia, nome, comerciantesList.get(i).getTelefones().get(0).getNumero());
                utilidadeArray.add(utilidadePublica);
            }

            UtilidadeAdapterFarmacia utilidadeAdater = new UtilidadeAdapterFarmacia(this, utilidadeArray, this);
            listView.setAdapter(utilidadeAdater);
            UIHelper.setListViewHeightBasedOnChildren(listView);

        } catch (Exception e) {
            Log.e(TAG, String.format("Erro ao mostrar comerciantes: %s", e.getMessage()));
        }
    }

    @NonNull
    private ArrayList<UtilidadePublica> getFarmaciasPlantao() {
        ArrayList<UtilidadePublica> utilidadeArray = new ArrayList<UtilidadePublica>();

//        utilidadeArray.add(new UtilidadePublica("01-03 Pharmavida", "(32) 3281-0943"));
//        utilidadeArray.add(new UtilidadePublica("02-03 Pharmavida", "(32) 3215-1109"));
//        utilidadeArray.add(new UtilidadePublica("03-03 Pharmavida", "(32) 3276-1125"));
//        utilidadeArray.add(new UtilidadePublica("04-03 Pharmavida", "(32) 3281-0943"));
//        utilidadeArray.add(new UtilidadePublica("05-03 Pharmavida", "(32) 3215-1109"));
//        utilidadeArray.add(new UtilidadePublica("06-03 Pharmavida", "(32) 3276-1125"));
//        utilidadeArray.add(new UtilidadePublica("07-03 Pharmavida", "(32) 3281-0943"));
//        utilidadeArray.add(new UtilidadePublica("08-03 Pharmavida", "(32) 3215-1109"));
//        utilidadeArray.add(new UtilidadePublica("09-03 Pharmavida", "(32) 3276-1125"));
//        utilidadeArray.add(new UtilidadePublica("10-03 Rodrigues ", "(32) 3281-0943"));
//        utilidadeArray.add(new UtilidadePublica("11-03 Pharmavida", "(32) 3215-1109"));
//        utilidadeArray.add(new UtilidadePublica("12-03 Pharmavida", "(32) 3276-1125"));
//        utilidadeArray.add(new UtilidadePublica("13-03 Pharmavida", "(32) 3281-0943"));
//        utilidadeArray.add(new UtilidadePublica("14-03 Pharmavida", "(32) 3215-1109"));
//        utilidadeArray.add(new UtilidadePublica("15-03 Pharmavida", "(32) 3276-1125"));
//        utilidadeArray.add(new UtilidadePublica("16-03 Pharmavida", "(32) 3281-0943"));
//        utilidadeArray.add(new UtilidadePublica("17-03 Pharmavida", "(32) 3215-1109"));
//        utilidadeArray.add(new UtilidadePublica("18-03 Pharmavida", "(32) 3276-1125"));
//        utilidadeArray.add(new UtilidadePublica("19-03 Pharmavida", "(32) 3281-0943"));
//        utilidadeArray.add(new UtilidadePublica("20-03 Pharmavida", "(32) 3215-1109"));
//        utilidadeArray.add(new UtilidadePublica("21-03 Pharmavida", "(32) 3276-1125"));
//        utilidadeArray.add(new UtilidadePublica("22-03 Pharmavida", "(32) 3281-0943"));
//        utilidadeArray.add(new UtilidadePublica("23-03 Pharmavida", "(32) 3215-1109"));
//        utilidadeArray.add(new UtilidadePublica("24-03 Pharmavida", "(32) 3276-1125"));
//        utilidadeArray.add(new UtilidadePublica("25-03 Pharmavida", "(32) 3276-1125"));
//        utilidadeArray.add(new UtilidadePublica("26-03 Pharmavida", "(32) 3281-0943"));
//        utilidadeArray.add(new UtilidadePublica("27-03 Pharmavida", "(32) 3215-1109"));
//        utilidadeArray.add(new UtilidadePublica("28-03 Pharmavida", "(32) 3276-1125"));
//        utilidadeArray.add(new UtilidadePublica("29-03 Pharmavida", "(32) 3215-1109"));
//        utilidadeArray.add(new UtilidadePublica("30-03 Pharmavida", "(32) 3276-1125"));
//        utilidadeArray.add(new UtilidadePublica("31-03 Pharmavida", "(32) 3215-1109"));

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
