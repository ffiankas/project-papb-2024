package ffs.mobile.habituary.Firebase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

import ffs.mobile.habituary.R;
import ffs.mobile.habituary.model.Habits;

public class AddHabitFirebaseAdapter extends RecyclerView.Adapter {

    private Context ctx;
    private List<AddHabitFirebase> dataset;
    private DatabaseReference appDb;

    public AddHabitFirebaseAdapter(Context ctx, List<AddHabitFirebase> dataset) {
        this.ctx = ctx;
        this.dataset = dataset;
    }

    public void setAppDb(DatabaseReference appDb) {
        this.appDb = appDb;
    }

    public class VH extends RecyclerView.ViewHolder {
        private TextView tvYourGoal;
        private TextView tvGoalName;
        private TextView tvPeriodList;
        private TextView tvHabitTypeList;
        private ImageView btDelete;
        private AddHabitFirebase habits;


        public VH(@NonNull View itemView) {
            super(itemView);
            this.tvYourGoal = itemView.findViewById(R.id.tvYourGoal);
            this.tvGoalName = itemView.findViewById(R.id.tvGoalName);
            this.tvPeriodList = itemView.findViewById(R.id.tvPeriodList);
            this.tvHabitTypeList = itemView.findViewById(R.id.tvHabitTypeList);
            this.btDelete = itemView.findViewById(R.id.ivDelete);

            this.btDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                Toast.makeText(ctx, habits.getHabitName() + " dihapus", Toast.LENGTH_SHORT).show();
                appDb.child(habits.getId()).removeValue();
            }
        });
        }

        public void bind(AddHabitFirebase habits) {
            this.habits = habits;
            this.tvYourGoal.setText(habits.getYourGoal());
            this.tvGoalName.setText(habits.getHabitName());
            this.tvPeriodList.setText(habits.getPeriod());
            this.tvHabitTypeList.setText(habits.getHabitType());
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View listGoals = LayoutInflater.from(this.ctx).inflate(R.layout.your_goals, parent, false);
        return new VH(listGoals);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        VH vh = (VH) holder;
        vh.bind(dataset.get(position));
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

}
