/*
 * Copyright 2019 by BuaaFreeTime
 */

package comp5216.sydney.edu.au.assignment3;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.ArrayList;

public class MusicActivity extends AppCompatActivity {

    //requestCode
    private final static int STORGE_REQUEST = 1 ;

    // define
    private ListView musicListView;                         // ListView of all music
    private ArrayList<MusicInfo> musicInfos;                // List to save the data
    private ArrayAdapter<MusicInfo> musicAdapter;           //  A adapter

    private MediaPlayer mPlayer = null;                     // music player
    private boolean isRelease = true;                       // the player working or not

    // Navigation
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_calculator:
                    Intent intent = new Intent(MusicActivity.this,
                            MainActivity.class);

                    if (intent != null) {
                        startActivity(intent);
                        MusicActivity.this.finish();
                    }
                    return true;
                case R.id.navigation_tracker:
                    Intent intentRunning = new Intent(MusicActivity.this,
                            RunningActivity.class);

                    if (intentRunning != null) {
                        startActivity(intentRunning);
                        MusicActivity.this.finish();
                    }
                    return true;
                case R.id.navigation_history:
                    Intent intentHistory = new Intent(MusicActivity.this,
                            HistoryActivity.class);

                    if (intentHistory != null) {
                        startActivity(intentHistory);
                        MusicActivity.this.finish();
                    }
                    return true;
                case R.id.navigation_music:
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // Reference the "listView" variable to the id "lstView" in the layout
        musicListView = (ListView) findViewById(R.id.lstMusicView);

        // First check the permission
        int check = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ;
        // Didn't have permission
        if (check != PackageManager.PERMISSION_GRANTED) {
            // Request permission
            ActivityCompat.requestPermissions(
                    this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    STORGE_REQUEST);
        }
        // initial the list view
        init();
    }

    // initial and read all music file.
    private void init() {
        // Get all information needed.
        Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI ,
                new String[] {MediaStore.Audio.Media._ID,
                        MediaStore.Audio.Media.DURATION,
                        MediaStore.Audio.Media.DISPLAY_NAME,
                        MediaStore.Audio.Media.SIZE,
                        MediaStore.Audio.Media.DATA},
                null ,null ,null) ;

        musicInfos = new ArrayList<>() ;
        try {
            while (cursor.moveToNext()) {

                MusicInfo musicInfo = new MusicInfo();
                musicInfo.set_id(cursor.getInt(0));
                musicInfo.setDuration(cursor.getInt(1));
                musicInfo.setMusicName(cursor.getString(2));
                musicInfo.setSize(cursor.getInt(3));
                musicInfo.setData(cursor.getString(4));
            musicInfos.add(musicInfo);
        }
        } catch (Exception ex) {
            Log.e("read photo error", ex.getStackTrace().toString());
        }

        // Create an adapter for the list view using Android's built-in item layout
        musicAdapter = new ArrayAdapter<MusicInfo>(this,
                android.R.layout.simple_list_item_1, musicInfos);

        // Connect the listView and the adapter。
        musicListView.setAdapter(musicAdapter);

        // Setup listView listeners
        setupListViewListener();

        mPlayer = new MediaPlayer();
    }


    private void setupListViewListener() {
        // Short click to play music
        musicListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (!isRelease) {
                    mPlayer.stop();
                    mPlayer.reset();
                    mPlayer.release();
                    isRelease = true;
                    mPlayer = new MediaPlayer();
                }

                // Get the original title and text and id
                mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                try {
                    mPlayer.setDataSource(getApplicationContext(),
                            musicAdapter.getItem(position).getUri());
                    mPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                mPlayer.start();
                isRelease = false;
            }
        });
    }

    // stop button
    public void onClick(View view) {
        if (!isRelease) {
            mPlayer.stop();
            mPlayer.reset();
            mPlayer.release();
            mPlayer = new MediaPlayer();
            isRelease = true;
        }
    }
}
