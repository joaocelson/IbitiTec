package com.ibititec.ldapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.ibititec.ldapp.helpers.ComercianteAdapter;
import com.ibititec.ldapp.helpers.HttpHelper;
import com.ibititec.ldapp.helpers.JsonHelper;
import com.ibititec.ldapp.helpers.UIHelper;
import com.ibititec.ldapp.models.Comerciante;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "LOG";
    public static final String PATH_FOTOS = "http://52.37.37.207:86/Comerciante/GetFotosComerciantes/";
    ArrayList<Comerciante> comerciantesArray = new ArrayList<Comerciante>();
    private ListView lsViewComerciantes;
    private ProgressBar progressBar;
    private  Comerciante comerciante;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        lsViewComerciantes = (ListView) findViewById(R.id.listview_comerciantes);

        //INICIALIZACAO DO FRESCO
        Fresco.initialize(this);

        progressBar = (ProgressBar) findViewById(R.id.progress_comerciantes);
        progressBar.setVisibility(View.VISIBLE);

        setupComerciantes();

        progressBar.setVisibility(View.GONE);

        lsViewComerciantes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> myAdapter, View myView, int myItemInt, long mylng) {
               try{
                   comerciante = (Comerciante) (lsViewComerciantes.getItemAtPosition(myItemInt));
                   //exibirMsgAtualizacao("Selecionado o item: " + comerciante.getNome());
                   StartarActivityDetalhe();
               }catch (Exception ex){
                   ex.getMessage();
               }
            }
        });
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_atualizar) {

            new AlertDialog.Builder(this)
                    .setTitle("Download")
                    .setMessage("Deseja atualizar os dados das empresas?")
                    .setPositiveButton("Sim, baixar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Log.i(TAG, "Usuário pediu atualização.");
                            AtualizarComerciantes();
                        }
                    })
                    .setNegativeButton("Cancelar", null)
                    .show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupComerciantes() {
        String json = leJsonComerciantes();

        if (json.isEmpty()) {
            Log.i(TAG, "Baixou comerciantes.");
            AtualizarComerciantes();
        } else {
            Log.i(TAG, "Carregou comerciantes salvos");
            preencherListComerciantes(json);
            //marcaPatios();
        }
    }

    private String leJsonComerciantes() {
        String json = PreferenceManager.getDefaultSharedPreferences(MainActivity.this)
                .getString("comerciantes.json", "");
        Log.i(TAG, "Lendo preferences: " + json);
        return json;
    }

    private void AtualizarComerciantes() {
        (new AsyncTask<String, Void, String>() {

            private ProgressDialog progressDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(MainActivity.this, "Aguarde", "Atualizando dados");
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

                PreferenceManager.getDefaultSharedPreferences(MainActivity.this).edit()
                        .putString("comerciantes.json", json)
                        .apply();

                preencherListComerciantes(json);
                exibirMsgAtualizacao(String.format("%d Empresas atualizadas.", comerciantesArray.size()));
                //marcaPatios()
            }


        }).execute(getString(R.string.url));
    }

    private void exibirMsgAtualizacao(String mensagem) {
       // Snackbar.make(findViewById(R.id.fab), String.format("%d Dados atualizados.", patios.size()), Snackbar.LENGTH_SHORT).show();
        Snackbar.make(findViewById(R.id.fab), mensagem, Snackbar.LENGTH_SHORT).show();
    }

    private void preencherListComerciantes(String json) {
        try {
            List<Comerciante> comerciantesList = JsonHelper.getList(json, Comerciante[].class);
            comerciantesArray = new ArrayList<>(comerciantesList);

            ListView listView = (ListView) findViewById(R.id.listview_comerciantes);
            listView.setAdapter(new ComercianteAdapter(this, comerciantesArray));
            UIHelper.setListViewHeightBasedOnChildren(listView);
        } catch (Exception e) {
            Log.e(TAG, String.format("Erro ao mostrar comerciantes: %s", e.getMessage()));
        }
    }



    private void StartarActivityDetalhe() {
        Intent i = new Intent(this, DetalheActivity.class);

        // Seta num campo estático da ActivityB
        i.putExtra("comerciante", (Serializable) comerciante);

        startActivity(i);
    }
}
