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

import com.google.android.material.textfield.TextInputLayout;
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

public class ActivityTask extends AppCompatActivity implements View.OnClickListener {

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

    ActivityTasks showHideActivity;
    CheckBox chkShowHide;
    int yesnoCHeckbox = -10;

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


        CheckMantoryItem();
        if (SCREEN_FROM == CommonConstants.FROM_MUTIPLE_ENTRY_EDITDATA) {
            Log.d(ActivityTask.class.getSimpleName(), " ===> Analysis  ==> SCREEN CAME FROM :FROM_MUTIPLE_ENTRY_EDITDATA");
            // SCREEN CAME FROM UPDATE CURRENT SCREEN
            String consignmentcode = extras.getString("consignmentcode");
            String intentTransactionId = extras.getString("transactionId");
            boolean enableEditing = extras.getBoolean("enableEditing");
            Log.d(ActivityTask.class.getSimpleName(), " ===> Analysis  ==> FROM_MUTIPLE_ENTRY_EDITDATA  ###### transaction Id :" + intentTransactionId);
            Log.d(ActivityTask.class.getSimpleName(), " ===> Analysis  ==> FROM_MUTIPLE_ENTRY_EDITDATA  ###### enableEditing :" + enableEditing);
            bindExistingData(intentTransactionId);
            int buttonid = 1;
//            Button btn = (Button) findViewById(buttonid);
//            if (enableEditing)
//                btn.setVisibility(View.VISIBLE);
//            else
//                btn.setVisibility(View.GONE);
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
            sapling.put("StatusTypeId", 346);  // TODO CHECK DB
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

            if (isFromMultipleEntry) {
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

            } else {
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
                    ll.addView(addCheckbox
                            (activityTasklist.get(i).getField(), activityTasklist.get(i).getId()));
                } else {
                    ll.addView(addCheckbox(activityTasklist.get(i).getField(), activityTasklist.get(i).getId()));
                }
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

                dataValue.add(new KeyValues(activityTasklist.get(i).getId(), et.getText() + ""));

                if (et.getVisibility() == View.VISIBLE && TextUtils.isEmpty(et.getText().toString())) {
                    //TOdo  need to check already exist or not

                    Toast.makeText(this, "Please Enter"+activityTasklist.get(i).getField(), Toast.LENGTH_SHORT).show();
                    return false;
                }

            }
            if (activityTasklist.get(i).getInputType().equalsIgnoreCase("Dropdown") || activityTasklist.get(i).getInputType().equalsIgnoreCase("dropdown")) {

                Spinner spinnner = findViewById(id);
                int selectedPo = spinnner.getSelectedItemPosition();
                dataValue.add(new KeyValues(activityTasklist.get(i).getId(), spinnner.getSelectedItem().toString()));
                Log.d(ActivityTask.class.getSimpleName(), "DropDownn Selected String :" + spinnner.getSelectedItem().toString());
                if (spinnner.getSelectedItemPosition() == 0) {
                    //TOdo  need to check already exist or not

                    Toast.makeText(this, "Please Select "+activityTasklist.get(i).getField(), Toast.LENGTH_SHORT).show();
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

        cb.setOnClickListener(this::onClick);
        if (content.contains("Requried?")) {
            yesnoCHeckbox = id;
            cb.setChecked(true);
            Log.d(ActivityTask.class.getSimpleName(), "===> Analysis YES NO CHK  ID:" + yesnoCHeckbox);

        }
        return cb;
    }

    public CheckBox yesNoChekcbox(String content, int id) {
        chkShowHide = new CheckBox(this);
        chkShowHide.setText(content);
        chkShowHide.setId(id);
        chkShowHide.setSelected(true);


        return chkShowHide;
    }

    public CheckBox isJoneDoneChecbox(String content, int id) {
        CheckBox cb = new CheckBox(this);
        cb.setText(content);
        cb.setId(id);
        return cb;
    }


    public TextInputLayout addEdittext(String content, int id, String dataType) {

        TextInputLayout textInputLayout = new TextInputLayout(this);
        textInputLayout.setId(id + 9000);
        EditText et = new EditText(this);
//        et.setHint(content);
        et.setId(id);
        et.setMinLines(1);
        et.setMaxLines(1);

        if (dataType.equalsIgnoreCase("Integer") || dataType.equalsIgnoreCase("Float")) {
            et.setInputType(InputType.TYPE_CLASS_NUMBER);

        }
        textInputLayout.setHint(content);
        textInputLayout.addView(et);


        return textInputLayout;

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
        btn.setOnClickListener(this::onClick);
        return btn;

    }

    private void saveData() {
        if (validate()) {

            Bundle extras = getIntent().getExtras();
            if (SCREEN_FROM == CommonConstants.FROM_MUTIPLE_ENTRY_EDITDATA) {
                Log.d(ActivityTask.class.getSimpleName(), " ===> Analysis  ==> SCREEN CAME FROM :FROM_MUTIPLE_ENTRY_EDITDATA");
                // SCREEN CAME FROM UPDATE CURRENT SCREEN
                String intentTransactionId = extras.getString("transactionId");
                String consignmentcode = extras.getString("consignmentcode");
                String ActivityTypeId = extras.getString("ActivityTypeId");

                int statusTypeId;
                if (isjobDoneId != 0) {
                    CheckBox chk = findViewById(isjobDoneId);
                    if (chk.isChecked()) {
                        statusTypeId = 346;
                    } else {
                        statusTypeId = 352;
                    }
                } else {
                    statusTypeId = 346;
                }
                Log.d(ActivityTask.class.getSimpleName(), "==> Analysis => FROM CHECKBOX  STATUS TYPEID : " + statusTypeId);
                updateSingleEntryData(consignmentcode, ActivityTypeId, intentTransactionId, statusTypeId);

            } else if (SCREEN_FROM == CommonConstants.FROM_MULTIPLE_ADD_NEW_TASK) {
                Log.d(ActivityTask.class.getSimpleName(), " ===> Analysis  ==> SCREEN CAME FROM :FROM_MULTIPLE_ADD_NEW_TASK");
                String activityTypeId = extras.getString("ActivityTypeId");
                String consignmentcode = extras.getString("consignmentcode");
                boolean Ismultipleentry = extras.getBoolean("Ismultipleentry");
                int statusTypeId;
                if (isjobDoneId != 0) {
                    CheckBox chk = findViewById(isjobDoneId);
                    if (chk.isChecked()) {
                        statusTypeId = 346;
                    } else {
                        statusTypeId = 352;
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

                int statusTypeId;
                if (isjobDoneId != 0) {
                    CheckBox chk = findViewById(isjobDoneId);
                    if (chk.isChecked()) {
                        statusTypeId = 346;
                    } else {
                        statusTypeId = 352;
                    }
                } else {
                    statusTypeId = 346;
                }
                Log.d(ActivityTask.class.getSimpleName(), "==> Analysis => FROM CHECKBOX  STATUS TYPEID : " + statusTypeId);
                String transactionId = dataAccessHandler.getSingleValue(Queries.getInstance().getTransactionIdUsingConsimentCode(consignmentcode, activityTypeId));
                if (null != transactionId && !transactionId.isEmpty() && !TextUtils.isEmpty(transactionId)) {
                    updateSingleEntryData(consignmentcode, activityTypeId, transactionId, statusTypeId);
                } else {
                    // TODO dont have any Existind data add new activity
                    Log.d(ActivityTask.class.getSimpleName(), "==> Analysis  ==> New Task Creation Started ");
                    String transactionIdNew = "T" + CommonConstants.TAB_ID + consignmentcode + activityTypeId + "-" + (dataAccessHandler.getOnlyOneIntValueFromDb(Queries.getInstance().getSaplingActivityMaxNumber()) + 1);
                    Log.d(ActivityTask.class.getSimpleName(), "==> Analysis   New Transaction ID :" + transactionIdNew);

                    addNewSingleEntryActivity(consignmentcode, activityTypeId, statusTypeId, transactionIdNew, false);
                }


            }
//                    setSaplingActivity();
//                    finish();
//                    Toast.makeText(ActivityTask.this, "Task Completed Successfully", Toast.LENGTH_SHORT).show();
        }
    }

    private void CheckMantoryItem() {
        for (int i = 0; i < activityTasklist.size(); i++) {


            if (activityTasklist.get(i).getActivityTypeId() == 26 || activityTasklist.get(i).getActivityTypeId() == 27 || activityTasklist.get(i).getActivityTypeId() == 93 ||
                    activityTasklist.get(i).getActivityTypeId() == 37 || activityTasklist.get(i).getActivityTypeId() == 38 || activityTasklist.get(i).getActivityTypeId() == 39 ||
                    activityTasklist.get(i).getActivityTypeId() == 40 || activityTasklist.get(i).getActivityTypeId() == 41 || activityTasklist.get(i).getActivityTypeId() == 16 ||
                    activityTasklist.get(i).getActivityTypeId() == 17 || activityTasklist.get(i).getActivityTypeId() == 18 || activityTasklist.get(i).getActivityTypeId() == 19 ||
                    activityTasklist.get(i).getActivityTypeId() == 124 || activityTasklist.get(i).getActivityTypeId() == 125 || activityTasklist.get(i).getActivityTypeId() == 130 ||
                    activityTasklist.get(i).getActivityTypeId() == 131 || activityTasklist.get(i).getActivityTypeId() == 132 || activityTasklist.get(i).getActivityTypeId() == 133 ||
                    activityTasklist.get(i).getActivityTypeId() == 134 || activityTasklist.get(i).getActivityTypeId() == 139 || activityTasklist.get(i).getActivityTypeId() == 140 ||
                    activityTasklist.get(i).getActivityTypeId() == 141 || activityTasklist.get(i).getActivityTypeId() == 142 || activityTasklist.get(i).getActivityTypeId() == 143 ||
                    activityTasklist.get(i).getActivityTypeId() == 163 || activityTasklist.get(i).getActivityTypeId() == 288 || activityTasklist.get(i).getActivityTypeId() == 289 ||
                    activityTasklist.get(i).getActivityTypeId() == 296 || activityTasklist.get(i).getActivityTypeId() == 21 || activityTasklist.get(i).getActivityTypeId() == 28 || activityTasklist.get(i).getActivityTypeId() == 33 ||
                    activityTasklist.get(i).getActivityTypeId() == 45 || activityTasklist.get(i).getActivityTypeId() == 49 || activityTasklist.get(i).getActivityTypeId() == 55 ||
                    activityTasklist.get(i).getActivityTypeId() == 59 || activityTasklist.get(i).getActivityTypeId() == 71 || activityTasklist.get(i).getActivityTypeId() == 76 ||
                    activityTasklist.get(i).getActivityTypeId() == 82 || activityTasklist.get(i).getActivityTypeId() == 86 || activityTasklist.get(i).getActivityTypeId() == 100 ||
                    activityTasklist.get(i).getActivityTypeId() == 104 || activityTasklist.get(i).getActivityTypeId() == 109 || activityTasklist.get(i).getActivityTypeId() == 113 ||
                    activityTasklist.get(i).getActivityTypeId() == 117 || activityTasklist.get(i).getActivityTypeId() == 22 || activityTasklist.get(i).getActivityTypeId() == 29 ||
                    activityTasklist.get(i).getActivityTypeId() == 34 || activityTasklist.get(i).getActivityTypeId() == 46 || activityTasklist.get(i).getActivityTypeId() == 50 ||
                    activityTasklist.get(i).getActivityTypeId() == 56 || activityTasklist.get(i).getActivityTypeId() == 60 || activityTasklist.get(i).getActivityTypeId() == 72 ||
                    activityTasklist.get(i).getActivityTypeId() == 77 || activityTasklist.get(i).getActivityTypeId() == 83 || activityTasklist.get(i).getActivityTypeId() == 87 || activityTasklist.get(i).getActivityTypeId() == 101 ||
                    activityTasklist.get(i).getActivityTypeId() == 105 || activityTasklist.get(i).getActivityTypeId() == 110 || activityTasklist.get(i).getActivityTypeId() == 114 ||
                    activityTasklist.get(i).getActivityTypeId() == 118 || activityTasklist.get(i).getActivityTypeId() == 23 || activityTasklist.get(i).getActivityTypeId() == 30 ||
                    activityTasklist.get(i).getActivityTypeId() == 35 || activityTasklist.get(i).getActivityTypeId() == 47 || activityTasklist.get(i).getActivityTypeId() == 51 ||
                    activityTasklist.get(i).getActivityTypeId() == 57 || activityTasklist.get(i).getActivityTypeId() == 61 || activityTasklist.get(i).getActivityTypeId() == 73 ||
                    activityTasklist.get(i).getActivityTypeId() == 78 || activityTasklist.get(i).getActivityTypeId() == 84 || activityTasklist.get(i).getActivityTypeId() == 88 ||
                    activityTasklist.get(i).getActivityTypeId() == 102 || activityTasklist.get(i).getActivityTypeId() == 106 || activityTasklist.get(i).getActivityTypeId() == 111 ||
                    activityTasklist.get(i).getActivityTypeId() == 115 || activityTasklist.get(i).getActivityTypeId() == 119 || activityTasklist.get(i).getActivityTypeId() == 24 ||
                    activityTasklist.get(i).getActivityTypeId() == 31 || activityTasklist.get(i).getActivityTypeId() == 36 || activityTasklist.get(i).getActivityTypeId() == 48 ||
                    activityTasklist.get(i).getActivityTypeId() == 52 || activityTasklist.get(i).getActivityTypeId() == 58 || activityTasklist.get(i).getActivityTypeId() == 62 ||
                    activityTasklist.get(i).getActivityTypeId() == 74 || activityTasklist.get(i).getActivityTypeId() == 79 || activityTasklist.get(i).getActivityTypeId() == 85 || activityTasklist.get(i).getActivityTypeId() == 89 ||
                    activityTasklist.get(i).getActivityTypeId() == 103 || activityTasklist.get(i).getActivityTypeId() == 107 || activityTasklist.get(i).getActivityTypeId() == 112 ||
                    activityTasklist.get(i).getActivityTypeId() == 116 || activityTasklist.get(i).getActivityTypeId() == 120 || activityTasklist.get(i).getActivityTypeId() == 151 ||
                    activityTasklist.get(i).getActivityTypeId() == 166 || activityTasklist.get(i).getActivityTypeId() == 178 || activityTasklist.get(i).getActivityTypeId() == 191 ||
                    activityTasklist.get(i).getActivityTypeId() == 203 || activityTasklist.get(i).getActivityTypeId() == 215 || activityTasklist.get(i).getActivityTypeId() == 228 ||
                    activityTasklist.get(i).getActivityTypeId() == 242 || activityTasklist.get(i).getActivityTypeId() == 255 || activityTasklist.get(i).getActivityTypeId() == 270 ||
                    activityTasklist.get(i).getActivityTypeId() == 152 || activityTasklist.get(i).getActivityTypeId() == 167 || activityTasklist.get(i).getActivityTypeId() == 179 ||
                    activityTasklist.get(i).getActivityTypeId() == 192 || activityTasklist.get(i).getActivityTypeId() == 204 || activityTasklist.get(i).getActivityTypeId() == 216 ||
                    activityTasklist.get(i).getActivityTypeId() == 229 || activityTasklist.get(i).getActivityTypeId() == 243 || activityTasklist.get(i).getActivityTypeId() == 256 ||
                    activityTasklist.get(i).getActivityTypeId() == 271 || activityTasklist.get(i).getActivityTypeId() == 154 || activityTasklist.get(i).getActivityTypeId() == 169 ||
                    activityTasklist.get(i).getActivityTypeId() == 181 || activityTasklist.get(i).getActivityTypeId() == 194 || activityTasklist.get(i).getActivityTypeId() == 206 ||
                    activityTasklist.get(i).getActivityTypeId() == 218 || activityTasklist.get(i).getActivityTypeId() == 231 || activityTasklist.get(i).getActivityTypeId() == 191 ||
                    activityTasklist.get(i).getActivityTypeId() == 203 || activityTasklist.get(i).getActivityTypeId() == 215 || activityTasklist.get(i).getActivityTypeId() == 245 ||
                    activityTasklist.get(i).getActivityTypeId() == 258 || activityTasklist.get(i).getActivityTypeId() == 273 || activityTasklist.get(i).getActivityTypeId() == 155 ||
                    activityTasklist.get(i).getActivityTypeId() == 170 || activityTasklist.get(i).getActivityTypeId() == 182 || activityTasklist.get(i).getActivityTypeId() == 195 ||
                    activityTasklist.get(i).getActivityTypeId() == 207 || activityTasklist.get(i).getActivityTypeId() == 219 || activityTasklist.get(i).getActivityTypeId() == 232 ||
                    activityTasklist.get(i).getActivityTypeId() == 246 || activityTasklist.get(i).getActivityTypeId() == 259 || activityTasklist.get(i).getActivityTypeId() == 274 ||
                    activityTasklist.get(i).getActivityTypeId() == 283 || activityTasklist.get(i).getActivityTypeId() == 297 || activityTasklist.get(i).getActivityTypeId() == 310 || activityTasklist.get(i).getActivityTypeId() == 324 ||
                    activityTasklist.get(i).getActivityTypeId() == 337 || activityTasklist.get(i).getActivityTypeId() == 284 || activityTasklist.get(i).getActivityTypeId() == 298 ||
                    activityTasklist.get(i).getActivityTypeId() == 311 || activityTasklist.get(i).getActivityTypeId() == 325 || activityTasklist.get(i).getActivityTypeId() == 338 ||
                    activityTasklist.get(i).getActivityTypeId() == 285 || activityTasklist.get(i).getActivityTypeId() == 299 || activityTasklist.get(i).getActivityTypeId() == 312 ||
                    activityTasklist.get(i).getActivityTypeId() == 326 || activityTasklist.get(i).getActivityTypeId() == 339 || activityTasklist.get(i).getActivityTypeId() == 286 ||
                    activityTasklist.get(i).getActivityTypeId() == 300 || activityTasklist.get(i).getActivityTypeId() == 313 || activityTasklist.get(i).getActivityTypeId() == 327 ||
                    activityTasklist.get(i).getActivityTypeId() == 340 || activityTasklist.get(i).getActivityTypeId() == 287 || activityTasklist.get(i).getActivityTypeId() == 301 || activityTasklist.get(i).getActivityTypeId() == 314 ||
                    activityTasklist.get(i).getActivityTypeId() == 328 || activityTasklist.get(i).getActivityTypeId() == 341 ||
                    activityTasklist.get(i).getActivityTypeId() == 153 || activityTasklist.get(i).getActivityTypeId() == 168 || activityTasklist.get(i).getActivityTypeId() == 180 || activityTasklist.get(i).getActivityTypeId() == 193 ||
                    activityTasklist.get(i).getActivityTypeId() == 205 || activityTasklist.get(i).getActivityTypeId() == 217 || activityTasklist.get(i).getActivityTypeId() == 230 ||
                    activityTasklist.get(i).getActivityTypeId() == 244 || activityTasklist.get(i).getActivityTypeId() == 257 || activityTasklist.get(i).getActivityTypeId() == 272 ||
                    activityTasklist.get(i).getActivityTypeId() == 43 || activityTasklist.get(i).getActivityTypeId() == 53 || activityTasklist.get(i).getActivityTypeId() == 69 || activityTasklist.get(i).getActivityTypeId() == 80 ||
                    activityTasklist.get(i).getActivityTypeId() == 44 || activityTasklist.get(i).getActivityTypeId() == 54 || activityTasklist.get(i).getActivityTypeId() == 70 ||
                    activityTasklist.get(i).getActivityTypeId() == 81 || activityTasklist.get(i).getActivityTypeId() == 38 || activityTasklist.get(i).getActivityTypeId() == 39 ||
                    activityTasklist.get(i).getActivityTypeId() == 40 || activityTasklist.get(i).getActivityTypeId() == 41 || activityTasklist.get(i).getActivityTypeId() == 64 ||
                    activityTasklist.get(i).getActivityTypeId() == 65 || activityTasklist.get(i).getActivityTypeId() == 66 || activityTasklist.get(i).getActivityTypeId() == 67 ||
                    activityTasklist.get(i).getActivityTypeId() == 68 || activityTasklist.get(i).getActivityTypeId() == 95 || activityTasklist.get(i).getActivityTypeId() == 96 ||
                    activityTasklist.get(i).getActivityTypeId() == 97 || activityTasklist.get(i).getActivityTypeId() == 98 || activityTasklist.get(i).getActivityTypeId() == 99 ||
                    activityTasklist.get(i).getActivityTypeId() == 137 || activityTasklist.get(i).getActivityTypeId() == 149 || activityTasklist.get(i).getActivityTypeId() == 164 ||
                    activityTasklist.get(i).getActivityTypeId() == 176 || activityTasklist.get(i).getActivityTypeId() == 189 || activityTasklist.get(i).getActivityTypeId() == 201 || activityTasklist.get(i).getActivityTypeId() == 220 ||
                    activityTasklist.get(i).getActivityTypeId() == 233 || activityTasklist.get(i).getActivityTypeId() == 247 || activityTasklist.get(i).getActivityTypeId() == 260 || activityTasklist.get(i).getActivityTypeId() == 275 ||
                    activityTasklist.get(i).getActivityTypeId() == 138 || activityTasklist.get(i).getActivityTypeId() == 150 || activityTasklist.get(i).getActivityTypeId() == 165 || activityTasklist.get(i).getActivityTypeId() == 177 || activityTasklist.get(i).getActivityTypeId() == 190 || activityTasklist.get(i).getActivityTypeId() == 202 ||
                    activityTasklist.get(i).getActivityTypeId() == 221 || activityTasklist.get(i).getActivityTypeId() == 234 || activityTasklist.get(i).getActivityTypeId() == 248 ||
                    activityTasklist.get(i).getActivityTypeId() == 261 || activityTasklist.get(i).getActivityTypeId() == 276 ||
                    activityTasklist.get(i).getActivityTypeId() == 214 || activityTasklist.get(i).getActivityTypeId() == 241 || activityTasklist.get(i).getActivityTypeId() == 268 ||
                    activityTasklist.get(i).getActivityTypeId() == 144 || activityTasklist.get(i).getActivityTypeId() == 145 ||
                    activityTasklist.get(i).getActivityTypeId() == 146 || activityTasklist.get(i).getActivityTypeId() == 147 || activityTasklist.get(i).getActivityTypeId() == 148 ||
                    activityTasklist.get(i).getActivityTypeId() == 158 || activityTasklist.get(i).getActivityTypeId() == 159 || activityTasklist.get(i).getActivityTypeId() == 160 ||
                    activityTasklist.get(i).getActivityTypeId() == 161 || activityTasklist.get(i).getActivityTypeId() == 162 || activityTasklist.get(i).getActivityTypeId() == 171 ||
                    activityTasklist.get(i).getActivityTypeId() == 172 || activityTasklist.get(i).getActivityTypeId() == 173 || activityTasklist.get(i).getActivityTypeId() == 174 ||
                    activityTasklist.get(i).getActivityTypeId() == 175 || activityTasklist.get(i).getActivityTypeId() == 184 || activityTasklist.get(i).getActivityTypeId() == 185 ||
                    activityTasklist.get(i).getActivityTypeId() == 186 || activityTasklist.get(i).getActivityTypeId() == 187 || activityTasklist.get(i).getActivityTypeId() == 188 ||
                    activityTasklist.get(i).getActivityTypeId() == 196 || activityTasklist.get(i).getActivityTypeId() == 197 || activityTasklist.get(i).getActivityTypeId() == 198 ||
                    activityTasklist.get(i).getActivityTypeId() == 199 || activityTasklist.get(i).getActivityTypeId() == 200 || activityTasklist.get(i).getActivityTypeId() == 209 || activityTasklist.get(i).getActivityTypeId() == 210 ||
                    activityTasklist.get(i).getActivityTypeId() == 211 || activityTasklist.get(i).getActivityTypeId() == 212 || activityTasklist.get(i).getActivityTypeId() == 213 || activityTasklist.get(i).getActivityTypeId() == 223 || activityTasklist.get(i).getActivityTypeId() == 224 ||
                    activityTasklist.get(i).getActivityTypeId() == 225 || activityTasklist.get(i).getActivityTypeId() == 226 || activityTasklist.get(i).getActivityTypeId() == 227 ||
                    activityTasklist.get(i).getActivityTypeId() == 236 || activityTasklist.get(i).getActivityTypeId() == 237 || activityTasklist.get(i).getActivityTypeId() == 238 || activityTasklist.get(i).getActivityTypeId() == 239 ||
                    activityTasklist.get(i).getActivityTypeId() == 240 || activityTasklist.get(i).getActivityTypeId() == 250 || activityTasklist.get(i).getActivityTypeId() == 251 || activityTasklist.get(i).getActivityTypeId() == 252 || activityTasklist.get(i).getActivityTypeId() == 253 || activityTasklist.get(i).getActivityTypeId() == 254 || activityTasklist.get(i).getActivityTypeId() == 263 ||
                    activityTasklist.get(i).getActivityTypeId() == 264 || activityTasklist.get(i).getActivityTypeId() == 265 || activityTasklist.get(i).getActivityTypeId() == 266 || activityTasklist.get(i).getActivityTypeId() == 302 ||
                    activityTasklist.get(i).getActivityTypeId() == 315 || activityTasklist.get(i).getActivityTypeId() == 329 || activityTasklist.get(i).getActivityTypeId() == 342 ||
                    activityTasklist.get(i).getActivityTypeId() == 316 || activityTasklist.get(i).getActivityTypeId() == 330 || activityTasklist.get(i).getActivityTypeId() == 343 || activityTasklist.get(i).getActivityTypeId() == 323 ||
                    activityTasklist.get(i).getActivityTypeId() == 278 || activityTasklist.get(i).getActivityTypeId() == 279 || activityTasklist.get(i).getActivityTypeId() == 280 ||
                    activityTasklist.get(i).getActivityTypeId() == 281 || activityTasklist.get(i).getActivityTypeId() == 282 || activityTasklist.get(i).getActivityTypeId() == 291 || activityTasklist.get(i).getActivityTypeId() == 292 ||
                    activityTasklist.get(i).getActivityTypeId() == 293 || activityTasklist.get(i).getActivityTypeId() == 294 || activityTasklist.get(i).getActivityTypeId() == 295 ||
                    activityTasklist.get(i).getActivityTypeId() == 305 || activityTasklist.get(i).getActivityTypeId() == 306 || activityTasklist.get(i).getActivityTypeId() == 307 || activityTasklist.get(i).getActivityTypeId() == 308 ||
                    activityTasklist.get(i).getActivityTypeId() == 309 || activityTasklist.get(i).getActivityTypeId() == 318 || activityTasklist.get(i).getActivityTypeId() == 319 || activityTasklist.get(i).getActivityTypeId() == 320 ||
                    activityTasklist.get(i).getActivityTypeId() == 321 || activityTasklist.get(i).getActivityTypeId() == 322 || activityTasklist.get(i).getActivityTypeId() == 332 ||
                    activityTasklist.get(i).getActivityTypeId() == 333 || activityTasklist.get(i).getActivityTypeId() == 334 || activityTasklist.get(i).getActivityTypeId() == 335 || activityTasklist.get(i).getActivityTypeId() == 336) {

                showHideActivity = activityTasklist.get(i);


                // if True We will get id
            }

        }

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
            activityMap.put("StatusTypeId", 346);  // TODO Check with In DB
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


    @Override
    public void onClick(View view) {
        int btnid = 1;
        if (view.getId() == btnid) {

            saveData();

        } else if (view.getId() == yesnoCHeckbox) {
//            if (yesnoCHeckbox > 0) {
//
//                if (((CheckBox) view).isChecked()) {
//                    for (ActivityTasks widget : activityTasklist) {
//                        findViewById(widget.getId()).setVisibility(View.VISIBLE);
//                        try {
//                            findViewById(widget.getId() + 9000).setVisibility(View.VISIBLE);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
////                    Toast.makeText(ActivityTask.this, "CHECKED", Toast.LENGTH_SHORT).show();
//                } else {
//                    // Need to disble remainign widgets
//                    for (ActivityTasks widget : activityTasklist) {
//                        String optional = dataAccessHandler.getSingleValueInt(Queries.getIsoptionalField(widget.getId()));
//                        Log.d(ActivityTask.class.getSimpleName(), "===> analysis ==> isOptional :" + optional);
//                        if (optional != null && !StringUtils.isEmpty(optional)) {
//                            findViewById(widget.getId()).setVisibility(View.GONE);
//                            findViewById(widget.getId() + 9000).setVisibility(View.GONE);
//                        }
//                    }
////                    Toast.makeText(ActivityTask.this, "UN-CHECKED", Toast.LENGTH_SHORT).show();
//                }
//            }
//
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