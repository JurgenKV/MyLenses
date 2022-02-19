package com.project.mylenses;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.annotation.NonNull;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DialogFragmentAddLens extends DialogFragment {

    String myMode;
    private TextView final_text;

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final String[] mode = {"toUp", "toDown"};
        final EditText userInputCount = new EditText(getActivity());
        userInputCount.setHint("Count of Lenses");
       // return builder.setTitle("Add Lens params").create();
        builder.setTitle("Lens Param").setView(userInputCount)
                .setSingleChoiceItems(mode, 0,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String myMode = mode[i];
                            }
                        }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                createLens(userInputCount.getText().toString(), myMode);
                dialogInterface.cancel();
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        return builder.create();
    }

    public void createLens(String count, String mode){
        Calendar nowDay = new GregorianCalendar();
        switch (mode){
            case "toUp":
                LensControl lensControlUp = new LensControl(Integer.parseInt(count), nowDay,LensControl.createEndDate(Integer.parseInt(count), nowDay), mode );
                break;
            case "toDown":
                LensControl lensControlDown = new LensControl(Integer.parseInt(count), nowDay,LensControl.createEndDate(Integer.parseInt(count), nowDay), mode );
                break;
        }

    }

}

/*
@Override
public Dialog onCreateDialog(Bundle savedInstanceState) {

    final String[] catNamesArray = {"Васька", "Рыжик", "Мурзик"};

    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    builder.setTitle("Выберите любимое имя кота")
            // добавляем переключатели
            .setSingleChoiceItems(catNamesArray, -1,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog,
                                            int item) {
                            Toast.makeText(
                                    getActivity(),
                                    "Любимое имя кота: "
                                            + catNamesArray[item],
                                    Toast.LENGTH_SHORT).show();
                        }
                    })
            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    // User clicked OK, so save the mSelectedItems results somewhere
                    // or return them to the component that opened the dialog

                }
            })
            .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {

                }
            });

    return builder.create();
}
 */