package com.mnectar.mnectardisc;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by ian on 6/24/15.
 */
public class Category {
    private List<Game> games;
    private String name;

    public Category(String name) {
        this.name = name;
        this.games = new ArrayList<Game>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(ArrayList<Game> games) {
        this.games = games;
    }

    public void addGame(Game game) {
        this.games.add(game);
    }

}

