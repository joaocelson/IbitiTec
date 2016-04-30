package com.ibititec.campeonatold.bolao;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ListView;

import com.appodeal.ads.Appodeal;
import com.ibititec.campeonatold.MainActivity;
import com.ibititec.campeonatold.R;
import com.ibititec.campeonatold.adapter.AdapterClassificacao;
import com.ibititec.campeonatold.helpers.JsonHelper;
import com.ibititec.campeonatold.helpers.UIHelper;
import com.ibititec.campeonatold.modelo.Classificacao;
import com.ibititec.campeonatold.util.AnalyticsApplication;

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

        carregarComponentes();
        lerIntent();
        atualizarClassificacaoBolao();
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
        Intent intent = getIntent();
        divisao = intent.getStringExtra("divisao");
    }

    private void atualizarClassificacaoBolao() {
        try {
            //cabecalhoLayout = (LinearLayout) findViewById(R.id.cabecalho_artilahria);
            //cabecalhoLayout.setVisibility(View.VISIBLE);
            if(divisao.equals("primeira")) {
                this.setTitle("Clas. Bolão 1ª Divisão");
                classificacao = JsonHelper.leJsonBancoLocal(MainActivity.PDCLASSIFICACAOBOLAO, this);
            }else {
                this.setTitle("Clas. Bolão 2ª Divisão");
                classificacao = JsonHelper.leJsonBancoLocal(MainActivity.SDCLASSIFICACAOBOLAO, this);
            }
            List<Classificacao> listClassificacao = JsonHelper.getList(classificacao, Classificacao[].class);
            AdapterClassificacao adapterArtilharia = new AdapterClassificacao(this, listClassificacao);
            lvClassificacao.setAdapter(adapterArtilharia);
            UIHelper.setListViewHeightBasedOnChildren(lvClassificacao);
        } catch (Exception ex) {
            Log.i(MainActivity.TAG, "Erro ao preencher listView: " + ex.getMessage());
        }
    }

}
