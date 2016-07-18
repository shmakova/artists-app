package ru.shmakova.artistsapp.ui.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.MenuItem;

import javax.inject.Inject;
import javax.inject.Named;

import ru.shmakova.artistsapp.App;
import ru.shmakova.artistsapp.R;
import ru.shmakova.artistsapp.developer_settings.DeveloperSettingsModule;
import ru.shmakova.artistsapp.network.models.Artist;
import ru.shmakova.artistsapp.ui.fragments.ArtistFragment;
import ru.shmakova.artistsapp.ui.fragments.ArtistsListFragment;
import ru.shmakova.artistsapp.ui.other.ViewModifier;

public class MainActivity extends BaseActivity implements ArtistsListFragment.OnArtistClickListener {
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

    @Override
    public void onArtistClick(Artist artist) {
        Fragment artistFragment = ArtistFragment.newInstance(artist);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_frame_layout, artistFragment)
                .addToBackStack(null)
                .commit();
    }

    /**
     * On Action bar back button pressed
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
