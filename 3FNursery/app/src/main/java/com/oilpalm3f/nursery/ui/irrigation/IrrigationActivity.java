package com.oilpalm3f.nursery.ui.irrigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.oilpalm3f.nursery.R;
import com.oilpalm3f.nursery.cloudhelper.ApplicationThread;
import com.oilpalm3f.nursery.cloudhelper.Log;
import com.oilpalm3f.nursery.common.CommonConstants;
import com.oilpalm3f.nursery.common.CommonUtils;
import com.oilpalm3f.nursery.database.DataAccessHandler;
import com.oilpalm3f.nursery.ui.ActivityTask;
import com.oilpalm3f.nursery.ui.HomeActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
public class IrrigationActivity extends AppCompatActivity {

    private TextView date, nursaryname, consignment_num;
    private EditText manregular_edt, femalereg_edt, manout_edt, femaleout_edt, labourt_edt;
    private Button save_btn;
    private DataAccessHandler dataAccessHandler;
    int manreg_int, femalereg_int, manout_int, femaleout_int;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_irrigation);
        dataAccessHandler = new DataAccessHandler(this);


        date = findViewById(R.id.textView_date);
        nursaryname = findViewById(R.id.nurseryname);
        consignment_num = findViewById(R.id.consignment);
        manregular_edt = findViewById(R.id.manreg_edt);
        femalereg_edt = findViewById(R.id.Femalereg_edt);
        manout_edt = findViewById(R.id.manout_edt);
        femaleout_edt = findViewById(R.id.femaleout_edt);
        //labourt_edt        = findViewById(R.id.labourt_edt);
        save_btn = findViewById(R.id.save_btn);


        Date date1 = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(date1);


        date.setText("  : " + formattedDate);
        nursaryname.setText(" : " + CommonConstants.NurseryName);
        consignment_num.setText(" : " + CommonConstants.ConsignmentID);

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (manregular_edt.length() != 0 || femalereg_edt.length() != 0 ||
                        manout_edt.length() != 0 || femaleout_edt.length() != 0) {

                    LinkedHashMap mapStatus = new LinkedHashMap();
                    mapStatus.put("Id", 0);
                    mapStatus.put("LogDate", CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
                    mapStatus.put("ConsignmentId", CommonConstants.ConsignmentID );
                    if (manregular_edt.length() != 0) {
                        mapStatus.put("RegularMale", manregular_edt.getText().toString());
                    }
                    if (femalereg_edt.length() != 0) {
                        mapStatus.put("RegularFemale", femalereg_edt.getText().toString());

                    }
                    if (manout_edt.length() != 0) {
                        mapStatus.put("ContractMale", manout_edt.getText().toString());

                    }
                    if (femaleout_edt.length() != 0) {
                        mapStatus.put("ContractFemale", femaleout_edt.getText().toString());

                    }


                    mapStatus.put("StatusTypeId", 346);
                    mapStatus.put("Comments", "");
                    mapStatus.put("IsActive",true);
                    mapStatus.put("CreatedByUserId", CommonConstants.USER_ID);
                    mapStatus.put("CreatedDate", CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
                    mapStatus.put("UpdatedByUserId", CommonConstants.USER_ID);
                    mapStatus.put("UpdatedDate",  CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
                    mapStatus.put("ServerUpdatedStatus", 0);



                    final List<LinkedHashMap> irrigationArray = new ArrayList<>();
                    irrigationArray.add(mapStatus);


                    dataAccessHandler.insertMyDataa("NurseryIrrigationLog",
                            irrigationArray, new ApplicationThread.OnComplete<String>() {
                                @Override
                                public void execute(boolean success, String result, String msg) {
                                    if (success) {
                                        Toast.makeText(IrrigationActivity.this, "Data Saved Successfully", Toast.LENGTH_SHORT).show();
                                        Intent newIntent = new Intent(IrrigationActivity.this, HomeActivity.class);
                                        newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(newIntent);

                                    }
                                    Log.d(ActivityTask.class.getSimpleName(),
                                            "==>  Analysis ==> irrigationlog details INSERT COMPLETED :"+success);

                                }
                            });


                } else {

                    Toast.makeText(getApplicationContext(), "Please enter at least one value", Toast.LENGTH_SHORT).show();

                }
            }
        });


    }
}