package ag.mobile.habituaryfirebase;

public class Habit {
    private String id;
    private String date;
    private String habitName;
    private boolean isCompleted;

    public Habit() {}

    public Habit(String id, String date, String habitName, boolean isCompleted) {
        this.id = id;
        this.date = date;
        this.habitName = habitName;
        this.isCompleted = isCompleted;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHabitName() {
        return habitName;
    }

    public void setHabitName(String habitName) {
        this.habitName = habitName;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
