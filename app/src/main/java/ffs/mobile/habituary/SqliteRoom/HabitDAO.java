package ffs.mobile.habituary.SqliteRoom;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface HabitDAO {
    @Query("SELECT * FROM habit")
    List<Habit> getAllHabit();

    @Insert
    void insertALL(Habit... habit);

    @Delete
    void delete(Habit habit);
}
