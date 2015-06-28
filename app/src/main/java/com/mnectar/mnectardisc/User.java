package com.mnectar.mnectardisc;

/**
 * Created by ian on 6/27/15.
 */
public class User {
    private static User thisUser;
    private int coins;
    private String name;

    public static synchronized User getUser()
    {
        if (thisUser== null) thisUser = new User();
        return thisUser;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private User()
    {
        coins=90;
        name="DemoUser";
    }
}
