package ru.shmakova.artistsapp.network;

import java.io.File;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.shmakova.artistsapp.App;
import ru.shmakova.artistsapp.network.interceptors.CachingInterceptor;
import ru.shmakova.artistsapp.utils.AppConfig;

/**
 * Created by shmakova on 16.07.16.
 */

public class ServiceGenerator {
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();


    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(AppConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        File httpCacheDirectory = new File(App.getContext().getCacheDir(), "responses");
        Cache cache = new Cache(httpCacheDirectory, 10 * 1024 * 1024);

        httpClient.addInterceptor(logging);
        httpClient.cache(cache);
        httpClient.addInterceptor(new CachingInterceptor());

        Retrofit retrofit = builder
                .client(httpClient.build())
                .build();

        return retrofit.create(serviceClass);
    }


}