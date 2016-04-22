package ru.shmakova.artistsapp.views;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.shmakova.artistsapp.R;
import ru.shmakova.artistsapp.adapters.ArtistsAdapter;
import ru.shmakova.artistsapp.models.Artist;
import ru.shmakova.artistsapp.services.CacheClient;
import ru.shmakova.artistsapp.services.YandexService;
import ru.shmakova.artistsapp.utils.Utils;

public class MainActivity extends AppCompatActivity {
    private OkHttpClient client = new CacheClient().getClient();

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://download.cdn.yandex.net/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private YandexService service = retrofit.create(YandexService.class);
    private ArrayList<Artist> artists;
    private ArrayAdapter<Artist> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        runTask();
    }

    private void runTask() {
        if (Utils.isNetworkAvailable(this)) {
            showArtistsList();
        } else {
            showInternetUnavailableAlert();
        }
    }

    private void showArtistsList() {
        ListView listView = (ListView) findViewById(R.id.listView);
        artists = new ArrayList<Artist>();
        adapter = new ArtistsAdapter(this, artists);
        listView.setAdapter(adapter);

        Call<ArrayList<Artist>> call = service.listArtists();
        call.enqueue(new Callback<ArrayList<Artist>>() {
            @Override
            public void onResponse(Call<ArrayList<Artist>> call, Response<ArrayList<Artist>> response) {
                if (response.isSuccessful()) {
                    artists = response.body();
                    adapter.addAll(artists);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Artist>> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int position, long id) {
                Artist artist = (Artist) adapterView.getItemAtPosition(position);
                Intent intent = new Intent(view.getContext(), ArtistActivity.class);
                intent.putExtra("artist", artist);
                ActivityOptions options = ActivityOptions
                        .makeSceneTransitionAnimation((Activity) view.getContext(), view, "cover");
                startActivity(intent, options.toBundle());
            }
        });
    }

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
