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
import com.taylorcassidy.honoursproject.controllers.VelocityController;
import com.taylorcassidy.honoursproject.databinding.FragmentVelocityReadoutBinding;
import com.taylorcassidy.honoursproject.filter.FilterFactory;
import com.taylorcassidy.honoursproject.filter.Vector3FilterChainer;
import com.taylorcassidy.honoursproject.helpers.SpinnerHelper;
import com.taylorcassidy.honoursproject.models.Vector3;

import java.util.List;

public class VelocityReadout extends Fragment {

    private FragmentVelocityReadoutBinding binding;
    private VelocityController velocityController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentVelocityReadoutBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.navVelAcc.setOnClickListener(l -> NavHostFragment.findNavController(VelocityReadout.this)
                .navigate(R.id.action_velocityReadout_to_accelerometerReadout));
        binding.navVelDis.setOnClickListener(l -> NavHostFragment.findNavController(VelocityReadout.this)
                .navigate(R.id.action_velocityReadout_to_displacementReadout));

        velocityController = ((MainActivity) getActivity()).getVelocityController();

        displayVelocityReadouts();
        populateSpinner();
        bindWriteToFileSwitch();
    }

    private void populateSpinner() {
        final List<FilterFactory.FilterTypes> filterTypes = List.of(FilterFactory.FilterTypes.values());
        Vector3FilterChainer filterChainer = velocityController.getFilterChainer();

        SpinnerHelper.populate(getView().findViewById(R.id.filterSpinner1), filterTypes, (filter) -> filterChainer.setFilter(filter, 0), filterChainer.getFilter(0));
    }

    private void bindWriteToFileSwitch() {
        binding.recordVelocity.setChecked(velocityController.shouldLogToFile());
        binding.recordVelocity.setOnCheckedChangeListener((buttonView, isChecked) -> velocityController.setShouldLogToFile(isChecked));
    }

    @SuppressLint("ClickableViewAccessibility")
    private void displayVelocityReadouts() {
        binding.measureVelocity.setOnTouchListener((v, event) -> {
            v.performClick();
            switch(event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    velocityController.registerVelocityListener(this::bindUI);
                    return true;
                case MotionEvent.ACTION_UP:
                    velocityController.unregisterVelocityListener();
                    return true;
            }
            return false;
        });
    }

    private void bindUI(Vector3 velocity, Long deltaT) {
        binding.x.setText(String.valueOf(velocity.getX()));
        binding.y.setText(String.valueOf(velocity.getY()));
        binding.z.setText(String.valueOf(velocity.getZ()));
    }
}