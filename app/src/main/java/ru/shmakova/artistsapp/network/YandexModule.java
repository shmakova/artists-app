package ru.shmakova.artistsapp.network;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by shmakova on 12.08.16.
 */

@Module
public class YandexModule {
    @Provides
    @ArtistsScope
    public YandexService providesYandexService(Retrofit retrofit) {
        return retrofit.create(YandexService.class);
    }
}
