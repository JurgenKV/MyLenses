package com.project.mylenses;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.RadioButton;

import androidx.fragment.app.DialogFragment;
import androidx.annotation.NonNull;

public class DialogFragmentAddLens extends DialogFragment {

    RadioButton radioButton;
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

       // builder.setItems(radioButton);
        return builder.setTitle("Add Lens params").create();
    }

}