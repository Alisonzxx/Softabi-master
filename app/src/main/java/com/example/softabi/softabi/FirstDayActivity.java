package com.example.softabi.softabi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class FirstDayActivity extends AppCompatActivity {
    static final String TAG = "FirstDay";
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
        //scheduleYear.setText(year);

        EditText scheduleMonth = (EditText)findViewById(R.id.monthEdit);
        String month = String.valueOf(scheduleMonth.getText());
        //scheduleMonth.setText(month);

        EditText scheduleDate = (EditText)findViewById(R.id.dateEdit);
        String day = String.valueOf(scheduleDate.getText());

        TextView errorView = (TextView) findViewById(R.id.errorMessage2);

        if(year.equals("") || month.equals("") || day.equals("")){
            errorView.setText("全て入力してください");
            return;
        }

        //int yearInt = Integer.parseInt(year);
        int monthInt = Integer.parseInt(month);
        int dayInt = Integer.parseInt(day);

        boolean flag1 = false, flag2 = false;
        if(monthInt<1 || monthInt >12){
            flag1 = true;
        }
        if (dayInt < 1 || (dayInt > 28 && monthInt ==2) ||
                (dayInt > 31 && (monthInt == 1 || monthInt == 3 || monthInt == 5 ||
                        monthInt == 7 || monthInt == 8 || monthInt == 10 || monthInt == 12))||
                (dayInt > 30 && (monthInt == 4 || monthInt == 6 || monthInt == 9 || monthInt == 11))){
            flag2 = true;
        }

        if(flag1 && flag2) {
            errorView.setText("月と日にちを正しく入力してください");
            return;
        }else if(flag1){
            errorView.setText("月を正しく入力してください");
            return;
        }else if(flag2){
            errorView.setText("日にちを正しく入力してください");
            return;
        }
        String month2 = getMonth(monthInt, dayInt);
        String month3 = getMonth(monthInt, dayInt);

        String day2 = getDay(monthInt, dayInt);
        String day3 = getDay(monthInt, Integer.parseInt(day2));

        //scheduleDate.setText(day);

        String date1 = year + "年" + month + "月" + day + "日";
        String date2 = year + "年" + month2 + "月" + day2 + "日";
        String date3 = year + "年" + month3 + "月" + day3 + "日";

        Log.d(TAG, date1 + ", " + date2 + ", " + date3);

        /*editor.putString("year",year);
        editor.putString("month1",month);
        editor.putString("month2",month2);
        editor.putString("month3",month3);
        editor.putString("day1",day);
        editor.putString("day2",day2);
        editor.putString("day3",day3);*/
        editor.putString("date1", date1);
        editor.putString("date2", date2);
        editor.putString("date3", date3);
        editor.commit();

        Intent intent = new Intent(this, FirstActivity.class);
        startActivity(intent);
    }
    public String getDay(int month,int day) {
        int nwDay = day + 1;
        if (day == 31 && (month == 1 ||
                month == 3 || month == 5 || month == 7 ||
                month == 8 || month == 10 || month == 12)) {
            nwDay = 1;
        } else if (day == 30 && (month == 4 ||
                month == 6 || month == 9 || month == 11)) {
            nwDay = 1;
        } else if (day == 28 && month == 2) {
            nwDay = 1;
        }
        return String.valueOf(nwDay);
    }

    public String getMonth(int month,int day) {
        int nwMonth = month;
        if (day == 31 && (month == 1 ||
                month == 3 || month == 5 || month == 7 ||
                month == 8 || month == 10 || month == 12)) {
            nwMonth = month + 1;
        } else if (day == 30 && (month == 4 ||
                month == 6 || month == 9 || month == 11)) {
            nwMonth = month % 12;
        } else if (day == 28 && month == 2) {
            nwMonth = month + 1;
        }
        return String.valueOf(nwMonth);
    }
}
