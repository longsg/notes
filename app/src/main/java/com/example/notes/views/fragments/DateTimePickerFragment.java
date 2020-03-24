package com.example.notes.views.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.example.notes.R;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateTimePickerFragment extends DialogFragment {
    private static final String ARG_DATE = "date";
    private DatePicker mDatePicker;
    public static String EXTRA_DATE = DateTimePickerFragment.class.toString();

    public DateTimePickerFragment() {
        // Required empty public constructor
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_date_time_picker, null);
        mDatePicker = view.findViewById(R.id.date_fragment_picker);

        return new AlertDialog.Builder(getContext())
                .setView(view)
                .setTitle(R.string.date_picker)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int year = mDatePicker.getYear();
                        int month = mDatePicker.getMonth();
                        int day = mDatePicker.getDayOfMonth();
                        Date date = new GregorianCalendar(year, month, day).getTime();

                        sendResult(Activity.RESULT_OK, date);
                        sendResultUpdate(911, date);
                    }
                })
                .create();
    }


    public static DateTimePickerFragment newInstance(Date date) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_DATE, date);
        DateTimePickerFragment fragment = new DateTimePickerFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    //create sendResult method
    private void sendResult(int requestCode, Date date) {
        if (getTargetFragment() == null)
            return;
        Intent intent = new Intent();
        intent.putExtra(EXTRA_DATE, date);
        //getTargetRequest from AddnewFragment
        getTargetFragment().onActivityResult(getTargetRequestCode(), requestCode, intent);
    }

    private void sendResultUpdate(int resquestCode, Date date) {
        if (getTargetFragment() == null)
            return;
        Intent intent = new Intent();
        intent.putExtra(EXTRA_DATE, date);
        getTargetFragment().onActivityResult(getTargetRequestCode(), resquestCode, intent);
    }

}
