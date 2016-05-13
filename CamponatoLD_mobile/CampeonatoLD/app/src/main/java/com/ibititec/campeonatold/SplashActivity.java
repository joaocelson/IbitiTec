package com.ibititec.campeonatold;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.appodeal.ads.Appodeal;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.ibititec.campeonatold.admin.LoginUsuarioActivity;
import com.ibititec.campeonatold.util.RegistrationIntentService;

public class SplashActivity extends AppCompatActivity {
    private  final int DURACAO_TELA = 3000;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //CHAMANDO A TELA MAIN ACTIVITY
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Intent novaTela = new Intent(SplashActivity.this, MainActivity.class);
                Intent novaTela = new Intent(SplashActivity.this, LoginUsuarioActivity.class);
                SplashActivity.this.startActivity(novaTela);
                SplashActivity.this.finish();
            }
        }, DURACAO_TELA);
        iniciarAppodeal();

        // //INICIALIZACAO DO FRESCO
        Fresco.initialize(this);
        inicializarPushMessage();

    }
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i("LOG", "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }
    private void inicializarPushMessage() {
        if(checkPlayServices()) {
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        }
    }
    private void iniciarAppodeal() {
        //String PRODUCAO
        String appKey = "a7abb670bb95499ee0c535d3d8f3787704b48736d99fab89";
        //String DESENVOLCIVMENTO
        //String appKey = "a7abb670bb95499ee0c535d3d8f3787704b48736d99fab8ssdsddsd9";
        Appodeal.setBannerViewId(R.id.appodealBannerView);
        Appodeal.initialize(this, appKey, Appodeal.INTERSTITIAL | Appodeal.BANNER | Appodeal.MREC);
        //Appodeal.setTesting(true);
        Appodeal.show(this, Appodeal.BANNER_BOTTOM);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
        //    return true;
        //}

        return super.onOptionsItemSelected(item);
    }


}
