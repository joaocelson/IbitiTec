package com.ibititec.ldapp;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import com.ibititec.ldapp.helpers.AlertMensage;

import com.ibititec.ldapp.helpers.HttpHelper;
import com.ibititec.ldapp.helpers.JsonHelper;

import com.ibititec.ldapp.models.Comerciante;
import com.ibititec.ldapp.models.Endereco;
import com.ibititec.ldapp.models.Telefone;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class DetalheActivity extends AppCompatActivity
        implements LocationListener {

    public static final int REQUEST_CODE_MY_LOCATION = 42;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 12;
    private Comerciante comerciante;
    private TextView txtNomeComerciante;
    private String telefoneChamar;
    private ImageView btnVerMapa;
    private Button enviarCordenada;
    protected LocationManager locationManager;
    private Location location;
    FloatingActionButton fab;
    ImageView imgCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        Fresco.initialize(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        lerIntent();
        //exibirMsgAtualizacao("Tela de detalhes aberta, comerciante: " + comerciante.getNome());
        txtNomeComerciante = (TextView) findViewById(R.id.txtNomeComercianteDetalhe);
        txtNomeComerciante.setText(comerciante.getNome());

        imgCall = (ImageView) findViewById(R.id.btn_ligar_detalhe);

        carregarTelefoneEndereco();
        setupFab();

        btnVerMapa = (ImageView) findViewById(R.id.btn_visualizar_mapa);

        btnVerMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DetalheActivity.this, MapaActivity.class);
                i.putExtra("comerciante", (Serializable) comerciante);
                startActivity(i);
            }
        });

        enviarCordenada = (Button) findViewById(R.id.btnEnviarCordeada);
        enviarCordenada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (location != null) {
                    comerciante.getEnderecos().get(0).setLatitude(String.valueOf(location.getLatitude()));
                    comerciante.getEnderecos().get(0).setLongitude(String.valueOf(location.getLongitude()));
                    EnviarCordenada(comerciante);
                }
            }
        });

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_CODE_MY_LOCATION);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            // location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }

        //Appodeal.show(this, Appodeal.BANNER_BOTTOM);
    }

    private void carregarTelefoneEndereco() {
        Uri imageUri = Uri.parse(comerciante.getNomeFoto());
        SimpleDraweeView draweeView = (SimpleDraweeView) findViewById(R.id.img_fresco_detalhe);
        draweeView.setImageURI(imageUri);

        LinearLayout layout = (LinearLayout) findViewById(R.id.linear_layout_detalhe);

        if (comerciante.getTelefones() != null && comerciante.getTelefones().size() > 0) {
            telefoneChamar = comerciante.getTelefones().get(0).getNumero();

            TextView tx = new TextView(this);
            tx.setText("Telefone: ");
            layout.addView(tx);

            for (Telefone telefone : comerciante.getTelefones()) {
                TextView txTelefone = new TextView(this);
                txTelefone.setText(telefone.getNumero());
                layout.addView(txTelefone);
            }
        }

        if (comerciante.getEnderecos() != null && comerciante.getEnderecos().size() > 0) {
            TextView txEndereco = new TextView(this);
            txEndereco.setText("Endereço: ");
            layout.addView(txEndereco);


            for (Endereco endereco : comerciante.getEnderecos()) {
                TextView txLogradouro = new TextView(this);
                txLogradouro.setText("Rua/Av:" + endereco.getLogradouro());
                txLogradouro.setTextSize(14);
                layout.addView(txLogradouro);

                TextView txEnderecoNumero = new TextView(this);
                txEnderecoNumero.setText("N°: " + endereco.getNumero());
                txEnderecoNumero.setTextSize(14);
                layout.addView(txEnderecoNumero);

                TextView txEnderecoBairro = new TextView(this);
                txEnderecoBairro.setText("Bairro: " + endereco.getBairro());
                txEnderecoBairro.setTextSize(14);
                layout.addView(txEnderecoBairro);

                TextView txComplemento = new TextView(this);
                if(endereco.getComplemento() != null)
                    txComplemento.setText("Complemento: " + endereco.getComplemento());
                else
                    txComplemento.setText("Complemento: ");
                txComplemento.setTextSize(14);
                layout.addView(txComplemento);
            }
        }
    }

    private void setupFab() {

        imgCall.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(DetalheActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(DetalheActivity.this, Manifest.permission.CALL_PHONE)) {
                        showMessageOKCancel("Você precisa permitir acesso ao discador do telefone!",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        ActivityCompat.requestPermissions(DetalheActivity.this, new String[]{Manifest.permission.CALL_PHONE},
                                                MY_PERMISSIONS_REQUEST_CALL_PHONE);
                                    }
                                });
                        return;
                    }
                    ActivityCompat.requestPermissions(DetalheActivity.this, new String[]{Manifest.permission.CALL_PHONE},
                            MY_PERMISSIONS_REQUEST_CALL_PHONE);
                    return;
                }
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + telefoneChamar));
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(callIntent);
            }
        });
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(DetalheActivity.this)
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

    private void lerIntent() {
        Intent intent = getIntent();
        comerciante = (Comerciante) intent.getSerializableExtra("comerciante");
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

    private void exibirMsgAtualizacao(String mensagem) {
        AlertMensage.setMessageAlert(mensagem, this, "Aviso");
    }

    public Context getActivity() {
        return this;
    }


    private void EnviarCordenada(final Comerciante comerciante) {
        try {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(DetalheActivity.this);
            (new AsyncTask<String, Void, String>() {
                ProgressDialog progressDialog;
                Comerciante objComer = comerciante;

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    progressDialog = ProgressDialog.show(DetalheActivity.this, "Aguarde", "Atualizando dados");
                }

                @Override
                protected String doInBackground(String... params) {
                    String json = null;

                    try {

                        String url = params[0];

                        String comerciante = JsonHelper.objectToJson(objComer.getEnderecos());

                        json = HttpHelper.POSTJson(url, comerciante);

                        if (json.equals("OK")) {

                            Log.i(MainActivity.TAG, json);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e(MainActivity.TAG, String.format(getString(R.string.msg_erro_json), e.getMessage()));
                    }
                    return json;
                }

                @Override
                protected void onPostExecute(String json) {
                    super.onPostExecute(json);

                    progressDialog.dismiss();
                    if (json.equals("OK")) {
                        Snackbar.make(findViewById(R.id.btnEnviarCordeada), "Cordenada atualizada", Snackbar.LENGTH_SHORT).show();
                    } else {
                        String mensagem = "Cadastro não realizado, tente novamente.";
                        Snackbar.make(findViewById(R.id.btnEnviarCordeada), mensagem, Snackbar.LENGTH_SHORT).show();
                    }
                }
            }).execute(getString(R.string.url_enviar_cordenada));
        } catch (Exception ex) {
            Log.i(MainActivity.TAG, "Erro ao salvar usuario." + ex.getMessage());
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
