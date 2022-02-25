package com.project.mylenses;

import android.app.AlertDialog;
import android.app.Dialog;
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


public class DialogFragmentAddLens extends DialogFragment {

    String myMode = "toUp";
    private CDFListener listener;
    private final static String FILENAME_LENS = "lensObj";
    FileSystem file = new FileSystem();

    @NonNull
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
                        }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {

                    listener.action(createLens(userInputCount.getText().toString(), myMode));
                    dialogInterface.cancel();
            // проблема бесконечного цикла ?
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
    public LensControl createLens(String count, String mode) {
        LensControl lensControlObj = null;
        try {
            Calendar nowDay = new GregorianCalendar();
            lensControlObj = new LensControl(mode, Integer.parseInt(count), nowDay, LensControl.createEndDate(Integer.parseInt(count), nowDay));

            Log.d(TAG, "Success create " + mode);
            Log.d(TAG, "Success create " + lensControlObj);
            return lensControlObj;
        }catch (Exception e){
            Log.d(TAG, "Creation failed");
        }
        //file.writeFileLens(lensControlObj,FILENAME_LENS);
        //file.writeFile();
        return lensControlObj;
    }
    public interface CDFListener {
        void action(LensControl data);
    }

    public void setDialogListener(CDFListener listener) {
        this.listener = listener;
    }

}