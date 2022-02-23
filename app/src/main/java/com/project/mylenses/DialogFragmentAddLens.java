package com.project.mylenses;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.icu.util.GregorianCalendar;
import android.icu.util.Calendar;
import static android.content.ContentValues.TAG;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.annotation.NonNull;


public class DialogFragmentAddLens extends DialogFragment {

    String myMode = "toUp";

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        String[] mode = {"toUp", "toDown"};

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
            @RequiresApi(api = Build.VERSION_CODES.N)
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void createLens(String count, String mode) {
        try {
            Calendar nowDay = new GregorianCalendar();
            LensControl lensControlObj = new LensControl(mode, Integer.parseInt(count), nowDay, LensControl.createEndDate(Integer.parseInt(count), nowDay));

            FileSystem file = new FileSystem();
            file.writeFileLens(lensControlObj);
            Log.d(TAG, "Success create " + mode);
            Log.d(TAG, "Success create " + lensControlObj);
        }catch (Exception e){
            Log.d(TAG, "Creation failed");
        }

    }

}