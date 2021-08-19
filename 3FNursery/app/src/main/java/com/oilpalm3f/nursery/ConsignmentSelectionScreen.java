package com.oilpalm3f.nursery;

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

                if(consignmentRecyclerviewAdapterMultiple.getSelected().size() > 0){
//                    String[] codes = consignmentRecyclerviewAdapterMultiple.getItemCount().toArray(new String[0]);
//
//                    Intent i =new Intent(ConsignmentSelectionScreen.this, IrrigationActivity.class);
//                    i.putExtra("SeelctedConsignments", codes);
//                    startActivity(i);

                }else{
                    Toast.makeText(ConsignmentSelectionScreen.this, "Please Select Atlest One Consignment", Toast.LENGTH_SHORT).show();
                }

            }





          });

    }


    private void init() {

        consignmentRecyclerview = findViewById(R.id.consignmentRecyclerview);
         select_btn              = findViewById(R.id.selectbtn);
        if (CommonConstants.COMMINGFROM == CommonConstants.POST_CONSIGNMENT) {
           select_btn.setVisibility(View.VISIBLE);
        }else{
            select_btn.setVisibility(View.GONE);
        }
    }

    private void setViews() {

        //mActivitiesList= dataAccessHandler.getNurseryActivities(Queries.getInstance().getNurseryActivities(selectedFarmer.getCode(), 193));

        consignmentList = dataAccessHandler.getConsignmentData(Queries.getInstance().getConsignmentDataQuery(CommonConstants.USER_ID, nurserycode));
        consignmentRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        if (CommonConstants.COMMINGFROM == CommonConstants.POST_CONSIGNMENT) {
            consignmentRecyclerviewAdapterMultiple = new MultiConsignmentRecyclerviewAdapter(ConsignmentSelectionScreen.this, consignmentList, nurserycode);
            consignmentRecyclerview.setAdapter(consignmentRecyclerviewAdapter);
        } else {
            consignmentRecyclerviewAdapter = new ConsignmentRecyclerviewAdapter(ConsignmentSelectionScreen.this, consignmentList, nurserycode);
            consignmentRecyclerview.setAdapter(consignmentRecyclerviewAdapter);
        }

    }
}