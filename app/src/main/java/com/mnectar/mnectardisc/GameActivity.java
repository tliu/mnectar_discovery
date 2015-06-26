package com.mnectar.mnectardisc;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;



public class GameActivity extends Activity {
    Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
<<<<<<< Updated upstream

=======
        game = getIntent().getExtras().get()
        WebView webView = (WebView)findViewById(R.id.game_info);
        webView.loadData(" <div class=\"id-app-orig-desc\">From rage-\u00ADfilled Barbarians with glorious mustaches to pyromaniac wizards, \n" +
                "raise your own army and lead your clan to victory! Build your village to fend off raiders, battle against \n" +
                "millions of players worldwide, and forge a powerful clan with others to destroy enemy clans.<p>PLEASE NOTE! \n" +
                "Clash of Clans is free to download and play, however some game items can also be purchased for real money. If \n" +
                "you do not want to use this feature, please set up password protection for purchases in the settings of your \n" +
                "Google Play Store app. Also, under our Terms of Service and Privacy Policy, you must be at least 13 years of \n" +
                "age to play or download Clash of Clans.<p>A network connection is also required.<p>FEATURES<br>●\tBuild \n" +
                "your village into an unbeatable fortress <br>●\tRaise your own army of Barbarians, Archers, Hog Riders, \n" +
                "Wizards, Dragons and other mighty fighters<br>●\tBattle with players worldwide and take their Trophies<br>●\t\n" +
                "Join together with other players to form the ultimate Clan<br>●\tFight against rival Clans in epic Clan Wars \n" +
                "<br>●\tBuild 18 unique units with multiple levels of upgrades<br>●\tDiscover your favorite attacking army \n" +
                "from countless combinations of troops, spells, Heroes and Clan reinforcements <br>●\tDefend your village \n" +
                "with a multitude of Cannons, Towers, Mortars, Bombs, Traps  and Walls<br>●\tFight against the Goblin King \n" +
                "in a campaign through the realm<p><br>PLAYER REVIEWS <br>Clash of Clans proudly announces over five million \n" +
                "five star reviews on Google Play.<p><br>SUPPORT<br>Chief, are you having problems?  Visit <a \n" +
                "href=\"https://www.google.com/url?q=https://www.google.com/url?q%3Dhttp://supercell.helpshift.com/a/clash-of-clans/%26sa%3DD%26usg%3DAFQjCNFcI_matDwefmbD0Azk46RfAf9Hag&amp;sa=D&amp;usg=AFQjCNGLcnaeSn0xKHVDqHJBxA6mgGV4bw\" \n" +
                "target=\"_blank\">http://supercell.helpshift.com/a/clash-of-clans/</a> or <a \n" +
                "href=\"https://www.google.com/url?q=https://www.google.com/url?q%3Dhttp://supr.cl/ClashForum%26sa%3DD%26usg%3DAFQjCNFs87w9K1PX6EdX0ZmlIdQMSRBq-w&amp;sa=D&amp;usg=AFQjCNF_8XXsl7-5P8ZSfJm5iHnCQzlF6A\" \n" +
                "target=\"_blank\">http://supr.cl/ClashForum</a> or contact us in game by going to Settings &gt; Help and \n" +
                "Support.<p>Privacy Policy:<br><a \n" +
                "href=\"https://www.google.com/url?q=https://www.google.com/url?q%3Dhttp://www.supercell.net/privacy-policy/%26sa%3DD%26usg%3DAFQjCNE162UdtDT8ylD_gazuKA46Tekurw&amp;sa=D&amp;usg=AFQjCNGWOhhjZ6azrErucAsezl89cg5DlA\" \n" +
                "target=\"_blank\">http://www.supercell.net/privacy-policy/</a><p>Terms of Service:<br><a \n" +
                "href=\"https://www.google.com/url?q=https://www.google.com/url?q%3Dhttp://www.supercell.net/terms-of-service/%26sa%3DD%26usg%3DAFQjCNFw6_0AxQO-3dlnZ9MNUFbEZbKmSw&amp;sa=D&amp;usg=AFQjCNHRV2r21GY5LM1zbP1tVt1Yblcd9g\" \n" +
                "target=\"_blank\">http://www.supercell.net/terms-of-service/</a><p>Parent’s Guide:<br><a \n" +
                "href=\"https://www.google.com/url?q=https://www.google.com/url?q%3Dhttp://www.supercell.net/parents%26sa%3DD%26usg%3DAFQjCNEzFcZDU63BKr8NHhTEc9cxqC-6Bg&amp;sa=D&amp;usg=AFQjCNGHiPzKy3g0EPtaX5tTxXQPeSAC7Q\" \n" +
                "target=\"_blank\">http://www.supercell.net/parents</a></p></p></p></p></p></p></p></p></div> \n", "text/html",null);
>>>>>>> Stashed changes
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
}
