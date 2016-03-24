package com.ibititec.ldapp.Ibitipoca;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.ibititec.ldapp.R;
import com.ibititec.ldapp.adapter.ComercianteAdapter;
import com.ibititec.ldapp.helpers.HttpHelper;
import com.ibititec.ldapp.helpers.JsonHelper;
import com.ibititec.ldapp.helpers.UIHelper;
import com.ibititec.ldapp.models.Comerciante;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PousadasChalesActivity extends AppCompatActivity {

    private ListView lsViewComerciantes;
    ArrayList<Comerciante> comerciantesArray = new ArrayList<Comerciante>();
    private static final String TAG = "POUSADA";

    private ListView lsViewPousadasComerciantes;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pousadas_chales);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lsViewPousadasComerciantes = (ListView) findViewById(R.id.listview_pousadas_chales);
//171940
        //INICIALIZACAO DO FRESCO
        Fresco.initialize(this);

        progressBar = (ProgressBar) findViewById(R.id.progress_pousadas_chales);
        progressBar.setVisibility(View.VISIBLE);

        setupComerciantes();

        progressBar.setVisibility(View.GONE);

        //setupFab();

        lsViewPousadasComerciantes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> myAdapter, View myView, int myItemInt, long mylng) {
                try {
                    //comerciante = (Comerciante) (lsViewComerciantes.getItemAtPosition(myItemInt));
                    //exibirMsgAtualizacao("Selecionado o item: " + comerciante.getNome());
                    // StartarActivityDetalhe();
                } catch (Exception ex) {
                    ex.getMessage();
                }
            }
        });
        //Appodeal.show(this, Appodeal.BANNER_BOTTOM);
    }

    public void setupComerciantes() {
        String json = leJsonComerciantes();

        if (json.isEmpty()) {
            Log.i(TAG, "Baixou comerciantes.");
            AtualizarPousadas();
        } else {
            Log.i(TAG, "Carregou comerciantes salvos");
            preencherListComerciantes(json);
            //marcaPatios();
        }
    }

    private String leJsonComerciantes() {
        String json = PreferenceManager.getDefaultSharedPreferences(PousadasChalesActivity.this)
                .getString("pousadas.json", "");
        Log.i(TAG, "Lendo preferences: " + json);
        return json;
    }

    public void AtualizarPousadas() {
        (new AsyncTask<String, Void, String>() {

            private ProgressDialog progressDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(PousadasChalesActivity.this, "Aguarde", "Atualizando dados");
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

                PreferenceManager.getDefaultSharedPreferences(PousadasChalesActivity.this).edit()
                        .putString("pousadas.json", json)
                        .apply();

                preencherListComerciantes(json);
                exibirMsgAtualizacao(String.format("%d Empresas atualizadas.", comerciantesArray.size()));
                //marcaPatios()
            }


        }).execute(getString(R.string.url_pousadas));
    }

    private void exibirMsgAtualizacao(String mensagem) {
        // Snackbar.make(findViewById(R.id.fab), String.format("%d Dados atualizados.", patios.size()), Snackbar.LENGTH_SHORT).show();
        Snackbar.make(findViewById(R.id.fab), mensagem, Snackbar.LENGTH_SHORT).show();
    }

    private void preencherListComerciantes(String json) {
        try {
            List<Comerciante> comerciantesList = JsonHelper.getList(json, Comerciante[].class);
            comerciantesArray = new ArrayList<>(comerciantesList);
            ComercianteAdapter comercianteAdapter = new ComercianteAdapter(this, comerciantesArray);
            ListView listView = (ListView) findViewById(R.id.listview_pousadas_chales);
            listView.setAdapter(comercianteAdapter);
            //listComerciantes = comercianteAdapter.listComerciantes;
            UIHelper.setListViewHeightBasedOnChildren(listView);


        } catch (Exception e) {
            Log.e(TAG, String.format("Erro ao mostrar comerciantes: %s", e.getMessage()));
        }
    }

}
