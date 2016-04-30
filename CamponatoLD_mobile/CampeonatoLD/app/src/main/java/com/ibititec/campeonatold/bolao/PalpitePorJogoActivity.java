package com.ibititec.campeonatold.bolao;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ibititec.campeonatold.R;

public class PalpitePorJogoActivity extends AppCompatActivity {

    private Button btnSalvar;
    private EditText txtGolsTimeMandante, txtGolsTimeVisitante;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_palpite_por_jogo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        carregarComponentes();
        executarAcoes();

    }

    private void executarAcoes() {
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void carregarComponentes() {
        btnSalvar = (Button) findViewById(R.id.btnSalvarPalpiteBolao);
        txtGolsTimeMandante = (EditText) findViewById(R.id.txtBolaoGolsTimeMandante);
        txtGolsTimeVisitante = (EditText) findViewById(R.id.txtBolaoGolsTimeVisitante);
    }

}
