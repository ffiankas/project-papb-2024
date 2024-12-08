package ag.mobile.habituaryfirebase;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HabitAdapter extends RecyclerView.Adapter<HabitAdapter.HabitViewHolder> {

    private List<Habit> habits;
    private FirebaseDatabaseHelper databaseHelper;
    private String date;
    private List<String> dates;

    public HabitAdapter(List<Habit> habits, FirebaseDatabaseHelper databaseHelper, String date, List<String> dates) {
        this.habits = habits;
        this.databaseHelper = databaseHelper;
        this.date = date;
        this.dates = dates;
    }

    @NonNull
    @Override
    public HabitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_habit, parent, false);
        return new HabitViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HabitViewHolder holder, int position) {
        Habit habit = habits.get(position);
        holder.habitName.setText(habit.getHabitName());
        holder.checkBox.setChecked(habit.isCompleted());

        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            habit.setCompleted(isChecked);
            databaseHelper.updateHabit(habit, habit.getHabitName());
        });

        holder.btnDelete.setOnClickListener(v -> {
            databaseHelper.deleteHabit(habit);
            habits.remove(position);
            notifyItemRemoved(position);
        });

        holder.btnEdit.setOnClickListener(v -> showEditHabitDialog(holder.itemView.getContext(), habit, position));
    }

    private void showEditHabitDialog(Context context, Habit habit, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Edit Habit");

        final EditText input = new EditText(context);
        input.setText(habit.getHabitName());
        builder.setView(input);

        builder.setPositiveButton("Save", (dialog, which) -> {
            String updatedName = input.getText().toString().trim();
            if (!updatedName.isEmpty()) {
                String oldName = habit.getHabitName();
                databaseHelper.updateHabitForAllDates(oldName, updatedName, dates);
                habit.setHabitName(updatedName);
                notifyItemChanged(position);
                Toast.makeText(context, "Habit updated for all dates", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Name cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    @Override
    public int getItemCount() {
        return habits.size();
    }

    static class HabitViewHolder extends RecyclerView.ViewHolder {
        TextView habitName;
        CheckBox checkBox;
        Button btnDelete, btnEdit;

        HabitViewHolder(@NonNull View itemView) {
            super(itemView);
            habitName = itemView.findViewById(R.id.tvHabit);
            checkBox = itemView.findViewById(R.id.cbHabit);
            btnDelete = itemView.findViewById(R.id.btnDeleteHabit);
            btnEdit = itemView.findViewById(R.id.btnEditHabit);
        }
    }
}
