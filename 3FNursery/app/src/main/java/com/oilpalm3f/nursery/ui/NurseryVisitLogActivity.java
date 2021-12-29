package com.oilpalm3f.nursery.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.oilpalm3f.nursery.R;
import com.oilpalm3f.nursery.cloudhelper.ApplicationThread;
import com.oilpalm3f.nursery.common.CommonConstants;
import com.oilpalm3f.nursery.common.CommonUtils;
import com.oilpalm3f.nursery.database.DataAccessHandler;
import com.oilpalm3f.nursery.database.Queries;
import com.oilpalm3f.nursery.datasync.helpers.DataSyncHelper;
import com.oilpalm3f.nursery.dbmodels.ConsignmentData;
import com.oilpalm3f.nursery.dbmodels.NurseryDetails;
import com.oilpalm3f.nursery.uihelper.ProgressBar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

public class NurseryVisitLogActivity extends AppCompatActivity {
    public static final String LOG_TAG = NurseryVisitLogActivity.class.getSimpleName();
    LinkedHashMap<String, Pair> nurserydatamap = null;
    LinkedHashMap<String, Pair> typeofLogdatamap = null;
    private Spinner nurserySpinner,Consingmentspinner,logtypespin;
    private DataAccessHandler dataAccessHandler;
    Button submitBtn;
    String nursery_code;
    List<NurseryDetails> nurseryDetails;
     List<ConsignmentData> consignmentList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nursery_visit_log);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Nursery Visit Log");
        setSupportActionBar(toolbar);
        initviews();
        setviews();
    }
    private void initviews() {
        dataAccessHandler = new DataAccessHandler(this);
        nurserySpinner = findViewById(R.id.nurserySpinner);
        submitBtn =  findViewById(R.id.submitBtn);
        logtypespin= findViewById(R.id.logtypespin);
        Consingmentspinner =findViewById(R.id.consignmentcode);

    }

    private void setviews() {


        nurserydatamap = dataAccessHandler.getPairData(Queries.getInstance().getNurseryMasterQuery());


        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, CommonUtils.arrayFromPair(nurserydatamap, "Nursery"));
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        nurserySpinner.setAdapter(spinnerArrayAdapter);

        nurserySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (nurserySpinner.getSelectedItemPosition() != 0) {

                    Log.d("Selected1", nurserySpinner.getSelectedItem().toString());
                    nurseryDetails = dataAccessHandler.getNurseryDetails(Queries.getInstance().getNurseryDetailsQuery(nurserySpinner.getSelectedItem().toString()));
                    nursery_code = nurseryDetails.get(0).getCode();
                    Log.d("Selected1===nurserycode", nursery_code);


                } else {


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        typeofLogdatamap = dataAccessHandler.getPairData(Queries.getInstance().getTypeofvisitLogQuery());
       
        ArrayAdapter<String> LogArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, CommonUtils.arrayFromPair(typeofLogdatamap, "Lo"));
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        logtypespin.setAdapter(LogArrayAdapter);

        logtypespin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (logtypespin.getSelectedItemPosition() != 0) {

                    Log.d("Selected1", logtypespin.getSelectedItem().toString());
//                    nurseryDetails = dataAccessHandler.getNurseryDetails(Queries.getInstance().getNurseryDetailsQuery(logtypespin.getSelectedItem().toString()));
//                    nursery_code = nurseryDetails.get(0).getCode();
//                    Log.d("Selected1===nurserycode", nursery_code);


                } else {


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        consignmentList = dataAccessHandler.getConsignmentData(Queries.getInstance().getAllConsignment(CommonConstants.USER_ID, nursery_code));
        Log.d("consignmentList===>", consignmentList.size()+"");
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (goValidate()){

                        LinkedHashMap mapStatus = new LinkedHashMap();
                        mapStatus.put("NurseryCode", nursery_code);
                    mapStatus.put("LogTypeId", nursery_code);
                    mapStatus.put("CosignmentCode", nursery_code);
                    mapStatus.put("ClientName", nursery_code);
                    mapStatus.put("MobileNumber", nursery_code);
                    mapStatus.put("Location", nursery_code);
                    mapStatus.put("Latitude", nursery_code);
                    mapStatus.put("Longitude", nursery_code);
                    mapStatus.put("Comments", nursery_code);
                    mapStatus.put("CreatedByUserId", CommonConstants.USER_ID);
                        mapStatus.put("CreatedDate", CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
                        mapStatus.put("ServerUpdatedStatus", 0);
                    mapStatus.put("FileName ", nursery_code);
                    mapStatus.put("FileLocation", nursery_code);
                    mapStatus.put("FileExtension", nursery_code);


                        final List<LinkedHashMap> nurseryvisit_log = new ArrayList<>();
                        nurseryvisit_log.add(mapStatus);

                        Log.e("==============>",nurseryvisit_log+"");
                        dataAccessHandler.insertMyDataa("NurseryVisitLog", nurseryvisit_log, new ApplicationThread.OnComplete<String>() {
                            @Override
                            public void execute(boolean success, String result, String msg) {
                                if (success) {
                                    if (CommonUtils.isNetworkAvailable(NurseryVisitLogActivity.this)) {


                                        DataSyncHelper.performRefreshTransactionsSync(NurseryVisitLogActivity.this, new ApplicationThread.OnComplete() {
                                            @Override
                                            public void execute(boolean success, Object result, String msg) {
                                                if (success) {
                                                    ApplicationThread.uiPost(LOG_TAG, "transactions sync message", new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            Toast.makeText(NurseryVisitLogActivity.this, "Successfully data sent to server", Toast.LENGTH_SHORT).show();
//
                                                            finish();
                                                        }
                                                    });
                                                } else {
                                                    ApplicationThread.uiPost(LOG_TAG, "transactions sync failed message", new Runnable() {
                                                        @Override
                                                        public void run() {

                                                            ProgressBar.hideProgressBar();

                                                            finish();

                                                        }
                                                    });
                                                }
                                            }
                                        });
                                    }


                                    Log.d(ActivityTask.class.getSimpleName(), "==> NurseryLabourLog    INSERT COMPLETED");
                                }
                            }
                        });


                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Please enter at least one value", Toast.LENGTH_SHORT).show();
                    }}
            });}

    private boolean goValidate() {
        if (nurserySpinner.getSelectedItemPosition() == 0){

            Toast.makeText(this, "Please Select Nursery", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

}