package ffs.mobile.habituary.model;

public class Habits {
    private int id;
    private String yourGoal;
    private String habitName;
    private String period;
    private String habitType;
    public boolean habitDipilih = false;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getYourGoal() {
        return yourGoal;
    }

    public void setYourGoal(String yourGoal) {
        this.yourGoal = yourGoal;
    }

    public String getHabitName() {
        return habitName;
    }

    public void setHabitName(String habitName) {
        this.habitName = habitName;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getHabitType() {
        return habitType;
    }

    public void setHabitType(String habitType) {
        this.habitType = habitType;
    }
}
