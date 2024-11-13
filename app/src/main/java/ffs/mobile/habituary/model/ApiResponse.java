package ffs.mobile.habituary.model;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponse {
    @SerializedName("status")
    private String status;

    @SerializedName("data_habits")
    private List<Habits> habits;

    public void setStatus(String status) { this.status = status; }
    public void setHabits(List<Habits> habits) { this.habits = habits; }

    public String getStatus() { return status; }
    public List<Habits> getHabits() { return habits; }
}
