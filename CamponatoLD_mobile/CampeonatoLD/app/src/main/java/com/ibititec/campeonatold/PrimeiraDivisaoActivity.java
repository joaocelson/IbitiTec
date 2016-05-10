package com.ibititec.campeonatold;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.appodeal.ads.Appodeal;
import com.ibititec.campeonatold.bolao.BolaoPrincipalActivity;

public class PrimeiraDivisaoActivity extends AppCompatActivity {

    private ImageButton btnTabela, btnArtilharia, btnClassificacao, btnAjuda, btnBolao;
    private TextView txtTabela, txtArtilharia, txtClassificacao, txtAjuda, txtBolao;
    private String divisao;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primeira_divisao);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnArtilharia = (ImageButton) findViewById(R.id.btnPrimeiraDivisaoArtilharia);
        btnTabela = (ImageButton) findViewById(R.id.btnPrimeiraDivisaoTabela);
        btnClassificacao = (ImageButton) findViewById(R.id.btnPrimeiraDivisaoClassificacao);
        btnAjuda = (ImageButton) findViewById(R.id.btnPrimeiraDivisaoSobre);
        btnBolao = (ImageButton) findViewById(R.id.btnPrimeiraDivisaoBolao);

        txtArtilharia = (TextView) findViewById(R.id.txtArtilharia);
        txtTabela = (TextView) findViewById(R.id.txtTabela);
        txtClassificacao = (TextView) findViewById(R.id.txtClassificacao);
        txtAjuda = (TextView) findViewById(R.id.txtHelp);
        txtBolao = (TextView) findViewById(R.id.txtBolao);

        lerIntent();
        executarAcoes();
    }

    @Override
    public void onResume() {
        try {
            super.onResume();
            lerIntent();
//        AnalyticsApplication.enviarGoogleAnalitcs(this);
            iniciarAppodeal();
        } catch (Exception ex) {
            Log.i(MainActivity.TAG, "Erro: onResume PrimeiraDivisao : " + ex.getMessage());
        }
    }

    private void iniciarAppodeal() {
        Appodeal.show(this, Appodeal.BANNER_BOTTOM);
    }

    @Override
    public void onBackPressed() {
        try {
            Intent intent = new Intent();
            intent.putExtra("divisao", divisao);
            // add data to Intent
            setResult(PrimeiraDivisaoTabelaActivity.RESULT_OK, intent);
            super.onBackPressed();
        } catch (Exception ex) {
            Log.i(MainActivity.TAG, "Erro: onBackPressed PrimeiraDivisao: " + ex.getMessage());
        }
    }

    private void executarAcoes() {
        try {
            if (divisao.equals("primeira")) {
                this.setTitle("Primeira Divisão");
            } else {
                this.setTitle("Segunda Divisão");

                btnBolao.setImageResource(R.drawable.sdbolao);
                btnAjuda.setImageResource(R.drawable.sdajuda);
                btnTabela.setImageResource(R.drawable.sdtabela);
                btnArtilharia.setImageResource(R.drawable.sdartilharia);
                btnClassificacao.setImageResource(R.drawable.sdclassificacao);

            }

            btnArtilharia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startarActivity(divisao, "artilharia");
                }
            });

            btnTabela.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startarActivity(divisao, "tabela");
                }
            });

            btnClassificacao.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startarActivity(divisao, "classificacao");
                }
            });

            btnAjuda.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startarActivityHelp(divisao, "sobre");
                }
            });

            btnBolao.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startarActivityBolao(divisao);
                }
            });

            txtArtilharia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startarActivity(divisao, "artilharia");
                }
            });

            txtTabela.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startarActivity(divisao, "tabela");
                }
            });

            txtClassificacao.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startarActivity(divisao, "classificacao");
                }
            });

            txtAjuda.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startarActivityHelp(divisao, "sobre");
                }
            });

            txtBolao.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startarActivityBolao(divisao);
                }
            });
        } catch (Exception ex) {
            Log.i(MainActivity.TAG, "Erro: executarAcoes PrimeiraDivisao: " + ex.getMessage());
        }
    }

    private void startarActivityHelp(String divisao, String funcionalidade) {
        try {
            Intent intent = new Intent(this, SobreActivity.class);
            intent.putExtra("divisao", divisao);
            intent.putExtra("funcionalidade", funcionalidade);
            startActivity(intent);
        } catch (Exception ex) {
            Log.i(MainActivity.TAG, "Erro: startarActivityHelp PrimeiraDivisao: " + ex.getMessage());
        }
    }

    private void startarActivityBolao(String divisao) {
        try {
            Intent intent = new Intent(this, BolaoPrincipalActivity.class);
            intent.putExtra("divisao", divisao);
            startActivity(intent);
        } catch (Exception ex) {
            Log.i(MainActivity.TAG, "Erro: startarActivityBolao PrimeiraDivisao: " + ex.getMessage());
        }
    }

    private void startarActivity(String divisao, String funcionalidade) {
        try {
            Intent intent = new Intent(this, PrimeiraDivisaoTabelaActivity.class);
            intent.putExtra("divisao", divisao);
            intent.putExtra("funcionalidade", funcionalidade);
            startActivity(intent);
        } catch (Exception ex) {
            Log.i(MainActivity.TAG, "Erro: startarActivity PrimeiraDivisao: " + ex.getMessage());
        }
    }

    private void lerIntent() {
        try {
            Intent intent = getIntent();
            divisao = intent.getStringExtra("divisao");
        } catch (Exception ex) {
            Log.i(MainActivity.TAG, "Erro: lerIntent PrimeiraDivisao: " + ex.getMessage());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        onBackPressed();
        return true;
    }
}
