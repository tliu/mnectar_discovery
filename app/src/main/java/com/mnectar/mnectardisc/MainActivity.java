package com.mnectar.mnectardisc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mnectar.mnectardisc.backend.GetCategoriesTask;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    List<Category> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new GetCategoriesTask(this).execute();
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public void buildUI()
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
