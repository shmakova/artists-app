package ru.shmakova.artistsapp.ui.fragments;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.preference.PreferenceFragmentCompat;


import ru.shmakova.artistsapp.R;
import ru.shmakova.artistsapp.ui.activities.MainActivity;

/**
 * Created by shmakova on 20.07.16.
 */

public class MusicPreferenceFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.preferences);
        updateToolBar();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateToolBar();
    }

    /**
     * Updates toolbar
     */
    private void updateToolBar() {
        ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();

        if (actionBar != null) {
            actionBar.setTitle(R.string.settings);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
}
