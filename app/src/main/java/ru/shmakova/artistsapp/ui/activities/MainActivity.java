package ru.shmakova.artistsapp.ui.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;

import javax.inject.Inject;
import javax.inject.Named;

import ru.shmakova.artistsapp.App;
import ru.shmakova.artistsapp.R;
import ru.shmakova.artistsapp.developer_settings.DeveloperSettingsModule;
import ru.shmakova.artistsapp.ui.fragments.ArtistsListFragment;
import ru.shmakova.artistsapp.ui.other.ViewModifier;

public class MainActivity extends BaseActivity {
    @Inject
    @Named(DeveloperSettingsModule.MAIN_ACTIVITY_VIEW_MODIFIER)
    ViewModifier viewModifier;

    @SuppressLint("InflateParams") // It's okay in our case.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.get(this).applicationComponent().inject(this);

        setContentView(viewModifier.modify(getLayoutInflater().inflate(R.layout.activity_main, null)));

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_frame_layout, new ArtistsListFragment())
                    .commit();
        }
    }
}
