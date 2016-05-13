package com.ibititec.lffa.bolao;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import com.appodeal.ads.Appodeal;
import com.ibititec.lffa.MainActivity;
import com.ibititec.lffa.R;

public class RegrasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regras);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView tx =(TextView) findViewById(R.id.txtRegulamento);
        tx.setText(Html.fromHtml(getString(R.string.regulamento)));

        Appodeal.show(this, Appodeal.BANNER_BOTTOM);
    }


    @Override
    public void onBackPressed() {
        try {
            Appodeal.show(this, Appodeal.NATIVE);
            super.onBackPressed();
        } catch (Exception ex) {
            Log.i(MainActivity.TAG, "Erro: onBackPressedPrimeiraDivisaoTabela: " + ex.getMessage());
        }
    }

}
