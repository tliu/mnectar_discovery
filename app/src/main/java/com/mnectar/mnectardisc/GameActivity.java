package com.mnectar.mnectardisc;

import android.app.Activity;
import android.content.Context;

import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;

import android.os.SystemClock;
import android.util.Log;

import android.view.Display;

import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.ShareActionProvider;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.manuelpeinado.fadingactionbar.FadingActionBarHelper;
import com.mnectar.mnectardisc.backend.GetGameTask;
import com.mnectar.mnectardisc.backend.URLUtil;


public class GameActivity extends Activity {
    private Game game;
    private RequestQueue queue;
    private ShareActionProvider shareActionProvider;
    private Uri streamPath;
    private User user;

    private Drawable mActionBarBackgroundDrawable;
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
        user = User.getUser();
        if (action.equals(Intent.ACTION_VIEW)) {
            Uri uri = intent.getData();
            String id = uri.getPath();
            Log.d("Uri path: ",id);
            id = id.substring(5,id.length()-7);
            Log.d("Uri path: ", id);
            new GetGameTask(this).execute(id);
            DummyView dummyView = new DummyView(this);
            dummyView.setTag(uri);
            launchStream(dummyView);


        }
        else {
            game = (Game) intent.getExtras().get(getString(R.string.game));


            preparePage();

            FadingActionBarHelper helper = new FadingActionBarHelper()
                    .actionBarBackground(R.drawable.ab_background)
                    .headerLayout(R.layout.header_light)
                    .contentLayout(R.layout.activity_scrollview);
            setContentView(helper.createView(this));
            helper.initActionBar(this);

            ImageView imageView = (ImageView)findViewById(R.id.image_header);
            Log.i("HEIGHT", ""+imageView.getHeight());

            WebView webView = (WebView) findViewById(R.id.webView);
            webView.setWebViewClient(new WebViewClient());
            webView.loadData(game.getDescription(), "text/html", null);

            /*
            Uri imagePath = new Uri.Builder().scheme("http").encodedAuthority(URLUtil.SERVER_IP+URLUtil.IMAGE_PORT).appendEncodedPath("assets/"+game.getId()+"/main.webp").build();
            NetworkImageView imageView = (NetworkImageView) findViewById(R.id.image_header);//ImageView)cv.findViewById(R.id.card_image);
            //imageView.setTag(cv);

            ImageLoader loader= RequestQueueSingleton.getInstance(imageView.getContext()).getImageLoader();
            imageView.setImageUrl(imagePath.toString(), loader);
            */


            /*
            WebView webView = (WebView)findViewById(R.id.webView);


            mActionBarBackgroundDrawable = getResources().getDrawable(R.drawable.ab_background);
            mActionBarBackgroundDrawable.setAlpha(0);

            getActionBar().setBackgroundDrawable(mActionBarBackgroundDrawable);

            ((NotifyingScrollView) findViewById(R.id.scroll_view)).setOnScrollChangedListener(mOnScrollChangedListener);
            */
        }



    }

    private NotifyingScrollView.OnScrollChangedListener mOnScrollChangedListener = new NotifyingScrollView.OnScrollChangedListener() {
        public void onScrollChanged(ScrollView who, int l, int t, int oldl, int oldt) {
            final int headerHeight = findViewById(R.id.image_header).getHeight() - getActionBar().getHeight();
            final float ratio = (float) Math.min(Math.max(t, 0), headerHeight) / headerHeight;
            final int newAlpha = (int) (ratio * 255);
            mActionBarBackgroundDrawable.setAlpha(newAlpha);
        }
    };

    public void preparePage()
    {
        //final View view = getLayoutInflater().inflate(R.layout.activity_home, null);
        //WebView webView = (WebView)findViewById(R.id.game_info);
        //webView.loadData(game.getDescription(), "text/html", null);
        queue = RequestQueueSingleton.getInstance(this).getRequestQueue();
        streamPath = new Uri.Builder().scheme("http").encodedAuthority(URLUtil.SERVER_IP+URLUtil.STREAM_PORT).appendEncodedPath("app/"+game.getId()+"/launch").build();


        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out this cool demo for " + game.getName() + ": " + streamPath.toString());
        shareIntent.setType("text/plain");
        setShareActionProvider(shareIntent);

        //buildPage(view);
    }

    private void buildPage(View view)
    {
        setContentView(view);
        //((TextView)findViewById(R.id.coin_count)).setText(String.valueOf(user.getCoins()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game, menu);
        /*
        MenuItem shareItem = menu.findItem(R.id.action_share);
        Log.d("Menu: ","Menu Inflated");
        shareActionProvider = (ShareActionProvider) shareItem.getActionProvider();
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        if (game != null)  shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out this cool demo for " + game.getName() + ": " + streamPath.toString());
        shareIntent.setType("text/plain");
        setShareActionProvider(shareIntent);
        Log.d("Share: ", shareActionProvider.toString());
        */
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

    private int getScale(){
        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int width = display.getWidth();
        Double val = new Double(width)/new Double(320.0);
        val = val * 100d;
        return val.intValue();
    }

    public void launchStream(View view) {
        if (view.getClass() == DummyView.class) streamPath = (Uri) view.getTag() ;
        WebView stream = new WebView(this);
        stream.getSettings().setJavaScriptEnabled(true);
        //stream.addJavascriptInterface(StreamJavascriptInterpreter);
        stream.loadUrl(streamPath.toString());
        stream.setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY|View.SYSTEM_UI_FLAG_FULLSCREEN|View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        stream.setPadding(0, 0, 0, 0);

        stream.setInitialScale(getScale());
        setContentView(stream);
        CountDownTimer timer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                user.addCoins(game.getCoins());
                preparePage();
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
    public void goToSettings(View view) {

    }

    public void goToMain(View view) {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);    }

    public void goToRewards(View view) {
        Intent intent = new Intent(this,RewardsActivity.class);
        startActivity(intent);
    }

}
