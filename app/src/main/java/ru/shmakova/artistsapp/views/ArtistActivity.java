package ru.shmakova.artistsapp.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ru.shmakova.artistsapp.R;
import ru.shmakova.artistsapp.models.Artist;

/**
 * Created by shmakova on 17.04.16.
 */
public class ArtistActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist);

        Artist artist = getIntent().getParcelableExtra("artist");
        getSupportActionBar().setTitle(artist.getName());

        ImageView cover = (ImageView) findViewById(R.id.cover);
        Picasso.with(this).load(artist.getCover().getBig()).into(cover);

        TextView genres = (TextView) findViewById(R.id.genres);
        genres.setText(artist.getGenres());

        TextView info = (TextView) findViewById(R.id.info);
        info.setText(getResources().getQuantityString(R.plurals.albums, artist.getAlbums(), artist.getAlbums())
                + "  \u00b7  "
                + getResources().getQuantityString(R.plurals.tracks, artist.getTracks(), artist.getTracks()));

        TextView description = (TextView) findViewById(R.id.description);
        description.setText(artist.getDescription());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        supportFinishAfterTransition();
    }

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
