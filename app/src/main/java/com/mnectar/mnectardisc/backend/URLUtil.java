package com.mnectar.mnectardisc.backend;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by thomas on 6/25/15.
 */
public class URLUtil {
    public static final String SERVER_IP = "http://192.168.10.22:8080";

    public static String getJSONFromURL(String endpoint) throws IOException {
        String output = "";
        BufferedReader in = null;
        URL url = new URL(SERVER_IP + endpoint);
        HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
        in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        String input;
        while ((input = in.readLine()) != null) {
            output += input;
        }
        in.close();
        Log.i("fuck", output);
        return output;
    }
}
