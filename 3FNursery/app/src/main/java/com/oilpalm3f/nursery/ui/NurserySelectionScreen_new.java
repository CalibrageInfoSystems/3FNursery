package com.oilpalm3f.nursery.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.oilpalm3f.nursery.R;
import com.oilpalm3f.nursery.cloudhelper.Log;
import com.oilpalm3f.nursery.common.CommonConstants;
import com.oilpalm3f.nursery.database.DataAccessHandler;
import com.oilpalm3f.nursery.database.Queries;
import com.oilpalm3f.nursery.dbmodels.NurseryData;
import com.oilpalm3f.nursery.ui.Adapter.NurseryRecyclerviewAdapternew;

import java.util.ArrayList;
import java.util.List;

public class NurserySelectionScreen_new extends AppCompatActivity {

    RecyclerView nurseryRecyclerview;
    private DataAccessHandler dataAccessHandler;
    private List<NurseryData> nurserysList = new ArrayList<>();
    private NurseryRecyclerviewAdapternew nurseryRecyclerviewAdapter;

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

        nurserysList = dataAccessHandler.getNurseryData(Queries.getInstance().getNurseryDataQuery(CommonConstants.USER_ID));
        nurseryRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        nurseryRecyclerviewAdapter = new NurseryRecyclerviewAdapternew(NurserySelectionScreen_new.this, nurserysList);
        nurseryRecyclerview.setAdapter(nurseryRecyclerviewAdapter);
    }


}