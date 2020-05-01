package com.example.hakob.notepad;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

public class CustomDialogFragment extends DialogFragment {
    private Datable datable;

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        datable = (Datable) context;
    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {

       final String value = getArguments().getString("userid");
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        return builder
                .setTitle("Delete Notes...")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage("Are you sure to remove?")
                .setPositiveButton("OK",  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        datable.remove(value);
                    }
                } )
                .setNegativeButton("Cancal", null)
                .create();
    }



}
