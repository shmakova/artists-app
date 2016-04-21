package ru.shmakova.artistsapp.services;


import java.io.File;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import ru.shmakova.artistsapp.App;

/**
 * Created by shmakova on 21.04.16.
 */
public class CacheClient {
    private OkHttpClient client;

    public CacheClient() {
        File httpCacheDirectory = new File(App.getContext().getCacheDir(), "responses");
        Cache cache = new Cache(httpCacheDirectory, 10 * 1024 * 1024);

        client = new OkHttpClient.Builder()
                .cache(cache)
                .addNetworkInterceptor(new CachingInterceptor())
                .build();
    }

    public OkHttpClient getClient() {
        return client;
    }
}
