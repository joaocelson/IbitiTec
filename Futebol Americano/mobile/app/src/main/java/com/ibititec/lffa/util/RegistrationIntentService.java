package com.ibititec.lffa.util;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.provider.SyncStateContract;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.ibititec.lffa.R;
import com.ibititec.lffa.helpers.HttpHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by ct002572 on 15/04/2016.
 */
// abbreviated tag name
public class RegistrationIntentService extends IntentService {

    // abbreviated tag name
    private static final String TAG = "RegIntentService";
    public static final String SENT_TOKEN_TO_SERVER = "sentTokenToServer";
    public static final String GCM_TOKEN = "gcmToken";
    public static final String GCM_TOKEN_ENVIADO = "enviado";
    String token = "";

    public RegistrationIntentService() {
        super(TAG);
        Log.d(TAG, "RegistrationIntentService Iniciado");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        synchronized (TAG) {
            // Make a call to Instance API
            InstanceID instanceID = InstanceID.getInstance(this);
            String senderId = getResources().getString(R.string.gcm_defaultSenderId);
            try {
                // request token that will be used by the server to send push notifications
                token = instanceID.getToken(senderId, GoogleCloudMessaging.INSTANCE_ID_SCOPE);
                Log.d(TAG, "GCM Registration Token: " + token);

                // Fetch token here
                // save token
                sharedPreferences.edit().putString(GCM_TOKEN, token).apply();

                // pass along this data
                sendRegistrationToServer(token);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendRegistrationToServer(String token) throws IOException, JSONException {
        // send network request
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String stringEnviado = sharedPreferences.getString(SENT_TOKEN_TO_SERVER, "");

        JSONObject json = new JSONObject();
        json.put("token", token);

        if (!stringEnviado.equals("OK")) {
            String result = HttpHelper.POST(getString(R.string.sendToken),json.toString());
            if (result.equals("OK")) {
                sharedPreferences.edit().putString(SENT_TOKEN_TO_SERVER, "OK").apply();
            }
        }
    }
}