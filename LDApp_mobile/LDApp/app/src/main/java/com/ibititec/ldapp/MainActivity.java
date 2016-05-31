package com.ibititec.ldapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.appodeal.ads.Appodeal;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.android.gms.ads.AdRequest;
import com.ibititec.ldapp.Ibitipoca.IbitipocaMainActivity;
import com.ibititec.ldapp.adapter.ComercianteAdapter;
import com.ibititec.ldapp.helpers.AlertMensage;
import com.ibititec.ldapp.helpers.HttpHelper;
import com.ibititec.ldapp.helpers.JsonHelper;
import com.ibititec.ldapp.helpers.UIHelper;
import com.ibititec.ldapp.models.Comerciante;
import com.ibititec.ldapp.utilidade_publica.UtilidadePulicaActivity;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String TAG = "LOG";
    public static final String PATH_FOTOS = "http://52.37.37.207:86/Comerciante/GetFotosComerciantes/";
    ArrayList<Comerciante> comerciantesArray = new ArrayList<Comerciante>();
    ArrayList<Comerciante> comerciantesArrayBusca = new ArrayList<Comerciante>();
    private ListView lsViewComerciantes;
    private ProgressBar progressBar;
    private Comerciante comerciante;
    List<Comerciante> comerciantesList;
    private List<String> listComerciantes;
    ImageView imgBuscarEmpresa;
    private AutoCompleteTextView actv;
    private AlertDialog alerta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actv = (AutoCompleteTextView)findViewById(R.id.text_view_procurar_empresa);//new AutoCompleteTextView(MainActivity.this);

        abrirAlerta();

        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(actv.getWindowToken(), 0);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //APPODEAL
        String appKey = "6ebf1bff27e0009e1dda34ee520c5bb456ddf692359e1628";
        Appodeal.initialize(this, appKey, Appodeal.BANNER | Appodeal.INTERSTITIAL);
        Appodeal.setTesting(true);
        AdRequest request = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)        // All emulators
                .build();


        lsViewComerciantes = (ListView) findViewById(R.id.listview_comerciantes_main);

        //NAVEGACAO DO MENU
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //CLICK DA BARRA DE NAVEGACAO
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //APPODEAL
        //Appodeal.show(this, Appodeal.BANNER_BOTTOM);

        // //INICIALIZACAO DO FRESCO
        Fresco.initialize(this);

        progressBar = (ProgressBar) findViewById(R.id.progress_comerciantes_main);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);

        imgBuscarEmpresa = (ImageView) findViewById(R.id.procurar_empresa);

        setupComerciantes();
        setarAutomaComplete();
        buscarComercianteEvento();
        startarActivityDetalhePorComerciante();
    }

    private void startarActivityDetalhePorComerciante() {
        //CLICK LISTVIEW
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
    }

    private void buscarComercianteEvento() {
        actv.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                buscaComerciante(actv.getText().toString().trim());
                return true;
            }
        });

        imgBuscarEmpresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscaComerciante(actv.getText().toString().trim());
            }
        });
    }

    private void setarAutomaComplete() {
        actv.setSingleLine();
        actv.setThreshold(1);
        actv.setAllCaps(true);

        //lsViewComerciantes.setFocusable(true);
//        LinearLayout.LayoutParams layoutParams =
//                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                        ViewGroup.LayoutParams.WRAP_CONTENT);
//        actv.setLayoutParams(layoutParams);

//        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this,
//                android.R.layout.simple_list_item_1,
//                listComerciantes);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_list_item_1,
                listComerciantes);
        actv.setAdapter(adapter);

        actv.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
    }

    private void StartarActivityDetalhe() {
        Intent i = new Intent(this, DetalheActivity.class);
        i.putExtra("comerciante", (Serializable) comerciante);
        startActivity(i);
    }

    public void abrirAlerta() {

        LayoutInflater li = getLayoutInflater();
        //inflamos o layout alerta.xml na view
        View view = li.inflate(R.layout.alerta, null);
        //definimos para o botão do layout um clickListener
        view.findViewById(R.id.bt).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                alerta.dismiss();
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Propaganda");
        builder.setView(view);
        alerta = builder.create();
        alerta.show();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        switch (id) {
            // Id correspondente ao botão Up/Home da actionbar
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }

        if (id == R.id.action_atualizar) {

            new AlertDialog.Builder(this)
                    .setTitle("Download")
                    .setMessage("Deseja atualizar os dados das empresas?")
                    .setPositiveButton("Sim, baixar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Log.i(TAG, "Usuário pediu atualização.");
                            AtualizarEmpresasPublicidade();
                            AtualizarComerciantes();
                            AtualizarRestaurantesIbitipoca();
                            AtualizarCasasIbitipoca();
                            AtualizarFarmaciasPlantao();
                            AtualizarPousadas();
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
            AtualizarEmpresasPublicidade();
            preencherListComerciantesBusca(leJsonComerciantesBusca());
        } else {
            Log.i(TAG, "Carregou comerciantes salvos");
            preencherListComerciantes(json);
            preencherListComerciantesBusca(leJsonComerciantesBusca());
        }
    }

    private String leJsonComerciantes() {
        String json = PreferenceManager.getDefaultSharedPreferences(MainActivity.this)
                .getString("publicidade.json", "");
        Log.i(TAG, "Lendo preferences publicidade: " + json);
        return json;
    }

    private String leJsonComerciantesBusca() {
        String json = PreferenceManager.getDefaultSharedPreferences(MainActivity.this)
                .getString("comerciantes.json", "");
        Log.i(TAG, "Lendo preferences comerciantes: " + json);
        return json;
    }

    private void exibirMsgAtualizacao(String mensagem) {
        // Snackbar.make(findViewById(R.id.fab), String.format("%d Dados atualizados.", patios.size()), Snackbar.LENGTH_SHORT).show();
       try {
           AlertMensage.setMessageAlert(mensagem, this, "Aviso");
       }catch (Exception e) {
        Log.i(TAG, " Erro ao mostrar mensagem " + e.getMessage());
       }
    }

    private void preencherListComerciantes(String json) {
        try {
            List<Comerciante> comerciantesList = JsonHelper.getList(json, Comerciante[].class);
            comerciantesArray = new ArrayList<>(comerciantesList);
            ComercianteAdapter comercianteAdapter = new ComercianteAdapter(this, comerciantesArray);
            ListView listView = (ListView) findViewById(R.id.listview_comerciantes_main);
            listView.setAdapter(comercianteAdapter);
            //listComerciantes = comercianteAdapter.listComerciantes;
            UIHelper.setListViewHeightBasedOnChildren(listView);
        } catch (Exception e) {
            Log.e(TAG, String.format("Erro ao mostrar comerciantes: %s", e.getMessage()));
        }
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

    private void atualizarListViewComerciante(ArrayList<Comerciante> comerciantes) {
        ComercianteAdapter comercianteAdapter = new ComercianteAdapter(MainActivity.this, comerciantes);
        lsViewComerciantes.setAdapter(comercianteAdapter);
        UIHelper.setListViewHeightBasedOnChildren(lsViewComerciantes);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_empresas) {
            Intent i = new Intent(this, ListComercianteActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_cidade) {
            Intent i = new Intent(this, CidadeActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_utilidade_publica) {
            Intent i = new Intent(this, UtilidadePulicaActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_ibitipoca) {
            Intent i = new Intent(this, IbitipocaMainActivity.class);
            startActivity(i);
        }else if (id == R.id.sair) {
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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

                //preencherListComerciantes(json);
                //exibirMsgAtualizacao(String.format("%d Empresas atualizadas.", comerciantesArray.size()));
                //marcaPatios()
            }


        }).execute(getString(R.string.url));
    }

    private void AtualizarCasasIbitipoca() {
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
                        .putString("casas.json", json)
                        .apply();

                //preencherListComerciantes(json);
                //exibirMsgAtualizacao(String.format("%d Empresas atualizadas.", comerciantesArray.size()));
                //marcaPatios()
            }


        }).execute(getString(R.string.url_casas));
    }

    private void AtualizarEmpresasPublicidade() {
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
                        .putString("publicidade.json", json)
                        .apply();

                //preencherListComerciantes(json);
                //exibirMsgAtualizacao(String.format("%d Empresas atualizadas.", comerciantesArray.size()));
                //marcaPatios()
            }


        }).execute(getString(R.string.url_publicidade));
    }

    private void AtualizarRestaurantesIbitipoca() {
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
                        .putString("restaurantes.json", json)
                        .apply();

                //preencherListComerciantes(json);
                //exibirMsgAtualizacao(String.format("%d Empresas atualizadas.", comerciantesArray.size()));
                //marcaPatios()
            }


        }).execute(getString(R.string.url_restaurantes));
    }

    private void AtualizarFarmaciasPlantao () {
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
                        .putString("farmacias.json", json)
                        .apply();

                //preencherListComerciantes(json);
                //exibirMsgAtualizacao(String.format("%d Empresas atualizadas.", comerciantesArray.size()));
                //marcaPatios()
            }
        }).execute(getString(R.string.url_farmacias_plantao));
    }

    public void AtualizarPousadas() {
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
                        .putString("pousadas.json", json)
                        .apply();

                preencherListComerciantes(json);
                exibirMsgAtualizacao(String.format("%d Empresas atualizadas.", comerciantesArray.size()));
                //marcaPatios()
            }


        }).execute(getString(R.string.url_pousadas));
    }

    public Context getActivity() {
        return this;
    }
}
