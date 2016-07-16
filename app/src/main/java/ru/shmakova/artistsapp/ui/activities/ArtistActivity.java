package ru.shmakova.artistsapp.ui.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import ru.shmakova.artistsapp.App;
import ru.shmakova.artistsapp.R;
import ru.shmakova.artistsapp.network.models.Artist;

/**
 * Created by shmakova on 17.04.16.
 */
public class ArtistActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist);

        Artist artist = getIntent().getParcelableExtra("artist");
        toolbar().setTitle(artist.getName());

        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        ImageView cover = (ImageView) findViewById(R.id.cover);
        Picasso.with(this).load(artist.getCover().getBig())
                .into(cover, new Callback() {
                    @Override
                    public void onSuccess() {
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        Toast.makeText(
                                App.getContext(),
                                R.string.network_unavailable,
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                });

        TextView genres = (TextView) findViewById(R.id.genres);
        genres.setText(artist.getGenres());

        TextView info = (TextView) findViewById(R.id.info);
        info.setText(artist.getTracksAndAlbumsInfo(" ·"));

        TextView description = (TextView) findViewById(R.id.description);
        description.setText(artist.getDescription());
    }

    /**
     * On Back button pressed
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        supportFinishAfterTransition();
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
                supportFinishAfterTransition();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
