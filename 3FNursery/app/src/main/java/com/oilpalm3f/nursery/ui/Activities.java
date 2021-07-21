package com.oilpalm3f.nursery.ui;

import android.os.Bundle;
import android.util.Pair;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.oilpalm3f.nursery.R;
import com.oilpalm3f.nursery.cloudhelper.Log;
import com.oilpalm3f.nursery.common.CommonUtils;
import com.oilpalm3f.nursery.database.DataAccessHandler;
import com.oilpalm3f.nursery.database.Queries;
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


    LinkedHashMap<String, Pair> consignmentdatamap = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Select Activity");
        setSupportActionBar(toolbar);
        dataAccessHandler = new DataAccessHandler(this);


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

//        Intent intent = getIntent();
//        String saplingDate = intent.getStringExtra("SaplingDate");
//        Log.d(LOG_TAG, "====> Analysis ===(SaplingDate :)"+saplingDate);



    }

    private void setViews() {

        //mActivitiesList= dataAccessHandler.getNurseryActivities(Queries.getInstance().getNurseryActivities(selectedFarmer.getCode(), 193));

        consignmentdatamap = dataAccessHandler.getPairData(Queries.getInstance().getConsignmentByNurceryMasterQuery("NurAPDub"));
        ArrayAdapter<String> consspinnerArrayAdapter = new ArrayAdapter<>(Activities.this, android.R.layout.simple_spinner_item, CommonUtils.arrayFromPair(consignmentdatamap, "Cons"));
        consspinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        consignmentSpinner.setAdapter(consspinnerArrayAdapter);

        mActivitiesList = dataAccessHandler.getNurseryActivityDetails(Queries.getInstance().getNurseryActivities());
        Log.d("AcvityList", mActivitiesList + "");
        activitiesRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        activitiesRecyclerviewAdapter = new ActivitiesRecyclerviewAdapter(Activities.this, mActivitiesList);
        activitiesRecyclerview.setAdapter(activitiesRecyclerviewAdapter);


        txtType.setText(": "+ dataAccessHandler.getSingleValue(Queries.getInstance().getSaplingVerirty("35")));
        txtAge.setText(": " + "");
        txtDateOfJoining.setText(": " + "");

    }

    }