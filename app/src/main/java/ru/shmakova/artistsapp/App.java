package ru.shmakova.artistsapp;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import ru.shmakova.artistsapp.developer_settings.DevMetricsProxy;
import ru.shmakova.artistsapp.developer_settings.DeveloperSettingsModel;
import timber.log.Timber;

/**
 * Created by shmakova on 17.04.16.
 */
public class App extends Application {

    private static Context context;
    private ApplicationComponent applicationComponent;

    public static Context getContext(){
        return context;
    }

    // Prevent need in a singleton (global) reference to the application object.
    @NonNull
    public static App get(@NonNull Context context) {
        return (App) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = prepareApplicationComponent().build();
        context = getApplicationContext();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());

            DeveloperSettingsModel developerSettingModel = applicationComponent.developerSettingModel();
            developerSettingModel.apply();

            DevMetricsProxy devMetricsProxy = applicationComponent.devMetricsProxy();
            devMetricsProxy.apply();
        }
    }

    @NonNull
    protected DaggerApplicationComponent.Builder prepareApplicationComponent() {
        return DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this));
    }

    @NonNull
    public ApplicationComponent applicationComponent() {
        return applicationComponent;
    }
}
