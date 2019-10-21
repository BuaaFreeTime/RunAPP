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

public class MainActivity extends AppCompatActivity {
    // this is the main activity also is the calculator activity

    private TextView textViewPace;
    private TextView textViewSpeed;
    private EditText editDistance;
    private EditText editTime;

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
        textViewPace.setText("Pace: "
                + String.valueOf(Math.round(time / distance * 100.0) / 100.0)
                + " min/km");
        textViewSpeed.setText("Speed: "
                + String.valueOf(Math.round(distance / time * 6000.0) / 100.0)
                + " km/hour");
    }

    @Override
    public void onBackPressed() {
        // your code
        this.finish();
        return;
    }
}
