package com.meow.sleepnightjava.sleepquality;


import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meow.sleepnightjava.R;
import com.meow.sleepnightjava.databinding.FragmentSleepQualityBinding;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class SleepQualityFragment extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // use data binding
        final FragmentSleepQualityBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_sleep_quality, container, false
        );
        final Activity currentActivity = getActivity();
        final Application app = Objects.requireNonNull(currentActivity).getApplication();

        // no idea what to do
        return binding.getRoot();
    }

}
