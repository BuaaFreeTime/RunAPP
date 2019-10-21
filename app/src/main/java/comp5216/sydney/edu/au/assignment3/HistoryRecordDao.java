/*
 * Copyright 2019 by BuaaFreeTime
 */

package comp5216.sydney.edu.au.assignment3;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

// database interface
@Dao
public interface HistoryRecordDao {

    @Query("SELECT * FROM historyrecord")

    List<HistoryRecordDF> listAll();

    @Insert
    void insert(HistoryRecordDF historyRecordDF);

    @Update
    public void update(HistoryRecordDF historyRecordDF);

    @Delete
    public void delete(HistoryRecordDF historyRecordDF);
}
