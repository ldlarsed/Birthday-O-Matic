package com.example.s198569_mappe2.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.s198569_mappe2.BuddyList;
import com.example.s198569_mappe2.LIB.Constants;
import com.example.s198569_mappe2.LIB.DialogYesNoListener;
import com.example.s198569_mappe2.R;

/**
 * Created by luke on 10/15/15.
 */
public class InfoDialogFragment extends DialogFragment{

    private DialogYesNoListener callback;
    private String dialogTitle;

    /**
     * Since DialogFragment requires standard constructor only
     * the title have to be provided through a handy method.
     * @param title
     */
    public void setDialogTitle(String title){
        this.dialogTitle = title;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            callback = (DialogYesNoListener) getActivity();
        }catch(ClassCastException e){
            throw new ClassCastException(Constants.EXCEPTION_INTERFACE_NOT_IMPLEMENTED);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        return new AlertDialog.Builder(getActivity())
                .setTitle(dialogTitle)
                .setPositiveButton(R.string.dialog_OK, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        callback.onYesClick();
                    }
                })
                .setNegativeButton(R.string.dialog_Cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        callback.onNoClick();
                    }
                })
                .create();
    }

}
