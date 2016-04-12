package ru.shmakova.artistsapp;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by shmakova on 12.04.16.
 */
public class Artist {
    private int id;
    private String name;
    private ArrayList<String> genres;
    private String coverSmall;
    private String coverBig;
    private int tracks;
    private int albums;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCoverSmall() {
        return coverSmall;
    }

    public String getCoverBig() {
        return coverBig;
    }

    public int getTracks() {
        return tracks;
    }

    public int getAlbums() {
        return albums;
    }

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

    public Artist(JSONObject object){
        try {
            this.name = object.getString("name");

            for (int i = 0; i < object.getJSONArray("genres").length(); i++) {
                this.genres.add(object.getJSONArray("genres").getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Artist> fromJson(JSONArray jsonObjects) {
        ArrayList<Artist> artists = new ArrayList<Artist>();

        for (int i = 0; i < jsonObjects.length(); i++) {
            try {
                artists.add(new Artist(jsonObjects.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return artists;
    }
}
