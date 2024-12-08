package ffs.mobile.habituary.SqliteRoom;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Habit {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "yourGoal")
    public String yourGoal;
    @ColumnInfo(name = "habitName")
    public String habitName;
    @ColumnInfo(name = "period")
    public String period;
    @ColumnInfo(name = "habitType")
    public String habitType;
}
