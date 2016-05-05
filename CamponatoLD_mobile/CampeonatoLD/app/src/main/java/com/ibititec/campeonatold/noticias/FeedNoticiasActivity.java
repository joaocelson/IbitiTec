package com.ibititec.campeonatold.noticias;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.ibititec.campeonatold.MainActivity;
import com.ibititec.campeonatold.R;
import com.ibititec.campeonatold.adapter.AdapterNoticia;
import com.ibititec.campeonatold.helpers.HttpHelper;
import com.ibititec.campeonatold.helpers.JsonHelper;
import com.ibititec.campeonatold.helpers.UIHelper;
import com.ibititec.campeonatold.modelo.Noticia;

import java.io.IOException;
import java.util.List;

public class FeedNoticiasActivity extends AppCompatActivity {

    private ListView lvFeedNoticias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_noticias);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //AQUI SERÁ O BOTÃO PARA ADICIONAR NOTÍCIA
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        lerIntent();
        carregarComponentes();
        executarAcoes();
        donwnloadFromUrl("feedNoticias", getString(R.string.url_feed_noticias),"");
    }

    private void lerIntent() {
        if (HttpHelper.existeConexao(this)) {
            Intent intent = getIntent();

        }
    }

    private void executarAcoes() {
    }

    private void exibirMensagem(String mensagem, String titulo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //define o titulo
        builder.setTitle(titulo);
        //define a mensagem
        builder.setMessage(mensagem);
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

    private void exibirMensagemOK(String mensagem, String titulo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //define o titulo
        builder.setTitle(titulo);
        //define a mensagem
        builder.setMessage(mensagem);
        //define um botão como positivo
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {

                onBackPressed();
                // Toast.makeText(MainActivity.this, "positivo=" + arg1, Toast.LENGTH_SHORT).show();
            }
        });
        //cria o AlertDialog
        AlertDialog alerta = builder.create();
        //Exibe
        alerta.show();
    }
    private void carregarComponentes() {
        lvFeedNoticias = (ListView) findViewById(R.id.lvFeedNoticias);
    }

    private void donwnloadFromUrl(final String nomeJsonParam, String urlJson, final String parametro) {
        (new AsyncTask<String, Void, String>() {
            ProgressDialog progressDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(FeedNoticiasActivity.this, "Aguarde", "Atualizando dados");
            }

            @Override
            protected String doInBackground(String... params) {
                String json = null;
                try {
                    String url = params[0];
                    //json = HttpHelper.POSTJson(url, parametro);

                    if (!HttpHelper.existeConexao(FeedNoticiasActivity.this)) {
                        exibirMensagem("Não identificado conexão com a internet, verifique se sua conexão está ativa.", "Atenção");
                    } else {
                        json = HttpHelper.POSTJson(getString(R.string.url_partida_ao_vivo), parametro);
                    }
                    Log.i(MainActivity.TAG, json);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(MainActivity.TAG, String.format(getString(R.string.msg_erro_json), e.getMessage()));
                }
                return json;
            }

            @Override
            protected void onPostExecute(String json) {
                super.onPostExecute(json);


                if (json.equals("")) {
                    exibirMensagem("Nenhuma notícia cadastrada.", "Notícias");

                } else {
                    carregaListaNoticias(json);
                }
                progressDialog.dismiss();
            }
        }).execute(urlJson);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.ao_vivo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_atualizar_aoVivo:
                donwnloadFromUrl("aovivo", getString(R.string.url_feed_noticias),"");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void carregaListaNoticias(String json) {
        try {
            List<Noticia> listNoticias = JsonHelper.getList(json, Noticia[].class);
            if (listNoticias != null && listNoticias.size() > 0) {

                AdapterNoticia adapterNoticia = new AdapterNoticia(this, listNoticias);
                lvFeedNoticias.setAdapter(adapterNoticia);
                UIHelper.setListViewHeightBasedOnChildren(lvFeedNoticias);
            } else {
                exibirMensagemOK("Nenhuma notícia cadastrada", "Notícias");
            }
        }catch (Exception ex){
            Log.i(MainActivity.TAG, "Erro ao carregar lista de notícias.");
        }
    }
}
