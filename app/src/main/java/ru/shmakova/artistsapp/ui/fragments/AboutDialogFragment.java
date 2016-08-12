package ru.shmakova.artistsapp.ui.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import ru.shmakova.artistsapp.R;

/**
 * Created by shmakova on 11.08.16.
 */

public class AboutDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.about)
                .setMessage(getString(R.string.about_text))
                .setNeutralButton(android.R.string.ok, (dialog, which) -> {
                });
        return builder.create();
    }
}
