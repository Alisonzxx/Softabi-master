package com.example.softabi.softabi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class FirstDayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_day);
    }

    public void saveDate(View view){

        SharedPreferences dataStore = getSharedPreferences("DataStore", MODE_PRIVATE);
        SharedPreferences.Editor editor = dataStore.edit();
        //editor.clear().commit();

        EditText scheduleYear = (EditText)findViewById(R.id.yearEdit);
        String year = String.valueOf(scheduleYear.getText());
        scheduleYear.setText(year);

        EditText scheduleMonth = (EditText)findViewById(R.id.monthEdit);
        String month = String.valueOf(scheduleMonth.getText());
        scheduleMonth.setText(month);

        EditText scheduleDate = (EditText)findViewById(R.id.dateEdit);
        String day = String.valueOf(scheduleDate.getText());
        scheduleDate.setText(day);

        String date = year +"年"+month+"月"+day+"日";

        editor.putString("year",year);
        editor.putString("month",month);
        editor.putString("day",day);
        editor.putString("date", date);
        editor.commit();

        Intent intent = new Intent(this, ScheduleActivity.class);
        startActivity(intent);
    }
}
