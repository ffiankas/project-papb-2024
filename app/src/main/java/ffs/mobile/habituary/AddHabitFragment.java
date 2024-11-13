package ffs.mobile.habituary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import ffs.mobile.habituary.api.ApiService;
import ffs.mobile.habituary.api.RetrofitClient;
import ffs.mobile.habituary.model.ApiResponse;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddHabitFragment extends Fragment {

    private Button btCreateNew;
    private EditText ptYourHabit;
    private EditText ptHabitName;
    private Spinner spinnerPeriod;
    private Spinner spinnerHabitType;
    private LinearLayout linearLayout;
    private ApiService apiService;

    public AddHabitFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_habit, container, false);

        this.btCreateNew = view.findViewById(R.id.btCreateNew);
        this.ptYourHabit = view.findViewById(R.id.ptYourHabit);
        this.ptHabitName = view.findViewById(R.id.ptHabitName);
        this.spinnerPeriod = view.findViewById(R.id.spinnerPeriod);

        spinnerPeriod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String text = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ArrayList<String> arrayListPeriod = new ArrayList<>();
        arrayListPeriod.add("1 Year (365 days)");
        arrayListPeriod.add("1 Month (30 days)");
        arrayListPeriod.add("1 Week (7 days)");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, arrayListPeriod);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPeriod.setAdapter(adapter);

        this.spinnerHabitType = view.findViewById(R.id.spinnerHabitType);

        spinnerPeriod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String text = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayList<String> arrayListHabitType = new ArrayList<>();
        arrayListHabitType.add("Everyday");
        arrayListHabitType.add("Every Sunday");
        arrayListHabitType.add("Every Monday");
        arrayListHabitType.add("Every Tuesday");
        arrayListHabitType.add("Every Wednesday");
        arrayListHabitType.add("Every Thursday");
        arrayListHabitType.add("Every Friday");
        arrayListHabitType.add("Every Saturday");
        ArrayAdapter<String> adapterListHabitType = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, arrayListHabitType);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHabitType.setAdapter(adapterListHabitType);

        btCreateNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                apiService = RetrofitClient.getClient().create(ApiService.class);

                RequestBody yourGoal = RequestBody.create(MediaType.parse("text/plain"), ptYourHabit.getText().toString());
                RequestBody habitName = RequestBody.create(MediaType.parse("text/plain"), ptHabitName.getText().toString());
                RequestBody period = RequestBody.create(MediaType.parse("text/plain"), spinnerPeriod.getSelectedItem().toString());
                RequestBody habitType = RequestBody.create(MediaType.parse("text/plain"), spinnerHabitType.getSelectedItem().toString());

                Call<ApiResponse> addHabits = apiService.addHabits(
                        yourGoal,
                        habitName,
                        period,
                        habitType
                );

                addHabits.enqueue(new Callback<ApiResponse>() {
                    @Override
                    public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                        if (response.isSuccessful()){
                            Toast.makeText(getContext(), "Habit berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Habit gagal ditambahkan: " + response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiResponse> call, Throwable t) {
                        Toast.makeText(getContext(), "gagal ditambahkan: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        return view;
    }
}