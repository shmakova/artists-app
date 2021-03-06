package ru.shmakova.artistsapp.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.shmakova.artistsapp.App;
import ru.shmakova.artistsapp.R;
import ru.shmakova.artistsapp.network.YandexService;
import ru.shmakova.artistsapp.network.models.Artist;
import ru.shmakova.artistsapp.ui.activities.MainActivity;
import ru.shmakova.artistsapp.ui.adapters.ArtistsAdapter;
import timber.log.Timber;

public class ArtistsListFragment extends BaseFragment {
    private List<Artist> artists;
    private ArtistsAdapter artistsAdapter;

    @BindView(R.id.artists_list)
    RecyclerView recyclerView;

    @Inject
    YandexService yandexService;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_artists_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        App.get(getActivity()).yandexComponent().inject(this);
        updateToolBar();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        loadArtists();
    }

    private void loadArtists() {
        Call<List<Artist>> call = yandexService.getArtistsList();

        call.enqueue(new Callback<List<Artist>>() {
            @Override
            public void onResponse(Call<List<Artist>> call, Response<List<Artist>> response) {
                artists = response.body();
                artistsAdapter = new ArtistsAdapter(artists, (position, cover) -> {
                    Artist artist = artists.get(position);
                    Fragment artistFragment = ArtistFragment.newInstance(artist);
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_frame_layout, artistFragment)
                            .addToBackStack(null)
                            .commit();
                });
                recyclerView.setAdapter(artistsAdapter);
            }

            @Override
            public void onFailure(Call<List<Artist>> call, Throwable t) {
                Timber.e(t.getMessage());
            }
        });
    }

    private void updateToolBar() {
        ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.main_activity_name);
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
    }
}
