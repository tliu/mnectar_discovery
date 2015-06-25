package com.mnectar.mnectardisc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends Activity {

    ArrayList<Category> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        categories = new ArrayList<>();
        //load game data here

        Category match3= new Category("Match 3 Games");
        Category hiddenObject = new Category("Hidden Object Games");
        Category fighting= new Category("Fighting Games");
        ArrayList<Game> match3Games= new ArrayList<>();
        ArrayList<Game> hiddenObjectGames= new ArrayList<>();
        ArrayList<Game> fightingGames= new ArrayList<>();

        match3Games.add(new Game("Candy Crush","1",null));
        match3Games.add(new Game("Bejeweled","2",null));
        match3Games.add(new Game("Mystery Match","3",null));

        hiddenObjectGames.add(new Game("Agent Alice","1",null));
        hiddenObjectGames.add(new Game("Secret Society","2",null));
        hiddenObjectGames.add(new Game("Some other game","3",null));

        fightingGames.add(new Game("Contest of Champions", "1", null));
        fightingGames.add(new Game("Fight!", "2", null));
        fightingGames.add(new Game("game 3!", "3", null));

        match3.setGames(match3Games);
        hiddenObject.setGames(hiddenObjectGames);
        fighting.setGames(fightingGames);

        categories.add(match3);
        categories.add(hiddenObject);
        categories.add(fighting);


        buildUI();
    }

    private void buildUI()
    {
        //ImageView mainImage = (ImageView)findViewById(R.id.main_image);
        //mainImage.setImageURI(null);//URI for image should be provided by backend.
        LinearLayout layout = (LinearLayout) findViewById(R.id.category_list);
        for (Category c : categories)
        {
            RelativeLayout categoryLayout = (RelativeLayout) getLayoutInflater().inflate(R.layout.game_list, layout, false);
            TextView title= (TextView) categoryLayout.findViewById(R.id.category_title);
            title.setText(c.getName());
            LinearLayout games = (LinearLayout) categoryLayout.findViewById(R.id.game_list);
            games.removeAllViews();
            populateList(c, games);
            layout.addView(categoryLayout);

        }
    }

    private void populateList(Category c, LinearLayout games)
    {
        for (Game g: c.getGames())
        {
            RelativeLayout cv = (RelativeLayout) getLayoutInflater().inflate(R.layout.game_card, games, false);
            TextView name = (TextView) cv.findViewById(R.id.game_title);
            name.setText(g.getName());
            cv.setTag(g);
            games.addView(cv);
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void displayGame(View view) {
        Game g = (Game)view.getTag();
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(getString(R.string.game), g);
        //startActivity(intent);
    }
}
