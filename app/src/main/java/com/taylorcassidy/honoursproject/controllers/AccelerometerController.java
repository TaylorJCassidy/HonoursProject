package com.taylorcassidy.honoursproject.controllers;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.taylorcassidy.honoursproject.filter.FilterFactory;
import com.taylorcassidy.honoursproject.filter.Vector3FilterChainer;
import com.taylorcassidy.honoursproject.models.Vector3;

import java.util.List;
import java.util.function.BiConsumer;

public class AccelerometerController {
    private final SensorManager sensorManager;
    private final Sensor accelerometer;
    private final FileController fileController;
    private final GyroscopeController gyroscopeController;

    private final List<FilterFactory.FilterTypes> filterTypes;
    private SensorEventListener accelerometerListener;
    private Vector3 gravityVector;
    private long previousTimestamp;
    private boolean shouldLogToFile = false;
    private boolean useGyroscope = false;

    public AccelerometerController(SensorManager sensorManager, FileController fileController, GyroscopeController gyroscopeController, List<FilterFactory.FilterTypes> filterTypes) {
        this.sensorManager = sensorManager;
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.fileController = fileController;
        this.gyroscopeController = gyroscopeController;
        this.filterTypes = filterTypes;
    }

    public void registerAccelerometerListener(BiConsumer<Vector3, Long> consumer) {
        final Vector3FilterChainer filterChain = new Vector3FilterChainer.Builder(new FilterFactory()).withFilterTypes(filterTypes).build();
        if (shouldLogToFile) fileController.open("accX,accY,accZ,rawX,rawY,rawZ", "acceleration");

        accelerometerListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                final Vector3 rawAcceleration = new Vector3(event.values, event.timestamp);

                if (gravityVector == null) {
                    if (useGyroscope) gyroscopeController.registerGyroscopeListener(rawAcceleration);
                    else gravityVector = rawAcceleration;
                    previousTimestamp = event.timestamp; //if first reading, make deltaT 0
                }

                if (useGyroscope) gravityVector = gyroscopeController.getRotatedPoint();
                final Vector3 rawMinusGravity = rawAcceleration.subtract(gravityVector);
                final Vector3 filteredAcceleration = filterChain.filter(rawMinusGravity);

                consumer.accept(filteredAcceleration, event.timestamp - previousTimestamp);
                previousTimestamp = event.timestamp;

                if (shouldLogToFile) fileController.write(filteredAcceleration.toCSV()  + ',' + rawMinusGravity.toCSV());
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                System.out.println(sensor.getName());
            }
        };

        sensorManager.registerListener(accelerometerListener, accelerometer, SensorManager.SENSOR_DELAY_FASTEST);
    }

    public void unregisterAccelerometerListener() {
        gravityVector = null;
        sensorManager.unregisterListener(accelerometerListener, accelerometer);
        if (useGyroscope) gyroscopeController.unregisterGyroscopeListener();
        if (shouldLogToFile) fileController.close();
    }

    public List<FilterFactory.FilterTypes> getFilterTypes() {
        return filterTypes;
    }

    public boolean isShouldLogToFile() {
        return shouldLogToFile;
    }

    public void setShouldLogToFile(boolean shouldLogToFile) {
        this.shouldLogToFile = shouldLogToFile;
    }

    public boolean isUseGyroscope() {
        return useGyroscope;
    }

    public void setUseGyroscope(boolean useGyroscope) {
        this.useGyroscope = useGyroscope;
    }
}
