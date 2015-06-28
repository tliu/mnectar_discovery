package com.mnectar.mnectardisc;

import android.content.res.Resources;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by thomas on 6/28/15.
 */
public class CategoryListAdapter extends BaseAdapter {
    private List<Category> cats;
    private LayoutInflater inflater;
    private Resources res;
    private View parent;
    public CategoryListAdapter(List<Category> cats, LayoutInflater inflater, Resources res, View parent) {
        this.cats = cats;
        this.inflater = inflater;
        this.res = res;
        this.parent = parent;
    }
    @Override
    public int getCount() {
        return cats.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RelativeLayout categoryLayout =(RelativeLayout) inflater.inflate(R.layout.game_list, parent, false);
        TextView title= (TextView) categoryLayout.findViewById(R.id.category_title);
        title.setText(cats.get(position).getName());
        LinearLayout games = (LinearLayout) categoryLayout.findViewById(R.id.game_list);
        //Random r = new Random();
        //Game g = c.getGames().get(r.nextInt(c.getGames().size()));
        //LinearLayout topthing=(LinearLayout)getLayoutInflater().inflate(R.layout.home_top, plv, false);
        //ImageView img = (ImageView)topthing.findViewById(R.id.main_image);
        //img.setImageDrawable(getResources().getDrawable(getResources().getIdentifier("com.mnectar.mnectardisc:drawable/main" + g.getId(), null, null)));

        games.removeAllViews();
        populateList(cats.get(position), games);
        //plv.addView(topthing);
        //plv.addView(categoryLayout);
        return categoryLayout;
    }


    private void populateList(Category c, LinearLayout games)
    {
        for (Game g: c.getGames())
        {
            final RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.game_card, games, false);
            CardView cv = (CardView) layout.findViewById(R.id.card_view);
            //final ImageView imageView = (ImageView) layout.findViewById(R.id.card_image);//ImageView)cv.findViewById(R.id.card_image);
            final String id = g.getId();
                    final ImageView imageView = (ImageView) layout.findViewById(R.id.card_image);//ImageView)cv.findViewById(R.id.card_image);
                    imageView.setImageDrawable(res.getDrawable(res.getIdentifier("logo" + id, "drawable", "com.mnectar.mnectardisc")));
            //imageView.setTag(cv);
            TextView name = (TextView) layout.findViewById(R.id.game_title);
            name.setText(g.getName());
            cv.setTag(g);
            games.addView(layout);
        }
    }
}
