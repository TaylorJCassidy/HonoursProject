package com.taylorcassidy.honoursproject.ui;


import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.taylorcassidy.honoursproject.R;
import com.taylorcassidy.honoursproject.databinding.FragmentAccelerometerReadoutBinding;
import com.taylorcassidy.honoursproject.controllers.AccelerometerController;
import com.taylorcassidy.honoursproject.filter.FilterFactory;
import com.taylorcassidy.honoursproject.filter.Vector3FilterChainer;
import com.taylorcassidy.honoursproject.helpers.SpinnerHelper;
import com.taylorcassidy.honoursproject.models.Vector3;

import java.util.List;

public class AccelerometerReadout extends Fragment {

    private FragmentAccelerometerReadoutBinding binding;
    private AccelerometerController accelerometerController;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAccelerometerReadoutBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.navAccVel.setOnClickListener(l -> NavHostFragment.findNavController(AccelerometerReadout.this)
                .navigate(R.id.action_accelerometerReadout_to_velocityReadout));

        accelerometerController = ((MainActivity) getActivity()).getAccelerationController();

        bindSwitches();
        populateSpinners();
        displayAccelerometerReadouts();
    }

    private void bindSwitches() {
        binding.recordAcceleration.setChecked(accelerometerController.shouldLogToFile());
        binding.recordAcceleration.setOnCheckedChangeListener((buttonView, isChecked) -> accelerometerController.setShouldLogToFile(isChecked));

        binding.useGyro.setChecked(accelerometerController.shouldUseGyroscope());
        binding.useGyro.setOnCheckedChangeListener((buttonView, isChecked) -> accelerometerController.setShouldUseGyroscope(isChecked));
    }

    private void populateSpinners() {
        final List<FilterFactory.FilterTypes> filterTypes = List.of(FilterFactory.FilterTypes.values());
        Vector3FilterChainer filterChainer = accelerometerController.getFilterChainer();

        SpinnerHelper.populate(getView().findViewById(R.id.filterSpinner1), filterTypes, (filter) -> filterChainer.setFilter(filter, 0), filterChainer.getFilter(0));
        SpinnerHelper.populate(getView().findViewById(R.id.filterSpinner2), filterTypes, (filter) -> filterChainer.setFilter(filter, 1), filterChainer.getFilter(1));
    }

    @SuppressLint("ClickableViewAccessibility")
    private void displayAccelerometerReadouts() {
        binding.measureAcceleration.setOnTouchListener((v, event) -> {
            v.performClick();
            switch(event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    accelerometerController.registerAccelerometerListener(this::bindUI);
                    return true;
                case MotionEvent.ACTION_UP:
                    accelerometerController.unregisterAccelerometerListener();
                    return true;
            }
            return false;
        });
    }

    private void bindUI(Vector3 acceleration, Long deltaT) {
        binding.x.setText(String.valueOf(acceleration.getX()));
        binding.y.setText(String.valueOf(acceleration.getY()));
        binding.z.setText(String.valueOf(acceleration.getZ()));
    }
}