package com.taylorcassidy.honoursproject.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.SensorManager;
import android.os.Bundle;

import com.taylorcassidy.honoursproject.controllers.AccelerometerController;
import com.taylorcassidy.honoursproject.controllers.DisplacementController;
import com.taylorcassidy.honoursproject.controllers.FileController;
import com.taylorcassidy.honoursproject.controllers.GyroscopeController;
import com.taylorcassidy.honoursproject.controllers.VelocityController;
import com.taylorcassidy.honoursproject.databinding.ActivityMainBinding;
import com.taylorcassidy.honoursproject.filter.FilterFactory;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private AccelerometerController accelerometerController;
    private VelocityController velocityController;
    private DisplacementController displacementController;
    private GyroscopeController gyroscopeController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        gyroscopeController = new GyroscopeController((SensorManager) getSystemService(SENSOR_SERVICE), List.of(FilterFactory.FilterTypes.NONE));
        accelerometerController = new AccelerometerController((SensorManager) getSystemService(SENSOR_SERVICE), new FileController(getBaseContext()), gyroscopeController, Arrays.asList(FilterFactory.FilterTypes.NONE, FilterFactory.FilterTypes.NONE));
        velocityController = new VelocityController(accelerometerController, new FileController(getBaseContext()), Arrays.asList(FilterFactory.FilterTypes.NONE));
        displacementController = new DisplacementController(velocityController, new FileController(getBaseContext()));
    }

    public AccelerometerController getAccelerationController() {
        return accelerometerController;
    }

    public VelocityController getVelocityController() {
        return velocityController;
    }

    public DisplacementController getDisplacementController() {
        return displacementController;
    }

    public GyroscopeController getGyroscopeController() {
        return gyroscopeController;
    }
}