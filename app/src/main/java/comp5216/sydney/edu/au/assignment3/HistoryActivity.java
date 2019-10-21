/*
 * Copyright 2019 by BuaaFreeTime
 */

package comp5216.sydney.edu.au.assignment3;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
// A activity to implement the history record function

    // Define variables
    private ListView historyListView;                           // Listview to show history
    private ArrayList<HistoryRecord> records;                   // Lists of history
    private ArrayAdapter<HistoryRecord> recordArrayAdapter;     // A adapter of listview
    private HistoryRecordDB db;                                 // database
    private HistoryRecordDao recordDao;                         // database interface
    private TextView distanceAVG;                               // textview of AVG things
    private TextView timeAVG;
    private TextView paceAVG;
    private TextView speedAVG;

    // the BottomNavigation
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_calculator:
                    Intent intentCalculator = new Intent(HistoryActivity.this,
                            MainActivity.class);

                    if (intentCalculator != null) {
                        startActivity(intentCalculator);
                        HistoryActivity.this.finish();
                    }
                    return true;
                case R.id.navigation_tracker:
                    Intent intentRunning = new Intent(HistoryActivity.this,
                            RunningActivity.class);

                    if (intentRunning != null) {
                        startActivity(intentRunning);
                        HistoryActivity.this.finish();

                    }
                    return true;
                case R.id.navigation_history:
                    return true;
                case R.id.navigation_music:
                    Intent intentMusic = new Intent(HistoryActivity.this,
                            MusicActivity.class);

                    if (intentMusic != null) {
                        startActivity(intentMusic);
                        HistoryActivity.this.finish();
                    }
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_history);

        // BottomNavigationView
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // AVG things
        distanceAVG = (TextView) findViewById(R.id.textViewDistanceAVG);
        timeAVG = (TextView) findViewById(R.id.textViewTimeAVG);
        paceAVG = (TextView) findViewById(R.id.textViewPaceAVG);
        speedAVG = (TextView) findViewById(R.id.textViewSpeedAVG);


        // Reference the "listView" variable to the id "lstRecordView" in the layout
        historyListView = (ListView) findViewById(R.id.lstRecordView);

        // Create an ArrayList of records
        // Read from database
        db = HistoryRecordDB.getDatabase(this.getApplication().getApplicationContext());
        recordDao = db.historyRecordDao();
        readItemsFromDatabase();

        // Add some data
        addSomeData();

        // Sort by date
        sortByDate();

        // Create an adapter for the list view using Android's built-in item layout
        recordArrayAdapter = new ArrayAdapter<HistoryRecord>(this,
                android.R.layout.simple_list_item_1, records);

        // Connect the listView and the adapter
        historyListView.setAdapter(recordArrayAdapter);

        // Calculate weekly avg
        calculateWeeklyAVG();

    }

    // Random Add some data
    public void addSomeData(){
        records.add(new HistoryRecord(66, 50, "10-01-1995 00:00:00"));
        records.add(new HistoryRecord(100, 100, "10-02-1995 00:00:00"));
        records.add(new HistoryRecord(100, 80, "10-03-1995 00:00:00"));
        records.add(new HistoryRecord(22, 20, "10-04-1995 00:00:00"));
        records.add(new HistoryRecord(77, 70, "10-05-1995 00:00:00"));
        records.add(new HistoryRecord(100, 30, "10-01-2019 00:00:00"));
        records.add(new HistoryRecord(100, 50, "10-02-2019 00:00:00"));
        records.add(new HistoryRecord(55, 200, "10-03-2019 00:00:00"));
        records.add(new HistoryRecord(98, 300, "10-04-2019 00:00:00"));
        records.add(new HistoryRecord(99, 400, "10-05-2019 00:00:00"));
        records.add(new HistoryRecord(80, 555, "10-06-2019 00:00:00"));
        records.add(new HistoryRecord(66, 666, "10-08-2019 00:00:00"));
        records.add(new HistoryRecord(50, 789, "10-09-2019 00:00:00"));
        records.add(new HistoryRecord(20, 360, "10-10-2019 00:00:00"));
        records.add(new HistoryRecord(10, 400, "10-11-2019 00:00:00"));
        records.add(new HistoryRecord(5, 60, "10-12-2019 00:00:00"));

    }

    // calculate the weekly AVG data
    public void calculateWeeklyAVG(){
        // the next four is the final answer
        double distance = 0;
        double time = 0;
        double pace = 0;
        double speed = 0;
        // the next Six is used in calculate weekly data
        double nowDistance = 0;
        double nowTime = 0;
        double nowPace = 0;
        double nowSpeed = 0;
        int lastYear;
        int lastWeek ;
        int num = 0;
        // Total week amount
        int numWeek = 0;
        // get the first data
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(records.get(0).getFormatDate());
        lastWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        lastYear = calendar.get(Calendar.YEAR);
        //calculate the weekly AVG data
        for (HistoryRecord item : records){
            Calendar cal = Calendar.getInstance();
            cal.setTime(item.getFormatDate());
            int week = cal.get(Calendar.WEEK_OF_YEAR);
            int year = cal.get(Calendar.YEAR);
            // in same week
            if ((year == lastYear) && (week == lastWeek)) {
                num++;
                nowDistance += item.getDistance();
                nowTime += item.getTime();
                nowPace += item.getPace();
                nowSpeed += item.getSpeed();
            }
            // in difference week
            else if (num > 0) {
                numWeek++;
                lastWeek = week;
                lastYear = year;
                distance = distance + (nowDistance / (double) num);
                time = time + (nowTime / (double) num);
                pace = pace + (nowPace / (double) num);
                speed = speed + (nowSpeed / (double) num);
                num = 0;
                nowDistance = 0;
                nowTime = 0;
                nowPace = 0;
                nowSpeed = 0;
            }
        }
        // calculate the final data
        if (numWeek > 0) {
            distance = distance / (double) numWeek;
            time = time / (double) numWeek;
            pace = pace / (double) numWeek;
            speed = speed / (double) numWeek;
        }else if (num > 0) {
            distance = distance + (nowDistance / (double) num);
            time = time + (nowTime / (double) num);
            pace = pace + (nowPace / (double) num);
            speed = speed + (nowSpeed / (double) num);
        }
        distance = Math.round(distance * 100.0) / 100.0;
        time = Math.round(time * 100.0) / 100.0;
        pace = Math.round(pace * 100.0) / 100.0;
        speed = Math.round(speed * 100.0) / 100.0;
        // output
        distanceAVG.setText("Weekly DistanceAVG : " + distance + " km");
        timeAVG.setText("Weekly TimeAVG : " + time + " min");
        paceAVG.setText("Weekly PaceAVG : " + pace + " min/km");
        speedAVG.setText("Weekly SpeedAVG : " + speed + " km/hour");
    }

    // A method to sort records by date
    public void sortByDate() {

        Collections.sort(records, new Comparator<HistoryRecord>() {

            @Override
            public int compare(HistoryRecord o1, HistoryRecord o2) {
                boolean flag = o1.getFormatDate().before(o2.getFormatDate());
                if (flag) return 1;
                else return -1;
            }
        });
    }

    // read data from database
    private void readItemsFromDatabase() {
        //Use asynchronous task to run query on the background and wait for result
        try {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    //read items from database
                    List<HistoryRecordDF> recordFromDB = recordDao.listAll();
                    records = new ArrayList<HistoryRecord>();
                    if (recordFromDB != null & recordFromDB.size() > 0) {
                        for (HistoryRecordDF item : recordFromDB) {
                            records.add(new HistoryRecord(item.getDistance(),
                                    item.getTime(),
                                    item.getDate()));
                            Log.i("SQLite read item",
                                    "ID: " + item.getHistoryRecordID());
                        } }
                    return null; }
            }.execute().get();
        }
        catch(Exception ex) {
            Log.e("readItemsFromDatabase", ex.getStackTrace().toString());
        }
    }


}
