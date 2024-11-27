package com.example.habbit.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.habbit.HabitAdapter;
import com.example.habbit.HabitViewModel;
import com.example.habbit.R;
import com.example.habbit.data.Habit;

import java.util.List;

public class TodayHabitFragment extends Fragment {

    private RecyclerView recyclerView;
    private HabitAdapter habitAdapter;
    private HabitViewModel habitViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_today_habit, container, false);
        recyclerView = rootView.findViewById(R.id.recycler_view_habits);

        // Setup RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Inisialisasi ViewModel
        habitViewModel = new ViewModelProvider(requireActivity()).get(HabitViewModel.class);

        // Observe LiveData untuk mengambil data habits dari Firestore
        habitViewModel.getAllHabits().observe(getViewLifecycleOwner(), habits -> {
            if (habits != null && !habits.isEmpty()) {
                // Log untuk memeriksa ukuran data yang diterima
                Log.d("TodayHabitFragment", "Habits received: " + habits.size() + " items.");
                habitAdapter = new HabitAdapter(habits);
                recyclerView.setAdapter(habitAdapter);
            } else {
                Log.e("TodayHabitFragment", "No habits found or data is empty.");
                Toast.makeText(getContext(), "No habits found", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }
}
