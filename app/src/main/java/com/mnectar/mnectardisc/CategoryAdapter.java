package com.mnectar.mnectardisc;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * Created by ian on 6/24/15.
 */

//update this with
public class CategoryAdapter extends ArrayAdapter<Category> {
    public CategoryAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Category c =getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.game_list, parent, false);
        }
        TextView title= (TextView) convertView.findViewById(R.id.category_title);
        title.setText(c.getName());
        LinearLayout games = (LinearLayout) convertView.findViewById(R.id.game_list);
        games.removeAllViews();
        populateList(c, games);
        return convertView;
    }

    private void populateList(Category c, LinearLayout games)
    {
        for (Game g: c.getGames())
        {
            RelativeLayout cv = (RelativeLayout) LayoutInflater.from(getContext()).inflate(R.layout.game_card,games,false);
            TextView name = (TextView) cv.findViewById(R.id.game_title);
            name.setText(g.getName());
            cv.setTag(g);
            games.addView(cv);
        }
    }
}
