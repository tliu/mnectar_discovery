package com.mnectar.mnectardisc.backend;

import android.os.AsyncTask;
import android.util.Log;

import com.mnectar.mnectardisc.Category;
import com.mnectar.mnectardisc.Game;
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
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by thomas on 6/25/15.
 */
public class GetCategoriesTask extends AsyncTask<Void, Void, List<Category>> {

    private MainActivity activity;
    public GetCategoriesTask(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    protected List<Category> doInBackground(Void... params) {
        List<Category> cats = new ArrayList<Category>();
        try {
            Log.i("Url: ", "getting url");
            String json = URLUtil.getJSONFromURL("/game/categories");
            JSONObject categories = new JSONObject(json);
            Log.i("fuck", String.valueOf(categories.getJSONArray("Role Playing")));
            Iterator<String> iter = categories.keys();
            while (iter.hasNext()) {

                Category cat = new Category(iter.next());
                if (cat.getName()!="") {
                    JSONArray games = categories.getJSONArray(cat.getName());
                    for (int i = 0; i < games.length(); i++) {
                        JSONObject jsonGame = games.getJSONObject(i);
                        Game game = new Game(jsonGame.getString("name"),
                                jsonGame.getString("id"),
                                jsonGame.getString("package"),
                                Float.parseFloat(jsonGame.getString("rating")),
                                jsonGame.getString("description"));
                        cat.addGame(game);
                    }
                    cats.add(cat);
                }

            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        Collections.shuffle(cats);
        return cats;
    }

    @Override
    protected void onPostExecute(List<Category> result) {

        activity.setCategories(result);
        activity.buildUI();
    }
}
