package ffs.mobile.habituary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ffs.mobile.habituary.model.Habits;

public class GoalsAdapter extends RecyclerView.Adapter<GoalsAdapter.VH> {
    private final Context ctx;
    private List<Habits> habitList;
    public Habits habitsDelete;

    public GoalsAdapter(Context ctx) {
        this.ctx = ctx;
        habitList = new ArrayList<>();
    }

    public void setGoals(List<Habits> habits) {
        this.habitList = habits;
        notifyDataSetChanged();
    }

    public class VH extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView tvYourGoal;
        private final TextView tvGoalName;
        private final TextView tvPeriodList;
        private final TextView tvHabitTypeList;
        private final ImageView btDelete;
        private Habits habits;

        public VH(@NonNull View itemView) {
            super(itemView);
            this.tvYourGoal = itemView.findViewById(R.id.tvYourGoal);
            this.tvGoalName = itemView.findViewById(R.id.tvGoalName);
            this.tvPeriodList = itemView.findViewById(R.id.tvPeriodList);
            this.tvHabitTypeList = itemView.findViewById(R.id.tvHabitTypeList);
            this.btDelete = itemView.findViewById(R.id.ivDelete);
            this.btDelete.setOnClickListener(view -> {
                Toast.makeText(ctx, tvGoalName.getText() + " dihapus", Toast.LENGTH_SHORT).show();
                habitList.remove(habits);
                notifyDataSetChanged();
            });
            itemView.setOnClickListener(this);
        }

        private void setGoalsDipilih(Habits habits) {
            this.habits = habits;
            if (this.habits != null && this.habits.habitDipilih)
                Toast.makeText(ctx, tvGoalName.getText() + " dipilih", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onClick(View view) {
            for (Habits g : habitList)
                g.habitDipilih = false;

            if (this.habits != null) {
                this.habits.habitDipilih = !this.habits.habitDipilih;
                habitsDelete = this.habits.habitDipilih ? this.habits : null;
                notifyDataSetChanged();
            }
        }
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View listGoals = LayoutInflater.from(this.ctx).inflate(R.layout.your_goals, parent, false);
        return new VH(listGoals);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Habits habits = this.habitList.get(position);
        holder.tvYourGoal.setText(habits.getYourGoal());
        holder.tvGoalName.setText(habits.getHabitName());
        holder.tvPeriodList.setText(habits.getPeriod());
        holder.tvHabitTypeList.setText(habits.getHabitType());
        holder.setGoalsDipilih(habits);
    }

    @Override
    public int getItemCount() {
        return this.habitList.size();
    }
}
