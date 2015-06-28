package com.mnectar.mnectardisc;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.mnectar.mnectardisc.backend.GetCategoriesTask;
import com.mnectar.mnectardisc.backend.URLUtil;
import com.nirhart.parallaxscroll.views.ParallaxListView;

import java.util.List;
import java.util.Random;
import java.util.logging.Handler;


public class MainActivity extends Activity {

    List<Category> categories;
    private User user;
    private ParallaxListView plv;

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "MainActivity";
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        plv = (ParallaxListView) findViewById(R.id.list_view);
//        CustomListAdapter
        new GetCategoriesTask(this).execute();
        user = User.getUser();
        ((TextView)findViewById(R.id.coin_count)).setText(String.valueOf(user.getCoins()));

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                SharedPreferences sharedPreferences =
                        PreferenceManager.getDefaultSharedPreferences(context);
                boolean sentToken = sharedPreferences
                        .getBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false);
                if (sentToken) {
                } else {
                }
            }
        };

        if (checkPlayServices()) {
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(QuickstartPreferences.REGISTRATION_COMPLETE));
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }




    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public void buildUI()
    {
        //ImageView mainImage = (ImageView)findViewById(R.id.main_image);
        //mainImage.setImageURI(null);//URI for image should be provided by backend.
        getActionBar().setTitle("Appable");



        Random r = new Random();
        Category c = categories.get(r.nextInt(categories.size()));
        Game g = c.getGames().get(r.nextInt(c.getGames().size()));
        RelativeLayout topthing=(RelativeLayout)getLayoutInflater().inflate(R.layout.home_top, plv, false);
        topthing.setTag(g);
        ImageView img = (ImageView)topthing.findViewById(R.id.main_image);
        img.setImageDrawable(getResources().getDrawable(getResources().getIdentifier("com.mnectar.mnectardisc:drawable/main" + g.getId(), null, null)));
        TextView tv = (TextView)topthing.findViewById(R.id.featured_title);
        tv.setText(g.getName());


        plv.addParallaxedHeaderView(topthing);
        plv.setAdapter(new CategoryListAdapter(categories, getLayoutInflater(), getResources(), plv));
        /*
        for (Category c : categories)
        {
            RelativeLayout categoryLayout =(RelativeLayout) getLayoutInflater().inflate(R.layout.game_list, plv, false);
            TextView title= (TextView) categoryLayout.findViewById(R.id.category_title);
            title.setText(c.getName());
            LinearLayout games = (LinearLayout) categoryLayout.findViewById(R.id.game_list);
            Random r = new Random();
            Game g = c.getGames().get(r.nextInt(c.getGames().size()));
            LinearLayout topthing=(LinearLayout)getLayoutInflater().inflate(R.layout.home_top, plv, false);
            ImageView img = (ImageView)topthing.findViewById(R.id.main_image);
            img.setImageDrawable(getResources().getDrawable(getResources().getIdentifier("com.mnectar.mnectardisc:drawable/main" + g.getId(), null, null)));

            games.removeAllViews();
            populateList(c, games);
            plv.addView(topthing);
            plv.addView(categoryLayout);

        }
        */
    }

    private void populateList(Category c, LinearLayout games)
    {
        for (Game g: c.getGames())
        {
            RelativeLayout layout = (RelativeLayout) getLayoutInflater().inflate(R.layout.game_card, games, false);
            CardView cv = (CardView) layout.findViewById(R.id.card_view);
            final ImageView imageView = (ImageView) layout.findViewById(R.id.card_image);//ImageView)cv.findViewById(R.id.card_image);
            final String id = g.getId();
            Thread thread = new Thread() {
                @Override
                public void run() {
                    imageView.setImageDrawable(getResources().getDrawable(getResources().getIdentifier("logo" + id, "drawable", getPackageName())));
                }
            };
            thread.start();
            //imageView.setTag(cv);
            TextView name = (TextView) layout.findViewById(R.id.game_title);
            name.setText(g.getName());
            cv.setTag(g);
            games.addView(layout);
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return false;
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

    public void displayGame(View view) {
        Game g = (Game)view.getTag();
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(getString(R.string.game), g);
        intent.setAction("showGame");
        startActivity(intent);
    }

    public void goToSettings(View view) {
        Random r = new Random();
        Category c = categories.get(r.nextInt(categories.size()));
        Game g = c.getGames().get(r.nextInt(c.getGames().size()));
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(getString(R.string.game), g);
        intent.setAction("showGame");
        startActivity(intent);
    }

    public void goToMain(View view) {
    }

    public void goToRewards(View view) {
        Intent intent = new Intent(this,RewardsActivity.class);
        startActivity(intent);
    }
}
