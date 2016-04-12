package ru.shmakova.artistsapp;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by shmakova on 13.04.16.
 */
public interface YandexService {
    @GET("mobilization-2016/artists.json")
    Call<ArrayList<Artist>> listArtists();
}
