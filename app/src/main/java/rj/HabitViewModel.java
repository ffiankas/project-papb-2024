package com.example.habbit;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.habbit.data.Habit;
import com.example.habbit.data.HabitRepository;

import java.util.List;

public class HabitViewModel extends AndroidViewModel {

    private final HabitRepository habitRepository;
    private final LiveData<List<Habit>> allHabits;

    public HabitViewModel(Application application) {
        super(application);
        habitRepository = new HabitRepository(application.getApplicationContext());
        allHabits = habitRepository.getAllHabits();  // Ambil data dari Firestore
    }

    public LiveData<List<Habit>> getAllHabits() {
        return allHabits;
    }

    public void addHabit(Habit habit) {
        habitRepository.addHabit(habit);  // Tambah habit baru ke Firestore
    }
}
