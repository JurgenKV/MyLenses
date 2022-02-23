package com.project.mylenses;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import static android.content.ContentValues.TAG;

import androidx.fragment.app.DialogFragment;
import androidx.annotation.NonNull;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DialogFragmentAddLens extends DialogFragment {

    String myMode;

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final String[] mode = {"toUp", "toDown"};

        final EditText userInputCount = new EditText(getActivity());
        userInputCount.setHint("Number of uses");

        builder.setTitle("Lens Param").setView(userInputCount)
                .setSingleChoiceItems(mode, 0,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                try {
                                    myMode = mode[i].toString();
                                } catch (Exception e) {
                                    Log.d(TAG, "WARNING 1");
                                }
                            }
                        }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    createLens(userInputCount.getText().toString(), myMode);
                    dialogInterface.cancel();
                } catch (Exception e) {
                    Log.d(TAG, "WARNING 2");
                }
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        return builder.create();
    }

    public void createLens(String count, String mode) {
        Calendar nowDay = new GregorianCalendar();
        LensControl lensControlObj = new LensControl(mode, Integer.parseInt(count), nowDay, LensControl.createEndDate(Integer.parseInt(count), nowDay));
        //запись в файл?
        Log.d(TAG, "Success create " + mode);
        Log.d(TAG, "Success create " + lensControlObj);
    }

}