package com.oilpalm3f.nursery.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.oilpalm3f.nursery.R;
import com.oilpalm3f.nursery.cloudhelper.Log;
import com.oilpalm3f.nursery.common.CommonConstants;
import com.oilpalm3f.nursery.database.DataAccessHandler;
import com.oilpalm3f.nursery.database.Queries;
import com.oilpalm3f.nursery.dbmodels.DisplayData;
import com.oilpalm3f.nursery.ui.Adapter.RecyclerAdapter;
import com.oilpalm3f.nursery.ui.Adapter.TransactionRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class TransactionDataActivity extends AppCompatActivity {
    String transactionId;
    private List<DisplayData> displayData = new ArrayList<>();
    private DataAccessHandler dataAccessHandler;
    TextView emptyView;
    RecyclerView trasactionlist;
    TransactionRecyclerAdapter recyclerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_data);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(" Check Activity ");
        setSupportActionBar(toolbar);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            try {
                transactionId = extras.getString("transactionId");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        intviews();
        setviews();
    }

    private void intviews() {
        dataAccessHandler = new DataAccessHandler(this);
        emptyView = findViewById(R.id.empty_view);
        trasactionlist = findViewById(R.id.trasactionlist);
        trasactionlist.setLayoutManager(new LinearLayoutManager(this));

    }

    private void setviews() {


        displayData = dataAccessHandler.getdisplayDetails(Queries.getInstance().getDisplayData(transactionId));
        Log.e("============>displayData",displayData.size()+"");

        if (displayData.size() > 0) {

            recyclerAdapter = new TransactionRecyclerAdapter(TransactionDataActivity.this, displayData);
            trasactionlist.setAdapter(recyclerAdapter);
            emptyView.setVisibility(View.GONE);
            trasactionlist.setVisibility(View.VISIBLE);
        } else {
            emptyView.setVisibility(View.VISIBLE);
            trasactionlist.setVisibility(View.GONE);

        }


    }
}