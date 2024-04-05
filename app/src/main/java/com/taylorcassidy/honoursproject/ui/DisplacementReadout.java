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
import com.taylorcassidy.honoursproject.controllers.DisplacementController;
import com.taylorcassidy.honoursproject.databinding.FragmentDisplacementReadoutBinding;
import com.taylorcassidy.honoursproject.models.Vector3;

public class DisplacementReadout extends Fragment {

    private FragmentDisplacementReadoutBinding binding;
    DisplacementController displacementController;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDisplacementReadoutBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.navDisVel.setOnClickListener(l -> NavHostFragment.findNavController(DisplacementReadout.this)
                .navigate(R.id.action_displacementReadout_to_velocityReadout));

        displacementController = ((MainActivity) getActivity()).getDisplacementController();

        bindWriteToFileSwitch();
        displayDisplacementReadouts();
    }

    private void bindWriteToFileSwitch() {
        binding.recordDisplacement.setChecked(displacementController.shouldLogToFile());
        binding.recordDisplacement.setOnCheckedChangeListener((buttonView, isChecked) -> displacementController.setShouldLogToFile(isChecked));
    }

    @SuppressLint("ClickableViewAccessibility")
    public void displayDisplacementReadouts() {
        binding.measureDisplacement.setOnTouchListener((v, event) -> {
            v.performClick();
            switch(event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    displacementController.registerDisplacementListener(this::bindUI);
                    return true;
                case MotionEvent.ACTION_UP:
                    displacementController.unregisterDisplacementController();
                    return true;
            }
            return false;
        });
    }

    private void bindUI(Vector3 displacement) {
        binding.x.setText(String.valueOf(displacement.getX()));
        binding.y.setText(String.valueOf(displacement.getY()));
        binding.z.setText(String.valueOf(displacement.getZ()));
        binding.total.setText(String.valueOf(displacement.magnitude()));
    }
}