package com.oilpalm3f.nursery.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;

import com.oilpalm3f.nursery.R;
import com.oilpalm3f.nursery.cloudhelper.Log;
import com.oilpalm3f.nursery.common.CommonConstants;
import com.oilpalm3f.nursery.database.DataAccessHandler;
import com.oilpalm3f.nursery.database.Queries;
import com.oilpalm3f.nursery.dbmodels.LandlevellingFields;
import com.oilpalm3f.nursery.dbmodels.MutipleData;
import com.oilpalm3f.nursery.dbmodels.NurseryData;

import java.util.ArrayList;
import java.util.List;

public class MultipleEntryScreen extends AppCompatActivity {

    RecyclerView multipleentryrcv;
    private DataAccessHandler dataAccessHandler;
    Button addBtn;
    String activityTypeId, activityName, ismultipleentry, consignmentcode;
    MultipleEntriesRecyclerViewAdapter multipleEntriesRecyclerViewAdapter;
    private List<MutipleData> multiplelist = new ArrayList<>();
    private List<LandlevellingFields> fieldslist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_entry_screen);


        dataAccessHandler = new DataAccessHandler(this);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            activityTypeId = extras.getString("ActivityTypeId1");
            activityName = extras.getString("ActivityName1");
            ismultipleentry = extras.getString("Ismultipleentry1");
            consignmentcode = extras.getString("consignmentcode");
            Log.d("ActivityTypeIdHere", activityTypeId + "");
            Log.d("ActivityTypeIdHere", activityTypeId + "");
        }

        init();
        setViews();

    }

    private void init(){

        multipleentryrcv = findViewById(R.id.multipleentries);
        addBtn = findViewById(R.id.addBtn);

    }
    private void  setViews(){


        multiplelist = dataAccessHandler.getMultipleDataDetails(Queries.getInstance().getMultiplerecordsDetailsQuery(consignmentcode, activityTypeId));

        fieldslist = dataAccessHandler.getlandlevelligfeildDetails(Queries.getInstance().getFieldsData());

        Log.d("multiplelist", multiplelist.size() + "");
        multipleentryrcv.setLayoutManager(new LinearLayoutManager(this));
        multipleEntriesRecyclerViewAdapter = new MultipleEntriesRecyclerViewAdapter(MultipleEntryScreen.this, multiplelist, fieldslist ,activityName, activityTypeId, ismultipleentry,consignmentcode);
        multipleentryrcv.setAdapter(multipleEntriesRecyclerViewAdapter);

    }

}