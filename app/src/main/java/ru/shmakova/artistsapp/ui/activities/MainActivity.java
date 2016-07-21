package ru.shmakova.artistsapp.ui.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;

import ru.shmakova.artistsapp.App;
import ru.shmakova.artistsapp.R;
import ru.shmakova.artistsapp.ui.fragments.ArtistsListFragment;
import ru.shmakova.artistsapp.ui.fragments.MusicFragment;
import ru.shmakova.artistsapp.ui.fragments.MusicPreferenceFragment;

public class MainActivity extends BaseActivity implements
        SharedPreferences.OnSharedPreferenceChangeListener {
    private static final String TAG = "MainActivity";
    private final static int HEADSET_PLUG_OUT = 0;
    private final static int HEADSET_PLUG_IN = 1;
    private FragmentManager supportFragmentManager;
    private SharedPreferences sharedPreferences;
    private int currentHeadSetState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportFragmentManager = getSupportFragmentManager();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        App.get(this).applicationComponent().inject(this);

        setContentView(getLayoutInflater().inflate(R.layout.activity_main, null));

        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_frame_layout, new ArtistsListFragment())
                    .commit();
        }
    }

    private BroadcastReceiver musicIntentReceiver;

    @Override
    protected void onResume() {
        IntentFilter filter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
        musicIntentReceiver = new MusicIntentReceiver();
        registerReceiver(musicIntentReceiver, filter);
        super.onResume();
    }

    @Override
    protected void onPause() {
        unregisterReceiver(musicIntentReceiver);
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }

    /**
     * Shows music bar
     */
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

    /**
     * Shows About alert
     */
    private void showAboutAlert() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.about)
                .setMessage(getString(R.string.about_text))
                .setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }

    /**
     * Opens mail app
     */
    public void composeEmail() {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:" + getString(R.string.email)));
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_subject));
        startActivity(Intent.createChooser(intent, getString(R.string.send_email)));
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        updateMusicBar();
    }

    /**
     * Updates music bar
     */
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
