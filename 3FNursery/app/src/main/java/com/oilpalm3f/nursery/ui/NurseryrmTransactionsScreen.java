package com.oilpalm3f.nursery.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.oilpalm3f.nursery.R;
import com.oilpalm3f.nursery.database.DataAccessHandler;
import com.oilpalm3f.nursery.dbmodels.LandlevellingFields;
import com.oilpalm3f.nursery.dbmodels.MutipleData;
import com.oilpalm3f.nursery.dbmodels.NurseryRMTransctions;
import com.oilpalm3f.nursery.ui.Adapter.MultipleEntriesRecyclerViewAdapter;
import com.oilpalm3f.nursery.ui.Adapter.NurseryrmActivitiesAdapter;
import com.oilpalm3f.nursery.ui.Adapter.RMTransactionRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class NurseryrmTransactionsScreen extends AppCompatActivity {
    RecyclerView Transactionrcv;
    private DataAccessHandler dataAccessHandler;
    Button addBtn;
    String activityTypeId, activityName,  status;
    int statusId;
TextView activity_name;
    private List<NurseryRMTransctions> Transactionlist = new ArrayList<>();

    String btn_visibility;
    int Feild_id;
  RMTransactionRecyclerViewAdapter rmtransactionRecyclerViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nurseryrm_transactions_screen);
        Bundle extras = getIntent().getExtras();  // SETUP title For Activity
        if (extras != null) {
            try {
                activityName = extras.getString("RmActivityname");
                Log.d("activity_Name========>", activityName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        initviews();
        setviews();
    }


    private void initviews() {
        dataAccessHandler = new DataAccessHandler(this);
        addBtn = findViewById(R.id.addBtn);
        activity_name = findViewById(R.id.activityname);
        activity_name.setText(activityName);
        Transactionrcv = findViewById(R.id.Transactionrcv);
        Transactionrcv.setHasFixedSize(true);
        Transactionrcv.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setviews() {
        nurseryrmTransactions();

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent selectionscreen = new Intent(NurseryrmTransactionsScreen.this, RMActivityFields.class);
                startActivity(selectionscreen);
            }
        });
    }

    private void nurseryrmTransactions() {
        NurseryRMTransctions a = new NurseryRMTransctions( "TRANRM00010001",1,346,"Job Completed","new RM","22/02/2022");
        Transactionlist.add(a);
        a = new NurseryRMTransctions( "TRANRM00010002",1,348,"Approved","test Approved","21/02/2022");
        Transactionlist.add(a);
        a = new NurseryRMTransctions( "TRANRM00010003",1,349,"Rejected","Rejected","22/02/2022");
        Transactionlist.add(a);
        //a = new NurseryRMTransctions( "TRANRM00010001",1,"","","24/02/2022");
//        Transactionlist.add(a); a = new NurseryRMTransctions( "TRANRM00010001",1,"","","24/02/2022");
//        Transactionlist.add(a);
//        a = new NurseryRMTransctions( "TRANRM00010001",1," ","","24/02/2022");
//        Transactionlist.add(a);

        rmtransactionRecyclerViewAdapter =    new RMTransactionRecyclerViewAdapter(this, Transactionlist,activityName);
        Transactionrcv.setAdapter(rmtransactionRecyclerViewAdapter);

    }
}