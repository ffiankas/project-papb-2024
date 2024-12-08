package ffs.mobile.habituary.SqliteRoom;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ffs.mobile.habituary.R;
import ffs.mobile.habituary.model.Habits;

public class HabitRoomAdapter extends RecyclerView.Adapter {

    private Context ctx;
    private List<Habit> dataset = new ArrayList<>();

    public HabitRoomAdapter(Context ctx){
        this.ctx = ctx;
    }

    public void setHabit(List<Habit> habit) {
        this.dataset = habit;
        notifyDataSetChanged();
    }

    private class VH extends RecyclerView.ViewHolder {
        private HabitDAO habitDAO;
        private HabitDatabase db;
        private TextView tvYourGoal;
        private TextView tvGoalName;
        private TextView tvPeriodList;
        private TextView tvHabitTypeList;
        private ImageView btDelete;
        private Habit habit;

        public VH(@NonNull View itemView) {
            super(itemView);
            this.tvYourGoal = itemView.findViewById(R.id.tvYourGoal);
            this.tvGoalName = itemView.findViewById(R.id.tvGoalName);
            this.tvPeriodList = itemView.findViewById(R.id.tvPeriodList);
            this.tvHabitTypeList = itemView.findViewById(R.id.tvHabitTypeList);
            this.btDelete = itemView.findViewById(R.id.ivDelete);
            this.btDelete.setOnClickListener(view -> {
                this.db = Room.databaseBuilder(ctx, HabitDatabase.class, "db-habit").build();
                this.habitDAO = db.habitDAO();
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Habit habit = dataset.get(position);

                    ExecutorService executor = Executors.newSingleThreadExecutor();
                    executor.execute(() -> {
                        habitDAO.delete(habit);

                        new Handler(Looper.getMainLooper()).post(() -> {
                            dataset.remove(position);
                            notifyItemRemoved(position);
                            Toast.makeText(ctx, habit.habitName + " dihapus", Toast.LENGTH_SHORT).show();
                        });
                    });
                }
            });
        }
        private void bind(Habit h){
            tvYourGoal.setText(h.yourGoal);
            tvGoalName.setText(h.habitName);
            tvPeriodList.setText(h.period);
            tvHabitTypeList.setText(h.habitType);
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx)
                .inflate(R.layout.your_goals, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        VH vh = (VH) holder;
        vh.bind(this.dataset.get(position));
    }

    @Override
    public int getItemCount() {
        return this.dataset.size();
    }
}
