package com.ibititec.campeonatold;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.ibititec.campeonatold.adapter.AdapterRodada;
import com.ibititec.campeonatold.helpers.JsonHelper;
import com.ibititec.campeonatold.helpers.UIHelper;
import com.ibititec.campeonatold.modelo.Rodada;

import java.util.List;

public class PrimeiraDivisaoTabelaActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private ListView lvTabela;
    private String funcionalidade, divisao;
    static final String TAG = "CAMPEONATOLD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primeira_divisao_tabela);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressBar = (ProgressBar) findViewById(R.id.progress_primeira_tabela);
        lvTabela = (ListView) findViewById(R.id.listview_primeira_tabela);

        lerIntent();
        executarAcoes();
    }

    private void lerIntent() {
        Intent intent = getIntent();
        divisao = intent.getStringExtra("divisao");
        funcionalidade = intent.getStringExtra("funcionalidade");
    }

    private void executarAcoes() {
        switch (funcionalidade) {
            case "tabela":
                if (divisao.equals("primeira")) {
                    atualizarTabelaPrimeiraDivisao();
                } else {
                    atualizarTabelaSegundaDivisao();
                }
                break;
            case "classificacao":
                if (divisao.equals("primeira")) {
                    atualizarClassificacaoPrimeiraDivisao();
                } else {
                    atualizarClassificacaoSegundaDivisao();
                }
                break;
            case "artilharia":
                if (divisao.equals("primeira")) {
                    atualizarArtilhariaPrimeiraDivisao();
                } else {
                    atualizarArtilhariaSegundaDivisao();
                }
                break;
        }
    }

    private String leJsonBancoLocal(String nomeJson) {
        try {
            String json = PreferenceManager.getDefaultSharedPreferences(PrimeiraDivisaoTabelaActivity.this)
                    .getString(nomeJson + ".json", "");
            Log.i(TAG, "Lendo preferences: " + json);
            return json;
        } catch (Exception ex) {
            Log.i(TAG, "Erro no metodo:  nomeJson: " + nomeJson + " - Erro: " + ex.getMessage());
            return null;
        }
    }

    private void atualizarArtilhariaSegundaDivisao() {
        leJsonBancoLocal(MainActivity.SDARTILHARIA);

    }

    private void atualizarArtilhariaPrimeiraDivisao() {
        leJsonBancoLocal(MainActivity.PDARTILHARIA);

    }

    private void atualizarClassificacaoSegundaDivisao() {
        leJsonBancoLocal(MainActivity.SDCLASSIFICACAO);

    }

    private void atualizarClassificacaoPrimeiraDivisao() {
        leJsonBancoLocal(MainActivity.PDCLASSIFICACAO);

    }

    private void atualizarTabelaSegundaDivisao() {
        leJsonBancoLocal(MainActivity.SDTABELA);

    }

    private void atualizarTabelaPrimeiraDivisao() {
        try {
            String tabela = leJsonBancoLocal(MainActivity.PDTABELA);
            List<Rodada> listRodada = JsonHelper.getList(tabela, Rodada[].class);
            AdapterRodada adapterRodada = new AdapterRodada(this,listRodada);
            lvTabela.setAdapter(adapterRodada);
            UIHelper.setListViewHeightBasedOnChildren(lvTabela);

        } catch (Exception ex) {
            Log.i(MainActivity.TAG, "Erro ao preencher listView: " + ex.getMessage());
        }
    }
}
