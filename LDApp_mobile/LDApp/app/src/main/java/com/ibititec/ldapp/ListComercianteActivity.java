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
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
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

public class ListComercianteActivity extends AppCompatActivity {

    public static final String TAG = "LOG";
    public static final String PATH_FOTOS = "http://52.37.37.207:86/Comerciante/GetFotosComerciantes/";
    ArrayList<Comerciante> comerciantesArray = new ArrayList<Comerciante>();
    private ListView lsViewComerciantes;
    private ProgressBar progressBar;
    private Comerciante comerciante;
    List<Comerciante> comerciantesList;
    private List<String> listComerciantes;
    ArrayList<Comerciante> comerciantesArrayBusca = new ArrayList<Comerciante>();
    FloatingActionButton fab;
    ImageView imgBuscarEmpresa;
    private AutoCompleteTextView actv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_comerciantes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        lsViewComerciantes = (ListView) findViewById(R.id.listview_comerciantes);

        //INICIALIZACAO DO FRESCO
        Fresco.initialize(this);

        progressBar = (ProgressBar) findViewById(R.id.progress_comerciantes);
        progressBar.setVisibility(View.VISIBLE);

        setupComerciantes();

        progressBar.setVisibility(View.GONE);

        setupFab();
        buscarComercianteEvento();

        lsViewComerciantes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> myAdapter, View myView, int myItemInt, long mylng) {
                try {
                    comerciante = (Comerciante) (lsViewComerciantes.getItemAtPosition(myItemInt));
                    //exibirMsgAtualizacao("Selecionado o item: " + comerciante.getNome());
                    StartarActivityDetalhe();
                } catch (Exception ex) {
                    ex.getMessage();
                }
            }
        });
        //Appodeal.show(this, Appodeal.BANNER_BOTTOM);
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

        if (json.isEmpty() || json.equals("[]")) {
            Log.i(TAG, "Baixou comerciantes.");
            AtualizarComerciantes();
            preencherListComerciantesBusca(leJsonComerciantes());
        } else {
            Log.i(TAG, "Carregou comerciantes salvos");
            preencherListComerciantes(json);
            preencherListComerciantesBusca(leJsonComerciantes());
        }
    }

    private String leJsonComerciantes() {
        String json = PreferenceManager.getDefaultSharedPreferences(ListComercianteActivity.this)
                .getString("comerciantes.json", "");
        Log.i(TAG, "Lendo preferences: " + json);
        return json;
    }

    private void preencherListComerciantesBusca(String json) {
        try {
            listComerciantes = new ArrayList<String>();
            comerciantesList = JsonHelper.getList(json, Comerciante[].class);
            comerciantesArrayBusca = new ArrayList<>(comerciantesList);
            for (int i =0; i< comerciantesArrayBusca.size(); i++){
                //listComerciantes.add(comerciantesArrayBusca.get(i).getTipoComercio().getDescricacao() + " - "
                //                    + comerciantesArrayBusca.get(i).getNome());
                listComerciantes.add(comerciantesArrayBusca.get(i).getNome());
            }
        } catch (Exception e) {
            Log.e(TAG, String.format("Erro ao mostrar comerciantes: %s", e.getMessage()));
        }
    }

    private void buscarComercianteEvento() {
        if(actv != null) {
            actv.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    buscaComerciante(actv.getText().toString().trim());
                    return true;
                }
            });
        }
    }

    private void AtualizarComerciantes() {
        (new AsyncTask<String, Void, String>() {

            private ProgressDialog progressDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(ListComercianteActivity.this, "Aguarde", "Atualizando dados");
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
                    return;
                }

                PreferenceManager.getDefaultSharedPreferences(ListComercianteActivity.this).edit()
                        .putString("comerciantes.json", json)
                        .apply();

                preencherListComerciantes(json);
                progressDialog.dismiss();
                exibirMsgAtualizacao(String.format("%d Empresas atualizadas.", comerciantesArray.size()));
                //marcaPatios()
            }


        }).execute(getString(R.string.url));
    }

    private void exibirMsgAtualizacao(String mensagem) {
        AlertMensage.setMessageAlert(mensagem, this, "Aviso");
    }

    private void preencherListComerciantes(String json) {
        try {
            List<Comerciante> comerciantesList = JsonHelper.getList(json, Comerciante[].class);
            comerciantesArray = new ArrayList<>(comerciantesList);
            ComercianteAdapter comercianteAdapter = new ComercianteAdapter(this, comerciantesArray);
            ListView listView = (ListView) findViewById(R.id.listview_comerciantes);
            listView.setAdapter(comercianteAdapter);
            listComerciantes = comercianteAdapter.listComerciantes;
            UIHelper.setListViewHeightBasedOnChildren(listView);
        } catch (Exception e) {
            Log.e(TAG, String.format("Erro ao mostrar comerciantes: %s", e.getMessage()));
        }
    }

    private void buscaComerciante(String comerciante) {
        Log.i(TAG, String.format("Busca: %s", comerciante));

        String comercianteBusca;
        if(comerciante.contains("-")) {
            comercianteBusca = comerciante.split("-")[1].trim();
        }else{
            comercianteBusca = comerciante;
        }
        if (comerciante.isEmpty()) {
            AlertMensage.setMessageAlert("Digite o nome de uma empresa.", this, "Atenção");
            return;
        }else {
            boolean encontrouregistro =false;
            for (Comerciante c : comerciantesArrayBusca) {
                if(c.getNome().equals(comercianteBusca)){
                    this.comerciante = c;
                    encontrouregistro = true;
                    StartarActivityDetalhe();
                }
            }
            if(!encontrouregistro) {
                exibirMsgAtualizacao("Nenhum resultado encontrado na pesquisa.");
                actv.setText("");
            }
        }
    }

    private void setupFab() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                actv = new AutoCompleteTextView(ListComercianteActivity.this);
                actv.setSingleLine();
                actv.setThreshold(1);
                actv.setAllCaps(true);

                LinearLayout.LayoutParams layoutParams =
                        new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT);
                actv.setLayoutParams(layoutParams);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(ListComercianteActivity.this,
                        android.R.layout.simple_list_item_1,
                        listComerciantes);
                actv.setAdapter(adapter);

                actv.setImeOptions(EditorInfo.IME_ACTION_SEARCH);

                //Fazer a busca de pátio
                final AlertDialog alertDialog = new AlertDialog.Builder(ListComercianteActivity.this)
                        .setTitle("Busca")
                        .setPositiveButton("Busca", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                buscaComerciante(actv.getText().toString().trim());
                                //buscaComerciante(actv.getText().toString().trim().toUpperCase());
                            }
                        })
                        .setNegativeButton("Cancela", null)
                        .setView(actv)
                        .show();

                actv.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        buscaComerciante(actv.getText().toString().trim());
                        return true;
                    }
                });

            }
        });
    }

    private void StartarActivityDetalhe() {
        Intent i = new Intent(this, DetalheActivity.class);

        // Seta num campo estático da ActivityB
        i.putExtra("comerciante", (Serializable) comerciante);

        startActivity(i);
    }
}
