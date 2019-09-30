package com.meow.sleepnightjava.sleepdetail;

import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.meow.sleepnightjava.databinding.FragmentSleepDetailBinding;

import java.util.Objects;

public class SleepDetailFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // inflate the layout using data binding
        final FragmentSleepDetailBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_sleep_detail, container, false
        );

        // get current app and object
        final Application app = Objects.requireNonNull(
                getActivity(), "current activity is null"
        )
                .getApplication();
        final Bundle bundle = Objects.requireNonNull(getArguments(), "arguments are null");
        // get the arguments passed into this fragment
        final long nightId = SleepDetailFragmentArgs.fromBundle(bundle).getNightId();

        // then create a data source and pass it into a new ViewModel
        final SleepDao dao = SleepDatabase.getInstance(app).sleepDao();
        final SleepDetailViewModelFactory factory = new SleepDetailViewModelFactory(nightId, dao);
        final SleepDetailViewModel viewModel = ViewModelProviders.of(this, factory)
                .get(SleepDetailViewModel.class);

        // set the ViewModel and LifecycleOwner for XML
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        attachObservers(viewModel);

        return binding.getRoot();
    }

    private void attachObservers(final SleepDetailViewModel viewModel) {
        final Observer<Boolean> goBackButtonObserver = clicked -> {
            if (clicked) {
                final NavDirections backHome = SleepDetailFragmentDirections
                        .actionSleepDetailFragmentToSleepTrackerFragment();
                NavHostFragment.findNavController(this).navigate(backHome);
                // inform that the navigation is completed
                viewModel.doneNavigating();
            }
        };
        viewModel.getNavigateBack().observe(this, goBackButtonObserver);
    }
}
