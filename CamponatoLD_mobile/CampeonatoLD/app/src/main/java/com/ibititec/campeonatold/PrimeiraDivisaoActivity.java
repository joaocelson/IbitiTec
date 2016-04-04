package com.ibititec.campeonatold;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

public class PrimeiraDivisaoActivity extends AppCompatActivity {

    private ImageButton btnTabela, btnArtilharia, btnClassificacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primeira_divisao);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnArtilharia = (ImageButton) findViewById(R.id.btnPrimeiraDivisaoTabela);
        btnTabela = (ImageButton) findViewById(R.id.btnPrimeiraDivisaoTabela);
        btnClassificacao = (ImageButton) findViewById(R.id.btnPrimeiraDivisaoClassificacao);

        executarAcoes();
    }

    private void executarAcoes() {
        btnArtilharia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startarActivity("primeira", "artilharia");
            }
        });

        btnTabela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startarActivity("primeira", "tabela");
            }
        });

        btnClassificacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startarActivity("segunda","classificacao");
            }
        });
    }

    private void startarActivity(String divisao, String funcionalidade) {
        Intent intent = new Intent(this, PrimeiraDivisaoTabelaActivity.class);
        intent.putExtra("divisao", divisao);
        intent.putExtra("funcionalidade", funcionalidade);
        startActivity(intent);
    }

}
