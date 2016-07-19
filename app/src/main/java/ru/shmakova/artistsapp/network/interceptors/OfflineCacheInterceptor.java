package ru.shmakova.artistsapp.network.interceptors;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import ru.shmakova.artistsapp.App;
import ru.shmakova.artistsapp.utils.Utils;

/**
 * Created by shmakova on 19.07.16.
 */

public class OfflineCacheInterceptor implements Interceptor {
    @Override
    public Response intercept (Chain chain) throws IOException {
        Request request = chain.request();

        if (!Utils.isNetworkAvailable(App.getContext())) {
            CacheControl cacheControl = new CacheControl.Builder()
                    .maxStale(7, TimeUnit.DAYS)
                    .build();

            request = request.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .cacheControl(cacheControl)
                    .build();
        }

        return chain.proceed(request);
    }
}
