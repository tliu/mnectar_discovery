package com.mnectar.mnectardisc.backend;

import android.os.AsyncTask;
import android.util.Log;

import com.mnectar.mnectardisc.Category;
import com.mnectar.mnectardisc.Game;
import com.mnectar.mnectardisc.GameActivity;
import com.mnectar.mnectardisc.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ian on 6/25/15.
 */
public class GetGameTask extends AsyncTask<String, Void, Game> {

    private GameActivity activity;
    public GetGameTask(GameActivity activity) {
        this.activity = activity;
    }

    @Override
    protected Game doInBackground(String... params) {
        Game game = null;
        try {
            Log.i("Url: ", "getting "+params[0]);
            String json = URLUtil.getJSONFromURL("/game/"+params[0]);
            JSONObject jsonGame = new JSONObject(json);
            game = new Game(jsonGame.getString("name"),
                    jsonGame.getString("id"),
                    jsonGame.getString("package"),
                    Float.parseFloat(jsonGame.getString("rating")),
                    jsonGame.getString("description"));
            Log.d("Game: ", game.getName());
        }catch(JSONException | IOException e)
        {
            Log.e("GetGame: ", e.getMessage());
        }
        return game;
    }

    @Override
    protected void onPostExecute(Game result) {

        activity.setGame(result);
    }
}
