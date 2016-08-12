package ru.shmakova.artistsapp;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import ru.shmakova.artistsapp.network.DaggerNetworkComponent;
import ru.shmakova.artistsapp.network.DaggerYandexComponent;
import ru.shmakova.artistsapp.network.NetworkComponent;
import ru.shmakova.artistsapp.network.NetworkModule;
import ru.shmakova.artistsapp.network.YandexComponent;
import ru.shmakova.artistsapp.network.YandexModule;
import ru.shmakova.artistsapp.utils.AppConfig;
import timber.log.Timber;

/**
 * Created by shmakova on 17.04.16.
 */
public class App extends Application {
    private ApplicationComponent applicationComponent;
    private NetworkComponent networkComponent;
    private YandexComponent yandexComponent;

    // Prevent need in a singleton (global) reference to the application object.
    @NonNull
    public static App get(@NonNull Context context) {
        return (App) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = prepareApplicationComponent().build();
        networkComponent = prepareNetworkComponent().build();
        yandexComponent = prepareYandexComponent().build();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    @NonNull
    protected DaggerApplicationComponent.Builder prepareApplicationComponent() {
        return DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this));
    }

    @NonNull
    protected DaggerNetworkComponent.Builder prepareNetworkComponent() {
        return DaggerNetworkComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .networkModule(new NetworkModule(AppConfig.BASE_URL));
    }

    @NonNull
    protected DaggerYandexComponent.Builder prepareYandexComponent() {
        return DaggerYandexComponent.builder()
                .networkComponent(networkComponent())
                .yandexModule(new YandexModule());
    }

    @NonNull
    public ApplicationComponent applicationComponent() {
        return applicationComponent;
    }

    @NonNull
    public NetworkComponent networkComponent() {
        return networkComponent;
    }

    @NonNull
    public YandexComponent yandexComponent() {
        return yandexComponent;
    }
}
