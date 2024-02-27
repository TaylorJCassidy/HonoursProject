package com.taylorcassidy.honoursproject.controllers;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.taylorcassidy.honoursproject.filter.FilterFactory;
import com.taylorcassidy.honoursproject.filter.Vector3FilterChainer;
import com.taylorcassidy.honoursproject.models.Vector3;

import java.util.List;
import java.util.function.Consumer;

public class AccelerometerController {
    private final SensorManager sensorManager;
    private final Sensor accelerometer;

    private List<FilterFactory.FilterTypes> filterTypes;
    private SensorEventListener accelerometerListener;
    private Vector3 initialAcceleration;

    public AccelerometerController(SensorManager sensorManager, List<FilterFactory.FilterTypes> filterTypes) {
        this.sensorManager = sensorManager;
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.filterTypes = filterTypes;
    }

    public void registerAccelerometerListener(Consumer<Vector3> consumer) {
        accelerometerListener = new SensorEventListener() {
            final Vector3FilterChainer filter = new Vector3FilterChainer.Builder().withFilterTypes(filterTypes).build();
            @Override
            public void onSensorChanged(SensorEvent event) {
                final Vector3 rawAcceleration = new Vector3(event.values, event.timestamp);
                if (initialAcceleration == null) initialAcceleration = rawAcceleration;
                final Vector3 filteredAcceleration = filter.filter(rawAcceleration.subtract(initialAcceleration));
                consumer.accept(filteredAcceleration);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                System.out.println(sensor.getName());
            }
        };

        sensorManager.registerListener(accelerometerListener, accelerometer, SensorManager.SENSOR_DELAY_FASTEST);
    }

    public void unregisterAccelerometerListener() {
        initialAcceleration = null;
        sensorManager.unregisterListener(accelerometerListener, accelerometer);
    }

    public List<FilterFactory.FilterTypes> getFilterTypes() {
        return filterTypes;
    }

    public void setFilterTypes(List<FilterFactory.FilterTypes> filterTypes) {
        this.filterTypes = filterTypes;
    }

}
