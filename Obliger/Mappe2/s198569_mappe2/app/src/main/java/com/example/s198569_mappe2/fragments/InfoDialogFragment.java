package com.example.s198569_mappe2.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.example.s198569_mappe2.LIB.Constants;
import com.example.s198569_mappe2.LIB.DialogYesNoListener;
import com.example.s198569_mappe2.R;

/**
 * Created by luke on 10/15/15.
 */
public class InfoDialogFragment extends DialogFragment{

    private DialogYesNoListener callback;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            //callback = (DialogClickListener) getActivity();
            callback = (DialogYesNoListener) getActivity();
        }catch(ClassCastException e){
            throw new ClassCastException(Constants.EXCEPTION_INTERFACE_NOT_IMPLEMENTED);
        }
    }

//    public static InfoDialogFragment newInstance(int title){
//        InfoDialogFragment dFrag = new InfoDialogFragment();
//        Bundle args = new Bundle();
//        args.putInt("DialogTitle", title);
//        dFrag.setArguments(args);
//        return dFrag;
//    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //return super.onCreateDialog(savedInstanceState);

        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.info_dialog_title)
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
