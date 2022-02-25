package com.oilpalm3f.nursery.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.oilpalm3f.nursery.R;
import com.oilpalm3f.nursery.common.CommonConstants;
import com.oilpalm3f.nursery.common.CommonUtils;
import com.oilpalm3f.nursery.database.DataAccessHandler;
import com.oilpalm3f.nursery.dbmodels.LandlevellingFields;
import com.oilpalm3f.nursery.dbmodels.MutipleData;
import com.oilpalm3f.nursery.dbmodels.NurseryRMTransctions;
import com.oilpalm3f.nursery.ui.Adapter.MultipleEntriesRecyclerViewAdapter;
import com.oilpalm3f.nursery.ui.Adapter.NurseryrmActivitiesAdapter;
import com.oilpalm3f.nursery.ui.Adapter.RMTransactionRecyclerViewAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

    private EditText fromDateEdt, toDateEdt;
    private String fromDateStr = "";
    private String toDateStr = "";
    private Calendar myCalendar = Calendar.getInstance();
    private Button searchBtn;
    private String searchQuery = "";
    public static String SearchCollectionwithoutPlotQuery = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nurseryrm_transactions_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Nursery R&M");
        setSupportActionBar(toolbar);
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

        fromDateEdt = (EditText) findViewById(R.id.fromDate);
        toDateEdt = (EditText) findViewById(R.id.toDate);
    }

    private void setviews() {

        String currentDate = CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        fromDateEdt.setText(sdf.format(new Date()));
        toDateEdt.setText(sdf.format(new Date()));

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(0);
            }
        };

        final DatePickerDialog.OnDateSetListener toDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(1);
            }
        };

        //To Date on Click Listener
        toDateEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(NurseryrmTransactionsScreen.this, toDate, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));  //date is dateSetListener as per your code in question
                datePickerDialog.show();
            }
        });
        //From Date on Click Listener
        fromDateEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(NurseryrmTransactionsScreen.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));  //date is dateSetListener as per your code in question
                datePickerDialog.show();
            }
        });

        nurseryrmTransactions();

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent selectionscreen = new Intent(NurseryrmTransactionsScreen.this, RMActivityFields.class);
                selectionscreen.putExtra("Name", activityName);
                selectionscreen.putExtra("camefrom",  1);
                selectionscreen.putExtra("transactionId",   "");
                startActivity(selectionscreen);
            }
        });
    }

    private void updateLabel(int type) {
        String myFormat = "dd-MM-yyyy";
        String dateFormatter = "yyyy-MM-dd";

        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        SimpleDateFormat sdf2 = new SimpleDateFormat(dateFormatter, Locale.US);

        if (type == 0) {
            fromDateStr = sdf2.format(myCalendar.getTime());
            fromDateEdt.setText(sdf.format(myCalendar.getTime()));
        } else {
            toDateStr = sdf2.format(myCalendar.getTime());
            toDateEdt.setText(sdf.format(myCalendar.getTime()));
        }

    }

    private void nurseryrmTransactions() {
        NurseryRMTransctions a = new NurseryRMTransctions( "TRANRM00010001",1,346,"Job Completed","Other","22/02/2022");
        Transactionlist.add(a);
        a = new NurseryRMTransctions( "TRANRM00010002",1,347,"Nursery Manager Approved","Other","21/02/2022");
        Transactionlist.add(a);
        a = new NurseryRMTransctions( "TRANRM00010003",1,348,"State Head Approved","Other","21/02/2022");
        Transactionlist.add(a);
        a = new NurseryRMTransctions( "TRANRM00010004",1,349,"Rejected","Labour","22/02/2022");
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