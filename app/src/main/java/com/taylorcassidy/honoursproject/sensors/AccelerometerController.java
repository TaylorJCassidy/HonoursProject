package com.taylorcassidy.honoursproject.sensors;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.taylorcassidy.honoursproject.filter.FilterHelper;
import com.taylorcassidy.honoursproject.filter.factories.FIRFactory;

public class AccelerometerController {

    private final SensorManager sensorManager;
    private final Sensor accelerometer;
    private SensorEventListener accelerometerListener;

    public AccelerometerController(SensorManager sensorManager) {
        this.sensorManager = sensorManager;
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    public void registerAccelerometerListener(OnData consumer) {
        accelerometerListener = new SensorEventListener() {
            final FilterHelper filter = new FilterHelper(new FIRFactory());
            @Override
            public void onSensorChanged(SensorEvent event) {
                consumer.consume(filter.filter(event.values));
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                System.out.println(sensor.getName());
            }
        };

        sensorManager.registerListener(accelerometerListener, accelerometer, SensorManager.SENSOR_DELAY_FASTEST);
    }

    public void unregisterAccelerometerListener() {
        sensorManager.unregisterListener(accelerometerListener, accelerometer);
    }

    public interface OnData {
        void consume(float[] data);
    }
}
