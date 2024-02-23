package com.taylorcassidy.honoursproject.controllers;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.taylorcassidy.honoursproject.filter.FilterFactory;
import com.taylorcassidy.honoursproject.filter.FilterHelper;
import com.taylorcassidy.honoursproject.models.Vector3;

import java.util.function.Consumer;

public class AccelerometerController {

    private static final String HEADER_LINE = "filteredX,filteredY,filteredZ";

    private final SensorManager sensorManager;
    private final Sensor accelerometer;
    private final FileController fileController;
    private FilterFactory.FilterTypes filterType;
    private SensorEventListener accelerometerListener;
    private Vector3 initialAcceleration;

    public AccelerometerController(SensorManager sensorManager, Context context, FilterFactory.FilterTypes filterType) {
        this.sensorManager = sensorManager;
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        fileController = new FileController(context);
        this.filterType = filterType;
    }

    public void registerAccelerometerListener(Consumer<Vector3> consumer) {
        fileController.open(HEADER_LINE, "acceleration");
        accelerometerListener = new SensorEventListener() {
            final FilterHelper filter = new FilterHelper(filterType);
            @Override
            public void onSensorChanged(SensorEvent event) {
                final Vector3 rawAcceleration = new Vector3(event.values, event.timestamp);
                if (initialAcceleration == null) initialAcceleration = rawAcceleration;
                final Vector3 filteredAcceleration = filter.filter(rawAcceleration.subtract(initialAcceleration));
                consumer.accept(filteredAcceleration);
                fileController.write(filteredAcceleration.toCSV());
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                System.out.println(sensor.getName());
            }
        };

        sensorManager.registerListener(accelerometerListener, accelerometer, SensorManager.SENSOR_DELAY_FASTEST);
    }

    public void unregisterAccelerometerListener() {
        fileController.close();
        initialAcceleration = null;
        sensorManager.unregisterListener(accelerometerListener, accelerometer);
    }

    public void setFilterType(FilterFactory.FilterTypes filterType) {
        this.filterType = filterType;
    }
}
