package com.ibititec.ldapp.helpers;

import android.util.Log;

import com.ibititec.ldapp.MainActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by pedro on 10/02/16.
 */
public class HttpHelper {
    public static String downloadFromURL(String urlAddress) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(urlAddress).openStream()));
        StringBuilder result = new StringBuilder();

        for (String line; (line = reader.readLine()) != null;)
            result.append(line);

        return result.toString();
    }

    public static String POSTJson(String requestURL, String dadosJson) throws IOException {
        String response = "";
        try {
            URL url;


            url = new URL(requestURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("content-type", "application/json; charset=utf-8");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os));
            writer.write(dadosJson);

            writer.flush();
            writer.close();
            os.close();
            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            } else {
                response = "";
            }
        } catch (Exception ex) {
            Log.i(MainActivity.TAG, "Erro ao post json usuario." + ex.getMessage());
        }
        return response;
    }
}
