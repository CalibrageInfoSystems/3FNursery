package com.oilpalm3f.nursery.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.oilpalm3f.nursery.R;

public class HomeActivity extends AppCompatActivity {

    RelativeLayout newactivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        init();
        setviews();

    }

    private void init() {

        newactivity = findViewById(R.id.newactivityRel);
    }

    private void setviews() {

        newactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent selectionscreen = new Intent(HomeActivity.this, SelectionScreen.class);
                startActivity(selectionscreen);

            }
        });
    }


}