package ffs.mobile.habituary;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class AddHabit extends AppCompatActivity {

    private Button btCreateNew;
    private EditText ptYourHabit;
    private EditText ptHabitName;
    private Spinner spinnerPeriod;
    private Spinner spinnerHabitType;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit);

        this.btCreateNew = this.findViewById(R.id.btCreateNew);
        this.ptYourHabit = this.findViewById(R.id.ptYourHabit);
        this.ptHabitName = this.findViewById(R.id.ptHabitName);
        this.spinnerPeriod = this.findViewById(R.id.spinnerPeriod);

        spinnerPeriod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String text = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(AddHabit.this, text, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ArrayList<String> arrayListPeriod = new ArrayList<>();
        arrayListPeriod.add("1 Year (365 days)");
        arrayListPeriod.add("1 Month (30 days)");
        arrayListPeriod.add("1 Week (7 days)");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrayListPeriod);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPeriod.setAdapter(adapter);

        this.spinnerHabitType = this.findViewById(R.id.spinnerHabitType);

        spinnerPeriod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String text = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(AddHabit.this, text, Toast.LENGTH_SHORT).show();

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
        ArrayAdapter<String> adapterListHabitType = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrayListHabitType);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHabitType.setAdapter(adapterListHabitType);

        btCreateNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AddHabit.this, MainActivity.class);
                i.putExtra("yourGoal", ptYourHabit.getText().toString());
                i.putExtra("habitName", ptHabitName.getText().toString());
                i.putExtra("period", spinnerPeriod.getSelectedItem().toString());
                i.putExtra("habitType", spinnerHabitType.getSelectedItem().toString());
                startActivity(i);
            }
        });
    }
}