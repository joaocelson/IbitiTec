package com.ibititec.campeonatold.bolao;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.appodeal.ads.Appodeal;
import com.ibititec.campeonatold.MainActivity;
import com.ibititec.campeonatold.R;
import com.ibititec.campeonatold.helpers.HttpHelper;

public class RegrasActivity extends AppCompatActivity {
    private String divisao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regras);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView tx =(TextView) findViewById(R.id.txtRegulamento);
        tx.setText(Html.fromHtml(getString(R.string.regulamento)));
        lerIntent();
        iniciarAppodeal();
    }

    private void lerIntent() {
        if (HttpHelper.existeConexao(this)) {
            Intent intent = getIntent();
            divisao = intent.getStringExtra("divisao");
        }

    }

    private void iniciarAppodeal() {
        try {
            Appodeal.show(this, Appodeal.BANNER);
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
            setResult(PalpitePorJogoActivity.RESULT_OK, intent);
            Appodeal.show(this, Appodeal.NATIVE);
            super.onBackPressed();
        } catch (Exception ex) {
            Log.i(MainActivity.TAG, "Erro: onBackPressedPrimeiraDivisaoTabela: " + ex.getMessage());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        onBackPressed();
        //noinspection SimplifiableIfStatement
//        if (id == R.id.home) {
//            onBackPressed();
//            return true;
//        }
        return true;
    }
}
