package com.meow.sleepnightjava.sleeptracker;


import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.meow.sleepnightjava.R;
import com.meow.sleepnightjava.database.SleepDao;
import com.meow.sleepnightjava.database.SleepDatabase;
import com.meow.sleepnightjava.database.SleepNight;
import com.meow.sleepnightjava.databinding.FragmentSleepTrackerBinding;

import java.util.List;
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
        final Activity currentActivity = Objects.requireNonNull(getActivity());
        final Application currentApp = currentActivity.getApplication();
        final SleepDao dataSource = SleepDatabase.getInstance(currentApp).sleepDao();
        final SleepTrackerViewModelFactory factory =
                new SleepTrackerViewModelFactory(dataSource, currentApp);

        final SleepTrackerViewModel viewModel = ViewModelProviders.of(this, factory)
                .get(SleepTrackerViewModel.class);

        // set the ViewModel variable inside the XML file
        binding.setViewModel(viewModel);

        // and set this fragment class as the lifecycle owner
        binding.setLifecycleOwner(this);
        setupGridLayoutManager(binding, currentActivity);

        setupObservers(binding, viewModel, currentActivity);

        return binding.getRoot();
    }

    private void setupGridLayoutManager(
            @NonNull FragmentSleepTrackerBinding binding,
            Activity currentActivity
    ) {
        // setup a new GridLayoutManager with 3 cols
        final GridLayoutManager manager = new GridLayoutManager(currentActivity, 3);
        // then set it as sleepList's layoutManager
        binding.sleepList.setLayoutManager(manager);
    }

    private void setupObservers(
            @NonNull FragmentSleepTrackerBinding binding,
            @NonNull SleepTrackerViewModel viewModel,
            @NonNull Activity activity
    ) {
        // first, define our ClickListener that signals the navigation
        // to the SleepDetail fragment
        final SleepNightListener listener = viewModel::onSleepNightClicked;
        // create a new adapter, and set the adapter of the RecyclerView inside the XML,
        // passing in the listener in the process
        final SleepNightAdapter adapter = new SleepNightAdapter(listener);
        binding.sleepList.setAdapter(adapter);

        // also observe for changes in the "nights" variable, and pass changes to the adapter
        final Observer<List<SleepNight>> nightObserver = night -> {
            if (night != null) {
                // Submit the list to the adapter
                adapter.submitList(night);
            }
        };
        viewModel.getNights().observe(this, nightObserver);

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

        // add an observer to observe the signal to navigate to the SleepDetails fragment
        final Observer<Long> sleepDetailsObserver = nightId -> {
            if (nightId == null) {
                return;
            }
            final NavDirections toSleepDetails = SleepTrackerFragmentDirections
                    .actionSleepTrackerFragmentToSleepDetailFragment(nightId);
            NavHostFragment.findNavController(this).navigate(toSleepDetails);
            // complete the navigation
            viewModel.onSleepDetailsNavigated();
        };
        viewModel.getNavigateToSleepDetails().observe(this, sleepDetailsObserver);
    }

}
