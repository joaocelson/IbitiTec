package com.ibititec.campeonatold;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.appodeal.ads.Appodeal;

public class SobreActivity extends AppCompatActivity {

    String divisao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        lerIntent();
        iniciarAppodeal();

    }
    private void iniciarAppodeal() {
        Appodeal.show(this, Appodeal.BANNER_BOTTOM);
    }

    private void lerIntent() {
        Intent intent = getIntent();
        divisao = intent.getStringExtra("divisao");
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("divisao", divisao);
        // add data to Intent
        setResult(PrimeiraDivisaoTabelaActivity.RESULT_OK, intent);
        super.onBackPressed();
    }
}
