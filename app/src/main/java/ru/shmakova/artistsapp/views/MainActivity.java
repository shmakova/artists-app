package ru.shmakova.artistsapp.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.shmakova.artistsapp.R;
import ru.shmakova.artistsapp.adapters.ArtistsAdapter;
import ru.shmakova.artistsapp.models.Artist;
import ru.shmakova.artistsapp.services.YandexService;

public class MainActivity extends AppCompatActivity {
    private Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://download.cdn.yandex.net/")
            .build();

    private YandexService service = retrofit.create(YandexService.class);
    private ArrayList<Artist> artists;
    private ArrayAdapter<Artist> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
            public void onItemClick(AdapterView<?> a,
                                    View v, int position, long id) {
                Artist artist = (Artist) a.getItemAtPosition(position);
                Intent intent = new Intent(v.getContext(), ArtistActivity.class);
                intent.putExtra("artist", artist);
                startActivity(intent);
            }
        });
    }
}
