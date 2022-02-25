package com.oilpalm3f.nursery.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
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
import com.oilpalm3f.nursery.database.Queries;
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
    private List<NurseryRMTransctions> RmTransactionlist = new ArrayList<>();

    String last_30day;
    int Feild_id;
  RMTransactionRecyclerViewAdapter rmtransactionRecyclerViewAdapter;

    private EditText fromDateEdt, toDateEdt;
    private String fromDateStr = "";
    private String toDateStr = "";
    private Calendar myCalendar = Calendar.getInstance();
    private Button searchBtn;
    private String searchQuery = "";
    public static String SearchCollectionwithoutPlotQuery = "";
    DatePickerDialog picker;
    String currentDate;
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

       // RmTransactionlist = dataAccessHandler.getNurseryrmTransactionsg(Queries.getInstance().getrmActivities());
       // multiplelist = dataAccessHandler.getMultipleDataDetails(Queries.getInstance().getMultiplerecordsDetailsQuery(consignmentcode, activityTypeId));

        currentDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
     //   sendcurrentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
  Log.i("LOG_RESPONSE date ", currentDate);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -30);

        Date date = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd");
        last_30day = format.format(date);
     //   sendweekdate =  formate.format(date);
        Log.i("LOG_RESPONSE ===week", last_30day);
        fromDateEdt.setText(last_30day);
        toDateEdt.setText(currentDate);

        fromDateEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(NurseryrmTransactionsScreen.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                fromDateEdt.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
                picker.getDatePicker().setMaxDate(System.currentTimeMillis());
            }
        });

        toDateEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(NurseryrmTransactionsScreen.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                toDateEdt.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
                picker.getDatePicker().setMaxDate(System.currentTimeMillis());
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


//        rmtransactionRecyclerViewAdapter =    new RMTransactionRecyclerViewAdapter(this, RmTransactionlist,activityName);
//        Transactionrcv.setAdapter(rmtransactionRecyclerViewAdapter);

    }
}