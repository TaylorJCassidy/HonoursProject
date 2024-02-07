package com.taylorcassidy.honoursproject;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import com.taylorcassidy.honoursproject.filter.factories.FIRFactory;
import com.taylorcassidy.honoursproject.filter.FilterHelper;
import com.taylorcassidy.honoursproject.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        FilterHelper filter = new FilterHelper(new FIRFactory());

        SensorEventListener sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float[] values = filter.filter(event.values);
                binding.X.setText(String.valueOf(values[0]));
                binding.Y.setText(String.valueOf(values[1]));
                binding.Z.setText(String.valueOf(values[2]));
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                System.out.println(sensor.getName());
            }
        };

        sensorManager.registerListener(sensorEventListener, accelerometer, SensorManager.SENSOR_DELAY_FASTEST);
    }

}