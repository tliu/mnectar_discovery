package com.mnectar.mnectardisc;

import android.net.Uri;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ian on 6/24/15.
 */
public class Game implements Serializable{
    private String name;

    private String id;
    private String packageName;
    private float rating;
    private String description;
    private Uri link;
    private int coins;

    public Game(String name, String id, Uri link) {
        this.name = name;
        this.id = id;
        this.link = link;
    }

    public Game(String name, String id, String packageName, float rating, String description) {
        this.name = name;
        this.id = id;
        this.packageName = packageName;
        this.rating = rating;
        this.description = description;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public Uri getLink() {
        return link;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPackageName() {
        return packageName;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLink(Uri link) {
        this.link = link;
    }


}
