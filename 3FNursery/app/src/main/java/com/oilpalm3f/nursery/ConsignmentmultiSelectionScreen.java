package com.oilpalm3f.nursery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.oilpalm3f.nursery.cloudhelper.Log;
import com.oilpalm3f.nursery.common.CommonConstants;
import com.oilpalm3f.nursery.database.DataAccessHandler;
import com.oilpalm3f.nursery.database.Queries;
import com.oilpalm3f.nursery.dbmodels.ConsignmentData;
import com.oilpalm3f.nursery.ui.ConsignmentRecyclerviewAdapter;
import com.oilpalm3f.nursery.ui.MultiConsignmentRecyclerviewAdapter;

import java.util.ArrayList;
import java.util.List;

public class ConsignmentmultiSelectionScreen extends AppCompatActivity {

    RecyclerView consignmentRecyclerview;
    private DataAccessHandler dataAccessHandler;
    private List<ConsignmentData> consignmentList = new ArrayList<>();
    private MultiConsignmentRecyclerviewAdapter consignmentRecyclerviewAdapter;
    String nurserycode;
    private ArrayList<ConsignmentData> selectedconsignmentList = new ArrayList<>();
    private AppCompatButton btnGetSelected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consignmentmulti_selection_screen2);

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
        btnGetSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (consignmentRecyclerviewAdapter.getSelected().size() > 0) {
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 0; i < consignmentRecyclerviewAdapter.getSelected().size(); i++) {
                        stringBuilder.append(consignmentRecyclerviewAdapter.getSelected().get(i).getConsignmentCode());
                        stringBuilder.append("\n");
                    }
                    showToast(stringBuilder.toString().trim());
                } else {
                    showToast("No Selection");
                }
            }
        });
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    private void init() {

        consignmentRecyclerview = findViewById(R.id.consignmentRecyclerview);
        this.btnGetSelected = (AppCompatButton) findViewById(R.id.btnGetSelected);
    }

    private void setViews() {

        //mActivitiesList= dataAccessHandler.getNurseryActivities(Queries.getInstance().getNurseryActivities(selectedFarmer.getCode(), 193));

        consignmentList = dataAccessHandler.getConsignmentData(Queries.getInstance().getConsignmentDataQuery(CommonConstants.USER_ID,nurserycode));
        consignmentRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        consignmentRecyclerviewAdapter = new MultiConsignmentRecyclerviewAdapter(ConsignmentmultiSelectionScreen.this, consignmentList,nurserycode);
        consignmentRecyclerview.setAdapter(consignmentRecyclerviewAdapter);
    }
}