package com.mnectar.mnectardisc;

import java.util.ArrayList;

/**
 * Created by ian on 6/24/15.
 */
public class Category {
    private ArrayList<Game> games;
    private String name;

    public Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Game> getGames() {
        return games;
    }

    public void setGames(ArrayList<Game> games) {
        this.games = games;
    }

}

