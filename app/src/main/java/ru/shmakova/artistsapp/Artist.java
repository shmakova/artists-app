package ru.shmakova.artistsapp;

import android.text.TextUtils;

import java.util.ArrayList;

/**
 * Created by shmakova on 12.04.16.
 */
public class Artist {
    private int id;
    private String name;
    private ArrayList<String> genres;
    private Cover cover;
    private int tracks;
    private int albums;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Cover getCover() {
        return cover;
    }

    public String getAlbumsAndTracksInfo() {
        return albums + " альбомов, " + tracks + " песен";
    }

    public String getGenres() {
        return TextUtils.join(",", genres);
    }
}
