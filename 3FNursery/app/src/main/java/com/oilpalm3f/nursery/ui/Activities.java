package com.oilpalm3f.nursery.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.oilpalm3f.nursery.R;
import com.oilpalm3f.nursery.cloudhelper.Log;
import com.oilpalm3f.nursery.database.DataAccessHandler;
import com.oilpalm3f.nursery.database.Queries;
import com.oilpalm3f.nursery.dbmodels.NurseryAcitivity;

import java.util.ArrayList;
import java.util.List;

public class Activities extends AppCompatActivity {
      private static final String LOG_TAG =  Activities.class.getSimpleName();
    private RecyclerView activitiesRecyclerview;
    private ActivitiesRecyclerviewAdapter activitiesRecyclerviewAdapter;
    private List<NurseryAcitivity> mActivitiesList = new ArrayList<>();
    private DataAccessHandler dataAccessHandler;


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

        activitiesRecyclerview = findViewById(R.id.activitiesRecyclerview);

        Intent intent = getIntent();
        String saplingDate = intent.getStringExtra("SaplingDate");
        Log.d(LOG_TAG, "====> Analysis ===(SaplingDate :)"+saplingDate);

    }

    private void setViews() {

        //mActivitiesList= dataAccessHandler.getNurseryActivities(Queries.getInstance().getNurseryActivities(selectedFarmer.getCode(), 193));

        mActivitiesList = dataAccessHandler.getNurseryActivityDetails(Queries.getInstance().getNurseryActivities());
        Log.d("AcvityList", mActivitiesList + "");
        activitiesRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        activitiesRecyclerviewAdapter = new ActivitiesRecyclerviewAdapter(Activities.this, mActivitiesList);
        activitiesRecyclerview.setAdapter(activitiesRecyclerviewAdapter);
    }

    }