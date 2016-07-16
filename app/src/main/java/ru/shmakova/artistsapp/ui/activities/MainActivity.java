package ru.shmakova.artistsapp.ui.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.shmakova.artistsapp.R;
import ru.shmakova.artistsapp.managers.DataManager;
import ru.shmakova.artistsapp.network.models.Artist;
import ru.shmakova.artistsapp.ui.adapters.ArtistsAdapter;
import ru.shmakova.artistsapp.utils.Utils;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";

    private DataManager dataManager;
    private List<Artist> artists;
    private ArtistsAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.artists_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        dataManager = DataManager.getInstance();

        runTask();
    }

    private void runTask() {
        if (Utils.isNetworkAvailable(this)) {
            loadArtists();
        } else {
            showInternetUnavailableAlert();
        }
    }

    /**
     * Load Artists list
     */
    private void loadArtists() {
        Call<List<Artist>> call = dataManager.getArtistsList();

        call.enqueue(new Callback<List<Artist>>() {
            @Override
            public void onResponse(Call<List<Artist>> call, Response<List<Artist>> response) {
                try {
                    artists = response.body();
                    adapter = new ArtistsAdapter(artists, new ArtistsAdapter.ArtistViewHolder.CustomClickListener() {
                        @Override
                        public void onArtistItemClickListener(int position) {
                            Artist artist = artists.get(position);

                            Intent profileIntent = new Intent(MainActivity.this, ArtistActivity.class);
                            profileIntent.putExtra("artist", artist);

                            startActivity(profileIntent);
                        }
                    });
                    recyclerView.setAdapter(adapter);
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
     * Show Internet Unavailable Alert Dialog
     */
    private void showInternetUnavailableAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.error);
        builder.setMessage(R.string.error_message);

        builder.setPositiveButton(R.string.retry, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
                runTask();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
        Toast.makeText(this, R.string.network_unavailable, Toast.LENGTH_SHORT).show();
    }
}
