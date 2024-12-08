package ag.mobile.habituaryfirebase;

import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class HabitFragment extends Fragment {

    private RecyclerView recyclerView;
    private HabitAdapter habitAdapter;
    private List<Habit> habits;
    private FirebaseDatabaseHelper databaseHelper;

    private String selectedDate = "1 March";
    private TextView lastSelectedDateView;
    private List<String> dates = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_habit, container, false);

        databaseHelper = new FirebaseDatabaseHelper();
        recyclerView = view.findViewById(R.id.recyclerViewHabits);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        setOnClickListenersForDates(view);
        onDateSelected(view.findViewById(R.id.tv_date_1), "1 March");

        Button addButton = view.findViewById(R.id.addHabit);
        addButton.setOnClickListener(v -> showAddHabitDialog());

        return view;
    }

    private void setOnClickListenersForDates(View view) {
        view.findViewById(R.id.tv_date_1).setOnClickListener(v -> onDateSelected((TextView) v, "1 March"));
        view.findViewById(R.id.tv_date_2).setOnClickListener(v -> onDateSelected((TextView) v, "2 March"));
        view.findViewById(R.id.tv_date_3).setOnClickListener(v -> onDateSelected((TextView) v, "3 March"));
        view.findViewById(R.id.tv_date_4).setOnClickListener(v -> onDateSelected((TextView) v, "4 March"));
        view.findViewById(R.id.tv_date_5).setOnClickListener(v -> onDateSelected((TextView) v, "5 March"));
        view.findViewById(R.id.tv_date_6).setOnClickListener(v -> onDateSelected((TextView) v, "6 March"));
        view.findViewById(R.id.tv_date_7).setOnClickListener(v -> onDateSelected((TextView) v, "7 March"));

        dates.add("1 March");
        dates.add("2 March");
        dates.add("3 March");
        dates.add("4 March");
        dates.add("5 March");
        dates.add("6 March");
        dates.add("7 March");
    }

    private void onDateSelected(TextView selectedDateView, String date) {
        if (lastSelectedDateView != null) {
            lastSelectedDateView.setBackgroundColor(Color.TRANSPARENT);
            lastSelectedDateView.setTextColor(Color.parseColor("#FFA500"));
        }

        selectedDateView.setBackgroundResource(R.drawable.selected_date_background);
        selectedDateView.setTextColor(Color.WHITE);

        lastSelectedDateView = selectedDateView;
        selectedDate = date;

        loadHabitsForDate(date);
    }

    private void loadHabitsForDate(String date) {
        databaseHelper.getHabits(date, new FirebaseDatabaseHelper.DataReadListener() {
            @Override
            public void onDataRead(List<Habit> habitList) {
                habits = habitList;
                habitAdapter = new HabitAdapter(habits, databaseHelper, date, dates);
                recyclerView.setAdapter(habitAdapter);
            }

            @Override
            public void onError(String error) {
                Toast.makeText(requireContext(), "Failed to load habits", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showAddHabitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Add New Habit");

        final EditText input = new EditText(requireContext());
        builder.setView(input);

        builder.setPositiveButton("Save", (dialog, which) -> {
            String newHabit = input.getText().toString().trim();
            if (!newHabit.isEmpty()) {
                for (String date : dates) {
                    databaseHelper.addHabit(date, newHabit);
                }
                loadHabitsForDate(selectedDate);
                Toast.makeText(requireContext(), "Habit added to all dates", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(requireContext(), "Habit name cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        builder.show();
    }
}
