package com.example.vacationapp;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.slider.RangeSlider;

public class Form extends AppCompatActivity {

    private Button mDatePickerBtn;
    private TextView mSelectedDateText;
    Spinner spinner;
    String[] activities = {"Family Activities", "Visiting Tours", "Experience the Local Flavor", "Star Gazing", "Camping"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formular);

        mDatePickerBtn = findViewById(R.id.buttonDatePicker);
        mSelectedDateText = findViewById(R.id.selectedDate);

        spinner = findViewById(R.id.spinner);

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

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Form.this, R.layout.item_file_activities, activities);
        adapter.setDropDownViewResource(R.layout.item_file_activities);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String value = parent.getItemAtPosition(position).toString();
                Toast.makeText(Form.this, value, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
