package ru.shmakova.artistsapp.ui.fragments;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import ru.shmakova.artistsapp.R;
import ru.shmakova.artistsapp.network.models.Artist;
import ru.shmakova.artistsapp.ui.activities.MainActivity;

public class ArtistFragment extends BaseFragment {
    private static final String ARTIST_KEY = "ARTIST_KEY";

    @BindView(R.id.cover_big)
    ImageView cover;
    @BindView(R.id.genres)
    TextView genres;
    @BindView(R.id.info)
    TextView info;
    @BindView(R.id.description)
    TextView description;


    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_artist, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Artist artist = getArguments().getParcelable(ARTIST_KEY);
        Resources resources = getResources();

        if (artist != null) {
            updateToolBar(artist.getName());

            Glide.with(view.getContext())
                    .load(artist.getCover().getBig())
                    .centerCrop()
                    .crossFade()
                    .into(cover);

            genres.setText(artist.getGenres());
            int albums = artist.getAlbums();
            int tracks = artist.getTracks();
            info.setText(resources.getQuantityString(R.plurals.albums, albums, albums) +
                    " ·" + resources.getQuantityString(R.plurals.tracks, tracks, tracks));
            description.setText(artist.getDescription());
        }
    }


    public static ArtistFragment newInstance(Artist artist) {
        Bundle args = new Bundle();
        ArtistFragment fragment = new ArtistFragment();
        args.putParcelable(ARTIST_KEY, artist);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Updates toolbar
     * @param text - toolbar's title
     */
    private void updateToolBar(String text) {
        ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();

        if (actionBar != null) {
            actionBar.setTitle(text);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
}
