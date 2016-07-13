package com.example.softabi.softabi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.softabi.softabi.scheDB.ScheduleContract;
import com.example.softabi.softabi.scheDB.ScheduleDbHelper;

import java.util.ArrayList;

public class ScheduleActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private ScheduleDbHelper sHelper;
    private static final String TAG = "ScheduleActivity";
    private SharedPreferences data;
    private ArrayAdapter<String> mAdapter;
    private ListView mScheduleListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/

/*
data = getSharedPreferences("DataStore", Context.MODE_PRIVATE);
        if(data!=null){
        TextView flagmentDate = (TextView) findViewById(R.id.year);
        flagmentDate.setText(data.getString("date",null));
        }
        */

/*
        EditText yearEdit = (EditText) findViewById(R.id.year);
        yearEdit.setHint(data.getString("year","先に日付を"));

        EditText monthEdit = (EditText) findViewById(R.id.month);
        monthEdit.setHint(data.getString("month","入力"));

        EditText dayEdit = (EditText) findViewById(R.id.date);
        dayEdit.setHint(data.getString("day","してね"));
*/
        //mAdapter = new ArrayAdapter<String>();
        sHelper = new ScheduleDbHelper(this);
        mScheduleListView = (ListView) findViewById(R.id.list_schedule);
        upDateUI();
    }

    public void upDateUI(){
        data = getSharedPreferences("DataStore", Context.MODE_PRIVATE);
        String date = data.getString("date",null);

        if(date!=null) {
            ArrayList<String> timeList = new ArrayList<>();
            ArrayList<String> titleList = new ArrayList<>();
            ArrayList<String> commentList = new ArrayList<>();
            SQLiteDatabase db = sHelper.getReadableDatabase();
            Cursor cursor = db.query(ScheduleContract.ScheduleEntry.TABLE,
                    new String[]{ScheduleContract.ScheduleEntry._ID,
                            ScheduleContract.ScheduleEntry.COL_SCHEDULE_DATE,
                            ScheduleContract.ScheduleEntry.COL_SCHEDULE_TIME,
                            ScheduleContract.ScheduleEntry.COL_SCHEDULE_TITLE,
                            ScheduleContract.ScheduleEntry.COL_SCHEDULE_COMMENT},
                    "date like ?", new String[]{date}, null, null,"time asc");
            while (cursor.moveToNext()) {
                //int idx = cursor.getColumnIndex(ScheduleContract.ScheduleEntry.COL_SCHEDULE_DATE);
                int idx = cursor.getColumnIndex(ScheduleContract.ScheduleEntry.COL_SCHEDULE_TIME);
                int idx2 = cursor.getColumnIndex(ScheduleContract.ScheduleEntry.COL_SCHEDULE_TITLE);
                int idx3 = cursor.getColumnIndex(ScheduleContract.ScheduleEntry.COL_SCHEDULE_COMMENT);
                timeList.add(cursor.getString(idx));
                titleList.add(cursor.getString(idx2));
                commentList.add(cursor.getString(idx3));
                //Log.d(TAG, cursor.getString(idx) + ", " + cursor.getString(idx2) + ", " + cursor.getString(idx3));
            }
            for(int i = 0; i < timeList.size();i++){
                Log.d(TAG,timeList.get(i));
            }
            if(mAdapter == null){
                mAdapter = new ArrayAdapter<>(this, R.layout.schedule_list,
                        R.id.schedule_time, timeList);
                mScheduleListView.setAdapter(mAdapter);//ここでぬるぽがでる
               /* mAdapter = new ArrayAdapter<>(this,
                        R.layout.schedule_list,
                        R.id.schedule_title,
                        titleList);
                mAdapter = new ArrayAdapter<>(this,
                        R.layout.schedule_list,
                        R.id.schedule_comment,
                        commentList);*/

            }else{
                mAdapter.clear();
                mAdapter.addAll(timeList);
                //mAdapter.addAll(titleList);
                //mAdapter.addAll(commentList);
                mAdapter.notifyDataSetChanged();
            }
            cursor.close();
            db.close();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_schedule, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_item) {
            Intent intent = new Intent(this, ListActivity.class);
            startActivity(intent);
        }
        if (id == R.id.action_add_schedule){
            Intent intent = new Intent(this, EditActivity.class);
            startActivity(intent);
        }

        if(id == R.id.action_choose){
            Intent intent = new Intent(this, FirstDayActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        //フラグメントの中身かな？
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_schedule, container, false);
            //TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));

            return rootView;
        }

    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "1日目";
                case 1:
                    return "2日目";
                case 2:
                    return "3日目";
            }
            return null;
        }
    }
}
