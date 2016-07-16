package ru.shmakova.artistsapp.managers;

import android.content.Context;

import java.util.List;

import retrofit2.Call;
import ru.shmakova.artistsapp.App;
import ru.shmakova.artistsapp.network.ServiceGenerator;
import ru.shmakova.artistsapp.network.YandexService;
import ru.shmakova.artistsapp.network.models.Artist;

/**
 * Created by shmakova on 16.07.16.
 */

public class DataManager {
    private static DataManager INSTANCE = null;
    private Context context;
    private YandexService yandexService;

    public DataManager() {
        this.context = App.getContext();
        this.yandexService = ServiceGenerator.createService(YandexService.class);
    }

    public static DataManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DataManager();
        }

        return INSTANCE;
    }

    public Context getContext() {
        return context;
    }

    public Call<List<Artist>> getArtistsList() {
        return yandexService.getArtistsList();
    }

}
