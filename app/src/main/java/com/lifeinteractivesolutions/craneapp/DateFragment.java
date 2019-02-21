package com.lifeinteractivesolutions.craneapp;

import android.accessibilityservice.GestureDescription;
import android.app.Activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

public class DateFragment extends DialogFragment {

    private DatePicker mDatepicker;
    private TimePicker mTimePicker;
    private static String bundle_choice = "com.lifeInteractiveSolutions.craneapp.DateFragment";
    private String date_choice = "date";
    public static DateFragment newInstance(String choice)
    {
        DateFragment dateFragment = new DateFragment();
        Bundle bundle = new Bundle();
        bundle.putString(DateFragment.bundle_choice,choice);
        dateFragment.setArguments(bundle);
        return dateFragment;
    }

    @Override
    @SuppressWarnings("deprecation")
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (getArguments().getString(DateFragment.bundle_choice).equalsIgnoreCase(date_choice)) {
            View v = LayoutInflater.from(getActivity()).inflate(R.layout.datepicker_holder, null, false);
            mDatepicker = (DatePicker) v.findViewById(R.id.date_picker);

            return new AlertDialog.Builder(getActivity()).setView(v).setPositiveButton(R.string.confirm_date, new AlertDialog.OnClickListener() {

                @Override
                @SuppressWarnings("deprecation")
                public void onClick(DialogInterface dialogInterface, int i) {

                    Intent data = new Intent();

                    int year = mDatepicker.getYear();
                    int day = mDatepicker.getDayOfMonth();
                    int month = mDatepicker.getMonth();
                    Date date = new Date(year, month, day);
                    data.putExtra(LiftPlanFragment.date, date.toString());
                    getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, data);
                    dismiss();
                }
            }).setNegativeButton(R.string.cancel_date, new AlertDialog.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_CANCELED, null);
                    dismiss();
                }
            }).setTitle("").create();
        } else {
              View v = LayoutInflater.from(getActivity()).inflate(R.layout.timepicker_holder,null,false);
              mTimePicker = (TimePicker)v.findViewById(R.id.time_picker);
              return new AlertDialog.Builder(getActivity()).setView(v).setPositiveButton(R.string.confirm_date, new DialogInterface.OnClickListener(){

                  @Override
                  public void onClick(DialogInterface dialogInterface, int i)
                  {
                      if(Build.VERSION.SDK_INT >= 23)
                      {
                          Date date = new Date();
                          date.setHours(mTimePicker.getHour());
                          date.setMinutes(mTimePicker.getMinute());
                          Intent intent = new Intent();
                          intent.putExtra(LiftPlanFragment.time,date.toString());
                          getTargetFragment().onActivityResult(getTargetRequestCode(),Activity.RESULT_OK,intent);
                          dialogInterface.dismiss();
                      }
                      else
                      {
                          Date date = new Date();
                          date.setHours(mTimePicker.getCurrentHour());
                          date.setMinutes(mTimePicker.getCurrentMinute());
                          Intent intent = new Intent();
                          intent.putExtra(LiftPlanFragment.time,date.toString());
                          getTargetFragment().onActivityResult(getTargetRequestCode(),Activity.RESULT_OK,intent);
                          dialogInterface.dismiss();
                      }
                  }
              }).setNegativeButton(R.string.cancel_date, new DialogInterface.OnClickListener(){
                  @Override
                  public void onClick(DialogInterface dialogInterface, int i)
                  {
                      getTargetFragment().onActivityResult(getTargetRequestCode(),Activity.RESULT_CANCELED,null);
                  }
              }).create();




        }
    }

}
