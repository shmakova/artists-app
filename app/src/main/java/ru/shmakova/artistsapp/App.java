package ru.shmakova.artistsapp;

import android.app.Application;
import android.content.Context;

/**
 * Created by shmakova on 17.04.16.
 */
public class App extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getContext(){
        return mContext;
    }
}
