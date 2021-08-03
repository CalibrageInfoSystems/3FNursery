package com.oilpalm3f.nursery;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.oilpalm3f.nursery.cloudhelper.Log;
import com.oilpalm3f.nursery.common.CommonConstants;
import com.oilpalm3f.nursery.database.DataAccessHandler;
import com.oilpalm3f.nursery.database.Queries;
import com.oilpalm3f.nursery.dbmodels.ConsignmentData;
import com.oilpalm3f.nursery.ui.ConsignmentRecyclerviewAdapter;

import java.util.ArrayList;
import java.util.List;

public class ConsignmentSelectionScreen extends AppCompatActivity {

    RecyclerView consignmentRecyclerview;
    private DataAccessHandler dataAccessHandler;
    private List<ConsignmentData> consignmentList = new ArrayList<>();
    private ConsignmentRecyclerviewAdapter consignmentRecyclerviewAdapter;
    String nurserycode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consignment_selection_screen);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            nurserycode = extras.getString("NurseryCode");
        }

        Log.d("nurserycode", nurserycode + "");
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

        consignmentList = dataAccessHandler.getConsignmentData(Queries.getInstance().getConsignmentDataQuery(CommonConstants.USER_ID,nurserycode));
        consignmentRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        consignmentRecyclerviewAdapter = new ConsignmentRecyclerviewAdapter(ConsignmentSelectionScreen.this, consignmentList,nurserycode);
        consignmentRecyclerview.setAdapter(consignmentRecyclerviewAdapter);
    }
}