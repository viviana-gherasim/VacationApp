package com.example.vacationapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.slider.RangeSlider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Form extends AppCompatActivity {

    private Button mDatePickerBtn;
    private TextView mSelectedDateText;
    TextView activities;
    boolean[] selectedActivity;
    ArrayList<Integer> activityList = new ArrayList<>();
    String[] activityArray = {"Visiting Tour", "Skuba Diving", "Relax", "Hiking" };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formular);

        mDatePickerBtn = findViewById(R.id.buttonDatePicker);
        mSelectedDateText = findViewById(R.id.selectedDate);

        MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();
        builder.setTitleText("Select a date");
        final MaterialDatePicker materialDatePicker = builder.build();

        mDatePickerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker.show(getSupportFragmentManager(), "DATE_PICKER");
            }
        });

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                mSelectedDateText.setText("Selected Date: " + materialDatePicker.getHeaderText());
            }
        });

        RangeSlider slider1 = findViewById(R.id.rangeSeekBar1);
        slider1.setValues(2000f, 3500f);

        RangeSlider slider2 = findViewById(R.id.rangeSeekBar2);
        slider2.setValues(2000f, 3500f);

        activities = findViewById(R.id.textViewFavouriteActivities);

        selectedActivity = new boolean[activityArray.length];

        activities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(
                       Form.this
                );
                builder.setTitle("Select Activity");

                builder.setCancelable(false);

                builder.setMultiChoiceItems(activityArray, selectedActivity, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        if (b) {
                            activityList.add(i);
                            Collections.sort(activityList);
                        } else {
                            activityList.remove(i);
                        }
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        StringBuilder stringBuilder = new StringBuilder();

                        for(int j=0; j<activityList.size();j++) {
                            stringBuilder.append(activityArray[activityList.get(j)]);

                            if(j!=activityList.size()-1) {
                                stringBuilder.append(", ");
                            }
                        }
                        activities.setText(stringBuilder.toString());
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                builder.setNeutralButton("Clear all", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        for(int j=0; j<selectedActivity.length;j++) {
                            selectedActivity[j]=false;
                            activityList.clear();
                            activities.setText("");
                        }
                    }
                });
                builder.show();
            }
        });

    }
}
