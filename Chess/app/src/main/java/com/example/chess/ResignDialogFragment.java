package com.example.chess;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

public class ResignDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.resign_dialog_message);
        // Add the buttons
        builder.setPositiveButton(R.string.yes_button, (dialog, id) -> {
            if (((MainActivity) getActivity()).turn % 2 == 1){
                ((MainActivity) getActivity()).showEndGamePopup('b');
            } else {
                ((MainActivity) getActivity()).showEndGamePopup('w');
            }
        });
        builder.setNegativeButton(R.string.no_button, (dialog, id) -> {});
        return builder.create();
    }
}
