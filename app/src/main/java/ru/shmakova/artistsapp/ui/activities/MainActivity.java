package ru.shmakova.artistsapp.ui.activities;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;

import ru.shmakova.artistsapp.App;
import ru.shmakova.artistsapp.R;
import ru.shmakova.artistsapp.ui.fragments.AboutDialogFragment;
import ru.shmakova.artistsapp.ui.fragments.ArtistsListFragment;
import ru.shmakova.artistsapp.ui.fragments.MusicFragment;
import ru.shmakova.artistsapp.ui.fragments.MusicPreferenceFragment;

public class MainActivity extends BaseActivity implements
        SharedPreferences.OnSharedPreferenceChangeListener {
    private final static int HEADSET_PLUG_OUT = 0;
    private final static int HEADSET_PLUG_IN = 1;
    private FragmentManager supportFragmentManager;
    private SharedPreferences sharedPreferences;
    private int currentHeadSetState;
    private BroadcastReceiver musicIntentReceiver;

    @SuppressLint("InflateParams")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportFragmentManager = getSupportFragmentManager();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        musicIntentReceiver = new MusicIntentReceiver();

        App.get(this).applicationComponent().inject(this);

        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_frame_layout, new ArtistsListFragment())
                    .commit();
        }
    }

    @Override
    protected void onResume() {
        IntentFilter filter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
        registerReceiver(musicIntentReceiver, filter);
        super.onResume();
    }

    @Override
    protected void onPause() {
        unregisterReceiver(musicIntentReceiver);
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }

    private void showMusicBar() {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.music_frame_layout, new MusicFragment())
                .commit();
    }

    private void hideMusicBar() {
        if (supportFragmentManager.findFragmentById(R.id.music_frame_layout) != null) {
            supportFragmentManager
                    .beginTransaction()
                    .remove(getSupportFragmentManager().findFragmentById(R.id.music_frame_layout))
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.about:
                showAboutAlert();
                break;
            case R.id.feedback:
                composeEmail();
                break;
            case R.id.settings:
                supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.main_frame_layout, new MusicPreferenceFragment())
                        .addToBackStack(null)
                        .commit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return true;
    }

    private void showAboutAlert() {
        DialogFragment dialog = new AboutDialogFragment();
        dialog.show(supportFragmentManager, "AboutDialogFragment");
    }

    /**
     * Opens mail app
     */
    public void composeEmail() {
        Intent intent = new Intent(Intent.ACTION_SENDTO)
                .setData(Uri.parse("mailto:" + getString(R.string.email)))
                .putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_subject));
        startActivity(Intent.createChooser(intent, getString(R.string.send_email)));
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        updateMusicBar();
    }

    private void updateMusicBar() {
        boolean musicBarEnabled = sharedPreferences.getBoolean(getString(R.string.music_preference), false);

        if (musicBarEnabled) {
            switch (currentHeadSetState) {
                case HEADSET_PLUG_OUT:
                    hideMusicBar();
                    break;
                case HEADSET_PLUG_IN:
                    showMusicBar();
                    break;
            }
        } else {
            hideMusicBar();
        }
    }

    private class MusicIntentReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_HEADSET_PLUG)) {
                currentHeadSetState = intent.getIntExtra("state", -1);
                updateMusicBar();
            }
        }
    }
}
