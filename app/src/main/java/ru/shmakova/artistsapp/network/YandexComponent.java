package ru.shmakova.artistsapp.network;

import android.support.annotation.NonNull;

import dagger.Component;
import ru.shmakova.artistsapp.ui.fragments.ArtistsListFragment;

/**
 * Created by shmakova on 12.08.16.
 */

@ArtistsScope
@Component(
        dependencies = NetworkComponent.class,
        modules = YandexModule.class
)
public interface YandexComponent {
    void inject(@NonNull ArtistsListFragment fragment);
}
