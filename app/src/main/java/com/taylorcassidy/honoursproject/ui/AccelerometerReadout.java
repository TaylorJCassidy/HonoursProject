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

public class AccelerometerReadout extends Fragment {

    private FragmentAccelerometerReadoutBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAccelerometerReadoutBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.navDisplacement.setOnClickListener(l -> NavHostFragment.findNavController(AccelerometerReadout.this)
                .navigate(R.id.action_accelerometerReadout_to_displacementReadout));

        displayAccelerometerReadouts();
    }

    @SuppressLint("ClickableViewAccessibility")
    public void displayAccelerometerReadouts() {
        AccelerometerController accelerometerController = ((MainActivity) getActivity()).getAccelerationController();

        binding.measureAcceleration.setOnTouchListener((v, event) -> {
            v.performClick();
            switch(event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    accelerometerController.registerAccelerometerListener(acceleration -> {
                        binding.x.setText(String.valueOf(acceleration.getX()));
                        binding.y.setText(String.valueOf(acceleration.getY()));
                        binding.z.setText(String.valueOf(acceleration.getZ()));
                    });
                    return true;
                case MotionEvent.ACTION_UP:
                    accelerometerController.unregisterAccelerometerListener();
                    return true;
            }
            return false;
        });
    }
}