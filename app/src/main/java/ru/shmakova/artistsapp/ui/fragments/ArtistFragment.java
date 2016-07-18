package ru.shmakova.artistsapp.ui.fragments;

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
import butterknife.ButterKnife;
import ru.shmakova.artistsapp.R;
import ru.shmakova.artistsapp.network.models.Artist;
import ru.shmakova.artistsapp.ui.activities.MainActivity;

public class ArtistFragment extends BaseFragment {
    private static final String ARTIST_KEY = "ARTIST_KEY";
    private Artist artist;

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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_artist, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        artist = getArguments().getParcelable(ARTIST_KEY);
        ButterKnife.bind(this, view);
        updateToolBar(artist.getName());

        Glide.with(view.getContext())
                .load(artist.getCover().getBig())
                .centerCrop()
                .crossFade()
                .into(cover);

        genres.setText(artist.getGenres());
        info.setText(artist.getTracksAndAlbumsInfo(" ·"));
        description.setText(artist.getDescription());
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
     * @param text
     */
    private void updateToolBar(String text) {
        ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle(text);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
}
