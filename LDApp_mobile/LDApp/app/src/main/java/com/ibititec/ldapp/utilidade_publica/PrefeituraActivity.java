package com.ibititec.ldapp.utilidade_publica;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.ibititec.ldapp.R;
import com.ibititec.ldapp.adapter.UtilidadeAdapter;
import com.ibititec.ldapp.helpers.UIHelper;
import com.ibititec.ldapp.models.UtilidadePublica;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PrefeituraActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 12;

    UtilidadePublica utilidadePublica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prefeitura);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress_prefeitura);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ArrayList<UtilidadePublica> utilidadeArray = new ArrayList<UtilidadePublica>();
        utilidadeArray.add(new UtilidadePublica("Recepção", "(32) 3281-1281"));
        utilidadeArray.add(new UtilidadePublica("Gabinete Prefeito", "(32) 3281-1235"));
        utilidadeArray.add(new UtilidadePublica("Secret. Saúde", "(32) 3281-1440"));
        utilidadeArray.add(new UtilidadePublica("Secret. Administração", "(32) 3281-1281"));
        utilidadeArray.add(new UtilidadePublica("Secret. Obras", "(32) 3281 3266"));
        utilidadeArray.add(new UtilidadePublica("Secret. Educação", "(32) 3281-1573"));
        utilidadeArray.add(new UtilidadePublica("Secret. Fazen/Finanças", "(32) 3281-1281"));
        utilidadeArray.add(new UtilidadePublica("Secret. Agric/Pecuária", "(32) 3281-3266"));
        utilidadeArray.add(new UtilidadePublica("Secret. Assist. Social", "(32) 3281-1936"));
        utilidadeArray.add(new UtilidadePublica("Secret. Meio Ambiente", "(32) 3281-1195"));
        utilidadeArray.add(new UtilidadePublica("Secret. Esporte", "(32) 3281-3266"));
        utilidadeArray.add(new UtilidadePublica("Secret. Turismo", "(32) 3281-1195"));
        utilidadeArray.add(new UtilidadePublica("Demae", "(32) 3281-1981"));

        UtilidadeAdapter utilidadeAdater = new UtilidadeAdapter(this, utilidadeArray, this);
        final ListView listView = (ListView) findViewById(R.id.listview_utilidades_prefeitura);
        listView.setAdapter(utilidadeAdater);
        UIHelper.setListViewHeightBasedOnChildren(listView);
    }

    private void setupFab() {
        if (ActivityCompat.checkSelfPermission(PrefeituraActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(PrefeituraActivity.this, Manifest.permission.CALL_PHONE)) {
                showMessageOKCancel("Você precisa permitir acesso ao discador do telefone!",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(PrefeituraActivity.this, new String[]{Manifest.permission.CALL_PHONE},
                                        MY_PERMISSIONS_REQUEST_CALL_PHONE);
                            }
                        });
                return;
            }
            ActivityCompat.requestPermissions(PrefeituraActivity.this, new String[]{Manifest.permission.CALL_PHONE},
                    MY_PERMISSIONS_REQUEST_CALL_PHONE);
            return;
        }

        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + utilidadePublica.getTelefoneUtilidade()));
        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(callIntent);
//            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//            fab.setOnClickListener(new View.OnClickListener() {
//                @TargetApi(Build.VERSION_CODES.M)
//                @Override
//                public void onClick(View view) {
//
//                }
//            });
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(PrefeituraActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancelar", null)
                .create()
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CALL_PHONE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Map<String, Integer> perms = new HashMap<String, Integer>();
                    // Inicial
                    perms.put(Manifest.permission.CALL_PHONE, PackageManager.PERMISSION_GRANTED);
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();
        switch (id) {
            // Id correspondente ao botão Up/Home da actionbar
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }


        return super.onOptionsItemSelected(item);
    }

}
