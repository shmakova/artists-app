package ru.shmakova.artistsapp.developer_settings;

import android.support.annotation.NonNull;

import ru.shmakova.artistsapp.ui.fragments.DeveloperSettingsFragment;

import dagger.Subcomponent;

@Subcomponent
public interface DeveloperSettingsComponent {
    void inject(@NonNull DeveloperSettingsFragment developerSettingsFragment);
}
