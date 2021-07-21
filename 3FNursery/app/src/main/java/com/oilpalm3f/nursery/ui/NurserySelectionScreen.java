package com.oilpalm3f.nursery.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.oilpalm3f.nursery.R;
import com.oilpalm3f.nursery.cloudhelper.Log;
import com.oilpalm3f.nursery.common.CommonConstants;
import com.oilpalm3f.nursery.database.DataAccessHandler;
import com.oilpalm3f.nursery.database.Queries;
import com.oilpalm3f.nursery.dbmodels.NurseryAcitivity;
import com.oilpalm3f.nursery.dbmodels.NurseryData;
import com.oilpalm3f.nursery.dbmodels.NurseryDetails;

import java.util.ArrayList;
import java.util.List;

public class NurserySelectionScreen extends AppCompatActivity {

    RecyclerView nurseryRecyclerview;
    private DataAccessHandler dataAccessHandler;
    private List<NurseryData> nurserysList = new ArrayList<>();
    private NurseryRecyclerviewAdapter nurseryRecyclerviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nursery_selection_screen);

        dataAccessHandler = new DataAccessHandler(this);
        init();
        setViews();
        String UserId = CommonConstants.USER_ID;
        Log.d("UserId Is : ", UserId);
    }

    private void init() {

        nurseryRecyclerview = findViewById(R.id.nurseryRecyclerview);

    }

    private void setViews() {

        //mActivitiesList= dataAccessHandler.getNurseryActivities(Queries.getInstance().getNurseryActivities(selectedFarmer.getCode(), 193));

        nurserysList = dataAccessHandler.getNurseryData(Queries.getInstance().getNurseryDataQuery());
        nurseryRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        nurseryRecyclerviewAdapter = new NurseryRecyclerviewAdapter(NurserySelectionScreen.this, nurserysList);
        nurseryRecyclerview.setAdapter(nurseryRecyclerviewAdapter);
    }
}