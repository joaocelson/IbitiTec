package com.ibititec.ldapp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.appodeal.ads.Appodeal;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.android.gms.ads.AdRequest;
import com.ibititec.ldapp.Ibitipoca.IbitipocaMainActivity;
import com.ibititec.ldapp.adapter.ComercianteAdapter;
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
    private ListView lsViewComerciantes;
    private ProgressBar progressBar;
    private Comerciante comerciante;
    private List<String> listComerciantes;
    ImageView imgBuscarEmpresa;
    private AutoCompleteTextView actv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actv = (AutoCompleteTextView)findViewById(R.id.text_view_procurar_empresa);//new AutoCompleteTextView(MainActivity.this);


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
        Appodeal.show(this, Appodeal.BANNER_BOTTOM);

        // //INICIALIZACAO DO FRESCO
        Fresco.initialize(this);

        progressBar = (ProgressBar) findViewById(R.id.progress_comerciantes_main);
        progressBar.setVisibility(View.VISIBLE);

        setupComerciantes();

        imgBuscarEmpresa = (ImageView) findViewById(R.id.procurar_empresa);

        imgBuscarEmpresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscaComerciante(actv.getText().toString().trim());
            }
        });

        progressBar.setVisibility(View.GONE);

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

//        TextView txView = (TextView) findViewById(R.id.text_view_procurar_empresa);
//        txView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.i(TAG, "Clicou no texto.");
//                //procurarEmpresa();
//            }
//        });

        setarAutomaComplete();

    }

    private void setarAutomaComplete() {

        actv.setSingleLine();
        actv.setThreshold(1);
        actv.setAllCaps(true);
        actv.setFocusable(false);
//        LinearLayout.LayoutParams layoutParams =
//                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                        ViewGroup.LayoutParams.WRAP_CONTENT);
//        actv.setLayoutParams(layoutParams);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_list_item_1,
                listComerciantes);
        actv.setAdapter(adapter);

        actv.setImeOptions(EditorInfo.IME_ACTION_SEARCH);

        //Fazer a busca de pátio
//        final AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
//                .setTitle("Busca")
//                .setPositiveButton("Busca", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        buscaComerciante(actv.getText().toString().trim());
//                        //buscaComerciante(actv.getText().toString().trim().toUpperCase());
//                    }
//                })
//                .setNegativeButton("Cancela", null)
//                .setView(actv)
//                .show();

//        actv.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                buscaComerciante(actv.getText().toString().trim());
//                alertDialog.dismiss();

//                return true;
//            }
//        });
    }

    private void StartarActivityDetalhe() {
        Intent i = new Intent(this, DetalheActivity.class);

        // Seta num campo estático da ActivityB
        i.putExtra("comerciante", (Serializable) comerciante);

        startActivity(i);
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
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
            ComercianteAdapter comercianteAdapter = new ComercianteAdapter(this, comerciantesArray);
            ListView listView = (ListView) findViewById(R.id.listview_comerciantes_main);
            listView.setAdapter(comercianteAdapter);
            listComerciantes = comercianteAdapter.listComerciantes;
            UIHelper.setListViewHeightBasedOnChildren(listView);


        } catch (Exception e) {
            Log.e(TAG, String.format("Erro ao mostrar comerciantes: %s", e.getMessage()));
        }
    }

    private void buscaComerciante(String comerciante) {
        Log.i(TAG, String.format("Busca: %s", comerciante));

        if (comerciante.isEmpty()) {
            //Snackbar.make(findViewById(R.id.fab), "Busca vazia!", Snackbar.LENGTH_SHORT).show();
            Snackbar.make(findViewById(R.id.listview_comerciantes_main), "Digite o nome de uma empresa.", Snackbar.LENGTH_SHORT).show();
            return;
        }else {
            ArrayList<Comerciante> comerciantesArrayPesquisa = new ArrayList<Comerciante>();

            for (Comerciante c : comerciantesArray) {
                if(c.getNome().equals(comerciante)){
                    this.comerciante = c;
                    StartarActivityDetalhe();
                }

//                if(comerciante.startsWith(c.getTipoComerciante().getDescricacao())){
//                    comerciantesArrayPesquisa.add(c);
//                    atualizarListViewComerciante(comerciantesArrayPesquisa);
//                }

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
        // Handle navigation view item clicks here.
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
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
