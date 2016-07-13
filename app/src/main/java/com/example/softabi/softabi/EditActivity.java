package com.example.softabi.softabi;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.softabi.softabi.scheDB.ScheduleContract;
import com.example.softabi.softabi.scheDB.ScheduleDbHelper;

public class EditActivity extends AppCompatActivity {
    private static final String TAG = "EditActivity";
    private ScheduleDbHelper sHelper;
    //private ListView mScheduleListView;
    //private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        sHelper = new ScheduleDbHelper(this);

        /*SQLiteDatabase db = sHelper.getReadableDatabase();
        Cursor cursor = db.query(ScheduleContract.ScheduleEntry.TABLE,
                new String[]{ScheduleContract.ScheduleEntry._ID,
                        ScheduleContract.ScheduleEntry.COL_SCHEDULE_DATE,
                        ScheduleContract.ScheduleEntry.COL_SCHEDULE_TIME,
                        ScheduleContract.ScheduleEntry.COL_SCHEDULE_TITLE,
                        ScheduleContract.ScheduleEntry.COL_SCHEDULE_COMMENT},
                null,null,null,null,null);
        while(cursor.moveToNext()) {
            int idx = cursor.getColumnIndex(ScheduleContract.ScheduleEntry.COL_SCHEDULE_DATE);
            Log.d(TAG, "Date: " + cursor.getString(idx));
        }
        cursor.close();
        db.close();*/

    }
    public void saveSchedule(View v){
        Intent intent = new Intent(this, ScheduleActivity.class);

        DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker);
        int year = datePicker.getYear();//年を取得
        int month = datePicker.getMonth();//月を取得
        int day = datePicker.getDayOfMonth();//日を取得
        String date = String.format("%d年%d月%d日",year,month+1,day );
        //monthに+1をする

        EditText scheduleTime = (EditText)findViewById(R.id.timeEdit);
        String time = String.valueOf(scheduleTime.getText());

        EditText scheduleTitle = (EditText)findViewById(R.id.scheduleEdit);
        String title = String.valueOf(scheduleTitle.getText());

        EditText scheduleComment = (EditText)findViewById(R.id.commentEdit);
        String comment = String.valueOf(scheduleComment.getText());

        //Log.d(TAG,date+ ", " + title +", " + time + ", "+ comment);

        //ぬるぽでた
        SQLiteDatabase db = sHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ScheduleContract.ScheduleEntry.COL_SCHEDULE_DATE, date);
        values.put(ScheduleContract.ScheduleEntry.COL_SCHEDULE_TIME, time);
        values.put(ScheduleContract.ScheduleEntry.COL_SCHEDULE_TITLE, title);
        values.put(ScheduleContract.ScheduleEntry.COL_SCHEDULE_COMMENT, comment);
        db.insertWithOnConflict(ScheduleContract.ScheduleEntry.TABLE,
        null, values,SQLiteDatabase.CONFLICT_REPLACE);
        db.close();

        startActivity(intent);

    }

}
