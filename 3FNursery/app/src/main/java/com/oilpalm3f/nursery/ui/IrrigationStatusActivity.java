package com.oilpalm3f.nursery.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Pair;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oilpalm3f.nursery.R;
import com.oilpalm3f.nursery.cloudhelper.Log;
import com.oilpalm3f.nursery.database.DataAccessHandler;
import com.oilpalm3f.nursery.database.Queries;
import com.oilpalm3f.nursery.dbmodels.ConsignmentStatuData;
import com.oilpalm3f.nursery.dbmodels.MutipleData;
import com.oilpalm3f.nursery.dbmodels.NurseryAcitivity;
import com.oilpalm3f.nursery.ui.Adapter.ActivitiesRecyclerviewAdapter;
import com.oilpalm3f.nursery.ui.Adapter.IrrigationstatusRecyclerviewAdapter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class IrrigationStatusActivity extends AppCompatActivity {
    private static final String LOG_TAG =  IrrigationStatusActivity.class.getSimpleName();
    private TextView txtSatus;
    private DataAccessHandler dataAccessHandler;
    private List<ConsignmentStatuData> consignmentstatusList = new ArrayList<>();
    private RecyclerView irrigationstatusRecyclerview;
    private List<NurseryAcitivity> mActivitiesList = new ArrayList<>();

    private String NURCERYCODE,CONSINEMENTCODE;
    private IrrigationstatusRecyclerviewAdapter irrigationstatusRecyclerviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_irrigation_status);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Irrigation Status");
        setSupportActionBar(toolbar);





        init();
        setViews();
    }

    private void init() {
        dataAccessHandler = new DataAccessHandler(this);

        irrigationstatusRecyclerview = findViewById(R.id.irrigationstatusRecyclerview);

        txtSatus = findViewById(R.id.txtSatus);



    }

    private void setViews() {
        displayActivityData();


    }

    private void displayActivityData() {
        String targetDate = dataAccessHandler.getSingleValue(Queries.getTargetDay(CONSINEMENTCODE));

        mActivitiesList = dataAccessHandler.getNurseryActivityDetails(Queries.getInstance().getNurseryActivities(CONSINEMENTCODE));
        irrigationstatusRecyclerview.setLayoutManager(new LinearLayoutManager(IrrigationStatusActivity.this));
        irrigationstatusRecyclerviewAdapter = new IrrigationstatusRecyclerviewAdapter(IrrigationStatusActivity.this, mActivitiesList, CONSINEMENTCODE,targetDate);
        irrigationstatusRecyclerview.setAdapter(irrigationstatusRecyclerviewAdapter);
    }


    @Override
    protected void onResume() {
        super.onResume();


    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }



}