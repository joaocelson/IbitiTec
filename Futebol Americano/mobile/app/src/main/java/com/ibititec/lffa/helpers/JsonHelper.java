package com.ibititec.lffa.helpers;

import android.app.Activity;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;
import com.ibititec.lffa.MainActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by pedro on 11/02/16.
 */
public class JsonHelper {
    public static <T> List<T> getList(String json, Class<T[]> tClass) {
        return Arrays.asList((new Gson()).fromJson(json, tClass));
    }

    public static <T> T getObject(String json, Class<T> tClass) {
        return (new Gson()).fromJson(json, tClass);
    }

    public static String leJsonBancoLocal(String nomeJson, Activity activity) {
        try {
            String json = PreferenceManager.getDefaultSharedPreferences(activity)
                    .getString(nomeJson + ".json", "");
            Log.i(MainActivity.TAG, "Lendo preferences: " + json);
            return json;
        } catch (Exception ex) {
            Log.i(MainActivity.TAG, "Erro no metodo:  nomeJson: " + nomeJson + " - Erro: " + ex.getMessage());
            return null;
        }
    }


}
