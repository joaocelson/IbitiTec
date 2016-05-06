package com.ibititec.campeonatold.bolao;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.ibititec.campeonatold.adapter.AdapterJogosBolao;
import com.ibititec.campeonatold.helpers.HttpHelper;
import com.ibititec.campeonatold.helpers.JsonHelper;
import com.ibititec.campeonatold.helpers.UIHelper;
import com.ibititec.campeonatold.modelo.Partida;
import com.ibititec.campeonatold.modelo.Usuario;
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lerIntent();
        carregarComponentes();
        executarAcoes();
    }

    private void executarAcoes() {

    }

    private void carregarComponentes() {
        lvJogosBolao = (ListView) findViewById(R.id.listview_bolao_palpite);
    }

    @Override
    public void onResume() {
        try {
            super.onResume();
            lerIntent();
            AnalyticsApplication.enviarGoogleAnalitcs(this);
            iniciarAppodeal();
        } catch (Exception ex) {
            Log.i(MainActivity.TAG, "Erro OnResume Palpite: " + ex.getMessage());
        }
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
        try {
            Intent intent = new Intent();
            intent.putExtra("divisao", divisao);

            // add data to Intent
            setResult(BolaoPrincipalActivity.RESULT_OK, intent);
            Appodeal.show(this, Appodeal.NATIVE);
            super.onBackPressed();
        } catch (Exception ex) {
            Log.i(MainActivity.TAG, "Erro onBackPressed Palpite: " + ex.getMessage());
        }
    }

    private void lerIntent() {
        try {
            if (HttpHelper.existeConexao(this)) {
                Intent intent = getIntent();
                divisao = intent.getStringExtra("divisao");
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(PalpiteActivity.this);

                Usuario usuarioLogado = JsonHelper.getObject(sharedPreferences.getString(MainActivity.USUARIO + ".json", ""), Usuario.class);

                if (divisao.equals("primeira")) {
                    donwnloadFromUrl(MainActivity.PDJOGOSBOLAO, getString(R.string.url_jogos_bolao), "{\"id\":\"1\", \"emailUsuario\":\"" + usuarioLogado.getLoginEmail() + "\",\"senha\":\"" + usuarioLogado.getSenha() + "\"}");
                } else {
                    donwnloadFromUrl(MainActivity.SDJOGOSBOLAO, getString(R.string.url_jogos_bolao), "{\"id\":\"2\", \"emailUsuario\":\"" + usuarioLogado.getLoginEmail() + "\",\"senha\":\"" + usuarioLogado.getSenha() + "\"}");
                }
            } else {
                exibirMensagem("Não identificado conexão com a internet, verifique se sua conexão está ativa.", "Atenção");
            }
        } catch (Exception ex) {
            Log.i(MainActivity.TAG, "Erro lerIntent Palpite: " + ex.getMessage());
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

    private void atualizarJogosBolao() {
        try {

            if (!HttpHelper.existeConexao(this)) {
                exibirMensagem("Não identificado conexão com a internet, verifique se sua conexão está ativa.", "Atenção");
            } else {
                //cabecalhoLayout = (LinearLayout) findViewById(R.id.cabecalho_artilahria);
                //cabecalhoLayout.setVisibility(View.VISIBLE);
                if (divisao.equals("primeira")) {
                    this.setTitle("Jogos 1ª Divisão");
                    jogosBolao = JsonHelper.leJsonBancoLocal(MainActivity.PDJOGOSBOLAO, this);
                } else {
                    this.setTitle("Jogos 2ª Divisão");
                    jogosBolao = JsonHelper.leJsonBancoLocal(MainActivity.SDJOGOSBOLAO, this);
                }
                if (!jogosBolao.equals("")) {
                    List<Partida> partidaList = JsonHelper.getList(jogosBolao, Partida[].class);
                    if (partidaList.size() > 0) {
                        AdapterJogosBolao adapterJogosBolao = new AdapterJogosBolao(this, partidaList);
                        lvJogosBolao.setAdapter(adapterJogosBolao);
                        UIHelper.setListViewHeightBasedOnChildren(lvJogosBolao);
                    } else {
                        exibirMensagem("Bolão ainda não liberado.", "Bolão");
                    }
                } else {
                    exibirMensagem("Bolão ainda não liberado.", "Bolão");
                }
            }
        } catch (Exception ex) {
            Log.i(MainActivity.TAG, "Erro ao preencher listView: " + ex.getMessage());
        }
    }

    private void donwnloadFromUrl(final String nomeJsonParam, String urlJson, final String parametro) {
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
                    atualizarJogosBolao();
                    return;
                }

                PreferenceManager.getDefaultSharedPreferences(PalpiteActivity.this).edit()
                        .putString(nomeJsonParam + ".json", json)
                        .apply();

                atualizarJogosBolao();
            }
        }).execute(urlJson);
    }

}
