package ag.mobile.habituaryfirebase;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    public static final String
            DBURL = "https://habituary-edit-default-rtdb.asia-southeast1.firebasedatabase.app/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseDatabase database = FirebaseDatabase.getInstance(DBURL);
        DatabaseReference myRef = database.getReference("habits");
        HabitFragment habitFragment = new HabitFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, habitFragment)
                .commit();
    }
}