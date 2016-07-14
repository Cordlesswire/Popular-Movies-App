package me.maxdev.popularmoviesapp.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;

import me.maxdev.popularmoviesapp.R;
import me.maxdev.popularmoviesapp.data.Sort;
import me.maxdev.popularmoviesapp.data.SortHelper;

public class SortingDialogFragment extends DialogFragment {

    public static final String BROADCAST_SORT_PREFERENCE_CHANGED = "SortPreferenceChanged";

    public static final String TAG = "SortingDialogFragment";

    private SortHelper sortHelper;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        sortHelper = new SortHelper(PreferenceManager.getDefaultSharedPreferences(getContext()));
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.DialogStyle);
        builder.setTitle(getString(R.string.sort_dialog_title));
        builder.setNegativeButton(getString(R.string.action_cancel), null);
        builder.setSingleChoiceItems(
                R.array.pref_sort_by_labels,
                sortHelper.getSortByPreference().ordinal(),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sortHelper.saveSortByPreference(Sort.values()[which]);
                        sendSortPreferenceChangedBroadcast();
                        dialog.dismiss();
                    }
                });

        return builder.create();
    }

    private void sendSortPreferenceChangedBroadcast() {
        Intent intent = new Intent(BROADCAST_SORT_PREFERENCE_CHANGED);
        LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
    }
}