package ru.shmakova.artistsapp.ui.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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

    public static class ArtistViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cover)
        ImageView cover;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.genres)
        TextView genres;
        @BindView(R.id.info)
        TextView info;

        private CustomClickListener listener;

        public ArtistViewHolder(View itemView, CustomClickListener customClickListener) {
            super(itemView);
            listener = customClickListener;

            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.artist_item)
        public void onArtistItemClick(View view) {
            if (listener != null) {
                listener.onArtistItemClickListener(getAdapterPosition());
            }
        }

        public interface CustomClickListener {
            void onArtistItemClickListener(int position);
        }
    }
}