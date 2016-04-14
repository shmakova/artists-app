package ru.shmakova.artistsapp;

/**
 * Created by shmakova on 15.04.16.
 */
public class Cover {
    private String small;
    private String big;

    public Cover(String small, String big) {
        this.small = small;
        this.big = big;
    }

    public String getSmall() {

        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public String getBig() {
        return big;
    }

    public void setBig(String big) {
        this.big = big;
    }
}
