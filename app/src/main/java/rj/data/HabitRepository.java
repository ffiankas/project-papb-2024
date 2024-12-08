package com.example.habbit.data;

import android.content.Context;
import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.List;

public class HabitRepository {

    private final CollectionReference habitRef;
    private final MutableLiveData<List<Habit>> allHabits = new MutableLiveData<>();

    public HabitRepository(Context context) {
        habitRef = FirebaseFirestore.getInstance().collection("habits");
    }

    // Menyimpan habit ke Firestore
    public void addHabit(Habit habit) {
        DocumentReference newHabitRef = habitRef.document(); // Mendapatkan referensi dokumen baru
        habit.setHabitId(Integer.parseInt(newHabitRef.getId())); // Ubah ID menjadi int
        newHabitRef.set(habit)  // Simpan objek Habit ke Firestore
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d("HabitRepository", "Habit added successfully with ID: " + newHabitRef.getId());
                    } else {
                        Log.e("HabitRepository", "Error adding habit: ", task.getException());
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("HabitRepository", "Failed to add habit due to: ", e);
                });
    }

    // Mendapatkan semua habit dari Firestore
    public LiveData<List<Habit>> getAllHabits() {
        Log.d("HabitRepository", "Fetching habits from Firestore...");
        habitRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot snapshot = task.getResult();
                if (snapshot != null) {
                    // Log data yang berhasil diambil
                    Log.d("HabitRepository", "Documents fetched: " + snapshot.getDocuments().size() + " items.");
                    List<Habit> habits = snapshot.toObjects(Habit.class);
                    allHabits.setValue(habits); // Meng-update LiveData dengan data yang berhasil diambil
                } else {
                    // Log jika snapshot null
                    Log.e("HabitRepository", "Snapshot is null, no documents found.");
                }
            } else {
                // Menangkap error jika terjadi kegagalan saat mengambil data
                Log.e("HabitRepository", "Error getting documents: ", task.getException());
                allHabits.setValue(null); // Mengatur data menjadi null jika gagal
            }
        });
        return allHabits;
    }
}
