package com.oilpalm3f.nursery.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.oilpalm3f.nursery.R;
import com.oilpalm3f.nursery.cloudhelper.Log;
import com.oilpalm3f.nursery.database.DataAccessHandler;
import com.oilpalm3f.nursery.database.Queries;
import com.oilpalm3f.nursery.dbmodels.ActivityTasks;
import com.oilpalm3f.nursery.dbmodels.NurseryAcitivity;
import com.oilpalm3f.nursery.dbmodels.TypeItem;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ActivityTask extends AppCompatActivity {

    String activityTypeId;
    private List<ActivityTasks> activityTasklist = new ArrayList<>();
    private DataAccessHandler dataAccessHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        LinearLayout ll = (LinearLayout)findViewById(R.id.linearLayout2);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            activityTypeId = extras.getString("ActivityTypeId");
            Log.d("ActivityTypeId", activityTypeId + "");
        }


        Log.d("ActivityTypeId", activityTypeId + "");

        dataAccessHandler = new DataAccessHandler(this);

        activityTasklist = dataAccessHandler.getActivityTasksDetails(Queries.getInstance().getActivityTaskDetails(7));

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


}