package com.oilpalm3f.nursery.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import android.view.Display;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.oilpalm3f.nursery.R;
import com.oilpalm3f.nursery.cloudhelper.ApplicationThread;
import com.oilpalm3f.nursery.cloudhelper.Log;
import com.oilpalm3f.nursery.common.CommonConstants;
import com.oilpalm3f.nursery.common.CommonUtils;
import com.oilpalm3f.nursery.database.DataAccessHandler;
import com.oilpalm3f.nursery.database.Queries;
import com.oilpalm3f.nursery.datasync.helpers.DataManager;
import com.oilpalm3f.nursery.dbmodels.ActivityTasks;
import com.oilpalm3f.nursery.dbmodels.DisplayData;
import com.oilpalm3f.nursery.dbmodels.ExistingData;
import com.oilpalm3f.nursery.dbmodels.LandlevellingFields;
import com.oilpalm3f.nursery.dbmodels.NurseryAcitivity;
import com.oilpalm3f.nursery.dbmodels.SaplingActivity;
import com.oilpalm3f.nursery.dbmodels.TypeItem;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class ActivityTask extends AppCompatActivity {

    String activityTypeId, consignmentCode, activityName, isMultipleentry, transactionId;
    private List<ActivityTasks> activityTasklist = new ArrayList<>();
    private DataAccessHandler dataAccessHandler;
    LinkedHashMap<String, Pair> typeofLabourdatamap = null;
    Boolean isSingleentry = false;

    private List<SaplingActivity> saplingActivitiesList = new ArrayList<>();
    int SaplingActivityCount;
   List<KeyValues>  dataValue = new ArrayList<>();
    int random_int  = 0;
    int maxnumber;
    TextView textView5;
    String TransactionID;
    int sapactivitysize,sapactivitysizeinc;
    private List<ExistingData> existingData = new ArrayList<>();
    private List<DisplayData> displayData = new ArrayList<>();
    boolean isUpdate  = false;

    int isjobDoneId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        dataAccessHandler = new DataAccessHandler(this);



        LinearLayout ll = (LinearLayout)findViewById(R.id.linearLayout2);
        textView5 = findViewById(R.id.textView5);

        int min = 50;
        int max = 100;

        //Generate random int value from 50 to 100
        System.out.println("Random value in int from "+min+" to "+max+ ":");
         random_int = (int)Math.floor(Math.random()*(max-min+1)+min);
        System.out.println(random_int);



        maxnumber = dataAccessHandler.getOnlyOneIntValueFromDb(Queries.getInstance().getSaplingActivityMaxNumber());
        Log.d("maxnumber", maxnumber+ "");


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            isSingleentry = extras.getBoolean("isSingleEntry");
            transactionId = extras.getString("transactionId");
            activityTypeId = extras.getString("ActivityTypeId");
            activityName = extras.getString("ActivityName");
            isMultipleentry = extras.getString("Ismultipleentry");
            consignmentCode = extras.getString("consignmentcode");
            Log.d("ActivityTaskisSingleentry", isSingleentry + "");
            Log.d("ActivityTasktransactionId", transactionId);
            Log.d("ActivityTaskActivityTypeId123", activityTypeId + "");
            Log.d("ActivityTaskActivityName", activityName + "");
            Log.d("ActivityTaskconsignmentCode", consignmentCode);
            Log.d("ActivityTaskIsmultipleentryy", isMultipleentry+ "");
        }


//        existingData = dataAccessHandler.getexistingDetails(Queries.getInstance().getExistingData(CommonConstants.ConsignmentCode,activityTypeId));
//        Log.d("existingData", existingData.size() + "");
//        Log.d("existingDataField", existingData.get(0).getField());
//        Log.d("existingDataValue", existingData.get(0).getValue());

       // displayData = dataAccessHandler.getdisplayDetails(Queries.getInstance().getDisplayData(CommonConstants.ConsignmentCode,CommonConstants.ActivityTypeId + ""));

