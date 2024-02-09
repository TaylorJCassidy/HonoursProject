package com.taylorcassidy.honoursproject.ui;

import static android.content.Context.SENSOR_SERVICE;

import android.annotation.SuppressLint;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.taylorcassidy.honoursproject.databinding.FragmentAccelerometerReadoutBinding;
import com.taylorcassidy.honoursproject.controllers.AccelerometerController;
import com.taylorcassidy.honoursproject.filter.factories.FIRFactory;

public class AccelerometerReadout extends Fragment {

    private FragmentAccelerometerReadoutBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAccelerometerReadoutBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @SuppressLint("ClickableViewAccessibility") //TODO maybe fix
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AccelerometerController accelerometerController =
                new AccelerometerController((SensorManager) requireActivity().getSystemService(SENSOR_SERVICE), getContext(), new FIRFactory());

        binding.measure.setOnTouchListener((v, event) -> {
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