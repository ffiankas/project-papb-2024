package com.example.habbit.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.habbit.R;
import com.example.habbit.data.Habit;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProgressBarFragment extends Fragment {

    private ProgressBar progressBar;
    private TextView progressText;
    private DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_progress_bar, container, false);

        progressBar = view.findViewById(R.id.circularProgress);
        progressText = view.findViewById(R.id.progress_text);

        // Inisialisasi Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference("habits");

        // Update ProgressBar sesuai data di Firebase
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int completedHabits = 0;
                int totalHabits = (int) dataSnapshot.getChildrenCount();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Habit habit = snapshot.getValue(Habit.class);
                    if (habit != null && habit.isCompleted()) {
                        completedHabits++;
                    }
                }

                // Mengatur ProgressBar dan TextView
                int progress = (int) ((completedHabits / (float) totalHabits) * 100);
                progressBar.setProgress(progress);
                progressText.setText("Progress: " + progress + "%");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Tangani error jika ada masalah
            }
        });

        return view;
    }
}
