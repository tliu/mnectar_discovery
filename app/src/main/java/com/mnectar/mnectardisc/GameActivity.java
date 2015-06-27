package com.mnectar.mnectardisc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ShareActionProvider;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.mnectar.mnectardisc.backend.GetGameTask;
import com.mnectar.mnectardisc.backend.URLUtil;


public class GameActivity extends Activity {
    private Game game;
    private RequestQueue queue;
    private ShareActionProvider shareActionProvider;
    private Uri streamPath;

    public void setGame(Game game) {
        this.game = game;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //javascript debugging
        WebView.setWebContentsDebuggingEnabled(true);
        //setContentView(R.layout.activity_game);
        Intent intent= getIntent();
        String action = intent.getAction();
        if (action.equals(Intent.ACTION_VIEW)) {
            Uri uri = intent.getData();
            String id = uri.getPath();
            Log.d("Uri path: ",id);
            id = id.substring(5,id.length()-6);
            Log.d("Uri path: ", id);
            new GetGameTask(this).execute(id);
            DummyView dummyView = new DummyView(this);
            dummyView.setTag(uri);
            launchStream(dummyView);


        }
        else {
            game = (Game) intent.getExtras().get(getString(R.string.game));


            preparePage();
        }

    }

    public void preparePage()
    {
        final View view = getLayoutInflater().inflate(R.layout.activity_game, null);
        WebView webView = (WebView)view.findViewById(R.id.game_info);
        webView.loadData(game.getDescription(), "text/html", null);
        queue = RequestQueueSingleton.getInstance(this).getRequestQueue();
        Uri imagePath = new Uri.Builder().scheme("http").encodedAuthority(URLUtil.SERVER_IP+URLUtil.IMAGE_PORT).appendEncodedPath("assets/"+game.getId()+"/0.webp").build();
        streamPath = new Uri.Builder().scheme("http").encodedAuthority(URLUtil.SERVER_IP+URLUtil.STREAM_PORT).appendEncodedPath("app/"+game.getId()+"/launch").build();
        ImageRequest imageRequest = new ImageRequest(imagePath.toString(), new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                ((ImageView) view.findViewById(R.id.game_image)).setImageBitmap(response);
            }
        }, 0, 0, ImageView.ScaleType.CENTER_CROP, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(imageRequest);
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out this cool demo for "+game.getName()+": "+streamPath.toString());
        shareIntent.setType("text/plain");
        setShareActionProvider(shareIntent);
        buildPage(view);
    }

    private void buildPage(View view)
    {
        setContentView(view);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game, menu);
        MenuItem shareItem = menu.findItem(R.id.action_share);
        Log.d("Menu: ","Menu Inflated");
        shareActionProvider = (ShareActionProvider) shareItem.getActionProvider();
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        if (game != null)  shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out this cool demo for " + game.getName() + ": " + streamPath.toString());
        shareIntent.setType("text/plain");
        setShareActionProvider(shareIntent);
        Log.d("Share: ", shareActionProvider.toString());
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
        if (view.getClass() == DummyView.class) streamPath = (Uri) view.getTag() ;
        WebView stream = new WebView(this);
        stream.getSettings().setJavaScriptEnabled(true);
        //stream.addJavascriptInterface(StreamJavascriptInterpreter);
        stream.loadUrl(streamPath.toString());
        stream.setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY|View.SYSTEM_UI_FLAG_FULLSCREEN|View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        //getActionBar().hide();
        setContentView(stream);
        CountDownTimer timer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                preparePage();
                //getActionBar().show();
            }

        };
        timer.start();
    }

    private void setShareActionProvider(Intent intent)
    {
        if (shareActionProvider!= null)
        {
            shareActionProvider.setShareIntent(intent);
        }
    }

    private class DummyView extends View{
        public DummyView(Context context)
        {
            super(context);
        }
    }
}
