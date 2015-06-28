package com.mnectar.mnectardisc;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class RewardsActivity extends Activity {

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewards);
        user = User.getUser();
        ((TextView)findViewById(R.id.coin_count)).setText(String.valueOf(user.getCoins()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rewards, menu);
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
    public void launchSpotify(View view) {
        //intent to launch spotify or get it from the app store
//        int cost = Integer.getInteger((String)view.getTag());
//        if (user.getCoins()<cost) return;
        Intent intent;

        //check to make sure app is installed
        PackageManager packageManager = getPackageManager();
        intent = packageManager.getLaunchIntentForPackage("com.spotify.music");
        if (intent != null)
        {
            if (user.getCoins()>=100) {
                //do something to make sure the reward is activated
                user.removeCoins(100);
                startActivity(intent);
            }
            else
            {
                Toast toast = Toast.makeText(this, "Not Enough Coins", Toast.LENGTH_LONG);
                toast.show();
            }        }
        else
        {
            String uri="market://details?id=com.spotify.music";
            intent= new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(uri));
            startActivity(intent);

            //change intent to get app from play store don't remove coins yet?
        }

    }

    public void launchPandora(View view) {
        //intent to launch pandora or get it from the app store
        Intent intent;

        //check to make sure app is installed
        PackageManager packageManager = getPackageManager();
        intent = packageManager.getLaunchIntentForPackage("com.pandora.android");
        if (intent!=null)
        {
            if (user.getCoins()>=100) {
                //do something to make sure the reward is activated
                user.removeCoins(100);
                startActivity(intent);
            }
            else
            {
                Toast toast = Toast.makeText(this, "Not Enough Coins", Toast.LENGTH_LONG);
                toast.show();
            }
        }
        else
        {
            String uri="market://details?id=com.pandora.android";
            intent= new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(uri));
            startActivity(intent);
            //change intent to get app from play store don't remove coins yet?
        }
    }
    public void goToSettings(View view) {

    }

    public void goToMain(View view) {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public void goToRewards(View view) {

    }
}
