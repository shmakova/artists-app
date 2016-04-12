package ru.shmakova.artistsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.listView);
        ArrayList<Artist> artists = new ArrayList<>();
        ArrayAdapter<Artist> adapter = new ArtistsAdapter(this, artists);
        listView.setAdapter(adapter);
    }
}
