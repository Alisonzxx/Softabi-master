package com.example.softabi.softabi.scheDB;

import android.provider.BaseColumns;

/**
 * Created by Himawari on 2016/07/10.
 */
public class ScheduleContract {
        public static final String DB_NAME="com.example.softabi.softabi.scheDB";
        public static final int DB_VERSION = 1;

        public class ScheduleEntry implements BaseColumns{
            public static final String TABLE = "schedule";
            public static final String COL_SCHEDULE_DATE ="date";
            public static final String COL_SCHEDULE_TIME ="time";
            public static final String COL_SCHEDULE_TITLE="title";
            public static final String COL_SCHEDULE_COMMENT="comment";

        }
}
