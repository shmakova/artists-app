package ru.shmakova.artistsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by shmakova on 13.04.16.
 */
public class ArtistsAdapter extends ArrayAdapter<Artist> {
    public ArtistsAdapter(Context context, ArrayList<Artist> artists) {
        super(context, 0, artists);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Artist artist = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView genres = (TextView) convertView.findViewById(R.id.genres);
        name.setText(artist.name);
        genres.setText(artist.getGenres());

        return convertView;
    }
}