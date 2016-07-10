package com.example.softabi.softabi.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.softabi.softabi.database.ScheduleContract;

/**
 * Created by Himawari on 2016/07/10.
 */
public class ScheduleDbHelper extends SQLiteOpenHelper {
    public ScheduleDbHelper(Context context){
        super(context, ScheduleContract.DB_NAME,null,ScheduleContract.DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + ScheduleContract.ScheduleEntry.TABLE + " ( " +
                ScheduleContract.ScheduleEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ScheduleContract.ScheduleEntry.COL_SCHEDULE_DATE + " TEXT NOT NULL, " +
                ScheduleContract.ScheduleEntry.COL_SCHEDULE_TIME + " TEXT NOT NULL, " +
                ScheduleContract.ScheduleEntry.COL_SCHEDULE_TITLE + " TEXT NOT NULL, " +
                ScheduleContract.ScheduleEntry.COL_SCHEDULE_COMMENT + " TEXT NOT NULL);";
        //String createTable ="DELETE FROM " + ScheduleContract.ScheduleEntry.TABLE;
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS "+ ScheduleContract.ScheduleEntry.TABLE);
        onCreate(db);
    }
}
