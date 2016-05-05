package com.ibititec.campeonatold.bolao;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ListView;

import com.appodeal.ads.Appodeal;
import com.ibititec.campeonatold.MainActivity;
import com.ibititec.campeonatold.R;
import com.ibititec.campeonatold.adapter.AdapterClassificacao;
import com.ibititec.campeonatold.helpers.HttpHelper;
import com.ibititec.campeonatold.helpers.JsonHelper;
import com.ibititec.campeonatold.helpers.UIHelper;
import com.ibititec.campeonatold.modelo.Classificacao;
import com.ibititec.campeonatold.util.AnalyticsApplication;

import java.io.IOException;
import java.util.List;

public class ClassificacaoActivity extends AppCompatActivity {
    private ListView lvClassificacao;

    private String classificacao, divisao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classificacao);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        carregarComponentes();
        lerIntent();
        carregarPartidasPalpite();
    }

    private void carregarPartidasPalpite() {
        if (divisao.equals("primeira")) {
            donwnloadFromUrlParam(MainActivity.PDCLASSIFICACAOBOLAO, getString(R.string.url_pdclassificacaobolao), "1");
        } else {
            donwnloadFromUrlParam(MainActivity.SDCLASSIFICACAOBOLAO, getString(R.string.url_sdclassificacaobolao), "2");
        }
    }

    private void carregarComponentes() {
        lvClassificacao = (ListView) findViewById(R.id.listview_bolao_classificacao);
    }

    @Override
    public void onResume() {
        super.onResume();
        AnalyticsApplication.enviarGoogleAnalitcs(this);
        iniciarAppodeal();
    }

    private void iniciarAppodeal() {
        try {
            Appodeal.show(this, Appodeal.BANNER_TOP);
        } catch (Exception ex) {
            Log.i(MainActivity.TAG, "Erro: iniciarAppodeal: " + ex.getMessage());
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("divisao", divisao);

        // add data to Intent
        setResult(BolaoPrincipalActivity.RESULT_OK, intent);
        Appodeal.show(this, Appodeal.NATIVE);
        super.onBackPressed();
    }

    private void lerIntent() {
        Intent intent = getIntent();
        divisao = intent.getStringExtra("divisao");
    }

    private void atualizarClassificacaoBolao() {
        try {
            //cabecalhoLayout = (LinearLayout) findViewById(R.id.cabecalho_artilahria);
            //cabecalhoLayout.setVisibility(View.VISIBLE);
            if (divisao.equals("primeira")) {
                this.setTitle("Clas. Bolão 1ª Divisão");
                classificacao = JsonHelper.leJsonBancoLocal(MainActivity.PDCLASSIFICACAOBOLAO, this);
            } else {
                this.setTitle("Clas. Bolão 2ª Divisão");
                classificacao = JsonHelper.leJsonBancoLocal(MainActivity.SDCLASSIFICACAOBOLAO, this);
            }
            if (!classificacao.equals("")) {
                List<Classificacao> listClassificacao = JsonHelper.getList(classificacao, Classificacao[].class);
                if (listClassificacao != null && listClassificacao.size() > 0) {

                    AdapterClassificacao adapterArtilharia = new AdapterClassificacao(this, listClassificacao);
                    lvClassificacao.setAdapter(adapterArtilharia);
                    UIHelper.setListViewHeightBasedOnChildren(lvClassificacao);
                } else {
                    exibirMensagem("Classificação ainda não disponível.", "Classificação");
                }
            } else {
                exibirMensagem("Classificação ainda não disponível.", "Classificação");
            }
        } catch (Exception ex) {
            Log.i(MainActivity.TAG, "Erro ao preencher listView: " + ex.getMessage());
        }
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

    private void donwnloadFromUrlParam(final String nomeJsonParam, String urlJson, final String parametro) {
        (new AsyncTask<String, Void, String>() {
            ProgressDialog progressDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(ClassificacaoActivity.this, "Aguarde", "Atualizando dados");
            }

            @Override
            protected String doInBackground(String... params) {
                String json = null;

                try {
                    String url = params[0];
                    json = HttpHelper.POSTJson(url, parametro);
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

                progressDialog.dismiss();


                if (json == null) {
                    Log.w(MainActivity.TAG, "JSON veio nulo!");
                    atualizarClassificacaoBolao();
                    return;
                }

                PreferenceManager.getDefaultSharedPreferences(ClassificacaoActivity.this).edit()
                        .putString(nomeJsonParam + ".json", json)
                        .apply();
                atualizarClassificacaoBolao();
            }
        }).execute(urlJson);
    }


}
