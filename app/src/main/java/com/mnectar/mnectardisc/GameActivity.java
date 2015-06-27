package com.mnectar.mnectardisc;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.mnectar.mnectardisc.backend.URLUtil;


public class GameActivity extends Activity {
    private Game game;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_game);
        game = (Game) getIntent().getExtras().get(getString(R.string.game));

        //javascript debugging
        WebView.setWebContentsDebuggingEnabled(true);

        preparePage();

/*        game = (Game) getIntent().getExtras().get(getString(R.string.game));
        WebView webView = (WebView)findViewById(R.id.game_info);
        webView.loadData(game.getDescription(), "text/html",null);
        queue = RequestQueueSingleton.getInstance(this).getRequestQueue();
        Uri imagePath = new Uri.Builder().scheme("http").encodedAuthority(URLUtil.SERVER_IP+URLUtil.IMAGE_PORT).appendEncodedPath("assets/"+game.getId()+"/image.webp").build();
        ImageRequest imageRequest = new ImageRequest(imagePath.toString(), new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                ((ImageView) findViewById(R.id.game_image)).setImageBitmap(response);
            }
        }, image, image, ImageView.ScaleType.CENTER_CROP, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(imageRequest);*/

    }

    public void preparePage()
    {
        final View view = getLayoutInflater().inflate(R.layout.activity_game, null);
        WebView webView = (WebView)view.findViewById(R.id.game_info);
        webView.loadData(game.getDescription(), "text/html",null);
        queue = RequestQueueSingleton.getInstance(this).getRequestQueue();
        Uri imagePath = new Uri.Builder().scheme("http").encodedAuthority(URLUtil.SERVER_IP+URLUtil.IMAGE_PORT).appendEncodedPath("assets/"+game.getId()+"/0.webp").build();
        ImageRequest imageRequest = new ImageRequest(imagePath.toString(), new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                ((ImageView) view.findViewById(R.id.game_image)).setImageBitmap(response);
                buildPage(view);
            }
        }, 0, 0, ImageView.ScaleType.CENTER_CROP, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(imageRequest);
    }

    private void buildPage(View view)
    {
        setContentView(view);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void launchStream(View view) {
        Uri streamPath = new Uri.Builder().scheme("http").encodedAuthority(URLUtil.SERVER_IP+URLUtil.STREAM_PORT).appendEncodedPath("app/"+game.getId()+"/launch").build();
        WebView stream = new WebView(this);
        stream.getSettings().setJavaScriptEnabled(true);
        //stream.addJavascriptInterface(StreamJavascriptInterpreter);
        stream.loadUrl(streamPath.toString());
        stream.setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY|View.SYSTEM_UI_FLAG_FULLSCREEN|View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        getActionBar().hide();
        setContentView(stream);
        CountDownTimer timer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                preparePage();
                getActionBar().show();
            }
        };
        timer.start();



    }
}
