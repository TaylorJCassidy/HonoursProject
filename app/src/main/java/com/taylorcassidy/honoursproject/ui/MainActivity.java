package com.taylorcassidy.honoursproject.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.SensorManager;
import android.os.Bundle;

import com.taylorcassidy.honoursproject.controllers.AccelerometerController;
import com.taylorcassidy.honoursproject.controllers.VelocityController;
import com.taylorcassidy.honoursproject.databinding.ActivityMainBinding;
import com.taylorcassidy.honoursproject.filter.FilterFactory;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private AccelerometerController accelerometerController;
    private VelocityController velocityController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        accelerometerController = new AccelerometerController((SensorManager) getSystemService(SENSOR_SERVICE), Arrays.asList(FilterFactory.FilterTypes.NONE, FilterFactory.FilterTypes.NONE));
        velocityController = new VelocityController(accelerometerController, Arrays.asList(FilterFactory.FilterTypes.NONE));
    }

    public AccelerometerController getAccelerationController() {
        return accelerometerController;
    }

    public VelocityController getVelocityController() {
        return velocityController;
    }
}