package ru.shmakova.artistsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://download.cdn.yandex.net/")
            .build();

    YandexService service = retrofit.create(YandexService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.listView);
        ArrayList<Artist> artists = new ArrayList<>();
        Call<ArrayList<Artist>> artistsCall = service.listArtists();

        try {
            artists = artistsCall.execute().body();
        } catch (IOException e) {

        }
        ArrayAdapter<Artist> adapter = new ArtistsAdapter(this, artists);
        listView.setAdapter(adapter);
    }
}
