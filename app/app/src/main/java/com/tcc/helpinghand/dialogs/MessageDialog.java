package com.tcc.helpinghand.dialogs;

import android.app.Dialog;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class MessageDialog extends DialogFragment {

    private String title;
    private String message;

    public MessageDialog() { }

    public MessageDialog(String title, String message) {
        this.message = message;
        this.title = title;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("Ok", (dialogInterface, i) -> {

                });

        return builder.create();
    }
}
