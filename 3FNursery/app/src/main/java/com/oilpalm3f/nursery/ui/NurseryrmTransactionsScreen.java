package com.oilpalm3f.nursery.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.oilpalm3f.nursery.R;
import com.oilpalm3f.nursery.database.DataAccessHandler;
import com.oilpalm3f.nursery.dbmodels.LandlevellingFields;
import com.oilpalm3f.nursery.dbmodels.MutipleData;
import com.oilpalm3f.nursery.ui.Adapter.MultipleEntriesRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class NurseryrmTransactionsScreen extends AppCompatActivity {
    RecyclerView Transactionrcv;
    private DataAccessHandler dataAccessHandler;
    Button addBtn;
    String activityTypeId, activityName,  status;
    int statusId;

    private List<MutipleData> multiplelist = new ArrayList<>();
    private List<LandlevellingFields> fieldslist = new ArrayList<>();
    String btn_visibility;
    int Feild_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nurseryrm_transactions_screen);

        addBtn = findViewById(R.id.addBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent selectionscreen = new Intent(NurseryrmTransactionsScreen.this, RMActivityFields.class);
                startActivity(selectionscreen);
            }
        });
    }
}