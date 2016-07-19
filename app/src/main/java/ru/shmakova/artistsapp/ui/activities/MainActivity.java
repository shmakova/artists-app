package ru.shmakova.artistsapp.ui.activities;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;

import javax.inject.Inject;
import javax.inject.Named;

import ru.shmakova.artistsapp.App;
import ru.shmakova.artistsapp.R;
import ru.shmakova.artistsapp.developer_settings.DeveloperSettingsModule;
import ru.shmakova.artistsapp.ui.fragments.ArtistsListFragment;
import ru.shmakova.artistsapp.ui.other.ViewModifier;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";

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
        intent.setData(Uri.parse("mailto:" + R.string.email));
        intent.putExtra(Intent.EXTRA_SUBJECT, R.string.email_subject);
        startActivity(Intent.createChooser(intent, getString(R.string.send_email)));
    }
}
