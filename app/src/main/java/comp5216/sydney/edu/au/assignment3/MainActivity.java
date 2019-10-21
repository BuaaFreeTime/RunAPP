/*
 * Copyright 2019 by BuaaFreeTime
 */

package comp5216.sydney.edu.au.assignment3;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


// this is the main activity also is the calculator activity
public class MainActivity extends AppCompatActivity {

    // define
    private TextView textViewPace;                // textview of pace
    private TextView textViewSpeed;               // textview of speed
    private EditText editDistance;                // input distance
    private EditText editTime;                    // input time

    // Navigation
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_calculator:
                    return true;
                case R.id.navigation_tracker:
                    Intent intentRunning = new Intent(MainActivity.this,
                            RunningActivity.class);

                    if (intentRunning != null) {
                        startActivity(intentRunning);
                        MainActivity.this.finish();
                    }
                    return true;
                case R.id.navigation_history:
                    Intent intentHistory = new Intent(MainActivity.this,
                            HistoryActivity.class);

                    if (intentHistory != null) {
                        startActivity(intentHistory);
                        MainActivity.this.finish();
                    }
                    return true;
                case R.id.navigation_music:
                    Intent intentMusic = new Intent(MainActivity.this,
                            MusicActivity.class);

                    if (intentMusic != null) {
                        startActivity(intentMusic);
                        MainActivity.this.finish();
                    }
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        textViewPace = findViewById(R.id.textViewPace);
        textViewPace.setText("Pace: ");
        textViewSpeed = findViewById(R.id.textViewSpeed);
        textViewSpeed.setText("Speed: ");
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    public void onClick(View view) {
        editDistance = (EditText) findViewById(R.id.etEditDistance);
        editTime = (EditText)findViewById(R.id.etEditTime);
        double distance = Double.valueOf(editDistance.getText().toString());
        double time = Double.valueOf(editTime.getText().toString());
        // calculate the pace and speed
        textViewPace.setText("Pace: "
                + String.valueOf(Math.round(time / distance * 100.0) / 100.0)
                + " min/km");
        textViewSpeed.setText("Speed: "
                + String.valueOf(Math.round(distance / time * 6000.0) / 100.0)
                + " km/hour");
    }

}
