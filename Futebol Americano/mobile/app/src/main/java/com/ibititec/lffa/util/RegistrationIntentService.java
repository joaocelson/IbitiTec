package com.ibititec.lffa.util;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.ibititec.lffa.R;

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

                // pass along this data
                sendRegistrationToServer(token);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendRegistrationToServer(String token) {
        // send network request

        // if registration sent was successful, store a boolean that indicates whether the generated token has been sent to server
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.edit().putBoolean(SENT_TOKEN_TO_SERVER, true).apply();
    }
}