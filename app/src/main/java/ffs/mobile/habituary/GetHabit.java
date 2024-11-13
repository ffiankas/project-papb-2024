package ffs.mobile.habituary;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ffs.mobile.habituary.api.ApiService;
import ffs.mobile.habituary.api.RetrofitClient;
import ffs.mobile.habituary.model.ApiResponse;
import ffs.mobile.habituary.model.Habits;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetHabit extends AppCompatActivity {
    private RecyclerView rvGoals;
    private GoalsAdapter goalsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_habit);

        rvGoals = findViewById(R.id.rvGoals);

        goalsAdapter = new GoalsAdapter(this);
        rvGoals.setLayoutManager(new LinearLayoutManager(this));
        rvGoals.setAdapter(goalsAdapter);

        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        apiService.getHabits().enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Habits> goalsList = response.body().getHabits();
                    if (goalsList != null) { // Null check untuk mencegah NullPointerException
                        goalsAdapter.setGoals(goalsList);
                    } else {
                        Toast.makeText(GetHabit.this, "Data goals kosong", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(GetHabit.this, "Gagal memuat data goals", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(GetHabit.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}