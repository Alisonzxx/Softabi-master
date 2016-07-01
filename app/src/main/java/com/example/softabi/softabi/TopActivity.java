package com.example.softabi.softabi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;

public class TopActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top);
    }

    public void goToSchedule(View view) {
    Intent intent = new Intent(this, ScheduleActivity.class);
        startActivity(intent);
    }

    public void goToMap(View view){
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
}
