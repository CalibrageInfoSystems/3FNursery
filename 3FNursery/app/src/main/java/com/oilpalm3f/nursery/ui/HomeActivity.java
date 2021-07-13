package com.oilpalm3f.nursery.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.oilpalm3f.nursery.R;
import com.oilpalm3f.nursery.common.CommonUtils;
import com.oilpalm3f.nursery.database.DataAccessHandler;
import com.oilpalm3f.nursery.database.Queries;
import com.oilpalm3f.nursery.dbmodels.ConsignmentDetails;
import com.oilpalm3f.nursery.dbmodels.NurseryDetails;

import java.util.LinkedHashMap;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    RelativeLayout newactivity;
    LinkedHashMap<String, Pair> nurserydatamap = null;
    LinkedHashMap<String, Pair> consignmentdatamap = null;
    List<NurseryDetails> nurseryDetails;
    List<ConsignmentDetails> consignmentDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        init();
        setviews();

    }

    private void init() {

        newactivity = findViewById(R.id.newactivityRel);
    }

    private void setviews() {

        newactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Intent selectionscreen = new Intent(HomeActivity.this, SelectionScreen.class);
//                startActivity(selectionscreen);

                showDialog(HomeActivity.this);

            }
        });
    }

    public void showDialog(Activity activity) {
        final Dialog dialog = new Dialog(activity, R.style.DialogSlideAnim);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog);
        final Spinner nurserySpinner, consignmentSpinner;
         final DataAccessHandler dataAccessHandler;
        final LinearLayout detailslyt, consignmentdetailslyt;
        final TextView nurseryname, nurserycode, nurserypin;
        final TextView consignmentname, consignmentcode, consignmentpin;

        Button submitBtn;

        dataAccessHandler = new DataAccessHandler(HomeActivity.this);

        nurserySpinner = dialog.findViewById(R.id.nurseryspin);
        consignmentSpinner = dialog.findViewById(R.id.consignmentSpin);
        nurseryname = dialog.findViewById(R.id.nurseryname);
        nurserycode = dialog.findViewById(R.id.nurserycode);
        nurserypin = dialog.findViewById(R.id.nurserypin);
        detailslyt =  dialog.findViewById(R.id.detailslyt);
        consignmentdetailslyt = dialog.findViewById(R.id.consignmentdetailslyt);
        consignmentname = dialog.findViewById(R.id.consignmentname);
        consignmentcode = dialog.findViewById(R.id.consignmentcode);
        consignmentpin = dialog.findViewById(R.id.consignmentpin);
        submitBtn =  dialog.findViewById(R.id.submitBtn);
        nurserydatamap = dataAccessHandler.getPairData(Queries.getInstance().getNurseryMasterQuery());


        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(HomeActivity.this, android.R.layout.simple_spinner_item, CommonUtils.arrayFromPair(nurserydatamap, "Nursery"));
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        nurserySpinner.setAdapter(spinnerArrayAdapter);

        nurserySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (nurserySpinner.getSelectedItemPosition() != 0) {
                    detailslyt.setVisibility(View.VISIBLE);
                    Log.d("Selected1", nurserySpinner.getSelectedItem().toString());
                    nurseryDetails = dataAccessHandler.getNurseryDetails(Queries.getInstance().getNurseryDetailsQuery(nurserySpinner.getSelectedItem().toString()));
                    nurseryname.setText(nurseryDetails.get(0).getName());
                    nurserycode.setText(nurseryDetails.get(0).getCode());
                    nurserypin.setText(nurseryDetails.get(0).getPinCode() + "");

                    consignmentdatamap = dataAccessHandler.getPairData(Queries.getInstance().getConsignmentByNurceryMasterQuery(""+nurseryDetails.get(0).getCode()));
                    ArrayAdapter<String> consspinnerArrayAdapter = new ArrayAdapter<>(HomeActivity.this, android.R.layout.simple_spinner_item, CommonUtils.arrayFromPair(consignmentdatamap, "Cons"));
                    consspinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    consignmentSpinner.setAdapter(consspinnerArrayAdapter);

                }else {

                    detailslyt.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });





        consignmentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (consignmentSpinner.getSelectedItemPosition() != 0) {
                    consignmentdetailslyt.setVisibility(View.VISIBLE);
                    Log.d("Selected1", consignmentSpinner.getSelectedItem().toString());
                    consignmentDetails = dataAccessHandler.getConsignmentDetails(Queries.getInstance().getConsignmentDetailsQuery(consignmentSpinner.getSelectedItem().toString()));
                    consignmentname.setText(consignmentDetails.get(0).getNurseryCode());
                    consignmentcode.setText(consignmentDetails.get(0).getConsignmentCode());
                    // consignmentpin.setText(consignmentDetails.get(0).getPinCode() + "");

                }else {

                    consignmentdetailslyt.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(HomeActivity.this, Activities.class);
                intent.putExtra("SaplingDate",consignmentDetails.get(0).getArrivalDate() );
                startActivity(intent);
            }
        });


        dialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        }, 500);
    }
}