package com.meow.sleepnightjava.sleeptracker;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meow.sleepnightjava.R;
import com.meow.sleepnightjava.databinding.FragmentSleepTrackerBinding;


/**
 * A simple {@link Fragment} subclass.
 */
public class SleepTrackerFragment extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // use data binding
        final FragmentSleepTrackerBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_sleep_tracker, container, false
        );

        return binding.getRoot();
    }

}
