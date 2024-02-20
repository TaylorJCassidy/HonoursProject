package com.taylorcassidy.honoursproject.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.SensorManager;
import android.os.Bundle;

import com.taylorcassidy.honoursproject.controllers.AccelerometerController;
import com.taylorcassidy.honoursproject.databinding.ActivityMainBinding;
import com.taylorcassidy.honoursproject.filter.factories.FIRFactory;
import com.taylorcassidy.honoursproject.filter.filters.coefficients.Lowpass;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private AccelerometerController accelerometerController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        accelerometerController = new AccelerometerController((SensorManager) getSystemService(SENSOR_SERVICE), getBaseContext(), new FIRFactory(Lowpass.COEFFICIENTS));
    }

    public AccelerometerController getAccelerationController() {
        return accelerometerController;
    }

}