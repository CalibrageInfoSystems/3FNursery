package com.oilpalm3f.nursery.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.oilpalm3f.nursery.R;

import java.util.Arrays;
import java.util.List;

public class RMActivityFields extends AppCompatActivity {

    Spinner typespinner, uomSpinner;
    LinearLayout labourlyt, otherlyt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rmfields);

        typespinner = findViewById(R.id.typeSpinner);
        uomSpinner = findViewById(R.id.uomspinner);

        labourlyt = findViewById(R.id.labourslyt);
        otherlyt = findViewById(R.id.otherslyt);

        labourlyt.setVisibility(View.GONE);
        otherlyt.setVisibility(View.GONE);

        String[] typeSpinnerArr = getResources().getStringArray(R.array.typespin_values);
        List<String> typeSpinnerList = Arrays.asList(typeSpinnerArr);
        ArrayAdapter<String> typeSpinnerAdapter = new ArrayAdapter<>(RMActivityFields.this, android.R.layout.simple_spinner_item, typeSpinnerList);
        typeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typespinner.setAdapter(typeSpinnerAdapter);

        typespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (typespinner.getSelectedItemPosition() == 0) {

                    labourlyt.setVisibility(View.GONE);
                    otherlyt.setVisibility(View.GONE);

                } else if(typespinner.getSelectedItemPosition() == 1) {
                    labourlyt.setVisibility(View.VISIBLE);
                    otherlyt.setVisibility(View.GONE);
                }else{

                    labourlyt.setVisibility(View.GONE);
                    otherlyt.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        String[] uomSpinnerArr = getResources().getStringArray(R.array.uom_values);
        List<String> uomSpinnerList = Arrays.asList(uomSpinnerArr);
        ArrayAdapter<String> uomSpinnerAdapter = new ArrayAdapter<>(RMActivityFields.this, android.R.layout.simple_spinner_item, uomSpinnerList);
        uomSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        uomSpinner.setAdapter(uomSpinnerAdapter);
    }
}