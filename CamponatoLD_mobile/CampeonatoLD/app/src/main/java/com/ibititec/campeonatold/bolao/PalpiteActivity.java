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

public class PalpiteActivity extends AppCompatActivity {

    private ListView lvJogosBolao;
    private String jogosBolao, divisao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_palpite);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        carregarComponentes();
        executarAcoes();
        atualizarJogosBolao();
    }

    private void executarAcoes() {

    }

    private void carregarComponentes() {
        lvJogosBolao = (ListView) findViewById(R.id.listview_bolao_palpite);
    }

    @Override
    public void onResume() {
        super.onResume();
        AnalyticsApplication.enviarGoogleAnalitcs(this);
        iniciarAppodeal();
    }

    private void iniciarAppodeal() {
        try{
            Appodeal.show(this, Appodeal.BANNER_TOP);
        }catch (Exception ex) {
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

        if(HttpHelper.existeConexao(this)) {
            Intent intent = getIntent();
            divisao = intent.getStringExtra("divisao");

            if (divisao.equals("primeira")) {
                donwnloadFromUrl(MainActivity.PDJOGOSBOLAO, getString(R.string.url_pd_jogos_bolao));
            } else {
                donwnloadFromUrl(MainActivity.SDJOGOSBOLAO, getString(R.string.url_sd_jogos_bolao));
            }
        }else{
            exibirMensagem();
        }
    }

    private void exibirMensagem() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //define o titulo
        builder.setTitle("Atenção");
        //define a mensagem
        builder.setMessage("Não identificado conexão com a internet, verifique se sua conexão está ativa.");
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

    private void atualizarJogosBolao() {
        try {
            //cabecalhoLayout = (LinearLayout) findViewById(R.id.cabecalho_artilahria);
            //cabecalhoLayout.setVisibility(View.VISIBLE);
            if(divisao.equals("primeira")) {
                this.setTitle("Jogos 1ª Divisão");
                jogosBolao = JsonHelper.leJsonBancoLocal(MainActivity.PDJOGOSBOLAO, this);
            }else {
                this.setTitle("Jogos 2ª Divisão");
                jogosBolao = JsonHelper.leJsonBancoLocal(MainActivity.SDJOGOSBOLAO, this);
            }
            List<Classificacao> listClassificacao = JsonHelper.getList(jogosBolao, Classificacao[].class);
            AdapterClassificacao adapterArtilharia = new AdapterClassificacao(this, listClassificacao);
            lvJogosBolao.setAdapter(adapterArtilharia);
            UIHelper.setListViewHeightBasedOnChildren(lvJogosBolao);
        } catch (Exception ex) {
            Log.i(MainActivity.TAG, "Erro ao preencher listView: " + ex.getMessage());
        }
    }

    private void donwnloadFromUrl(final String nomeJsonParam, String urlJson) {
        (new AsyncTask<String, Void, String>() {
            ProgressDialog progressDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(PalpiteActivity.this, "Aguarde", "Atualizando dados");
            }

            @Override
            protected String doInBackground(String... params) {
                String json = null;

                try {
                    String url = params[0];
                    json = HttpHelper.downloadFromURL(url);
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
                    return;
                }

                PreferenceManager.getDefaultSharedPreferences(PalpiteActivity.this).edit()
                        .putString(nomeJsonParam + ".json", json)
                        .apply();
            }
        }).execute(urlJson);
    }

}
