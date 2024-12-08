package ag.mobile.habituaryfirebase;

import androidx.annotation.NonNull;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class FirebaseDatabaseHelper {

    private final DatabaseReference databaseReference;

    public FirebaseDatabaseHelper() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("habits");
    }

    public void addHabit(String date, String habit) {
        Habit newHabit = new Habit(null, date, habit, false);
        String habitId = databaseReference.child(date).push().getKey();
        newHabit.setId(habitId);
        databaseReference.child(date).child(habitId).setValue(newHabit);
    }

    public void getHabits(String date, final DataReadListener listener) {
        databaseReference.child(date).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Habit> habitList = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    Habit habit = data.getValue(Habit.class);
                    habitList.add(habit);
                }
                listener.onDataRead(habitList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onError(error.getMessage());
            }
        });
    }

    public void updateHabit(Habit habit, String newHabitName) {
        habit.setHabitName(newHabitName);
        databaseReference.child(habit.getDate()).child(habit.getId()).setValue(habit);
    }

    public void updateHabitForAllDates(String oldHabitName, String newHabitName, List<String> dates) {
        for (String date : dates) {
            databaseReference.child(date).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    boolean isUpdated = false;
                    for (DataSnapshot data : snapshot.getChildren()) {
                        Habit habit = data.getValue(Habit.class);
                        if (habit != null && oldHabitName.equals(habit.getHabitName())) {
                            habit.setHabitName(newHabitName);
                            databaseReference.child(date).child(habit.getId()).setValue(habit)
                                    .addOnCompleteListener(task -> {
                                        if (task.isSuccessful()) {
                                            System.out.println("Habit updated successfully on date: " + date);
                                        } else {
                                            System.err.println("Failed to update habit on date: " + date + ". Error: " + task.getException());
                                        }
                                    });
                            isUpdated = true;
                        }
                    }

                    if (!isUpdated) {
                        System.out.println("No habit with name '" + oldHabitName + "' found on date: " + date);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    System.err.println("Error reading habits for date: " + date + ". Error: " + error.getMessage());
                }
            });
        }
    }

    public void deleteHabit(Habit habit) {
        databaseReference.child(habit.getDate()).child(habit.getId()).removeValue();
    }

    public interface DataReadListener {
        void onDataRead(List<Habit> habits);
        void onError(String error);
    }
}
