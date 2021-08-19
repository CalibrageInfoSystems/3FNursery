package com.oilpalm3f.nursery.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.Toast;

import com.oilpalm3f.nursery.ConsignmentSelectionScreen;
import com.oilpalm3f.nursery.R;

public class IrrigationpostActivity extends AppCompatActivity {
    private String CONSINEMENTCODES;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_irrigationpost);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Irrigation Log Post Pre Nursery");
        setSupportActionBar(toolbar);


        CONSINEMENTCODES = getIntent().getStringExtra("consignmentCode");

        Toast.makeText(IrrigationpostActivity.this,CONSINEMENTCODES, Toast.LENGTH_SHORT).show();

        intViews();
        setViews();
    }

    private void intViews() {
    }
    private void setViews() {
    }
}