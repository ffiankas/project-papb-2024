package com.example.habbit.data;

import com.google.firebase.firestore.PropertyName;

public class Habit {

    private int habitId;
    private String name;
    private boolean isCompleted;

    @PropertyName("isCompleted")  // Sesuaikan nama field di Firestore
    public boolean isCompleted() {
        return isCompleted;
    }

    @PropertyName("isCompleted")
    public void setCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public int getHabitId() {
        return habitId;
    }

    public void setHabitId(int habitId) {
        this.habitId = habitId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
