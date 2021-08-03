package com.oilpalm3f.nursery.ui;

import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.oilpalm3f.nursery.R;
import com.oilpalm3f.nursery.cloudhelper.ApplicationThread;
import com.oilpalm3f.nursery.cloudhelper.Log;
import com.oilpalm3f.nursery.common.CommonConstants;
import com.oilpalm3f.nursery.common.CommonUtils;
import com.oilpalm3f.nursery.database.DataAccessHandler;
import com.oilpalm3f.nursery.database.Queries;
import com.oilpalm3f.nursery.dbmodels.ActivityTasks;
import com.oilpalm3f.nursery.dbmodels.DisplayData;
import com.oilpalm3f.nursery.dbmodels.ExistingData;
import com.oilpalm3f.nursery.dbmodels.SaplingActivity;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class ActivityTask extends AppCompatActivity {

    String activityTypeId, consignmentCode, activityName, isMultipleentry, transactionIdFromMultiple;

    private List<ActivityTasks> activityTasklist = new ArrayList<>();
    private DataAccessHandler dataAccessHandler;
    LinkedHashMap<String, Pair> typeofLabourdatamap = null;
    Boolean isSingleentry = false, addactivity = false;

    private List<SaplingActivity> saplingActivitiesList = new ArrayList<>();
    int SaplingActivityCount;
    List<KeyValues> dataValue = new ArrayList<>();
    int random_int = 0;
    int maxnumber;
    TextView textView5;
    String TransactionID;
    int sapactivitysize, sapactivitysizeinc;
    private List<ExistingData> existingData = new ArrayList<>();
    private List<DisplayData> displayData = new ArrayList<>();
    boolean isUpdate = false;
    int activityStatus;
    int isjobDoneId = 0;
    int SCREEN_FROM = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            activityTypeId = extras.getString("ActivityTypeId");
            activityName = extras.getString("ActivityName");
            SCREEN_FROM = extras.getInt(CommonConstants.SCREEN_CAME_FROM);

        }
        // SETUP title For Activity
        textView5 = findViewById(R.id.textView5);
        textView5.setText(activityName);

        dataAccessHandler = new DataAccessHandler(this);
        LinearLayout ll = (LinearLayout) findViewById(R.id.linearLayout2);

        maxnumber = dataAccessHandler.getOnlyOneIntValueFromDb(Queries.getInstance().getSaplingActivityMaxNumber());
        Log.d("maxnumber", maxnumber + "");


        createDynamicUI(ll);
        if (SCREEN_FROM == CommonConstants.FROM_MUTIPLE_ENTRY_EDITDATA) {
            Log.d(ActivityTask.class.getSimpleName(), " ===> Analysis  ==> SCREEN CAME FROM :FROM_MUTIPLE_ENTRY_EDITDATA");
            // SCREEN CAME FROM UPDATE CURRENT SCREEN
            String consignmentcode = extras.getString("consignmentcode");
            String intentTransactionId = extras.getString("transactionId");
            Log.d(ActivityTask.class.getSimpleName(), " ===> Analysis  ==> FROM_MUTIPLE_ENTRY_EDITDATA  ###### transaction Id :"+intentTransactionId);
            bindExistingData(intentTransactionId);
            // TODO Bind DATA UsingTransactionID

        } else if (SCREEN_FROM == CommonConstants.FROM_MULTIPLE_ADD_NEW_TASK) {
            Log.d(ActivityTask.class.getSimpleName(), " ===> Analysis  ==> SCREEN CAME FROM :FROM_MULTIPLE_ADD_NEW_TASK");
            String activityTypeId = extras.getString("ActivityTypeId");
            String consignmentcode = extras.getString("consignmentcode");
            boolean Ismultipleentry = extras.getBoolean("Ismultipleentry");
            // TODO Just Add New Task

        } else if (SCREEN_FROM == CommonConstants.FROM_SINGLE_ENTRY_EDITDATA) {
            Log.d(ActivityTask.class.getSimpleName(), " ===> Analysis  ==> SCREEN CAME FROM :FROM_SINGLE_ENTRY_EDITDATA");
            String consignmentcode = extras.getString("consignmentcode");
            String activityTypeId = extras.getString("ActivityTypeId");
            String multipleentry = extras.getString("multipleEntry");


            // TODO CHECK DATA EXIST OR NOT      IF EXIST BIND DATA

            String transactionId = dataAccessHandler.getSingleValue(Queries.getInstance().getTransactionIdUsingConsimentCode(consignmentcode, activityTypeId));
            if (null != transactionId && !transactionId.isEmpty() && !TextUtils.isEmpty(transactionId)) {
                bindExistingData(transactionId);
            } else {
                Log.d(ActivityTask.class.getSimpleName(), "==> Analysis  ==> New Task Creation Started ");
                String transactionIdNew = "T" + CommonConstants.TAB_ID + consignmentcode + activityTypeId + "-" + (dataAccessHandler.getOnlyOneIntValueFromDb(Queries.getInstance().getSaplingActivityMaxNumber()) + 1);
                Log.d(ActivityTask.class.getSimpleName(), "==> Analysis   New Transaction ID :" + transactionIdNew);


            }


        }



    }

    private void bindExistingData(String transactionId) {
        displayData = dataAccessHandler.getdisplayDetails(Queries.getInstance().getDisplayData(transactionId));
        Log.d(ActivityTask.class.getSimpleName(), "==> Analysis Count Of DisplayData :" + displayData.size());

        for (int i = 0; i < displayData.size(); i++) {
            if (displayData.get(i).getInputType().equalsIgnoreCase("Check box")) {
                CheckBox chk = (CheckBox) findViewById(displayData.get(i).getFieldId());
                if (displayData.get(i).getValue().equalsIgnoreCase("true")) {
                    chk.setChecked(true);
                } else
                    chk.setChecked(false);
            } else if (displayData.get(i).getInputType().equalsIgnoreCase("TextBox")) {
                EditText editText = (EditText) findViewById(displayData.get(i).getFieldId());
                if (!TextUtils.isEmpty(displayData.get(i).getValue())) {
                    editText.setText(displayData.get(i).getValue());
                } else
                    editText.setText("");
            } else if (displayData.get(i).getInputType().equalsIgnoreCase("Label") || displayData.get(i).getInputType().equalsIgnoreCase("Display")) {
                TextView textView = (TextView) findViewById(displayData.get(i).getFieldId());
                if (!TextUtils.isEmpty(displayData.get(i).getValue())) {
                    textView.setText(displayData.get(i).getValue());
                } else
                    textView.setText("");
            } else if (displayData.get(i).getInputType().equalsIgnoreCase("Dropdown") || displayData.get(i).getInputType().equalsIgnoreCase("dropdown")) {
                String value = displayData.get(i).getValue();
                int position = 0;
                String[] data = CommonUtils.arrayFromPair(typeofLabourdatamap, "Type of Labour");
                for (int j = 0; j < data.length; j++) {
                    if (value.equalsIgnoreCase(data[j])) {
                        position = j;
                    }
                }
                Spinner sp = (Spinner) findViewById(displayData.get(i).getFieldId());
                sp.setSelection(position);

            }

        }
    }

    private void addNewSingleEntryActivity(String _consignmentCode, String _activityId, int _statusTypeId, String _transactionId, boolean isFromMultipleEntry) {
        if (validate()) {
            // DATA Validated next saving data locally
            final List<LinkedHashMap> listKey = new ArrayList<>();
            for (int j = 0; j < dataValue.size(); j++) {

                LinkedHashMap mapXref = new LinkedHashMap();
                mapXref.put("Id", 0);
                mapXref.put("TransactionId", _transactionId);
                mapXref.put("FieldId", dataValue.get(j).id);
                mapXref.put("Value", dataValue.get(j).value);
                mapXref.put("FilePath", "");
                mapXref.put("IsActive", 1);
                mapXref.put("CreatedByUserId", CommonConstants.USER_ID);
                mapXref.put("CreatedDate", CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
                mapXref.put("UpdatedByUserId", CommonConstants.USER_ID);
                mapXref.put("UpdatedDate", CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
                mapXref.put("ServerUpdatedStatus", 0);

                listKey.add(mapXref);
            }


            dataAccessHandler.insertMyDataa("SaplingActivityXref", listKey, new ApplicationThread.OnComplete<String>() {
                @Override
                public void execute(boolean success, String result, String msg) {
                    Log.d(ActivityTask.class.getSimpleName(), "==>  Analysis ==> SaplingActivityXref INSERT COMPLETED");


                }
            });

            LinkedHashMap sapling = new LinkedHashMap();
            sapling.put("TransactionId", _transactionId);
            sapling.put("ConsignmentCode", _consignmentCode);
            sapling.put("ActivityId", _activityId);
            sapling.put("StatusTypeId", _statusTypeId);
            sapling.put("Comment", "");
            sapling.put("IsActive", 1);
            sapling.put("CreatedByUserId", CommonConstants.USER_ID);
            sapling.put("CreatedDate", CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
            sapling.put("UpdatedByUserId", CommonConstants.USER_ID);
            sapling.put("UpdatedDate", CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
            sapling.put("ServerUpdatedStatus", 0);
            final List<LinkedHashMap> saplingList = new ArrayList<>();

            saplingList.add(sapling);
            dataAccessHandler.insertMyDataa("SaplingActivity", saplingList, new ApplicationThread.OnComplete<String>() {
                @Override
                public void execute(boolean success, String result, String msg) {
                    if (success) {
                        Log.d(ActivityTask.class.getSimpleName(), "==>  Analysis ==> SaplingActivity INSERT COMPLETED");
                        Log.d(ActivityTask.class.getSimpleName(), "==>  Analysis ==> Add new Task Completed");
                        finish();
                        Toast.makeText(ActivityTask.this, "Data Saved Successfully", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            if(isFromMultipleEntry)
            {
                // Came from Multiple entry then we can update Status only
                LinkedHashMap status = new LinkedHashMap();

                status.put("ConsignmentCode", _consignmentCode);
                status.put("ActivityId", _activityId);
                status.put("StatusTypeId", _statusTypeId);
                status.put("CreatedByUserId", CommonConstants.USER_ID);
                status.put("CreatedDate", CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
                status.put("UpdatedByUserId", CommonConstants.USER_ID);
                status.put("UpdatedDate", CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
                status.put("ServerUpdatedStatus", 0);

                final List<LinkedHashMap> statusList = new ArrayList<>();
                statusList.add(status);
                dataAccessHandler.updateData("SaplingActivityStatus", statusList, true, " where ConsignmentCode = " + "'" + _consignmentCode + "' AND ActivityId ='" + _activityId + "'", new ApplicationThread.OnComplete<String>() {
                    @Override
                    public void execute(boolean success, String result, String msg) {
                        Log.d(ActivityTask.class.getSimpleName(), "==>  Analysis ==> SaplingActivityStatus INSERT COMPLETED");
                        Log.d(ActivityTask.class.getSimpleName(), "==>  Analysis ==> Update Task Completed");
                        finish();
                        Toast.makeText(ActivityTask.this, "Data Saved Successfully", Toast.LENGTH_SHORT).show();
                    }
                });

            }else{
                LinkedHashMap mapStatus = new LinkedHashMap();
                mapStatus.put("Id", 0);
                mapStatus.put("ConsignmentCode", _consignmentCode);
                mapStatus.put("ActivityId", _activityId);
                mapStatus.put("StatusTypeId", _statusTypeId);
                mapStatus.put("CreatedByUserId", CommonConstants.USER_ID);
                mapStatus.put("CreatedDate", CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
                mapStatus.put("UpdatedByUserId", CommonConstants.USER_ID);
                mapStatus.put("UpdatedDate", CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
                mapStatus.put("ServerUpdatedStatus", 0);

                final List<LinkedHashMap> statusArray = new ArrayList<>();
                statusArray.add(mapStatus);

                dataAccessHandler.insertMyDataa("SaplingActivityStatus", statusArray, new ApplicationThread.OnComplete<String>() {
                    @Override
                    public void execute(boolean success, String result, String msg) {
                        if (success) {
                            Log.d(ActivityTask.class.getSimpleName(), "==>  Analysis ==> SaplingActivityStatus INSERT COMPLETED");

                        }

                    }
                });
            }
        }
    }

    private void createDynamicUI(LinearLayout ll) {
        activityTasklist = dataAccessHandler.getActivityTasksDetails(Queries.getInstance().getActivityTaskDetails(Integer.parseInt(activityTypeId)));

        for (int i = 0; i < activityTasklist.size(); i++) {
            if (activityTasklist.get(i).getInputType().equalsIgnoreCase("Check box")) {
                if (activityTasklist.get(i).getField().equalsIgnoreCase("Is the activity completed")) {
                    isjobDoneId = activityTasklist.get(i).getId();
                }
                ll.addView(addCheckbox(activityTasklist.get(i).getField(), activityTasklist.get(i).getId()));
            } else if (activityTasklist.get(i).getInputType().equalsIgnoreCase("TextBox")) {
                ll.addView(addEdittext(activityTasklist.get(i).getField(), activityTasklist.get(i).getId(), activityTasklist.get(i).getDataType()));
            } else if (activityTasklist.get(i).getInputType().equalsIgnoreCase("Label") || activityTasklist.get(i).getInputType().equalsIgnoreCase("Display")) {
                ll.addView(addTexView(activityTasklist.get(i).getField(), activityTasklist.get(i).getId()));
            } else if (activityTasklist.get(i).getInputType().equalsIgnoreCase("Dropdown") || activityTasklist.get(i).getInputType().equalsIgnoreCase("dropdown")) {
                ll.addView(addSpinner(activityTasklist.get(i).getId()));
            }

        }

        ll.addView(addButton("Submit", 1));
    }

    private boolean validate() {
        dataValue = new ArrayList<>();
        for (int i = 0; i < activityTasklist.size(); i++) {
            int id = activityTasklist.get(i).getId();
            if (activityTasklist.get(i).getInputType().equalsIgnoreCase("Check box")) {

                CheckBox chk = (CheckBox) findViewById(id);
                Log.d("TESTING", "IS CHECKED  " + chk.isChecked() + "");
                dataValue.add(new KeyValues(activityTasklist.get(i).getId(), chk.isChecked() + ""));

            }
            if (activityTasklist.get(i).getInputType().equalsIgnoreCase("TextBox")) {

                EditText et = findViewById(id);

                dataValue.add(new KeyValues(activityTasklist.get(i).getId(), et.getText().toString() + ""));

                if (activityTasklist.get(i).getIsOptional() == 0 && TextUtils.isEmpty(et.getText().toString())) {
                    //TOdo  need to check already exist or not

                    Toast.makeText(this, "Please Enter Proper Data", Toast.LENGTH_SHORT).show();
                    return false;
                }

            }
            if (activityTasklist.get(i).getInputType().equalsIgnoreCase("Dropdown") || activityTasklist.get(i).getInputType().equalsIgnoreCase("dropdown")) {

                Spinner spinnner = findViewById(id);

                if (spinnner.getSelectedItemPosition() == 0) {
                    //TOdo  need to check already exist or not

                    Toast.makeText(this, "Please Select Dropdown Data", Toast.LENGTH_SHORT).show();
                    return false;
                }

            }


        }
        return true;
    }

    public Spinner addSpinner(int id) {
        Spinner sp = new Spinner(this);
        typeofLabourdatamap = dataAccessHandler.getPairData(Queries.getInstance().getTypeofLabourQuery());

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(ActivityTask.this, android.R.layout.simple_spinner_item, CommonUtils.arrayFromPair(typeofLabourdatamap, "Type of Labour"));
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(spinnerArrayAdapter);

        sp.setId(id);
        //sp.setAdapter(new SpinnerTypeArrayAdapter(this,datatoseed));
        return sp;

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public CheckBox addCheckbox(String content, int id) {
        CheckBox cb = new CheckBox(this);
        cb.setText(content);
        cb.setId(id);
        return cb;
    }

    public CheckBox isJoneDoneChecbox(String content, int id) {
        CheckBox cb = new CheckBox(this);
        cb.setText(content);
        cb.setId(id);
        return cb;
    }


    public EditText addEdittext(String content, int id,String dataType) {
        EditText et = new EditText(this);
        et.setHint(content);
        et.setId(id);
        et.setMinLines(1);
        et.setMaxLines(1);

        if(dataType.equalsIgnoreCase("Integer")  || dataType.equalsIgnoreCase("Float"))
        {
            et.setInputType(InputType.TYPE_CLASS_NUMBER);

        }

        return et;

    }

    public TextView addTexView(String content, int id) {
        TextView tv = new TextView(this);
        tv.setId(id);
        tv.setText(content);
        return tv;

    }

    public Button addButton(String content, int id) {
        Button btn = new Button(this);
        btn.setText(content);
        btn.setBackgroundColor(getResources().getColor(R.color.green_dark));

        btn.setId(id);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    Bundle extras = getIntent().getExtras();
                    if (SCREEN_FROM == CommonConstants.FROM_MUTIPLE_ENTRY_EDITDATA) {
                        Log.d(ActivityTask.class.getSimpleName(), " ===> Analysis  ==> SCREEN CAME FROM :FROM_MUTIPLE_ENTRY_EDITDATA");
                        // SCREEN CAME FROM UPDATE CURRENT SCREEN
                        String intentTransactionId = extras.getString("transactionId");
                        String consignmentcode = extras.getString("consignmentcode");
                        String ActivityTypeId = extras.getString("ActivityTypeId");

                        int statusTypeId ;
                        if (isjobDoneId != 0) {
                            CheckBox chk = findViewById(isjobDoneId);
                            if (chk.isChecked()) {
                                statusTypeId = 346;
                            } else {
                                statusTypeId  = 352;
                            }
                        } else {
                            statusTypeId = 346;
                        }
                        Log.d(ActivityTask.class.getSimpleName(),"==> Analysis => FROM CHECKBOX  STATUS TYPEID : "+statusTypeId);
                        updateSingleEntryData(consignmentcode, ActivityTypeId, intentTransactionId,statusTypeId);

                    } else if (SCREEN_FROM == CommonConstants.FROM_MULTIPLE_ADD_NEW_TASK) {
                        Log.d(ActivityTask.class.getSimpleName(), " ===> Analysis  ==> SCREEN CAME FROM :FROM_MULTIPLE_ADD_NEW_TASK");
                        String activityTypeId = extras.getString("ActivityTypeId");
                        String consignmentcode = extras.getString("consignmentcode");
                        boolean Ismultipleentry = extras.getBoolean("Ismultipleentry");
                        int statusTypeId ;
                        if (isjobDoneId != 0) {
                            CheckBox chk = findViewById(isjobDoneId);
                            if (chk.isChecked()) {
                                statusTypeId = 346;
                            } else {
                                statusTypeId  = 352;
                            }
                        } else {
                            statusTypeId = 346;
                        }
                        String transactionIdNew = "T" + CommonConstants.TAB_ID + consignmentcode + activityTypeId + "-" + (dataAccessHandler.getOnlyOneIntValueFromDb(Queries.getInstance().getSaplingActivityMaxNumber()) + 1);
                        Log.d(ActivityTask.class.getSimpleName(), "==> Analysis   New Transaction ID :" + transactionIdNew);
                        addNewSingleEntryActivity(consignmentcode, activityTypeId, statusTypeId, transactionIdNew, true);

                    } else if (SCREEN_FROM == CommonConstants.FROM_SINGLE_ENTRY_EDITDATA) {
                        Log.d(ActivityTask.class.getSimpleName(), " ===> Analysis  ==> SCREEN CAME FROM :FROM_SINGLE_ENTRY_EDITDATA");
                        String consignmentcode = extras.getString("consignmentcode");
                        String activityTypeId = extras.getString("ActivityTypeId");
                        String multipleentry = extras.getString("multipleEntry");

                        int statusTypeId ;
                        if (isjobDoneId != 0) {
                            CheckBox chk = findViewById(isjobDoneId);
                            if (chk.isChecked()) {
                                statusTypeId = 346;
                            } else {
                                statusTypeId  = 352;
                            }
                        } else {
                            statusTypeId = 346;
                        }
                        Log.d(ActivityTask.class.getSimpleName(),"==> Analysis => FROM CHECKBOX  STATUS TYPEID : "+statusTypeId);
                        String transactionId = dataAccessHandler.getSingleValue(Queries.getInstance().getTransactionIdUsingConsimentCode(consignmentcode, activityTypeId));
                        if (null != transactionId && !transactionId.isEmpty() && !TextUtils.isEmpty(transactionId)) {
                            updateSingleEntryData(consignmentcode, activityTypeId, transactionId,statusTypeId);
                        } else {
                            // TODO dont have any Existind data add new activity
                            Log.d(ActivityTask.class.getSimpleName(), "==> Analysis  ==> New Task Creation Started ");
                            String transactionIdNew = "T" + CommonConstants.TAB_ID + consignmentcode + activityTypeId + "-" + (dataAccessHandler.getOnlyOneIntValueFromDb(Queries.getInstance().getSaplingActivityMaxNumber()) + 1);
                            Log.d(ActivityTask.class.getSimpleName(), "==> Analysis   New Transaction ID :" + transactionIdNew);

                            addNewSingleEntryActivity(consignmentcode, activityTypeId, statusTypeId, transactionIdNew,false);
                        }


                    }
//                    setSaplingActivity();
//                    finish();
//                    Toast.makeText(ActivityTask.this, "Task Completed Successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return btn;

    }

    private void updateSingleEntryData(String _consignmentcode, String _activityTypeId, String _transactionId, int _statusTypeId) {
        displayData = dataAccessHandler.getdisplayDetails(Queries.getInstance().getDisplayData(_transactionId));
        Log.d(ActivityTask.class.getSimpleName(), "==> Analysis Count Of DisplayData :" + displayData.size());
        if (displayData != null && displayData.size() > 0) {


            for (int j = 0; j < dataValue.size(); j++) {

                final List<LinkedHashMap> listKeyUpdate = new ArrayList<>();
                LinkedHashMap updateXref = new LinkedHashMap();
                // map2.put("Id", 0);
                updateXref.put("TransactionId", _transactionId);
                updateXref.put("FieldId", dataValue.get(j).id);
                updateXref.put("Value", dataValue.get(j).value);
                updateXref.put("FilePath", "");
                updateXref.put("IsActive", 1);
//                updateXref.put("CreatedByUserId", CommonConstants.USER_ID);
                updateXref.put("CreatedDate", CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
                updateXref.put("UpdatedByUserId", CommonConstants.USER_ID);
                updateXref.put("UpdatedDate", CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
                updateXref.put("ServerUpdatedStatus", 0);

                listKeyUpdate.add(updateXref);

                dataAccessHandler.updateData("SaplingActivityXref", listKeyUpdate, true, " where TransactionId = " + "'" + _transactionId + "'" + " AND FieldId = " + dataValue.get(j).getId(), new ApplicationThread.OnComplete<String>() {
                    @Override
                    public void execute(boolean success, String result, String msg) {
                        Log.d(ActivityTask.class.getSimpleName(), "==> Analysis   => Update of SaplingXref Done");
                        // Update Sapling Activity status


                    }
                });

            }

            LinkedHashMap activityMap = new LinkedHashMap();
            activityMap.put("TransactionId", _transactionId);
            activityMap.put("ConsignmentCode", _consignmentcode);
            activityMap.put("ActivityId", _activityTypeId);
            activityMap.put("StatusTypeId", _statusTypeId);
//            activityMap.put("Comment", "");
            activityMap.put("IsActive", 1);
//            activityMap.put("CreatedByUserId", CommonConstants.USER_ID);
            activityMap.put("CreatedDate", CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
            activityMap.put("UpdatedByUserId", CommonConstants.USER_ID);
            activityMap.put("UpdatedDate", CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
            activityMap.put("ServerUpdatedStatus", 0);
            final List<LinkedHashMap> activityList = new ArrayList<>();
            activityList.add(activityMap);

            dataAccessHandler.updateData("SaplingActivity", activityList, true, " where TransactionId = " + "'" + _transactionId + "'", new ApplicationThread.OnComplete<String>() {
                @Override
                public void execute(boolean success, String result, String msg) {
                    Log.d(ActivityTask.class.getSimpleName(), "==> Analysis   => Update of SaplingActivity");

                    LinkedHashMap status = new LinkedHashMap();

                    status.put("ConsignmentCode", _consignmentcode);
                    status.put("ActivityId", _activityTypeId);
                    status.put("StatusTypeId", _statusTypeId);
                    status.put("CreatedByUserId", CommonConstants.USER_ID);
                    status.put("CreatedDate", CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
                    status.put("UpdatedByUserId", CommonConstants.USER_ID);
                    status.put("UpdatedDate", CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
                    status.put("ServerUpdatedStatus", 0);

                    final List<LinkedHashMap> statusList = new ArrayList<>();
                    statusList.add(status);
                    dataAccessHandler.updateData("SaplingActivityStatus", statusList, true, " where ConsignmentCode = " + "'" + _consignmentcode + "' AND ActivityId ='" + _activityTypeId + "'", new ApplicationThread.OnComplete<String>() {
                        @Override
                        public void execute(boolean success, String result, String msg) {
                            Log.d(ActivityTask.class.getSimpleName(), "==>  Analysis ==> SaplingActivityStatus INSERT COMPLETED");
                            Log.d(ActivityTask.class.getSimpleName(), "==>  Analysis ==> Update Task Completed");
                            finish();
                            Toast.makeText(ActivityTask.this, "Data Saved Successfully", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });

        }
    }


}


class KeyValues {
    int id;
    String value;

    public KeyValues(int id, String value) {
        this.id = id;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}