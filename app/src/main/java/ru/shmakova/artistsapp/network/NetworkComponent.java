package ru.shmakova.artistsapp.network;

import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;
import ru.shmakova.artistsapp.ApplicationModule;
import ru.shmakova.artistsapp.ui.activities.MainActivity;

/**
 * Created by shmakova on 11.08.16.
 */

@Singleton
@Component(modules = {
        ApplicationModule.class,
        NetworkModule.class
})
public interface NetworkComponent {
    Retrofit retrofit();

    void inject(@NonNull MainActivity mainActivity);
}