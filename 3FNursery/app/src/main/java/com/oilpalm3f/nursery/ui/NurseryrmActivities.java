package com.oilpalm3f.nursery.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.oilpalm3f.nursery.ConsignmentSelectionScreen;
import com.oilpalm3f.nursery.R;
import com.oilpalm3f.nursery.cloudhelper.ApplicationThread;
import com.oilpalm3f.nursery.database.DataAccessHandler;
import com.oilpalm3f.nursery.datasync.helpers.DataSyncHelper;
import com.oilpalm3f.nursery.dbmodels.NurseryRMActivity;
import com.oilpalm3f.nursery.ui.Adapter.NotificationDisplayAdapter;
import com.oilpalm3f.nursery.ui.Adapter.NurseryrmActivitiesAdapter;
import com.oilpalm3f.nursery.ui.irrigation.IrrigationActivity;
import com.oilpalm3f.nursery.utils.UiUtils;

import java.util.ArrayList;
import java.util.List;

public class NurseryrmActivities extends AppCompatActivity {

    public static final String LOG_TAG = NurseryrmActivities.class.getSimpleName();
     Button ok_btn;
    private RecyclerView ActivityRecyclerView;
    private NotificationDisplayAdapter notificationDisplayAdapter;
    private LinearLayoutManager layoutManager;
    private DataAccessHandler dataAccessHandler;
    TextView othertext;
    EditText Activitynameedit;
    LinearLayout otherlinear;
    private List<NurseryRMActivity> request_List = new ArrayList<>();
    NurseryrmActivitiesAdapter nurseryrmListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nurseryrm_activities);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Nursery R&M");
        setSupportActionBar(toolbar);
        initviews();
        setviews();
    }

    private void initviews() {
        dataAccessHandler = new DataAccessHandler(this);
        othertext= findViewById(R.id.othertext);
        ActivityRecyclerView = findViewById(R.id.ActivityRecyclerView);
        otherlinear = findViewById(R.id.other_linear);
        ok_btn = findViewById(R.id.ok_btn);
        Activitynameedit = findViewById(R.id.Activitynameedit);
        ActivityRecyclerView.setHasFixedSize(true);
        ActivityRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    private void setviews() {


        nurseryrmActivities();

        othertext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                otherlinear.setVisibility(View.VISIBLE);
            }
        });

            ok_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!Activitynameedit.getText().toString().isEmpty() && !Activitynameedit.getText().toString().equals("null")) {
                    Intent NurseryrmTransactions = new Intent(NurseryrmActivities.this, NurseryrmTransactionsScreen.class);
                    NurseryrmTransactions.putExtra("RmActivityname", Activitynameedit.getText().toString());
                    startActivity(NurseryrmTransactions);
                    }
                    else{

                        Toast.makeText(NurseryrmActivities.this, "Please Enter Nursery RM Activity Name", Toast.LENGTH_SHORT).show();
                    }
                }
            });


        }


    private void nurseryrmActivities() {

        NurseryRMActivity a = new NurseryRMActivity( "Shade Net R & M");
        request_List.add(a);
        a = new NurseryRMActivity( "Fencing R & M");
        request_List.add(a);
        a = new NurseryRMActivity( "Fencing_A R & M");
        request_List.add(a);
        a = new NurseryRMActivity( "Electricity R & M");
        request_List.add(a);
        a = new NurseryRMActivity( "Irrigation Setup R & M");
        request_List.add(a);




        nurseryrmListAdapter =    new NurseryrmActivitiesAdapter(this, request_List);
        ActivityRecyclerView.setAdapter(nurseryrmListAdapter);
    }
}