package com.taylorcassidy.honoursproject.controllers;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.taylorcassidy.honoursproject.filter.FilterHelper;
import com.taylorcassidy.honoursproject.filter.factories.IFilterFactory;
import com.taylorcassidy.honoursproject.models.Acceleration;

public class AccelerometerController {

    private final SensorManager sensorManager;
    private final Sensor accelerometer;
    private final FileController fileController;
    private final IFilterFactory filterFactory;
    private SensorEventListener accelerometerListener;

    public AccelerometerController(SensorManager sensorManager, Context context, IFilterFactory filterFactory) {
        this.sensorManager = sensorManager;
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        fileController = new FileController(context);
        this.filterFactory = filterFactory;
    }

    public void registerAccelerometerListener(OnData consumer) {
        fileController.open();
        accelerometerListener = new SensorEventListener() {
            final FilterHelper filter = new FilterHelper(filterFactory);
            @Override
            public void onSensorChanged(SensorEvent event) {
                Acceleration acceleration = new Acceleration(filter.filter(event.values));
                fileController.write(acceleration.toString());
                consumer.consume(acceleration);
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
        sensorManager.unregisterListener(accelerometerListener, accelerometer);
    }

    public interface OnData {
        void consume(Acceleration acceleration);
    }
}
