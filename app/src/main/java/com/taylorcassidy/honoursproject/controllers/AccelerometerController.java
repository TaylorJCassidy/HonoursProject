package com.taylorcassidy.honoursproject.controllers;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.taylorcassidy.honoursproject.filter.FilterHelper;
import com.taylorcassidy.honoursproject.filter.factories.IFilterFactory;
import com.taylorcassidy.honoursproject.models.Vector3;

import java.util.function.Consumer;

public class AccelerometerController {

    private static final String HEADER_LINE = "rawX,rawY,rawZ,filteredX,filteredY,filteredZ";

    private final SensorManager sensorManager;
    private final Sensor accelerometer;
    private final FileController fileController;
    private final IFilterFactory filterFactory;
    private SensorEventListener accelerometerListener;
    private Vector3 initialAcceleration;

    public AccelerometerController(SensorManager sensorManager, Context context, IFilterFactory filterFactory) {
        this.sensorManager = sensorManager;
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        fileController = new FileController(context);
        this.filterFactory = filterFactory;
    }

    public void registerAccelerometerListener(Consumer<Vector3> consumer) {
        fileController.open(HEADER_LINE);
        accelerometerListener = new SensorEventListener() {
            final FilterHelper filter = new FilterHelper(filterFactory);
            @Override
            public void onSensorChanged(SensorEvent event) {
                final Vector3 rawAcceleration = new Vector3(event.values, event.timestamp);
                if (initialAcceleration == null) initialAcceleration = rawAcceleration;
                final Vector3 filteredAcceleration = filter.filter(rawAcceleration.subtract(initialAcceleration));
                consumer.accept(filteredAcceleration);
                fileController.write(rawAcceleration.toCSV() + "," + filteredAcceleration.toCSV());
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
}
