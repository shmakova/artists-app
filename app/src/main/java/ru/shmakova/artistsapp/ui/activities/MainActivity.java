package ru.shmakova.artistsapp.ui.activities;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.shmakova.artistsapp.App;
import ru.shmakova.artistsapp.R;
import ru.shmakova.artistsapp.developer_settings.DeveloperSettingsModule;
import ru.shmakova.artistsapp.ui.fragments.ArtistsListFragment;
import ru.shmakova.artistsapp.ui.other.ViewModifier;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";

    @BindView(R.id.main_drawer_layout)
    DrawerLayout navigationDrawer;

    @Inject
    @Named(DeveloperSettingsModule.MAIN_ACTIVITY_VIEW_MODIFIER)
    ViewModifier viewModifier;

    @SuppressLint("InflateParams") // It's okay in our case.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.get(this).applicationComponent().inject(this);

        setContentView(viewModifier.modify(getLayoutInflater().inflate(R.layout.activity_main, null)));

        ButterKnife.bind(this);
        setupDrawer();

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_frame_layout, new ArtistsListFragment())
                    .commit();
        }
    }

    /**
     * Setups navigation drawer
     */
    private void setupDrawer() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                Log.d(TAG, item.getTitle().toString());
                Log.d(TAG, String.valueOf(item.getOrder()));
                switch (item.getItemId()) {
                    case R.id.about:
                        showAboutAlert();
                        break;
                    case R.id.feedback:
                        openMailApp();
                        break;

                }
                navigationDrawer.closeDrawer(GravityCompat.START);
                return false;
            }
        });
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
            case R.id.menu:
                navigationDrawer.openDrawer(GravityCompat.START);
                return true;
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
    private void openMailApp() {
        Intent mailIntent = new Intent(Intent.ACTION_SEND);
        mailIntent.setType("text/plain");
        mailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] { getString(R.string.email) });
        mailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_subject));
        startActivity(Intent.createChooser(mailIntent, getString(R.string.send_email)));
    }
}
