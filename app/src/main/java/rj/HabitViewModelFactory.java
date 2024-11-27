package com.example.habbit;

import android.app.Application;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class HabitViewModelFactory implements ViewModelProvider.Factory {

    private final Application application;

    public HabitViewModelFactory(Application application) {
        this.application = application;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new HabitViewModel(application);
    }
}
