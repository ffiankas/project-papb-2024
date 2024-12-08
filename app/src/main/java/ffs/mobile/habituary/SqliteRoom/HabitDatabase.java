package ffs.mobile.habituary.SqliteRoom;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Habit.class}, version = 1)
public abstract class HabitDatabase extends RoomDatabase {
    public abstract HabitDAO habitDAO();
}
