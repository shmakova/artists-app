package ru.shmakova.artistsapp;

import android.app.Application;
import android.content.Context;

/**
 * Created by shmakova on 17.04.16.
 */
public class App extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext(){
        return context;
    }
}
