package ffs.mobile.habituary.Fragment;

import static ffs.mobile.habituary.Fragment.AddHabitFragment.FirebaseURL;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ffs.mobile.habituary.Firebase.AddHabitFirebase;
import ffs.mobile.habituary.Firebase.AddHabitFirebaseAdapter;
import ffs.mobile.habituary.R;
import ffs.mobile.habituary.SqliteRoom.Habit;
import ffs.mobile.habituary.SqliteRoom.HabitDAO;
import ffs.mobile.habituary.SqliteRoom.HabitDatabase;
import ffs.mobile.habituary.SqliteRoom.HabitRoomAdapter;

public class GetHabitFragment extends Fragment {
    private RecyclerView rvGoals;
    private AddHabitFirebaseAdapter adapter;
//    private HabitDatabase db;
//    private HabitDAO habitDAO;
    private List<AddHabitFirebase> dataset  = new ArrayList<>();
    private FirebaseDatabase db;
    private DatabaseReference appDB;


    public GetHabitFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_get_habit, container, false);

        rvGoals = view.findViewById(R.id.rvHabits);

//        db = Room.databaseBuilder(
//                        getContext(),HabitDatabase.class,
//                        "db-habit")
//                .build();
//        habitDAO = db.habitDAO();
//
//        new Thread(() -> {
//            dataset = habitDAO.getAllHabit(); // Ambil data dari database
//            getActivity().runOnUiThread(() -> {
//                habitAdapter.setHabit(dataset);
//                habitAdapter.notifyDataSetChanged(); // Beritahu adapter bahwa data berubah
//            });
//        }).start();

        adapter = new AddHabitFirebaseAdapter(getContext(), dataset);
        rvGoals.setLayoutManager(new LinearLayoutManager(getContext()));
        rvGoals.setAdapter(adapter);

        this.db = FirebaseDatabase.getInstance(FirebaseURL);
        this.appDB = this.db.getReference("habit");
        this.adapter.setAppDb(this.appDB);

        this.appDB.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        dataset.clear();
                        for (DataSnapshot s : snapshot.getChildren()){
                            AddHabitFirebase habit = s.getValue(AddHabitFirebase.class);
                            dataset.add(habit);
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                }
        );


//        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
//        apiService.getHabits().enqueue(new Callback<ApiResponse>() {
//            @Override
//            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    List<Habits> goalsList = response.body().getHabits();
//                    if (goalsList != null) { // Null check untuk mencegah NullPointerException
//                        goalsAdapter.setGoals(goalsList);
//                    } else {
//                        Toast.makeText(getContext(), "Data goals kosong", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    Toast.makeText(getContext(), "Gagal memuat data goals", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ApiResponse> call, Throwable t) {
//                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
        return view;
    }
}