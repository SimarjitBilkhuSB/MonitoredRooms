package com.example.monitoredrooms;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import org.jetbrains.annotations.NotNull;

/**
 * create an instance of this fragment.
 */
public class AddRoomDialog extends DialogFragment {

    protected TextView mAcceptedTempRangeTextView;
    protected TextView mOccupancyIntervalTextView;
    protected Toolbar mDialogTitleToolbar;
    protected EditText mRoomNameEditText;
    protected EditText mMinTempEditText;
    protected EditText mMaxTempEditText;
    protected EditText mOccupancyIntervalEditText;
    protected Button mSaveButton;


    public AddRoomDialog() {
        // Required empty public constructor
    }

    /**@NonNull
    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        return dialog;

    }

    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();
        if(dialog != null){
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        }
    }*/

    @Override
    public void onDismiss(@NonNull @NotNull DialogInterface dialog) {
        super.onDismiss(dialog);

        //invokes parent fragment's onDismiss method
        Fragment parentFragment = getParentFragment();
        if(parentFragment instanceof DialogInterface.OnDismissListener){
            ((DialogInterface.OnDismissListener) parentFragment).onDismiss(dialog);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_room_dialog, container, false);

        setupDialogFragment(view);


        return view;
    }

    private void setupDialogFragment(View view) {

        //initialize UI components
        mAcceptedTempRangeTextView = view.findViewById(R.id.addRoomDialogTemperatureTextView);
        mOccupancyIntervalTextView = view.findViewById(R.id.addRoomDialogIntervalTextView);
        mDialogTitleToolbar = view.findViewById(R.id.addRoomDialogTitleToolbar);
        mRoomNameEditText = view.findViewById(R.id.addRoomDialogRoomNameEditText);
        mMinTempEditText = view.findViewById(R.id.addRoomDialogMinTempEditText);
        mMaxTempEditText = view.findViewById(R.id.addRoomDialogMaxTempEditText);
        mOccupancyIntervalEditText = view.findViewById(R.id.addRoomDialogIntervalEditText);
        mSaveButton = view.findViewById(R.id.addRoomDialogSaveButton);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private boolean roomDataValidation(){

        //implements if statements to verify that the data entered is valid

        return true;
    }
}