package com.ibititec.campeonatold.aovivo;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.ibititec.campeonatold.MainActivity;
import com.ibititec.campeonatold.R;
import com.ibititec.campeonatold.adapter.AdapterAoVivo;
import com.ibititec.campeonatold.helpers.HttpHelper;
import com.ibititec.campeonatold.helpers.JsonHelper;
import com.ibititec.campeonatold.helpers.UIHelper;
import com.ibititec.campeonatold.modelo.AoVivo;
import com.ibititec.campeonatold.modelo.Partida;

import java.io.IOException;
import java.util.List;

public class PartidaTempoRealActivity extends AppCompatActivity {
    private Button btnSalvar;
    private TextView nomeTimePalpitePorJogoMandante, nomeTimePalpitePorJogoVisitante;
    private TextView txtGolsTimeMandante, txtGolsTimeVisitante;
    private SimpleDraweeView escudoMandante, escudoVisitante;
    private ListView lvComentarioAoVivo;
    private String jogosBolao, divisao;
    private Partida partida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partida_tempo_real);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lerIntent();
        carregarComponentes();
        executarAcoes();
        donwnloadFromUrl("aovivo", getString(R.string.url_partida_ao_vivo),"{\"id\": \""+ String.valueOf(partida.getId())+"\"}");
    }

    private void lerIntent() {
        if (HttpHelper.existeConexao(this)) {
            Intent intent = getIntent();
            divisao = intent.getStringExtra("divisao");
            partida = (Partida) intent.getSerializableExtra("partida_tempo_real");
        }
    }

    private void executarAcoes() {

    }


    private void exibirMensagem(String mensagem, String titulo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //define o titulo
        builder.setTitle(titulo);
        //define a mensagem
        builder.setMessage(mensagem);
        //define um botão como positivo
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                return;
                // Toast.makeText(MainActivity.this, "positivo=" + arg1, Toast.LENGTH_SHORT).show();
            }
        });
        //cria o AlertDialog
        AlertDialog alerta = builder.create();
        //Exibe
        alerta.show();
    }

    private void exibirMensagemOK(String mensagem, String titulo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //define o titulo
        builder.setTitle(titulo);
        //define a mensagem
        builder.setMessage(mensagem);
        //define um botão como positivo
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {

                onBackPressed();
                // Toast.makeText(MainActivity.this, "positivo=" + arg1, Toast.LENGTH_SHORT).show();
            }
        });
        //cria o AlertDialog
        AlertDialog alerta = builder.create();
        //Exibe
        alerta.show();
    }
    private void carregarComponentes() {
        txtGolsTimeMandante = (TextView) findViewById(R.id.txtTempoRealGolsTimeMandante);
        txtGolsTimeVisitante = (TextView) findViewById(R.id.txtTempoRealGolsTimeVisitante);
        escudoMandante = (SimpleDraweeView) findViewById(R.id.img_time_mandante_tempo_real);
        escudoVisitante = (SimpleDraweeView) findViewById(R.id.img_time_visitante_tempo_real);
        nomeTimePalpitePorJogoMandante = (TextView) findViewById(R.id.nomeTimeMandanteTempoReal);
        nomeTimePalpitePorJogoVisitante = (TextView) findViewById(R.id.nomeTimeVisitanteTempoReal);
        lvComentarioAoVivo = (ListView) findViewById(R.id.lvTempoReal);

        Uri imageUriMandante = Uri.parse(MainActivity.PATH_FOTOS + partida.getEscudoPequenoMandante().trim());
        Uri imageUriVisitante = Uri.parse(MainActivity.PATH_FOTOS + partida.getEscudoPequenoVisitante().trim());

        nomeTimePalpitePorJogoMandante.setText(partida.getTimeMandante());
        nomeTimePalpitePorJogoVisitante.setText(partida.getTimeVisitante());

        escudoMandante.setImageURI(imageUriMandante);
        escudoVisitante.setImageURI(imageUriVisitante);
    }

    private void donwnloadFromUrl(final String nomeJsonParam, String urlJson, final String parametro) {
        (new AsyncTask<String, Void, String>() {
            ProgressDialog progressDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(PartidaTempoRealActivity.this, "Aguarde", "Atualizando dados");
            }

            @Override
            protected String doInBackground(String... params) {
                String json = null;
                try {
                    String url = params[0];
                    //json = HttpHelper.POSTJson(url, parametro);

                    if (!HttpHelper.existeConexao(PartidaTempoRealActivity.this)) {
                        exibirMensagem("Não identificado conexão com a internet, verifique se sua conexão está ativa.", "Atenção");
                    } else {
                        json = HttpHelper.POSTJson(getString(R.string.url_partida_ao_vivo), parametro);
                    }
                    Log.i(MainActivity.TAG, json);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(MainActivity.TAG, String.format(getString(R.string.msg_erro_json), e.getMessage()));
                }
                return json;
            }

            @Override
            protected void onPostExecute(String json) {
                super.onPostExecute(json);


                if (json.equals("")) {
                    exibirMensagemOK("Nenhum comentário para a partida.", "Ao Vivo");

                } else {
                    carregaListaComentarioAoVivo(json);
                }
                progressDialog.dismiss();
            }
        }).execute(urlJson);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.ao_vivo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_atualizar_aoVivo:
                donwnloadFromUrl("aovivo", getString(R.string.url_partida_ao_vivo),"{\"id\": \""+ String.valueOf(partida.getId())+"\"}");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void carregaListaComentarioAoVivo(String json) {
        try {
            List<AoVivo> listComentariosAoVivo = JsonHelper.getList(json, AoVivo[].class);
            if (listComentariosAoVivo != null && listComentariosAoVivo.size() > 0) {

                AdapterAoVivo adapterAoVivo = new AdapterAoVivo(this, listComentariosAoVivo);
                lvComentarioAoVivo.setAdapter(adapterAoVivo);
                UIHelper.setListViewHeightBasedOnChildren(lvComentarioAoVivo);

                AoVivo aoVivo = listComentariosAoVivo.get(listComentariosAoVivo.size()-1);
                String[] resultado = aoVivo.getComentario().split("_");
                txtGolsTimeMandante.setText(resultado[0] == null ? "" : resultado[0] );
                txtGolsTimeVisitante.setText(resultado[1] == null ? "" : resultado[1]);
            } else {
                exibirMensagemOK("Nenhum comentário para a partida.", "Ao Vivo");
            }
        }catch (Exception ex){
            Log.i(MainActivity.TAG, "Erro ao carregar lista de comentarios ao vivo.");
        }
    }
}
