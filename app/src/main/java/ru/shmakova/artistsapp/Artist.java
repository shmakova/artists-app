package ru.shmakova.artistsapp;

import android.text.TextUtils;

import java.util.ArrayList;

/**
 * Created by shmakova on 12.04.16.
 */
public class Artist {
    public int id;
    public String name;
    public ArrayList<String> genres;
    public String coverSmall;
    public String coverBig;
    public int tracks;
    public int albums;

    public String getGenres() {
        return TextUtils.join(",", genres);
    }

    public Artist(int id, String name, ArrayList<String> genres, String coverSmall, String coverBig) {
        this.id = id;
        this.name = name;
        this.genres = genres;
        this.coverSmall = coverSmall;
        this.coverBig = coverBig;
    }
}
