package com.taylorcassidy.honoursproject.ui;

import static android.content.Context.SENSOR_SERVICE;

import android.annotation.SuppressLint;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.taylorcassidy.honoursproject.R;
import com.taylorcassidy.honoursproject.controllers.AccelerometerController;
import com.taylorcassidy.honoursproject.controllers.DisplacementController;
import com.taylorcassidy.honoursproject.databinding.FragmentDisplacementReadoutBinding;
import com.taylorcassidy.honoursproject.filter.factories.FIRFactory;
import com.taylorcassidy.honoursproject.filter.filters.coefficients.Lowpass;

public class DisplacementReadout extends Fragment {

    private FragmentDisplacementReadoutBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDisplacementReadoutBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.navAcceleration.setOnClickListener(l -> NavHostFragment.findNavController(DisplacementReadout.this)
                .navigate(R.id.action_displacementReadout_to_accelerometerReadout));

        displayDisplacementReadouts();
    }

    @SuppressLint("ClickableViewAccessibility")
    public void displayDisplacementReadouts() {
        AccelerometerController accelerometerController =
                new AccelerometerController((SensorManager) requireActivity().getSystemService(SENSOR_SERVICE), getContext(), new FIRFactory(Lowpass.COEFFICIENTS));

        DisplacementController displacementController = new DisplacementController(accelerometerController);

        binding.measureDisplacement.setOnTouchListener((v, event) -> {
            v.performClick();
            switch(event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    displacementController.registerDisplacementListener(displacement -> {
                        binding.x.setText(String.valueOf(displacement.getX()));
                        binding.y.setText(String.valueOf(displacement.getY()));
                        binding.z.setText(String.valueOf(displacement.getZ()));
                    });
                    return true;
                case MotionEvent.ACTION_UP:
                    displacementController.unregisterDisplacementController();
                    return true;
            }
            return false;
        });
    }
}