package ru.shmakova.artistsapp.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.OnClick;
import ru.shmakova.artistsapp.R;

public class MusicFragment extends BaseFragment {
    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_music, container, false);
    }

    @OnClick(R.id.yandex_music_button)
    public void onYandexMusicClick() {
        startNewActivity(getString(R.string.yandex_music_package));
    }

    @OnClick(R.id.yandex_radio_button)
    public void onYandexRadioClick() {
        startNewActivity(getString(R.string.yandex_radio_package));
    }

    public void startNewActivity(String packageName) {
        Context context = getActivity();
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);

        if (intent == null) {
            intent = new Intent(Intent.ACTION_VIEW)
                    .setData(Uri.parse("market://details?id=" + packageName));
        }

        context.startActivity(intent);
    }
}
