package com.ibititec.ldapp.helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

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
}
