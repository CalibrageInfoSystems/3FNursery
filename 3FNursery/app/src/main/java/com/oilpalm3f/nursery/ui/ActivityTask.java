package com.oilpalm3f.nursery.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
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
import com.oilpalm3f.nursery.dbmodels.NurseryAcitivity;
import com.oilpalm3f.nursery.dbmodels.SaplingActivity;
import com.oilpalm3f.nursery.dbmodels.TypeItem;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class ActivityTask extends AppCompatActivity {

    String activityTypeId;
    private List<ActivityTasks> activityTasklist = new ArrayList<>();
    private DataAccessHandler dataAccessHandler;
    private List<SaplingActivity> saplingActivitiesList;
   List<KeyValues>  dataValue = new ArrayList<>();
    int random_int  = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        LinearLayout ll = (LinearLayout)findViewById(R.id.linearLayout2);

        int min = 50;
        int max = 100;

        //Generate random int value from 50 to 100
        System.out.println("Random value in int from "+min+" to "+max+ ":");
         random_int = (int)Math.floor(Math.random()*(max-min+1)+min);
        System.out.println(random_int);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            activityTypeId = extras.getString("ActivityTypeId");
            Log.d("ActivityTypeId123", activityTypeId + "");
        }


        Log.d("ActivityTypeId456", activityTypeId + "");

        dataAccessHandler = new DataAccessHandler(this);

        activityTasklist = dataAccessHandler.getActivityTasksDetails(Queries.getInstance().getActivityTaskDetails(Integer.parseInt(activityTypeId)));

        Log.d("activityTasklist", activityTasklist.size() + "");

  for(int i = 0 ; i < activityTasklist.size();i ++){
      if(activityTasklist.get(i).getInputType().equalsIgnoreCase("Check box")){
          ll.addView( addCheckbox(activityTasklist.get(i).getField(),activityTasklist.get(i).getId()));
      }else  if(activityTasklist.get(i).getInputType().equalsIgnoreCase("TextBox")){
          ll.addView( addEdittext(activityTasklist.get(i).getField(),activityTasklist.get(i).getId()));
      }else  if(activityTasklist.get(i).getInputType().equalsIgnoreCase("Label") || activityTasklist.get(i).getInputType().equalsIgnoreCase("Display")){
          ll.addView( addTexView(activityTasklist.get(i).getField(),activityTasklist.get(i).getId()));
      }else  if(activityTasklist.get(i).getInputType().equalsIgnoreCase("Dropdown") || activityTasklist.get(i).getInputType().equalsIgnoreCase("dropdown")){
          ll.addView( addSpinner(activityTasklist.get(i).getId()));
      }

  }

        ll.addView( addButton("Submit", 1));

    }
    public CheckBox addCheckbox(String content, int id){
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
             if (chk.isChecked() == false){
                 //TOdo  need to check already exist or not

                 Toast.makeText(this, "Please Select the checkbox", Toast.LENGTH_SHORT).show();
                 return false;
             }

            }
        }
        return true;
    }

    public Spinner addSpinner(int id)
    {
        ArrayList<TypeItem> datatoseed = new ArrayList<>();
        datatoseed.add(new TypeItem(1,"Select Gender"));
        datatoseed.add(new TypeItem(2,"Male"));
        datatoseed.add(new TypeItem(3,"Female"));
        Spinner sp = new Spinner(this);
        sp.setId(id);
        sp.setAdapter(new SpinnerTypeArrayAdapter(this,datatoseed));
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



        LinkedHashMap map = new LinkedHashMap();

        map.put("Id", 0);
        map.put("TransactionId",  random_int+"");
        map.put("ConsignmentCode", "CONS001");
        map.put("ActivityId", activityTypeId);
        map.put("StatusTypeId", 346);
        map.put("Comment", "Test");
        map.put("IsActive", 1);
        map.put("CreatedByUserId", CommonConstants.USER_ID);
        map.put("CreatedDate", CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
        map.put("UpdatedByUserId", CommonConstants.USER_ID);
        map.put("UpdatedDate", CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
        map.put("ServerUpdatedStatus", 0);

        final List<LinkedHashMap> list = new ArrayList<>();

        list.add(map);


      //  DataManager.getInstance().addData(DataManager.SAPLING_ACTIVITY, saplingActivitiesList);

        dataAccessHandler.insertMyDataa("SaplingActivity", list, new ApplicationThread.OnComplete<String>() {
            @Override
            public void execute(boolean success, String result, String msg) {

                if (success) {
//                    saplingActivitiesList.get(0).setServerUpdatedStatus(0);
                    final List<LinkedHashMap> listKey = new ArrayList<>();
                    for (int j =0; j<dataValue.size(); j ++){

                        LinkedHashMap map = new LinkedHashMap();
                        map.put("Id", 0);
                        map.put("TransactionId", random_int+"");
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