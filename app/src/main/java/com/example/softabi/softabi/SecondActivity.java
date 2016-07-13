package com.example.softabi.softabi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.softabi.softabi.scheDB.ScheduleContract;
import com.example.softabi.softabi.scheDB.ScheduleDbHelper;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {
    private ScheduleDbHelper sHelper;
    private SharedPreferences data;
    private ArrayAdapter<String> mAdapter;
    private ListView mScheduleListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        TextView date = (TextView) findViewById(R.id.LabelDate2);
        data = getSharedPreferences("DataStore", Context.MODE_PRIVATE);
        String date2 = data.getString("date2","");
        date.setText("2日目　"+ date2);

        sHelper = new ScheduleDbHelper(this);
        mScheduleListView = (ListView) findViewById(R.id.list_secondDay);

        upDateUI();
    }
    public void upDateUI(){
        data = getSharedPreferences("DataStore", Context.MODE_PRIVATE);
        String date = data.getString("date2",null);

        if(date!=null) {
            ArrayList<String> scheduleList = new ArrayList<>();
            SQLiteDatabase db = sHelper.getReadableDatabase();
            Cursor cursor = db.query(ScheduleContract.ScheduleEntry.TABLE,
                    new String[]{ScheduleContract.ScheduleEntry._ID,
                            ScheduleContract.ScheduleEntry.COL_SCHEDULE_DATE,
                            ScheduleContract.ScheduleEntry.COL_SCHEDULE_TIME,
                            ScheduleContract.ScheduleEntry.COL_SCHEDULE_TITLE},
                            //ScheduleContract.ScheduleEntry.COL_SCHEDULE_COMMENT},
                    "date like ?", new String[]{date}, null, null,"time asc");
            while (cursor.moveToNext()) {
                int idx = cursor.getColumnIndex(ScheduleContract.ScheduleEntry.COL_SCHEDULE_TIME);
                int idx2 = cursor.getColumnIndex(ScheduleContract.ScheduleEntry.COL_SCHEDULE_TITLE);
                if(cursor.getString(idx).length()<1||cursor.getString(idx2).length()<1)break;
                scheduleList.add(cursor.getString(idx) + "　" + cursor.getString(idx2));
            }
            if(mAdapter == null){
                mAdapter = new ArrayAdapter<>(this, R.layout.schedule_list,
                        R.id.schedule_time, scheduleList);
                mScheduleListView.setAdapter(mAdapter);

            }else{
                mAdapter.clear();
                mAdapter.addAll(scheduleList);
                mAdapter.notifyDataSetChanged();
            }
            cursor.close();
            db.close();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_schedule,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_day1) {
            Intent intent = new Intent(this, FirstActivity.class);
            startActivity(intent);
        }
        if (id == R.id.action_day2){
            Intent intent = new Intent(this, SecondActivity.class);
            startActivity(intent);
        }

        if(id == R.id.action_day3){
            Intent intent = new Intent(this, ThirdActivity.class);
            startActivity(intent);
        }

        if(id==R.id.action_top){
            Intent intent = new Intent(this, TopActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void goToItems(View view){
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }

    public void deleteSchedule(View view) {
        View parent = (View) view.getParent();
        TextView scheduleTextView = (TextView) parent.findViewById(R.id.schedule_time);
        String[] schedule = String.valueOf(scheduleTextView.getText()).split("　", 0);
        SQLiteDatabase db = sHelper.getWritableDatabase();
        db.delete(ScheduleContract.ScheduleEntry.TABLE,
                ScheduleContract.ScheduleEntry.COL_SCHEDULE_TIME + " = ?",
                new String[]{schedule[0]});
        db.close();
        upDateUI();
    }

    public void addSchedule(View view){
        Intent intent = new Intent(this, EditActivity.class);
        startActivity(intent);
    }


}
