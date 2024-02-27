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
import com.taylorcassidy.honoursproject.controllers.FileController;
import com.taylorcassidy.honoursproject.databinding.FragmentAccelerometerReadoutBinding;
import com.taylorcassidy.honoursproject.controllers.AccelerometerController;
import com.taylorcassidy.honoursproject.filter.FilterFactory;
import com.taylorcassidy.honoursproject.helpers.SpinnerHelper;
import com.taylorcassidy.honoursproject.models.Vector3;

import java.util.List;

public class AccelerometerReadout extends Fragment {
    private static final String HEADER_LINE = "filteredX,filteredY,filteredZ";

    private FragmentAccelerometerReadoutBinding binding;
    private AccelerometerController accelerometerController;
    private FileController fileController;
    private boolean writeToFile;

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

        accelerometerController = ((MainActivity) getActivity()).getAccelerationController();
        fileController = new FileController(getContext());

        bindWriteToFileSwitch();
        populateSpinners();
        displayAccelerometerReadouts();
    }

    private void bindWriteToFileSwitch() {
        binding.recordAcceleration.setOnCheckedChangeListener((buttonView, isChecked) -> writeToFile = isChecked);
    }

    private void populateSpinners() {
        SpinnerHelper.populate(getView().findViewById(R.id.filterSpinner1), List.of(FilterFactory.FilterTypes.values()), (filter) -> accelerometerController.getFilterTypes().set(0, filter), accelerometerController.getFilterTypes().get(0));
        SpinnerHelper.populate(getView().findViewById(R.id.filterSpinner2), List.of(FilterFactory.FilterTypes.values()), (filter) -> accelerometerController.getFilterTypes().set(1, filter), accelerometerController.getFilterTypes().get(1));
    }

    @SuppressLint("ClickableViewAccessibility")
    private void displayAccelerometerReadouts() {
        binding.measureAcceleration.setOnTouchListener((v, event) -> {
            v.performClick();
            switch(event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (writeToFile) {
                        fileController.open(HEADER_LINE, "acceleration");
                        accelerometerController.registerAccelerometerListener(this::bindUIWithFileWrite);
                    }
                    else {
                        accelerometerController.registerAccelerometerListener(this::bindUI);
                    }
                    return true;
                case MotionEvent.ACTION_UP:
                    accelerometerController.unregisterAccelerometerListener();
                    if (writeToFile) fileController.close();
                    return true;
            }
            return false;
        });
    }

    private void bindUIWithFileWrite(Vector3 acceleration) {
        fileController.write(acceleration.toCSV());
        bindUI(acceleration);
    }

    private void bindUI(Vector3 acceleration) {
        binding.x.setText(String.valueOf(acceleration.getX()));
        binding.y.setText(String.valueOf(acceleration.getY()));
        binding.z.setText(String.valueOf(acceleration.getZ()));
    }
}