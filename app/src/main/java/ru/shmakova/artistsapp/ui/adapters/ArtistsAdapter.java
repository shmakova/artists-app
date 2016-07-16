package ru.shmakova.artistsapp.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ru.shmakova.artistsapp.network.models.Artist;
import ru.shmakova.artistsapp.R;

/**
 * Created by shmakova on 13.04.16.
 */
public class ArtistsAdapter extends RecyclerView.Adapter<ArtistsAdapter.ArtistViewHolder> {
    private List<Artist> artists;
    private ArtistViewHolder.CustomClickListener customClickListener;

    public ArtistsAdapter(List<Artist> artists, ArtistViewHolder.CustomClickListener customClickListener) {
        this.artists = artists;
        this.customClickListener = customClickListener;
    }

    @Override
    public ArtistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_artists_list, parent, false);
        return new ArtistViewHolder(convertView, customClickListener);
    }

    @Override
    public void onBindViewHolder(ArtistViewHolder holder, int position) {
        Artist artist = artists.get(position);
        Context context = holder.cover.getContext();

        Picasso.with(context)
                .load(artist.getCover().getSmall())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(holder.cover);

        holder.name.setText(artist.getName());
        holder.genres.setText(artist.getGenres());
        holder.info.setText(artist.getTracksAndAlbumsInfo(","));
    }

    @Override
    public int getItemCount() {
        return artists.size();
    }

    public static class ArtistViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected ImageView cover;
        protected TextView name, genres, info;
        private CustomClickListener listener;

        public ArtistViewHolder(View itemView, CustomClickListener customClickListener) {
            super(itemView);
            listener = customClickListener;

            name = (TextView) itemView.findViewById(R.id.name);
            genres = (TextView) itemView.findViewById(R.id.genres);
            info = (TextView) itemView.findViewById(R.id.info);
            cover = (ImageView) itemView.findViewById(R.id.cover);
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onArtistItemClickListener(getAdapterPosition());
            }

        }

        public interface CustomClickListener {
            void onArtistItemClickListener(int position);
        }
    }
}