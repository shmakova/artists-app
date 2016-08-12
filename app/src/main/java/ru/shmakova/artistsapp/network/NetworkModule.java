package ru.shmakova.artistsapp.network;

import android.app.Application;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;

import java.io.File;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.shmakova.artistsapp.network.interceptors.CacheInterceptor;
import ru.shmakova.artistsapp.network.interceptors.OfflineCacheInterceptor;

/**
 * Created by shmakova on 11.08.16.
 */

@Module
public class NetworkModule {
    String baseUrl;

    public NetworkModule(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Provides
    @Singleton
    SharedPreferences providesSharedPreferences(Application application) {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

    @Provides
    @Singleton
    OfflineCacheInterceptor provideOfflineCacheInterceptor(Application application) {
        return new OfflineCacheInterceptor(application);
    }

    @Provides
    @Singleton
    CacheInterceptor provideCacheInterceptor() {
        return new CacheInterceptor();
    }

    @Provides
    @Singleton
    HttpLoggingInterceptor provideLoggingInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return logging;
    }


    @Provides
    @Singleton
    Cache provideOkHttpCache(Application application) {
        File httpCacheDirectory = new File(application.getCacheDir(), "http-cache");
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(httpCacheDirectory, cacheSize);
        return cache;
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(
            Cache cache,
            HttpLoggingInterceptor loggingInterceptor,
            OfflineCacheInterceptor offlineCacheInterceptor,
            CacheInterceptor cacheInterceptor) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(loggingInterceptor)
                .addInterceptor(offlineCacheInterceptor)
                .addNetworkInterceptor(cacheInterceptor)
                .cache(cache);
        return httpClient.build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .build();
        return retrofit;
    }
}
