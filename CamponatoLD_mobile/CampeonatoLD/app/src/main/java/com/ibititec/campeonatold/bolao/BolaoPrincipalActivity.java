package com.ibititec.campeonatold.bolao;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ibititec.campeonatold.MainActivity;
import com.ibititec.campeonatold.R;
import com.ibititec.campeonatold.helpers.HttpHelper;

public class BolaoPrincipalActivity extends AppCompatActivity {

    private ImageButton btnClassificacao, btnPalpite;
    private TextView txtClassificacao, txtPalpite;
    private String divisao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_bolao_principal);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            lerIntent();
            carregarComponentes();
            executarAcoes();
        } catch (Exception ex) {
            Log.i(MainActivity.TAG, "Erro onCreate Bolao Principal: " + ex.getMessage());
        }
    }

    private void lerIntent() {
        try {
            if (HttpHelper.existeConexao(this)) {
                Intent intent = getIntent();
                divisao = intent.getStringExtra("divisao");
            }
        } catch (Exception ex) {
            Log.i(MainActivity.TAG, "Erro leIntent BolaoPrincipal : " + ex.getMessage());
        }
    }

    private void executarAcoes() {
        try {
            if (divisao.equals("primeira")) {
                this.setTitle("Bolão 1ª Divisão");
            } else {
                this.setTitle("Bolão 2ª Divisão");
                btnPalpite.setImageResource(R.drawable.sdpalpite);
                btnClassificacao.setImageResource(R.drawable.sdclassificacao);
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
        } catch (Exception ex) {
            Log.i(MainActivity.TAG, "Erro Bolao Principal : " + ex.getMessage());
        }
    }

    private void startarActivityClassificacao(String divisao) {
        try {
            Intent intent = new Intent(this, ClassificacaoActivity.class);
            intent.putExtra("divisao", divisao);
            startActivity(intent);
        } catch (Exception ex) {
            Log.i(MainActivity.TAG, "Erro StartarActivityClassificacao: " + ex.getMessage());
        }
    }

    private void startarActivityPalpites(String divisao) {
        try {
            Intent intent = new Intent(this, PalpiteActivity.class);
            intent.putExtra("divisao", divisao);
            startActivity(intent);
        } catch (Exception ex) {
            Log.i(MainActivity.TAG, "Erro statarActivityPalpites : " + ex.getMessage());
        }
    }

    private void carregarComponentes() {
        try {
            btnClassificacao = (ImageButton) findViewById(R.id.btnClassificaoBolao);
            btnPalpite = (ImageButton) findViewById(R.id.btnPalpitesBolao);
            txtClassificacao = (TextView) findViewById(R.id.txtClassificacaoBolao);
            txtPalpite = (TextView) findViewById(R.id.txtPalpitesBolao);
        } catch (Exception ex) {
            Log.i(MainActivity.TAG, "Erro Bolao Principal CarregagarComponetes: " + ex.getMessage());
        }
    }
}
