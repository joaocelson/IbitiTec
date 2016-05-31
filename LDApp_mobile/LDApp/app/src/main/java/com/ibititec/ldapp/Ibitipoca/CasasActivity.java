package com.ibititec.ldapp.Ibitipoca;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.ibititec.ldapp.DetalheActivity;
import com.ibititec.ldapp.R;
import com.ibititec.ldapp.adapter.ComercianteAdapter;
import com.ibititec.ldapp.helpers.AlertMensage;
import com.ibititec.ldapp.helpers.HttpHelper;
import com.ibititec.ldapp.helpers.JsonHelper;
import com.ibititec.ldapp.helpers.UIHelper;
import com.ibititec.ldapp.models.Comerciante;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CasasActivity extends AppCompatActivity {
    Comerciante comerciante =null;
    private ListView lsViewComerciantes;
    private ProgressBar progressBar;
    ArrayList<Comerciante> comerciantesArray = new ArrayList<Comerciante>();

    private static final String TAG = "CASA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_casas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lsViewComerciantes = (ListView) findViewById(R.id.listview_casas_ibitipoca);

        //INICIALIZACAO DO FRESCO
        Fresco.initialize(this);

        progressBar = (ProgressBar) findViewById(R.id.progress_casas);
        progressBar.setVisibility(View.VISIBLE);

        setupCasas();

        progressBar.setVisibility(View.GONE);

        lsViewComerciantes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> myAdapter, View myView, int myItemInt, long mylng) {
                try {
                   Comerciante comerciante = (Comerciante) (lsViewComerciantes.getItemAtPosition(myItemInt));
                   // exibirMsgAtualizacao("Selecionado o item: " + comerciante.getNome());
                    StartarActivityDetalhe();
                } catch (Exception ex) {
                    ex.getMessage();
                }
            }
        });
        //Appodeal.show(this, Appodeal.BANNER_BOTTOM);
    }

    private void StartarActivityDetalhe() {
        Intent i = new Intent(this, DetalheActivity.class);

        // Seta num campo estático da ActivityB
        i.putExtra("comerciante", (Serializable) comerciante);

        startActivity(i);
    }

    public void setupCasas() {
        ProgressDialog progressDialog;
        progressDialog = ProgressDialog.show(CasasActivity.this, "Aguarde", "Carregando os dados!");
        String json = leJsonComerciantes();

        if (json.isEmpty() || json.equals("[]")) {
            Log.i(TAG, "Baixou comerciantes.");
            progressDialog.dismiss();
            AtualizarCasas();
        } else {
            Log.i(TAG, "Carregou comerciantes salvos");
            preencherListCasas(json);
            progressDialog.dismiss();
        }
    }

    private String leJsonComerciantes() {
        String json = PreferenceManager.getDefaultSharedPreferences(CasasActivity.this)
                .getString("casas.json", "");
        Log.i(TAG, "Lendo preferences: " + json);
        return json;
    }

    public void AtualizarCasas() {
        (new AsyncTask<String, Void, String>() {

            private ProgressDialog progressDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(CasasActivity.this, "Aguarde", "Atualizando dados");
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

                if (json == null) {
                    Log.w(TAG, "JSON veio nulo!");
                    progressDialog.dismiss();
                    return;
                }

                PreferenceManager.getDefaultSharedPreferences(CasasActivity.this).edit()
                        .putString("casas.json", json)
                        .apply();

                preencherListCasas(json);
                progressDialog.dismiss();
                exibirMsgAtualizacao(String.format("%d Empresas atualizadas.", comerciantesArray.size()));
            }


        }).execute(getString(R.string.url_casas));
    }

    private void exibirMsgAtualizacao(String mensagem) {
        // Snackbar.make(findViewById(R.id.fab), String.format("%d Dados atualizados.", patios.size()), Snackbar.LENGTH_SHORT).show();
        AlertMensage.setMessageAlert(mensagem, this, "Aviso");
    }

    private void preencherListCasas(String json) {
        try {
            Log.i(TAG, "Inicio preenchimento lista casa.");
            List<Comerciante> comerciantesList = JsonHelper.getList(json, Comerciante[].class);
            comerciantesArray = new ArrayList<>(comerciantesList);
            ComercianteAdapter comercianteAdapter = new ComercianteAdapter(this, comerciantesArray);
            ListView listView = (ListView) findViewById(R.id.listview_casas_ibitipoca);
            listView.setAdapter(comercianteAdapter);
            //listComerciantes = comercianteAdapter.listComerciantes;
            UIHelper.setListViewHeightBasedOnChildren(listView);
            Log.i(TAG, "Fim preenchimento lista casa.");

        } catch (Exception e) {
            Log.e(TAG, String.format("Erro ao mostrar casas: %s", e.getMessage()));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_atualizar) {

            new AlertDialog.Builder(this)
                    .setTitle("Download")
                    .setMessage("Deseja atualizar os dados das casas?")
                    .setPositiveButton("Sim, baixar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Log.i(TAG, "Usuário pediu atualização.");
                            AtualizarCasas();
                        }
                    })
                    .setNegativeButton("Cancelar", null)
                    .show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
