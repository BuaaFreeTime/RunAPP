/*
 * Copyright 2019 by BuaaFreeTime
 */

package comp5216.sydney.edu.au.assignment3;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;


@Entity(tableName = "historyrecord", indices = {@Index("historyRecordID")})


// A database entity class
public class HistoryRecordDF {


    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "historyRecordID")
    private int historyRecordID;

    @ColumnInfo(name = "distance")
    private double distance;

    @ColumnInfo(name = "time")
    private double time;

    @ColumnInfo(name = "date")
    private String date;

    public HistoryRecordDF(double distance, double time, String date){
        this.distance = distance;
        this.date = date;
        this.time = time;
    }

    public void setHistoryRecordID(int historyRecordID) {
        this.historyRecordID = historyRecordID;
    }

    public double getTime() {
        return time;
    }

    public double getDistance() {
        return distance;
    }

    public int getHistoryRecordID() {
        return historyRecordID;
    }

    public String getDate() {
        return date;
    }
}
