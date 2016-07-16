package ru.shmakova.artistsapp.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ru.shmakova.artistsapp.models.Artist;
import ru.shmakova.artistsapp.R;

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
        name.setText(artist.getName());

        TextView genres = (TextView) convertView.findViewById(R.id.genres);
        genres.setText(artist.getGenres());

        TextView info = (TextView) convertView.findViewById(R.id.info);
        info.setText(artist.getTracksAndAlbumsInfo(","));

        ImageView cover = (ImageView) convertView.findViewById(R.id.cover);
        Picasso.with(parent.getContext())
                .load(artist.getCover().getSmall())
                .placeholder(R.drawable.placeholder)
                .into(cover);

        return convertView;
    }
}