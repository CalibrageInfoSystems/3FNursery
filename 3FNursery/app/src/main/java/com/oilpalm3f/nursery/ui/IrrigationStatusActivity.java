package com.oilpalm3f.nursery.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oilpalm3f.nursery.R;
import com.oilpalm3f.nursery.database.DataAccessHandler;
import com.oilpalm3f.nursery.database.Queries;
import com.oilpalm3f.nursery.dbmodels.ConsignmentStatuData;
import com.oilpalm3f.nursery.dbmodels.MutipleData;
import com.oilpalm3f.nursery.dbmodels.NurseryAcitivity;
import com.oilpalm3f.nursery.dbmodels.NurseryIrrigationLog;
import com.oilpalm3f.nursery.ui.Adapter.ActivitiesRecyclerviewAdapter;
import com.oilpalm3f.nursery.ui.Adapter.IrrigationstatusRecyclerviewAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

public class IrrigationStatusActivity extends AppCompatActivity {
    private static final String LOG_TAG =  IrrigationStatusActivity.class.getSimpleName();
    private TextView txtSatus;
    private DataAccessHandler dataAccessHandler;
    private List<ConsignmentStatuData> consignmentstatusList = new ArrayList<>();
    private RecyclerView irrigationstatusRecyclerview;
    private List<NurseryIrrigationLog> irrigationloglist = new ArrayList<>();

    private String NURCERYCODE,CONSINEMENTCODE;
    private IrrigationstatusRecyclerviewAdapter irrigationstatusRecyclerviewAdapter;
    String   Fromdate,Todate;
    EditText fromText, toText;
    DatePickerDialog picker;
    String currentDate,last_weekday,sendcurrentDate,sendweekdate;
    Button buttonSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_irrigation_status);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Irrigation Status");
        setSupportActionBar(toolbar);





        init();
        setViews();
    }

    private void init() {
        dataAccessHandler = new DataAccessHandler(this);

        irrigationstatusRecyclerview = findViewById(R.id.irrigationstatusRecyclerview);

        txtSatus = findViewById(R.id.txtSatus);
        fromText = (EditText) findViewById(R.id.from_date);
        toText = (EditText) findViewById(R.id.to_date);
        buttonSubmit =(Button)findViewById(R.id.buttonSubmit);

    }

    private void setViews() {
        currentDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        sendcurrentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        android.util.Log.i("LOG_RESPONSE date ", currentDate);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -7);

        Date date = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd");
        last_weekday = format.format(date);
        sendweekdate =  formate.format(date);
        Log.i("LOG_RESPONSE ===week", last_weekday);
        fromText.setText(last_weekday);
        toText.setText(currentDate);

        fromText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(IrrigationStatusActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                fromText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
                picker.getDatePicker().setMaxDate(System.currentTimeMillis());
            }
        });
        toText.setInputType(InputType.TYPE_NULL);
        toText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(IrrigationStatusActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                toText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
                picker.getDatePicker().setMaxDate(System.currentTimeMillis());
            }
        });



        displayActivityData();

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String fromString = fromText.getText().toString().trim();
                String  toString = toText.getText().toString().trim();
                SimpleDateFormat fromUser = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");

                try {

                    Fromdate = myFormat.format(fromUser.parse(fromString));
                    Todate = myFormat.format(fromUser.parse(toString));
                    Log.d("collection", "RESPONSE reformattedStr======" + Fromdate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                irrigationloglist = dataAccessHandler.getirigationlogs(Queries.getInstance().getIrrigationStatus(Fromdate,Todate));
                irrigationstatusRecyclerview.setLayoutManager(new LinearLayoutManager(IrrigationStatusActivity.this));
                irrigationstatusRecyclerviewAdapter = new IrrigationstatusRecyclerviewAdapter(IrrigationStatusActivity.this, irrigationloglist);
                irrigationstatusRecyclerview.setAdapter(irrigationstatusRecyclerviewAdapter);
            }
        });
    }

    private void displayActivityData() {


        irrigationloglist = dataAccessHandler.getirigationlogs(Queries.getInstance().getIrrigationStatus(sendweekdate,sendcurrentDate));
        irrigationstatusRecyclerview.setLayoutManager(new LinearLayoutManager(IrrigationStatusActivity.this));
        irrigationstatusRecyclerviewAdapter = new IrrigationstatusRecyclerviewAdapter(IrrigationStatusActivity.this, irrigationloglist);
        irrigationstatusRecyclerview.setAdapter(irrigationstatusRecyclerviewAdapter);
    }


    @Override
    protected void onResume() {
        super.onResume();


    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }



}