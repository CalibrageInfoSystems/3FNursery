package com.oilpalm3f.nursery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.oilpalm3f.nursery.cloudhelper.Log;
import com.oilpalm3f.nursery.common.CommonConstants;
import com.oilpalm3f.nursery.database.DataAccessHandler;
import com.oilpalm3f.nursery.database.Queries;
import com.oilpalm3f.nursery.dbmodels.ConsignmentData;
import com.oilpalm3f.nursery.dbmodels.NurseryData;
import com.oilpalm3f.nursery.ui.ConsignmentRecyclerviewAdapter;
import com.oilpalm3f.nursery.ui.NurseryRecyclerviewAdapter;
import com.oilpalm3f.nursery.ui.NurserySelectionScreen;

import java.util.ArrayList;
import java.util.List;

public class ConsignmentSelectionScreen extends AppCompatActivity {

    RecyclerView consignmentRecyclerview;
    private DataAccessHandler dataAccessHandler;
    private List<ConsignmentData> consignmentList = new ArrayList<>();
    private ConsignmentRecyclerviewAdapter consignmentRecyclerviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consignment_selection_screen);

        dataAccessHandler = new DataAccessHandler(this);
        init();
        setViews();
        String UserId = CommonConstants.USER_ID;
        Log.d("UserId Is : ", UserId);


    }

    private void init() {

        consignmentRecyclerview = findViewById(R.id.consignmentRecyclerview);

    }

    private void setViews() {

        //mActivitiesList= dataAccessHandler.getNurseryActivities(Queries.getInstance().getNurseryActivities(selectedFarmer.getCode(), 193));

        consignmentList = dataAccessHandler.getConsignmentData(Queries.getInstance().getConsignmentDataQuery("NurARRoi"));
        consignmentRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        consignmentRecyclerviewAdapter = new ConsignmentRecyclerviewAdapter(ConsignmentSelectionScreen.this, consignmentList);
        consignmentRecyclerview.setAdapter(consignmentRecyclerviewAdapter);
    }
}