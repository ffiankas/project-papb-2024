package ffs.mobile.habituary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
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

public class GetHabitFragment extends Fragment {
    private RecyclerView rvGoals;
    private GoalsAdapter goalsAdapter;

    public GetHabitFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_get_habit, container, false);

        rvGoals = view.findViewById(R.id.rvHabits);

        goalsAdapter = new GoalsAdapter(getContext());
        rvGoals.setLayoutManager(new LinearLayoutManager(getContext()));
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
                        Toast.makeText(getContext(), "Data goals kosong", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Gagal memuat data goals", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}