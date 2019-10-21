/*
 * Copyright 2019 by BuaaFreeTime
 */

package comp5216.sydney.edu.au.assignment3;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

// A entity class of history record
public class HistoryRecord {

    double distance;
    double time;
    double pace;
    double speed;
    String date;
    Date formatDate;

    public HistoryRecord(double distance, double time, String date) {
        this.distance = Math.round(distance * 100.0) / 100.0;
        this.time = Math.round(time * 100.0) / 100.0;
        this.date = date;
        this.pace = Math.round(time / distance * 100.0) / 100.0;
        this.speed = Math.round(distance / time * 6000.0) / 100.0;
        SimpleDateFormat ft = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
        try {
            this.formatDate = ft.parse(date);
        } catch (ParseException e) {
            Log.e("ToDoItem", "DatatoSpring error");
        }
    }

    public HistoryRecord(double distance, double time, Date date) {
        this.distance = Math.round(distance * 100.0) / 100.0;
        this.time = Math.round(time * 100.0) / 100.0;
        this.formatDate = date;
        this.pace = Math.round(time / distance * 100.0) / 100.0;
        this.speed = Math.round(distance / time * 6000.0) / 100.0;
        SimpleDateFormat ft = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
        this.date = ft.format(date);
    }

    public String getDate() {
        return date;
    }

    public double getDistance() {
        return distance;
    }

    public double getTime() {
        return time;
    }

    public double getPace() {
        return pace;
    }

    public double getSpeed() {
        return speed;
    }

    public Date getFormatDate() {
        return formatDate;
    }

    public String toString() {
        return ("Distance : " + distance + " km      "
                + "Time : " + time + " min\r\n"
                + "Pace : " + pace + " min/km      "
                + "Speed : " + speed + " km/hour");
    }
}
