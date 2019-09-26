package com.meow.sleepnightjava.sleeptracker;


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

import com.google.android.material.snackbar.Snackbar;
import com.meow.sleepnightjava.R;
import com.meow.sleepnightjava.database.SleepDao;
import com.meow.sleepnightjava.database.SleepDatabase;
import com.meow.sleepnightjava.database.SleepNight;
import com.meow.sleepnightjava.databinding.FragmentSleepTrackerBinding;

import java.util.Objects;


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

        // try getting the data source and create the ViewModel
        final Application currentApp = Objects.requireNonNull(getActivity()).getApplication();
        final SleepDao dataSource = SleepDatabase.getInstance(currentApp).sleepDao();
        final SleepTrackerViewModelFactory factory =
                new SleepTrackerViewModelFactory(dataSource, currentApp);

        final SleepTrackerViewModel viewModel = ViewModelProviders.of(this, factory)
                .get(SleepTrackerViewModel.class);

        // set the ViewModel variable inside the XML file
        binding.setViewModel(viewModel);

        // and set this fragment class as the lifecycle owner
        binding.setLifecycleOwner(this);

        // register an observer that will navigate to SleepQuality fragment
        // when the signal is fired
        final Observer<SleepNight> sqNavigationObserver = night -> {
            if (night == null) {
                return;
            }
            final long nightId = night.getNightId();
            final NavDirections toSQFragment = SleepTrackerFragmentDirections
                    .actionSleepTrackerFragmentToSleepQualityFragment(nightId);
            NavHostFragment.findNavController(this).navigate(toSQFragment);
            viewModel.doneNavigating();
        };
        viewModel.getNavigateToSleepQuality().observe(this, sqNavigationObserver);

        // Observe and show the snackbar when the event signal is fired
        final Observer<Boolean> snackbarObserver = snackbarState -> {
            if (snackbarState) {
                final Activity currentActivity = Objects.requireNonNull(getActivity());
                final View contentView = currentActivity.findViewById(android.R.id.content);
                final String clearedMsg = getString(R.string.cleared_message);
                Snackbar.make(contentView, clearedMsg, Snackbar.LENGTH_SHORT).show();
                viewModel.doneShowingSnackbar();
            }
        };
        viewModel.getShowSnackbarEvent().observe(this, snackbarObserver);

        return binding.getRoot();
    }

}
