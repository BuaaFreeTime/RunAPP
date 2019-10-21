package comp5216.sydney.edu.au.assignment3;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class RunningActivity extends AppCompatActivity {
    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_calculator:
                    Intent intentCalculator = new Intent(RunningActivity.this,
                            MainActivity.class);

                    if (intentCalculator != null) {
                        startActivity(intentCalculator);
                        RunningActivity.this.finish();
                    }
                    return true;
                case R.id.navigation_tracker:
                    return true;
                case R.id.navigation_history:
                    Intent intentHistory = new Intent(RunningActivity.this,
                            HistoryActivity.class);

                    if (intentHistory != null) {
                        startActivity(intentHistory);
                        RunningActivity.this.finish();
                    }
                    return true;
                case R.id.navigation_music:
                    Intent intentMusic = new Intent(RunningActivity.this,
                            MusicActivity.class);

                    if (intentMusic != null) {
                        startActivity(intentMusic);
                        RunningActivity.this.finish();
                    }
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracker);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
}
