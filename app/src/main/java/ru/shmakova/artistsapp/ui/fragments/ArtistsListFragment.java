package ru.shmakova.artistsapp.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.shmakova.artistsapp.R;
import ru.shmakova.artistsapp.managers.DataManager;
import ru.shmakova.artistsapp.network.models.Artist;
import ru.shmakova.artistsapp.ui.activities.MainActivity;
import ru.shmakova.artistsapp.ui.adapters.ArtistsAdapter;

public class ArtistsListFragment extends BaseFragment {
    private static final String TAG = "ArtistsListFragment";

    private DataManager dataManager;
    private List<Artist> artists;
    private ArtistsAdapter artistsAdapter;

    @BindView(R.id.artists_list)
    RecyclerView recyclerView;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_artists_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        updateToolBar();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        dataManager = DataManager.getInstance();
        loadArtists();
    }

    private void loadArtists() {
        Call<List<Artist>> call = dataManager.getArtistsList();

        call.enqueue(new Callback<List<Artist>>() {
            @Override
            public void onResponse(Call<List<Artist>> call, Response<List<Artist>> response) {
                try {
                    artists = response.body();
                    artistsAdapter = new ArtistsAdapter(artists, new ArtistsAdapter.ArtistViewHolder.OnItemCLickListener() {
                        @Override
                        public void onItemClick(int position, ImageView cover) {
                            Artist artist = artists.get(position);
                            Fragment artistFragment = ArtistFragment.newInstance(artist);

                            getFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.main_frame_layout, artistFragment)
                                    .addToBackStack(null)
                                    .commit();
                        }
                    });
                    recyclerView.setAdapter(artistsAdapter);
                } catch (NullPointerException e) {
                    Log.e(TAG, e.toString());
                }
            }

            @Override
            public void onFailure(Call<List<Artist>> call, Throwable t) {

            }
        });
    }


    /**
     * Updates toolbar
     */
    private void updateToolBar() {
        ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle(R.string.main_activity_name);
        actionBar.setDisplayHomeAsUpEnabled(false);
    }
}
