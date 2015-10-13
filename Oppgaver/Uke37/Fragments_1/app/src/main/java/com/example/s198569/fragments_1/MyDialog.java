package com.example.s198569.fragments_1;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by luke on 10/13/15.
 */
public class MyDialog extends DialogFragment {

    private DialogClickListener callback;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            callback = (DialogClickListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Kallende klasse må implementere interfacet!");
        }
    }

    public static MyDialog newInstance(int Title) {
        MyDialog frag = new MyDialog();
        Bundle args = new Bundle();
        args.putInt("tittel", Title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //return super.onCreateDialog(savedInstanceState);

        return new AlertDialog.Builder(getActivity())
                .setTitle("Avslutt")
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog, int whichButton) {}})
                .setNegativeButton(R.string.ikkeok, new DialogInterface.OnClickListener() {public void onClick (DialogInterface dialog,int whichButton){}{
                callback.onNoClick();
            }
        })
        .create();
    }
}
