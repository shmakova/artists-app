package ru.shmakova.artistsapp.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.shmakova.artistsapp.R;
import ru.shmakova.artistsapp.network.models.Artist;
import ru.shmakova.artistsapp.ui.activities.MainActivity;

public class MusicFragment extends BaseFragment {
    @BindView(R.id.yandex_music_button)
    ImageView yandexMusicButton;
    @BindView(R.id.yandex_radio_button)
    ImageView yandexRadioButton;


    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_music, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
    }


    @OnClick(R.id.yandex_music_button)
    public void onYandexMusicClick(View view) {
        startNewActivity(view.getContext(), getString(R.string.yandex_music));
    }

    @OnClick(R.id.yandex_radio_button)
    public void onYandexRadioClick(View view) {
        startNewActivity(view.getContext(), getString(R.string.yandex_radio));
    }

    /**
     * Starts new activity
     * @param context
     * @param packageName
     */
    public void startNewActivity(Context context, String packageName) {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);

        if (intent == null) {
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("market://details?id=" + packageName));
        }

        context.startActivity(intent);
    }
}
