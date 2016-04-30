package com.ibititec.campeonatold.bolao;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ibititec.campeonatold.R;

public class BolaoPrincipalActivity extends AppCompatActivity {

    private ImageButton btnClassificacao, btnPalpite;
    private TextView txtClassificacao, txtPalpite;
    private String divisao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bolao_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        carregarComponentes();
        executarAcoes();
    }

    private void executarAcoes() {
        if (divisao.equals("primeira")) {
            this.setTitle("Primeira Divisão");
        } else {
            this.setTitle("Segunda Divisão");
        }

        btnPalpite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startarActivityPalpites(divisao);
            }
        });

        txtPalpite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startarActivityPalpites(divisao);
            }
        });

        btnClassificacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startarActivityClassificacao(divisao);
            }
        });
        txtClassificacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startarActivityClassificacao(divisao);
            }
        });
    }

    private void startarActivityClassificacao(String divisao) {
        Intent intent = new Intent(this, ClassificacaoActivity.class);
        intent.putExtra("divisao", divisao);
        startActivity(intent);
    }

    private void startarActivityPalpites(String divisao) {
        Intent intent = new Intent(this, PalpiteActivity.class);
        intent.putExtra("divisao", divisao);
        startActivity(intent);
    }

    private void carregarComponentes() {

        btnClassificacao = (ImageButton) findViewById(R.id.btnClassificaoBolao);
        btnPalpite = (ImageButton) findViewById(R.id.btnPalpitesBolao);
        txtClassificacao = (TextView) findViewById(R.id.txtClassificacao);
        txtPalpite = (TextView) findViewById(R.id.txtPalpitesBolao);
    }

}
