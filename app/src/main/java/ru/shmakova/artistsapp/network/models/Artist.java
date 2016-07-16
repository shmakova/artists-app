package ru.shmakova.artistsapp.network.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import java.util.ArrayList;

import ru.shmakova.artistsapp.App;
import ru.shmakova.artistsapp.R;

/**
 * Created by shmakova on 12.04.16.
 */
public class Artist implements Parcelable {
    private int id;
    private String name;
    private ArrayList<String> genres;
    private Cover cover;
    private int tracks;
    private int albums;
    private String description;
    private String link;

    public String getDescription() {
        return description;
    }

    public String getLink() {
        return link;
    }

    public int getTracks() {
        return tracks;
    }

    public int getAlbums() {
        return albums;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Cover getCover() {
        return cover;
    }

    public String getGenres() {
        return TextUtils.join(", ", genres);
    }

    public String getTracksAndAlbumsInfo(String separateString) {
        return App.getContext().getResources().getQuantityString(R.plurals.albums, albums, albums)
                + separateString + " "
                + App.getContext().getResources().getQuantityString(R.plurals.tracks, tracks, tracks);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeStringList(this.genres);
        dest.writeParcelable(this.cover, flags);
        dest.writeInt(this.tracks);
        dest.writeInt(this.albums);
        dest.writeString(this.description);
        dest.writeString(this.link);
    }

    protected Artist(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.genres = in.createStringArrayList();
        this.cover = in.readParcelable(Cover.class.getClassLoader());
        this.tracks = in.readInt();
        this.albums = in.readInt();
        this.description = in.readString();
        this.link = in.readString();
    }

    public static final Parcelable.Creator<Artist> CREATOR = new Parcelable.Creator<Artist>() {
        @Override
        public Artist createFromParcel(Parcel source) {
            return new Artist(source);
        }

        @Override
        public Artist[] newArray(int size) {
            return new Artist[size];
        }
    };

    @Override
    public String toString() {
        return "Artist: " + name;
    }
}
