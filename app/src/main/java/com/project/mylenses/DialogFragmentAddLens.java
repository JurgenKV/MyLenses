package com.project.mylenses;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import android.icu.util.GregorianCalendar;
import android.icu.util.Calendar;

import static android.content.ContentValues.TAG;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;


public class DialogFragmentAddLens extends DialogFragment {

    String myMode = "toUp";
    private final static String FILENAME_LENS = "lensObj";
    NoticeDialogListener listener;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        String[] mode = {"toUp", "toDown"};

        final EditText userInputCount = new EditText(getActivity());
        userInputCount.setHint("Number of uses");

        builder.setTitle("Lens Param").setView(userInputCount)
                .setSingleChoiceItems(mode, 0,
                        (dialogInterface, i) -> {
                            try {
                                myMode = mode[i];
                            } catch (Exception e) {
                                Log.d(TAG, "WARNING 1");
                            }
                        });

        builder.setPositiveButton("OK", (dialogInterface, i) -> {
            try {
                listener.onDialogPositiveClick(DialogFragmentAddLens.this, createLens(userInputCount.getText().toString(), myMode));
                dialogInterface.cancel();
            } catch (RuntimeException e) {
                System.out.printf("runtimeEX");
            }
        dialogInterface.dismiss();
        });

        builder.setNegativeButton("Cancel", ((dialogInterface, i) -> {
            dialogInterface.dismiss();
        }));

        return builder.create();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public LensControl createLens(String count, String mode) {
        LensControl lensControlObj = null;

        try {
            Calendar endDay = new GregorianCalendar();
            endDay.getInstance();
            lensControlObj = new LensControl(mode, Integer.parseInt(count), LensControl.createEndDate(Integer.parseInt(count), endDay));

            Log.d(TAG, "Success create " + mode + lensControlObj);

            return lensControlObj;
        } catch (Exception e) {
            Log.d(TAG, "Creation failed");
        }
        return lensControlObj;
    }

    public interface NoticeDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog, LensControl lensControl);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (NoticeDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement NoticeDialogListener");
        }
    }

}