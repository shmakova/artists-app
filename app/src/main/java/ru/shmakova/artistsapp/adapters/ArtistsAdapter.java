package ru.shmakova.artistsapp.adapters;

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
        TextView genres = (TextView) convertView.findViewById(R.id.genres);
        TextView info = (TextView) convertView.findViewById(R.id.info);
        ImageView cover = (ImageView) convertView.findViewById(R.id.cover);
        name.setText(artist.getName());
        genres.setText(artist.getGenres());
        Picasso.with(parent.getContext()).load(artist.getCover().getSmall()).into(cover);
        info.setText(convertView.getResources().getQuantityString(R.plurals.albums, artist.getAlbums(), artist.getAlbums())
                + ", "
                + convertView.getResources().getQuantityString(R.plurals.tracks, artist.getTracks(), artist.getTracks()));

        return convertView;
    }
}