package ru.shmakova.artistsapp.services;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;
import ru.shmakova.artistsapp.App;
import ru.shmakova.artistsapp.utils.Utils;

/**
 * Created by shmakova on 21.04.16.
 */
public class CachingInterceptor implements Interceptor {
    @Override public Response intercept(Interceptor.Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());

        if (Utils.isNetworkAvailable(App.getContext())) {
            int maxAge = 60;

            return originalResponse.newBuilder()
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .build();
        } else {
            int maxStale = 60 * 60 * 24 * 28;

            return originalResponse.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .build();
        }
    }
}