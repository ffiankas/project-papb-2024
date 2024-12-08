package ffs.mobile.habituary;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import ffs.mobile.habituary.Fragment.AddHabitFragment;
import ffs.mobile.habituary.Fragment.GetHabitFragment;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.fm = this.getSupportFragmentManager();
        FragmentTransaction ft = this.fm.beginTransaction();
        ft.add(R.id.container_bawah, new GetHabitFragment(), "GHF");
        ft.add(R.id.container_atas, new AddHabitFragment(), "AHF");
        ft.commit();
    }
}
