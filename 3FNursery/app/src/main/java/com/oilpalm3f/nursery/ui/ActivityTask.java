package com.oilpalm3f.nursery.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.oilpalm3f.nursery.R;
import com.oilpalm3f.nursery.cloudhelper.Log;

public class ActivityTask extends AppCompatActivity {

    String activityTypeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            activityTypeId = extras.getString("ActivityTypeId");
            Log.d("ActivityTypeId", activityTypeId + "");
        }

        Log.d("ActivityTypeId", activityTypeId + "");

    }
}