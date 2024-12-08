package com.example.habbit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.habbit.data.Habit;

import java.util.List;

public class HabitAdapter extends RecyclerView.Adapter<HabitAdapter.HabitViewHolder> {

    private List<Habit> habitList;

    public HabitAdapter(List<Habit> habitList) {
        this.habitList = habitList;
    }

    @Override
    public HabitViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_habit, parent, false);
        return new HabitViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HabitViewHolder holder, int position) {
        Habit habit = habitList.get(position);
        holder.nameTextView.setText(habit.getName());
        holder.completedCheckBox.setChecked(habit.isCompleted());
    }

    @Override
    public int getItemCount() {
        return habitList.size();
    }

    public static class HabitViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView;
        CheckBox completedCheckBox;

        public HabitViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.text_habit_name);
            completedCheckBox = itemView.findViewById(R.id.checkbox_completed);
        }
    }
}