//        Log.d("displayData", displayData.size() + "");
//        Log.d("displayDataField", displayData.get(0).getInputType());
//        Log.d("displayDataValue", displayData.get(0).getValue());

        textView5.setText(activityName);

       // consignmentCode = CommonConstants.ConsignmentCode;

//        Log.d("ActivityTypeId456", activityTypeId + "");
//        Log.d("consignmentCode234", consignmentCode + "");

        saplingActivitiesList = dataAccessHandler.getSaplingActivityData(Queries.getInstance().getSaplingActivityCountQuery());

        Log.d("SaplingActivityCount", saplingActivitiesList.size()+"");

        sapactivitysize = saplingActivitiesList.size();
        sapactivitysizeinc = sapactivitysize +1;

//        Log.d("TABID", CommonConstants.TAB_ID+ "");
//        Log.d("ConsignmentID", CommonConstants.ConsignmentID+ "");
//        Log.d("sapactivitysize", sapactivitysize+ "");
//        Log.d("sapactivitysizeinc", sapactivitysizeinc+ "");


        TransactionID = "T"+CommonConstants.TAB_ID+consignmentCode+activityTypeId+"-"+(dataAccessHandler.getOnlyOneIntValueFromDb(Queries.getInstance().getSaplingActivityMaxNumber())+1);
        Log.d("TransactionIDddd", TransactionID);

        activityTasklist = dataAccessHandler.getActivityTasksDetails(Queries.getInstance().getActivityTaskDetails(Integer.parseInt(activityTypeId)));

//        Log.d("activityTasklist", activityTasklist.size() + "");
//        Log.d("activityTasklistISOPtional", activityTasklist.get(0).getIsOptional()+"");

  for(int i = 0 ; i < activityTasklist.size();i ++){
      if(activityTasklist.get(i).getInputType().equalsIgnoreCase("Check box")){
          if(activityTasklist.get(i).getField().equalsIgnoreCase("Is the activity completed"))
          {
            isjobDoneId =  activityTasklist.get(i).getId();
          }
          ll.addView( addCheckbox(activityTasklist.get(i).getField(),activityTasklist.get(i).getId()));
      }else  if(activityTasklist.get(i).getInputType().equalsIgnoreCase("TextBox")){
          ll.addView( addEdittext(activityTasklist.get(i).getField(),activityTasklist.get(i).getId()));
      }else  if(activityTasklist.get(i).getInputType().equalsIgnoreCase("Label") || activityTasklist.get(i).getInputType().equalsIgnoreCase("Display")){
          ll.addView( addTexView(activityTasklist.get(i).getField(),activityTasklist.get(i).getId()));
      }else  if(activityTasklist.get(i).getInputType().equalsIgnoreCase("Dropdown") || activityTasklist.get(i).getInputType().equalsIgnoreCase("dropdown")){
          ll.addView( addSpinner(activityTasklist.get(i).getId()));
      }

  }
