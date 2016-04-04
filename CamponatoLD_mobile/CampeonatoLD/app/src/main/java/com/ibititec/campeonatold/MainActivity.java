package com.ibititec.campeonatold;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.ibititec.campeonatold.helpers.HttpHelper;

import java.io.IOException;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //DECLARACAO DOS OBJETOS DE TELA
    private ImageButton btnPrimeiraDivisao, btnSegundaDivisao;
    private ProgressDialog progressDialog;

    //CONSTANTES NOME DO JSON NA BASE DE DADOS
    public static final String PDARTILHARIA = "pdartilharia", PDTABELA = "pdtabela", PDCLASSIFICACAO = "pdclassificacao",
            SDARTILHARIA = "sdartilharia", SDTABELA = "dstabela", SDCLASSIFICACAO = "sdclassificacao";
    public static final String TAG = "CAMPEONATOLD";

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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //IDENTIFICACAO DOS OBJETOS DE LAYOUT
        btnPrimeiraDivisao = (ImageButton) findViewById(R.id.btnPrimeiraDivisao);
        btnSegundaDivisao = (ImageButton) findViewById(R.id.btnSegundaDivisao);

        //METODOS DOS CLICKS
        executarAcoes();

        atualizarBaseDados();

    }

    private void atualizarBaseDados() {
        try {
            donwnloadFromUrl(PDTABELA, getString(R.string.url_pdtabela));

//            donwnloadFromUrl(PDARTILHARIA, getString(R.string.url_pdartilharia));
//
//            donwnloadFromUrl(PDCLASSIFICACAO, getString(R.string.url_pdclassificacao));
//
//            donwnloadFromUrl(SDTABELA, getString(R.string.url_sdtabela));
//
//            donwnloadFromUrl(SDARTILHARIA, getString(R.string.url_sdartilharia));
//
//            donwnloadFromUrl(SDCLASSIFICACAO, getString(R.string.url_sdclassificacao));

        } catch (Exception ex) {
            Log.i(TAG, "Não foi possível atualizar  base de dados." + ex.getMessage());
        }
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

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camara) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void executarAcoes() {

        btnPrimeiraDivisao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startarActivity("primeira");
            }
        });

        btnSegundaDivisao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startarActivity("segunda");
            }
        });
    }

    private void startarActivity(String divisao) {
        Intent intent = new Intent(this, PrimeiraDivisaoActivity.class);
        intent.putExtra("divisao", divisao);
        startActivity(intent);
    }

    private void gravarJson(String nomeJson, String json) {
        try {
            PreferenceManager.getDefaultSharedPreferences(MainActivity.this).edit()
                    .putString(nomeJson + ".json", json)
                    .apply();
            Log.i(TAG, "Gravou Json: " + json);
        } catch (Exception ex) {
            Log.i(TAG, "Erro no metodo:  gravarJson: " + nomeJson + " - Erro: " + ex.getMessage());
        }
    }

    private void donwnloadFromUrl(final String nomeJsonParam, String urlJson) {
        (new AsyncTask<String, Void, String>() {

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
                        .putString(nomeJsonParam + ".json", json)
                        .apply();
            }
        }).execute(urlJson);
    }

}
