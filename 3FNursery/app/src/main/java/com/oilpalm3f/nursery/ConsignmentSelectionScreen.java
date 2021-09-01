package com.oilpalm3f.nursery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.oilpalm3f.nursery.cloudhelper.Log;
import com.oilpalm3f.nursery.common.CommonConstants;
import com.oilpalm3f.nursery.database.DataAccessHandler;
import com.oilpalm3f.nursery.database.Queries;
import com.oilpalm3f.nursery.dbmodels.ConsignmentData;
import com.oilpalm3f.nursery.ui.Adapter.MultiConsignmentRecyclerviewAdapter;
import com.oilpalm3f.nursery.ui.ConsignmentRecyclerviewAdapter;
import com.oilpalm3f.nursery.ui.irrigation.IrrigationActivity;

import java.util.ArrayList;
import java.util.List;

public class ConsignmentSelectionScreen extends AppCompatActivity {

    RecyclerView consignmentRecyclerview;
    private DataAccessHandler dataAccessHandler;
    private List<ConsignmentData> consignmentList = new ArrayList<>();
    private ConsignmentRecyclerviewAdapter consignmentRecyclerviewAdapter;
    String nurserycode;
    private  Button select_btn;
       private MultiConsignmentRecyclerviewAdapter consignmentRecyclerviewAdapterMultiple;
    // Button select_btn;

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

        select_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (consignmentRecyclerviewAdapterMultiple.getSelected().size() > 0) {
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 0; i < consignmentRecyclerviewAdapterMultiple.getSelected().size(); i++) {
                        stringBuilder.append(consignmentRecyclerviewAdapterMultiple.getSelected().get(i).getConsignmentCode());
                        stringBuilder.append(",");

                    }
                    stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                    Intent intent = new Intent(getBaseContext(), IrrigationActivity.class);
                    intent.putExtra("consignmentCode",stringBuilder.toString().trim());
                    startActivity(intent);
//
                    Toast.makeText(ConsignmentSelectionScreen.this, stringBuilder.toString().trim(), Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(ConsignmentSelectionScreen.this, "Please Select Atlest One Consignment", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }


    private void init() {

        consignmentRecyclerview = findViewById(R.id.consignmentRecyclerview);
         select_btn = findViewById(R.id.selectbtn);
        if (CommonConstants.COMMINGFROM == CommonConstants.POST_CONSIGNMENT) {
           select_btn.setVisibility(View.VISIBLE);
        }else{
            select_btn.setVisibility(View.GONE);
        }
    }

    private void setViews() {

        //mActivitiesList= dataAccessHandler.getNurseryActivities(Queries.getInstance().getNurseryActivities(selectedFarmer.getCode(), 193));


        consignmentRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        if (CommonConstants.COMMINGFROM == CommonConstants.POST_CONSIGNMENT) {
            consignmentList = dataAccessHandler.getConsignmentData(Queries.getInstance().getConsignmentPostPreeDataQuery(CommonConstants.USER_ID, nurserycode));
            consignmentRecyclerviewAdapterMultiple = new MultiConsignmentRecyclerviewAdapter(ConsignmentSelectionScreen.this, consignmentList, nurserycode);
            consignmentRecyclerview.setAdapter(consignmentRecyclerviewAdapterMultiple);
        }else if( CommonConstants.COMMINGFROM == CommonConstants.NEWACTIVITYSCREEEN)
        {
            consignmentList = dataAccessHandler.getConsignmentData(Queries.getInstance().getAllConsignmentDataQuery(CommonConstants.USER_ID, nurserycode));
            consignmentRecyclerviewAdapter = new ConsignmentRecyclerviewAdapter(ConsignmentSelectionScreen.this, consignmentList, nurserycode);
            consignmentRecyclerview.setAdapter(consignmentRecyclerviewAdapter);
        }
        else {
            consignmentList = dataAccessHandler.getConsignmentData(Queries.getInstance().getConsignmentDataQuery(CommonConstants.USER_ID, nurserycode));
            consignmentRecyclerviewAdapter = new ConsignmentRecyclerviewAdapter(ConsignmentSelectionScreen.this, consignmentList, nurserycode);
            consignmentRecyclerview.setAdapter(consignmentRecyclerviewAdapter);
        }

    }
}