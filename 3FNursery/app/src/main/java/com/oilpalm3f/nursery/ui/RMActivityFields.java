package com.oilpalm3f.nursery.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.oilpalm3f.nursery.R;
import com.oilpalm3f.nursery.cloudhelper.Log;
import com.oilpalm3f.nursery.common.CommonConstants;
import com.oilpalm3f.nursery.database.Queries;
import com.oilpalm3f.nursery.ui.irrigation.IrrigationActivity;
import com.oilpalm3f.nursery.utils.UiUtils;

import java.util.Arrays;
import java.util.List;

public class RMActivityFields extends AppCompatActivity {
    public static final String LOG_TAG = RMActivityFields.class.getSimpleName();
    Spinner typespinner, uomSpinner;
    LinearLayout labourlyt, otherlyt;
    EditText mandaysmale, mandaysfemale, mandaysmaleoutside, mandaysfemaleoutside;
    EditText expensetype, quantity;
    ImageView imageView;
    Button submitBtn;
    int Flag;
    String transactionId,Activity_Name;
TextView activity_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rmfields);

        init();
        setviews();



    }

    private void init() {

        typespinner = findViewById(R.id.typeSpinner);
        uomSpinner = findViewById(R.id.uomspinner);

        labourlyt = findViewById(R.id.labourslyt);
        otherlyt = findViewById(R.id.otherslyt);

        mandaysmale = findViewById(R.id.mandaysmale);
        mandaysfemale = findViewById(R.id.mandaysfemale);
        mandaysmaleoutside = findViewById(R.id.mandaysmaleoutside);
        mandaysfemaleoutside = findViewById(R.id.mandaysfemaleoutside);

        expensetype = findViewById(R.id.expensetype);
        quantity = findViewById(R.id.quantity);
        imageView = findViewById(R.id.rmimageview);
        activity_name = findViewById(R.id.activityname);
        submitBtn = findViewById(R.id.rmsubmitBtn);

    }

    private void setviews() {

        labourlyt.setVisibility(View.GONE);
        otherlyt.setVisibility(View.GONE);

        if (getIntent() != null) {

            Activity_Name = getIntent().getStringExtra("Name");
            Flag = getIntent().getIntExtra("camefrom", 1); // if Flag 2 , edit on rejection
            transactionId = getIntent().getStringExtra("transactionId");
            Log.d(LOG_TAG, "Name==========> :" + Activity_Name);
            Log.d(LOG_TAG, "Flag=====" + Flag);
            if (Flag == 2) {


                activity_name.setText(Activity_Name+"");
                mandaysmale.setText("5");
                mandaysfemale.setText("6");
                mandaysmaleoutside.setText("4");
                mandaysfemaleoutside.setText("9");
                String[] typeSpinnerArr = getResources().getStringArray(R.array.typespin_values);


            } else {

            }

        }

        String[] typeSpinnerArr = getResources().getStringArray(R.array.typespin_values);
        List<String> typeSpinnerList = Arrays.asList(typeSpinnerArr);
        ArrayAdapter<String> typeSpinnerAdapter = new ArrayAdapter<>(RMActivityFields.this, android.R.layout.simple_spinner_item, typeSpinnerList);
        typeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typespinner.setAdapter(typeSpinnerAdapter);

        typespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (Flag == 2) {
                    typespinner.setSelection(1);
                }
                    if (typespinner.getSelectedItemPosition() == 0) {

                        labourlyt.setVisibility(View.GONE);
                        otherlyt.setVisibility(View.GONE);

                    } else if (typespinner.getSelectedItemPosition() == 1) {
                        labourlyt.setVisibility(View.VISIBLE);
                        otherlyt.setVisibility(View.GONE);
                    } else {

                        labourlyt.setVisibility(View.GONE);
                        otherlyt.setVisibility(View.VISIBLE);
                    }
                }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                if (Flag == 2) {
                    typespinner.setSelection(1);
                }
            }
        });


        String[] uomSpinnerArr = getResources().getStringArray(R.array.uom_values);
        List<String> uomSpinnerList = Arrays.asList(uomSpinnerArr);
        ArrayAdapter<String> uomSpinnerAdapter = new ArrayAdapter<>(RMActivityFields.this, android.R.layout.simple_spinner_item, uomSpinnerList);
        uomSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        uomSpinner.setAdapter(uomSpinnerAdapter);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validations()){

                    Toast.makeText(RMActivityFields.this, "Submit Success", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

    }

    public boolean validations() {

        if (typespinner.getSelectedItemPosition() == 0) {
            UiUtils.showCustomToastMessage("Please Select Type", RMActivityFields.this, 0);
            return false;
        }

        if (typespinner.getSelectedItemPosition() == 1) {

            if (mandaysmale.length() != 0 || mandaysfemale.length() != 0 || mandaysmaleoutside.length() != 0 || mandaysfemaleoutside.length() != 0) {
                //UiUtils.showCustomToastMessage("Please enter atleast one value", RMActivityFields.this, 0);
                return true;
            }else{

                UiUtils.showCustomToastMessage("Please enter atleast one value", RMActivityFields.this, 0);
                return false;
            }
        }

        if (typespinner.getSelectedItemPosition() == 2){

            if (TextUtils.isEmpty(expensetype.getText().toString())){
                UiUtils.showCustomToastMessage("Please Enter Expense Type", RMActivityFields.this, 0);
                return false;
            }

            if (TextUtils.isEmpty(quantity.getText().toString())){
                UiUtils.showCustomToastMessage("Please Enter Quantity", RMActivityFields.this, 0);
                return false;
            }

            if (uomSpinner.getSelectedItemPosition() == 0) {
                UiUtils.showCustomToastMessage("Please Select UOM Type", RMActivityFields.this, 0);
                return false;
            }
        }
            return true;
    }

}