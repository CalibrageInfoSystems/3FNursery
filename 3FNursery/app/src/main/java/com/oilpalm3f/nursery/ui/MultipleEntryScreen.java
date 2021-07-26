package com.oilpalm3f.nursery.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // TOdo  Check Job Done or Not
               int value = dataAccessHandler.getOnlyOneIntValueFromDb(Queries.getInstance().CheckJobDoneOrnot(consignmentcode, activityTypeId));
                if(value  != 346){
                    Intent at = new Intent(MultipleEntryScreen.this, ActivityTask.class);
                    at.putExtra("consignmentcode", consignmentcode);
                    at.putExtra("isSingleEntry",false);
                    at.putExtra("ActivityTypeId", activityTypeId);
                    at.putExtra("ActivityName", activityName);
                    at.putExtra("Ismultipleentry",ismultipleentry );
                    at.putExtra("addActivity",true );
                    startActivity(at);
                }else
                {
                    Toast.makeText(MultipleEntryScreen.this, "Already Job done", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

}