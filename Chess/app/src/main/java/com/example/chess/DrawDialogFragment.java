package com.example.chess;

import android.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

public class DrawDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.draw_dialog_message);
        // Add the buttons
        builder.setPositiveButton(R.string.yes_button, (dialog, id) ->
                ((MainActivity)getActivity()).showEndGamePopup('d')
        );
        builder.setNegativeButton(R.string.no_button, (dialog, id) -> {});
        return builder.create();
    }
}

