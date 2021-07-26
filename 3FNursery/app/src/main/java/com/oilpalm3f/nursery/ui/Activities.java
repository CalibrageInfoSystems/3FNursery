package com.oilpalm3f.nursery.ui;

import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.oilpalm3f.nursery.R;
import com.oilpalm3f.nursery.cloudhelper.Log;
import com.oilpalm3f.nursery.common.CommonConstants;
import com.oilpalm3f.nursery.common.CommonUtils;
import com.oilpalm3f.nursery.database.DataAccessHandler;
import com.oilpalm3f.nursery.database.Queries;
import com.oilpalm3f.nursery.dbmodels.ConsignmentData;
import com.oilpalm3f.nursery.dbmodels.ConsignmentStatuData;
import com.oilpalm3f.nursery.dbmodels.MutipleData;
import com.oilpalm3f.nursery.dbmodels.NurseryAcitivity;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class Activities extends AppCompatActivity {
    private static final String LOG_TAG =  Activities.class.getSimpleName();
    private TextView txtSatus,txtType,txtAge,txtDateOfJoining;
    private RecyclerView activitiesRecyclerview;
    private ActivitiesRecyclerviewAdapter activitiesRecyclerviewAdapter;
    private List<NurseryAcitivity> mActivitiesList = new ArrayList<>();
    private DataAccessHandler dataAccessHandler;
    private Spinner consignmentSpinner;
    private String Nurserycode;
    private List<ConsignmentStatuData> consignmentstatusList = new ArrayList<>();
    LinearLayout consignmentdatalyt, recyclerviewlayout;
    private List<MutipleData> multiplelist = new ArrayList<>();



    LinkedHashMap<String, Pair> consignmentdatamap = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Select Activity");
        setSupportActionBar(toolbar);
        dataAccessHandler = new DataAccessHandler(this);
        Nurserycode = CommonConstants.NurseryCode;
        Log.d("NurseryCode", Nurserycode);

        init();
        setViews();
    }

    private void init() {
        txtSatus = findViewById(R.id.txtSatus);
        txtType = findViewById(R.id.txtType);
        txtAge = findViewById(R.id.txtAge);
        txtDateOfJoining = findViewById(R.id.txtDateOfJoining);

        activitiesRecyclerview = findViewById(R.id.activitiesRecyclerview);
        consignmentSpinner = findViewById(R.id.consignmentSpin);
        consignmentdatalyt = findViewById(R.id.consignmentdatalyt);
        recyclerviewlayout = findViewById(R.id.recyclerviewlayout);

//        Intent intent = getIntent();
//        String saplingDate = intent.getStringExtra("SaplingDate");
//        Log.d(LOG_TAG, "====> Analysis ===(SaplingDate :)"+saplingDate);



    }

    private void setViews() {

        //mActivitiesList= dataAccessHandler.getNurseryActivities(Queries.getInstance().getNurseryActivities(selectedFarmer.getCode(), 193));

        consignmentdatamap = dataAccessHandler.getPairData(Queries.getInstance().getConsignmentByNurceryMasterQuery(Nurserycode + ""));
        ArrayAdapter<String> consspinnerArrayAdapter = new ArrayAdapter<>(Activities.this, android.R.layout.simple_spinner_item, CommonUtils.arrayFromPair(consignmentdatamap, "Cons"));
        consspinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        consignmentSpinner.setAdapter(consspinnerArrayAdapter);


        consignmentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (consignmentSpinner.getSelectedItemPosition() != 0) {

                    consignmentdatalyt.setVisibility(View.VISIBLE);
                    recyclerviewlayout.setVisibility(View.VISIBLE);
                    Log.d("SelectedConsignment", consignmentSpinner.getSelectedItem() + "");
                    consignmentstatusList = dataAccessHandler.getConsignmentStatus(Queries.getInstance().getConsignmentStatusQuery(consignmentSpinner.getSelectedItem() + ""));
                    Log.d("ConsignmentStatus", consignmentstatusList.get(0).getStatusType());
                    Log.d("ConsignmentSaplingType", consignmentstatusList.get(0).getVarietyname());

                    txtSatus.setText(":  " +consignmentstatusList.get(0).getStatusType() + "");
                    txtType.setText(":  " +consignmentstatusList.get(0).getVarietyname() + "");

                    //CommonConstants.ConsignmentCode = consignmentSpinner.getSelectedItem() + "";

                    mActivitiesList = dataAccessHandler.getNurseryActivityDetails(Queries.getInstance().getNurseryActivities());
                    Log.d("AcvityList", mActivitiesList + "");
                    activitiesRecyclerview.setLayoutManager(new LinearLayoutManager(Activities.this));
                    activitiesRecyclerviewAdapter = new ActivitiesRecyclerviewAdapter(Activities.this, mActivitiesList, consignmentSpinner.getSelectedItem() + "");
                    activitiesRecyclerview.setAdapter(activitiesRecyclerviewAdapter);


                }else {
                    consignmentdatalyt.setVisibility(View.GONE);
                    recyclerviewlayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




        //txtType.setText(": "+ dataAccessHandler.getSingleValue(Queries.getInstance().getSaplingVerirty("35")));
        txtAge.setText(": " + "");
        txtDateOfJoining.setText(": " + "");

    }

    }