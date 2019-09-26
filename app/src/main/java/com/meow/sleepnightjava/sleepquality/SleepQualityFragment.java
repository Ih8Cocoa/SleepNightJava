package com.meow.sleepnightjava.sleepquality;


import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meow.sleepnightjava.R;
import com.meow.sleepnightjava.database.SleepDao;
import com.meow.sleepnightjava.database.SleepDatabase;
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

        // Get the nightId from the argument
        final Bundle bundle = Objects.requireNonNull(getArguments());
        final long nightId = SleepQualityFragmentArgs.fromBundle(bundle).getNightId();

        // find the DAO and make the ViewModel from it
        final SleepDao dataSource = SleepDatabase.getInstance(app).sleepDao();
        final SleepQualityViewModelFactory factory =
                new SleepQualityViewModelFactory(dataSource, nightId);
        final SleepQualityViewModel viewModel = ViewModelProviders.of(this, factory)
                .get(SleepQualityViewModel.class);

        // set the ViewModel variable inside the XML file
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        // Add an observer to navigate back once the signal's fired
        final Observer<Boolean> navigateBackObserver = signal -> {
            if (signal) {
                final NavDirections toTracker = SleepQualityFragmentDirections
                        .actionSleepQualityFragmentToSleepTrackerFragment();
                NavHostFragment.findNavController(this).navigate(toTracker);
                // completed the navigation
                viewModel.doneNavigating();
            }
        };
        viewModel.getNavigateBackToTracker().observe(this, navigateBackObserver);

        // no idea what to do
        return binding.getRoot();
    }

}
