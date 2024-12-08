package com.example.habbit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import com.example.habbit.data.Habit;
import com.example.habbit.fragments.ProgressBarFragment;
import com.example.habbit.fragments.TodayHabitFragment;
import com.example.habbit.fragments.YourGoalsFragment;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private HabitViewModel habitViewModel;
    RecyclerView recyclerView;
    HabitAdapter habitAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Inisialisasi ViewModel dengan Factory
        habitViewModel = new ViewModelProvider(this, new HabitViewModelFactory(getApplication())).get(HabitViewModel.class);

        // Observasi LiveData dari ViewModel
        habitViewModel.getAllHabits().observe(this, new Observer<List<Habit>>() {
            @Override
            public void onChanged(List<Habit> habits) {
                habitAdapter = new HabitAdapter(habits);
                recyclerView.setAdapter(habitAdapter);
            }
        });

        // Menambahkan fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container_progress_bar, new ProgressBarFragment());
        transaction.replace(R.id.fragment_container_today_habit, new TodayHabitFragment());
        transaction.replace(R.id.fragment_container_your_goals, new YourGoalsFragment());
        transaction.commit();
    }
}
