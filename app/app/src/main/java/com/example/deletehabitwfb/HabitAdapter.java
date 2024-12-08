package com.example.deletehabitwfb;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HabitAdapter extends RecyclerView.Adapter<HabitAdapter.HabitViewHolder> {
    private final List<Habit> habits;
    private final OnDeleteClickListener onDeleteClickListener;

    public HabitAdapter(List<Habit> habits, OnDeleteClickListener onDeleteClickListener) {
        this.habits = habits;
        this.onDeleteClickListener = onDeleteClickListener;
    }

    @NonNull
    @Override
    public HabitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.habit_item, parent, false);
        return new HabitViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HabitViewHolder holder, int position) {
        Habit habit = habits.get(position);
        holder.tvTitle.setText(habit.getTitle());
        holder.tvDescription.setText(habit.getDescription());
        holder.tvDate.setText(habit.getDate());
        holder.btnDelete.setOnClickListener(v -> onDeleteClickListener.onDeleteClick(habit));
    }

    @Override
    public int getItemCount() {
        return habits.size();
    }

    public static class HabitViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDescription, tvDate;
        Button btnDelete;

        public HabitViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvNamaHabit);
            tvDescription = itemView.findViewById(R.id.tvIsiHabit);
            tvDate = itemView.findViewById(R.id.tvTanggal);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(Habit habit);
    }
}

