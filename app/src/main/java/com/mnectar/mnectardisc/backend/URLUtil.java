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
    public static final String SERVER_IP = "192.168.1.222";
    public static final String CATEGORY_PORT=":8002";
    public static final String IMAGE_PORT=":8000";
    public static final String STREAM_PORT=":8001";

    public static String getJSONFromURL(String endpoint) throws IOException {
        String output = "";
        BufferedReader in = null;
        URL url = new URL("http://"+SERVER_IP +CATEGORY_PORT+ endpoint);
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
