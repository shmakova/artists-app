package ru.shmakova.artistsapp.ui.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

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
    private ArtistViewHolder.OnItemCLickListener onItemClickListener;

    public ArtistsAdapter(List<Artist> artists, ArtistViewHolder.OnItemCLickListener onItemClickListener) {
        this.artists = artists;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ArtistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_artists_list, parent, false);
        return new ArtistViewHolder(convertView, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(ArtistViewHolder holder, int position) {
        Artist artist = artists.get(position);
        Context context = holder.cover.getContext();
        Resources resources = context.getResources();

        Glide.with(context)
                .load(artist.getCover().getSmall())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .crossFade()
                .into(holder.cover);

        holder.name.setText(artist.getName());
        holder.genres.setText(artist.getGenres());
        int albums = artist.getAlbums();
        int tracks = artist.getTracks();
        holder.info.setText(resources.getQuantityString(R.plurals.albums, albums, albums) +
                "," + resources.getQuantityString(R.plurals.tracks, tracks, tracks));
    }

    @Override
    public int getItemCount() {
        return artists.size();
    }

    public static class ArtistViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cover_small)
        ImageView cover;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.genres)
        TextView genres;
        @BindView(R.id.info)
        TextView info;

        private OnItemCLickListener listener;

        ArtistViewHolder(View itemView, OnItemCLickListener OnItemClickListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            listener = OnItemClickListener;
        }

        @OnClick(R.id.artist_item)
        void onArtistItemClick() {
            if (listener != null) {
                listener.onItemClick(getAdapterPosition(), cover);
            }
        }

        public interface OnItemCLickListener {
            void onItemClick(int position, ImageView cover);
        }
    }
}