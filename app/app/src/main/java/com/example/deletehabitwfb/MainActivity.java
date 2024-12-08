package com.example.deletehabitwfb;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements HabitAdapter.OnDeleteClickListener {

    // Firebase Database URL
    public static final String DBURL = "https://pamc-p6-default-rtdb.asia-southeast1.firebasedatabase.app/";

    private RecyclerView recyclerView;
    private HabitAdapter habitAdapter;
    private List<Habit> habitList;
    private DatabaseReference habitRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase Database with the provided URL
        habitRef = FirebaseDatabase.getInstance(DBURL).getReference("habits");

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.rvDaftarHabit);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        habitList = new ArrayList<>();
        habitAdapter = new HabitAdapter(habitList, this);
        recyclerView.setAdapter(habitAdapter);

        // Fetch habits from Firebase and update RecyclerView
        fetchHabitsFromFirebase();
    }

    // Fetch data from Firebase and synchronize
    private void fetchHabitsFromFirebase() {
        habitRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                habitList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Habit habit = snapshot.getValue(Habit.class);
                    habitList.add(habit);
                }
                habitAdapter.notifyDataSetChanged();

                // Show a Toast message to indicate that the data is synchronized
                Toast.makeText(MainActivity.this, "Data synchronized with Firebase!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Show a Toast message if there is an error in syncing data
                Toast.makeText(MainActivity.this, "Failed to synchronize data with Firebase", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDeleteClick(Habit habit) {
        // Delete habit from Firebase
        habitRef.child(habit.getId()).removeValue().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(MainActivity.this, "Habit deleted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Failed to delete habit", Toast.LENGTH_SHORT).show();
            }
        });
    }
}