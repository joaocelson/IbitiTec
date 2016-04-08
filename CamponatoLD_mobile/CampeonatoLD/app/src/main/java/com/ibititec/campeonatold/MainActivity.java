package com.ibititec.campeonatold;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
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
import android.widget.ImageButton;

import com.appodeal.ads.Appodeal;
import com.ibititec.campeonatold.helpers.HttpHelper;
import com.ibititec.campeonatold.helpers.JsonHelper;
import com.ibititec.campeonatold.util.AnalyticsApplication;

import java.io.IOException;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //DECLARACAO DOS OBJETOS DE TELA
    private ImageButton btnPrimeiraDivisao, btnSegundaDivisao;

    boolean haveConnectedWifi = false;
    boolean haveConnectedMobile = false;

    // private ProgressDialog progressDialog;

    //CONSTANTES NOME DO JSON NA BASE DE DADOS
    public static final String PDARTILHARIA = "pdartilharia", PDTABELA = "pdtabela", PDCLASSIFICACAO = "pdclassificacao",
            SDARTILHARIA = "sdartilharia", SDTABELA = "dstabela", SDCLASSIFICACAO = "sdclassificacao";
    public static final String TAG = "CAMPEONATOLD";
    public static final String PATH_FOTOS = "http://52.37.37.207:88/Campeonato/Image?nomeimagem=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        //AS DUAS LINHAS ABAIXO DESABILITAR A BARRA DE MENU
        toggle.setDrawerIndicatorEnabled(false);
        //toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //IDENTIFICACAO DOS OBJETOS DE LAYOUT
        btnPrimeiraDivisao = (ImageButton) findViewById(R.id.btnPrimeiraDivisao);
        btnSegundaDivisao = (ImageButton) findViewById(R.id.btnSegundaDivisao);


    }

    private void iniciarAppodeal() {
        Appodeal.show(this, Appodeal.BANNER_BOTTOM);
    }

    @Override
    public void onResume() {
        super.onResume();
        //METODOS DOS CLICKS
        executarAcoes();

        atualizarBaseDados(false);
        AnalyticsApplication.enviarGoogleAnalitcs(this);
        iniciarAppodeal();
    }

    private void atualizarBaseDados(boolean atualizar) {
        try {

            if (JsonHelper.leJsonBancoLocal(MainActivity.PDTABELA, this) == "" || atualizar == true) {
                if (existeConexao()) {
                    donwnloadFromUrl(PDTABELA, getString(R.string.url_pdtabela));
                    donwnloadFromUrl(PDARTILHARIA, getString(R.string.url_pdartilharia));
                    donwnloadFromUrl(PDCLASSIFICACAO, getString(R.string.url_pdclassificacao));
                    donwnloadFromUrl(SDTABELA, getString(R.string.url_sdtabela));
                    donwnloadFromUrl(SDARTILHARIA, getString(R.string.url_sdartilharia));
                    donwnloadFromUrl(SDCLASSIFICACAO, getString(R.string.url_sdclassificacao));
                } else {
                    Log.i(TAG, "Sem conexão com a internet.");
                }
            }
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
        if (id == R.id.action_atualizar) {
            atualizarBaseDados(true);
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
            ProgressDialog progressDialog;

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

    private void exibirMensagem() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //define o titulo
        builder.setTitle("Atenção");
        //define a mensagem
        builder.setMessage("Não identificado conexão com a internet, verifique sua conexão está ativa.");
        //define um botão como positivo
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                return;
                // Toast.makeText(MainActivity.this, "positivo=" + arg1, Toast.LENGTH_SHORT).show();
            }
        });

        //cria o AlertDialog
        AlertDialog alerta = builder.create();
        //Exibe
        alerta.show();
    }

    public boolean existeConexao() {
        ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        try {
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo[] netInfo = cm.getAllNetworkInfo();
            for (NetworkInfo ni : netInfo) {
                if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                    if (ni.isConnected())
                        haveConnectedWifi = true;
                if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                    if (ni.isConnected())
                        haveConnectedMobile = true;
            }
            if (!haveConnectedWifi && !haveConnectedMobile) {
                exibirMensagem();
            }
            return haveConnectedWifi || haveConnectedMobile;

        } catch (Exception ex) {
            Log.i(MainActivity.TAG, "Erro ao verificar conexao com a intenet." + ex.getMessage());
            return false;
        }
    }

}
