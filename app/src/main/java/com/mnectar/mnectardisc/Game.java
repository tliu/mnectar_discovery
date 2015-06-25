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
    private Uri link;

    public Game(String name, String id, Uri link) {
        this.name = name;
        this.id = id;
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public Uri getLink() {
        return link;
    }
}
