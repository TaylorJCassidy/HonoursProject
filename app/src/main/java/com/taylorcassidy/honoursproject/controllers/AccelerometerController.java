package com.taylorcassidy.honoursproject.controllers;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.taylorcassidy.honoursproject.filter.FilterFactory;
import com.taylorcassidy.honoursproject.filter.Vector3FilterChainer;
import com.taylorcassidy.honoursproject.models.Vector3;

import java.util.function.BiConsumer;

public class AccelerometerController {
    private final SensorManager sensorManager;
    private final Sensor accelerometer;
    private FilterFactory.FilterTypes filterType;
    private SensorEventListener accelerometerListener;
    private Vector3 initialAcceleration;
    private long previousTimestamp;

    public AccelerometerController(SensorManager sensorManager, FilterFactory.FilterTypes filterType) {
        this.sensorManager = sensorManager;
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.filterType = filterType;
    }

    public void registerAccelerometerListener(BiConsumer<Vector3, Long> consumer) {
        accelerometerListener = new SensorEventListener() {
            final Vector3FilterChainer filterChain = new Vector3FilterChainer.Builder().withFilterType(filterType).build();
            @Override
            public void onSensorChanged(SensorEvent event) {
                final Vector3 rawAcceleration = new Vector3(event.values, event.timestamp);
                if (initialAcceleration == null) {
                    initialAcceleration = rawAcceleration;
                    previousTimestamp = event.timestamp; //if first reading, make deltaT 0
                }

                final Vector3 filteredAcceleration = filterChain.filter(rawAcceleration.subtract(initialAcceleration));
                final long deltaT = event.timestamp - previousTimestamp;
                consumer.accept(filteredAcceleration, deltaT);
                previousTimestamp = event.timestamp;
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

    public FilterFactory.FilterTypes getFilterType() {
        return filterType;
    }

    public void setFilterType(FilterFactory.FilterTypes filterType) {
        this.filterType = filterType;
    }
}
