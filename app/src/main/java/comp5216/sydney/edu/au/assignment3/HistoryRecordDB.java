package comp5216.sydney.edu.au.assignment3;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {HistoryRecordDF.class}, version = 1, exportSchema = false)
public abstract class HistoryRecordDB extends RoomDatabase {

    private static final String DATABASE_NAME = "historyRecord_db";
    private static HistoryRecordDB DBINSTANCE;

    public abstract HistoryRecordDao historyRecordDao();

    public static HistoryRecordDB getDatabase(Context context) {
        if (DBINSTANCE == null) {
            synchronized (HistoryRecordDB.class) {
                DBINSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        HistoryRecordDB.class, DATABASE_NAME).build();
            }
        }
        return DBINSTANCE;
    }

    public static void destroyInstance() {
        DBINSTANCE = null;
    }
}