//        if (isMultipleentry.equalsIgnoreCase("true")){
//
//        ll.addView( isJoneDoneChecbox("Is Job Done", 10001));
//        }

        ll.addView( addButton("Submit", 1));


        // TODO
        // Get Data feald id and value

        displayData = dataAccessHandler.getdisplayDetails(Queries.getInstance().getDisplayData(consignmentCode,activityTypeId));

        Log.d("displayData", displayData.size() + "");


        if(displayData  != null  && displayData.size() > 0){
            int id = 1;
           Button  btn = (Button) findViewById(id);

            if(displayData.get(0).getServerUpdatedStatus()  == 0)
            {
                btn.setEnabled(true);
            }else{
                btn.setEnabled(false);
            }

            for(int i = 0; i < displayData.size() ; i ++){
                if(displayData.get(i).getInputType().equalsIgnoreCase("Check box")){


                    CheckBox chk = (CheckBox)findViewById(displayData.get(i).getFieldId());
                    if(displayData.get(i).getValue().equalsIgnoreCase("true")){
                        chk.setChecked(true);
                    }else
                        chk.setChecked(false);
                }else  if(displayData.get(i).getInputType().equalsIgnoreCase("TextBox")){
                    EditText editText = (EditText)findViewById(displayData.get(i).getFieldId());
                    if(!TextUtils.isEmpty(displayData.get(i).getValue())){
                        editText.setText(displayData.get(i).getValue());
                    }else
                        editText.setText("");
                }else  if(displayData.get(i).getInputType().equalsIgnoreCase("Label") || displayData.get(i).getInputType().equalsIgnoreCase("Display")){
                    TextView textView = (TextView)findViewById(displayData.get(i).getFieldId());
                    if(!TextUtils.isEmpty(displayData.get(i).getValue())){
                        textView.setText(displayData.get(i).getValue());
                    }else
                        textView.setText("");
               }
                else  if(displayData.get(i).getInputType().equalsIgnoreCase("Dropdown") || displayData.get(i).getInputType().equalsIgnoreCase("dropdown")){
                      String value = displayData.get(i).getValue();
                      int  position =0;
                  String[] data =  CommonUtils.arrayFromPair(typeofLabourdatamap,"Type of Labour");
                      for(int j =0; j <data.length; j ++)
                      {
                          if(value.equalsIgnoreCase(data[j])){
                              position =j;
                          }
                      }
                        Spinner sp = (Spinner)findViewById(displayData.get(i).getFieldId());
                        sp.setSelection(position);

                    }

                    }

            }



    }
    public CheckBox addCheckbox(String content, int id){
                    CheckBox cb = new CheckBox(this);
            cb.setText(content);
            cb.setId(id);
return  cb;
    }
    public CheckBox isJoneDoneChecbox(String content, int id){
        CheckBox cb = new CheckBox(this);
        cb.setText(content);
        cb.setId(id);
        return  cb;
    }


    public EditText addEdittext(String content, int id)
    {
                EditText et = new EditText(this);
        et.setHint(content);
        et.setId(id);
        et.setMinLines(1);
        et.setMaxLines(1);
        return et;

    }

    public TextView addTexView(String content, int id)
    {
        TextView tv = new TextView(this);
        tv.setId(id);
        tv.setText(content);
        return  tv;

    }

    public Button addButton(String content, int id)
    {
        Button btn = new Button(this);
        btn.setText(content);
        btn.setId(id);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              if (validate()) {
                  setSaplingActivity();
                  finish();
                  Toast.makeText(ActivityTask.this, "Task Completed Successfully", Toast.LENGTH_SHORT).show();
              }
            }
        });
        return  btn;

    }

    private boolean validate() {
        dataValue  = new ArrayList<>();
        for(int i = 0; i <activityTasklist.size(); i ++ )
        {
            int id = activityTasklist.get(i).getId();
            if(activityTasklist.get(i).getInputType().equalsIgnoreCase("Check box")){

              CheckBox chk = (CheckBox)findViewById(id);
             Log.d("TESTING", "IS CHECKED  "+chk.isChecked()+"");
                dataValue.add(new KeyValues(activityTasklist.get(i).getId(),chk.isChecked()+""));
//             if (chk.isChecked() == false){
//                 //Todo  need to check already exist or not
//
//                 Toast.makeText(this, "Please Select the checkbox", Toast.LENGTH_SHORT).show();
//                 return false;
//             }

            }
            if(activityTasklist.get(i).getInputType().equalsIgnoreCase("TextBox")){

                EditText et = findViewById(id);

                dataValue.add(new KeyValues(activityTasklist.get(i).getId(),et.getText().toString()+""));

                if (activityTasklist.get(i).getIsOptional() == 0 && TextUtils.isEmpty(et.getText().toString())){
                    //TOdo  need to check already exist or not

                    Toast.makeText(this, "Please Enter Proper Data", Toast.LENGTH_SHORT).show();
                    return false;
                }

            }
            if(activityTasklist.get(i).getInputType().equalsIgnoreCase("Dropdown") || activityTasklist.get(i).getInputType().equalsIgnoreCase("dropdown")){

                Spinner spinnner = findViewById(id);

                if (spinnner.getSelectedItemPosition() ==0){
                    //TOdo  need to check already exist or not

                    Toast.makeText(this, "Please Select Dropdown Data", Toast.LENGTH_SHORT).show();
                    return false;
                }

            }


        }
        return true;
    }

    public Spinner addSpinner(int id)
    {
        Spinner sp = new Spinner(this);
        typeofLabourdatamap = dataAccessHandler.getPairData(Queries.getInstance().getTypeofLabourQuery());

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(ActivityTask.this, android.R.layout.simple_spinner_item, CommonUtils.arrayFromPair(typeofLabourdatamap, "Type of Labour"));
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(spinnerArrayAdapter);

//        ArrayList<TypeItem> datatoseed = new ArrayList<>();
//        datatoseed.add(new TypeItem(1,"Select Gender"));
//        datatoseed.add(new TypeItem(2,"Male"));
//        datatoseed.add(new TypeItem(3,"Female"));

        sp.setId(id);
        //sp.setAdapter(new SpinnerTypeArrayAdapter(this,datatoseed));
        return  sp;

    }

    private void setSaplingActivity() {

//        SaplingActivity saplingActivity = new SaplingActivity();
//
//        saplingActivity.setId(0);
//        saplingActivity.setTransactionId("");
//        saplingActivity.setConsignmentCode("");
//        saplingActivity.setActivityId(0);
//        saplingActivity.setStatusTypeId(0);
//        saplingActivity.setComment("");
//        saplingActivity.setIsActive(0);
//        saplingActivity.setCreatedByUserId(0);
//        saplingActivity.setCreatedDate("");
//        saplingActivity.setUpdatedByUserId(0);
//        saplingActivity.setUpdatedDate("");
//        saplingActivity.setServerUpdatedStatus(0);
//
//        saplingActivitiesList.add(saplingActivity);

   if(displayData != null  && displayData.size() > 0)
   {
       isUpdate  = true;

       TransactionID  = displayData.get(0).getTransactionId();
   }

        LinkedHashMap map = new LinkedHashMap();

       // map.put("Id", 0);
        map.put("TransactionId",  TransactionID);
        map.put("ConsignmentCode", consignmentCode+"");
        map.put("ActivityId", activityTypeId);

        if (isjobDoneId != 0) {


            CheckBox chk = findViewById(isjobDoneId);

            if (chk.isChecked()) {
                map.put("StatusTypeId", 346);
            }else{
                map.put("StatusTypeId", 352);
            }
        }else{
            map.put("StatusTypeId", 346);
        }

        map.put("Comment", "Test");
        map.put("IsActive", 1);
        map.put("CreatedByUserId", CommonConstants.USER_ID);
        map.put("CreatedDate", CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
        map.put("UpdatedByUserId", CommonConstants.USER_ID);
        map.put("UpdatedDate", CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
        map.put("ServerUpdatedStatus", 0);

        final List<LinkedHashMap> list = new ArrayList<>();

        list.add(map);


        LinkedHashMap map1 = new LinkedHashMap();

        map1.put("Id", 0);
        map1.put("ConsignmentCode", consignmentCode+"");
        map1.put("ActivityId", activityTypeId);
        // TOdo
        if (isjobDoneId != 0) {


            CheckBox chk = findViewById(isjobDoneId);

            if (chk.isChecked()) {
                map1.put("StatusTypeId", 346);
            }else{
                map1.put("StatusTypeId", 352);
            }
        }else{
            map1.put("StatusTypeId", 346);
        }

        map1.put("CreatedByUserId", CommonConstants.USER_ID);
        map1.put("CreatedDate", CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
        map1.put("UpdatedByUserId", CommonConstants.USER_ID);
        map1.put("UpdatedDate", CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
        map1.put("ServerUpdatedStatus", 0);

        final List<LinkedHashMap> list1 = new ArrayList<>();

        list1.add(map1);
        if(isUpdate)
        {

            for (int j =0; j<dataValue.size(); j ++){
                final List<LinkedHashMap> listKeyUpdate = new ArrayList<>();
                LinkedHashMap map2 = new LinkedHashMap();
               // map2.put("Id", 0);
                map2.put("TransactionId", TransactionID);
                map2.put("FieldId", dataValue.get(j).id);
                map2.put("Value", dataValue.get(j).value);
                map2.put("FilePath", "");
                map2.put("IsActive", 1);
                map2.put("CreatedByUserId", CommonConstants.USER_ID);
                map2.put("CreatedDate", CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
                map2.put("UpdatedByUserId", CommonConstants.USER_ID);
                map2.put("UpdatedDate", CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
                map2.put("ServerUpdatedStatus", 0);
Log.d(ActivityTask.class.getSimpleName(), "==> Analysis   KEY :"+dataValue.get(j).id+"   Value :"+dataValue.get(j).value);


                listKeyUpdate.add(map2);
                dataAccessHandler.updateData("SaplingActivityXref", listKeyUpdate,true," where TransactionId = " + "'"+TransactionID+"'"+" AND FieldId = "+dataValue.get(j).getId(), new ApplicationThread.OnComplete<String>() {
                    @Override
                    public void execute(boolean success, String result, String msg) {

                    }});

            }

            dataAccessHandler.insertMyDataa("SaplingActivityStatus", list1, new ApplicationThread.OnComplete<String>() {
                @Override
                public void execute(boolean success, String result, String msg) {}});

            if (isjobDoneId != 0 ){

                int id = 10001;
                CheckBox chk = findViewById(isjobDoneId);

                if(chk.isChecked()) {

                    dataAccessHandler.updateData("SaplingActivity", list,true," where TransactionId = " + "'"+TransactionID+"'", new ApplicationThread.OnComplete<String>() {
                        @Override
                        public void execute(boolean success, String result, String msg) {

                        }});
                }
            }

        }else{
            dataAccessHandler.insertMyDataa("SaplingActivityStatus", list1, new ApplicationThread.OnComplete<String>() {
                @Override
                public void execute(boolean success, String result, String msg) {

                    if (success) {
                        dataAccessHandler.insertMyDataa("SaplingActivity", list, new ApplicationThread.OnComplete<String>() {
                            @Override
                            public void execute(boolean success, String result, String msg) {

                                if (success) {
//                    saplingActivitiesList.get(0).setServerUpdatedStatus(0);
                                    final List<LinkedHashMap> listKey = new ArrayList<>();
                                    for (int j =0; j<dataValue.size(); j ++){

                                        LinkedHashMap map = new LinkedHashMap();
                                        map.put("Id", 0);
                                        map.put("TransactionId", TransactionID);
                                        map.put("FieldId", dataValue.get(j).id);
                                        map.put("Value", dataValue.get(j).value);
                                        map.put("FilePath", "");
                                        map.put("IsActive", 1);
                                        map.put("CreatedByUserId", CommonConstants.USER_ID);
                                        map.put("CreatedDate", CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
                                        map.put("UpdatedByUserId", CommonConstants.USER_ID);
                                        map.put("UpdatedDate", CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
                                        map.put("ServerUpdatedStatus", 0);

                                        listKey.add(map);

                                    }

                                    dataAccessHandler.insertMyDataa("SaplingActivityXref", listKey, new ApplicationThread.OnComplete<String>() {
                                        @Override
                                        public void execute(boolean success, String result, String msg) {

                                        }});

                                }

                            }
                        });
                    }

                }
            });




        }





        //  DataManager.getInstance().addData(DataManager.SAPLING_ACTIVITY, saplingActivitiesList);



    }

}
class  KeyValues{
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