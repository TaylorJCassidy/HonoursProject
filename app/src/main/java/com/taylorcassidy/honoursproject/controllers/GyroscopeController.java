package com.taylorcassidy.honoursproject.controllers;

import static android.util.Half.EPSILON;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.taylorcassidy.honoursproject.filter.FilterFactory;
import com.taylorcassidy.honoursproject.filter.Vector3FilterChainer;
import com.taylorcassidy.honoursproject.models.Matrix;
import com.taylorcassidy.honoursproject.models.Vector3;

import java.util.List;

public class GyroscopeController {
    private final SensorManager sensorManager;
    private final Sensor gyroscope;

    private Vector3 point;

    private SensorEventListener gyroscopeListener;
    private List<FilterFactory.FilterTypes> filterTypes;
    private long previousTimestamp;

    public GyroscopeController(SensorManager sensorManager, List<FilterFactory.FilterTypes> filterTypes) {
        this.sensorManager = sensorManager;
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        this.filterTypes = filterTypes;
    }

    public void registerGyroscopeListener(Vector3 initialPoint) {
        point = initialPoint;
        final Vector3FilterChainer filterChainer = new Vector3FilterChainer.Builder(new FilterFactory()).withFilterTypes(filterTypes).build();
        gyroscopeListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                final Vector3 rawRotation = new Vector3(event.values, event.timestamp);
                Vector3 filteredRotation = filterChainer.filter(rawRotation);
                float[] deltaRotationVector = new float[4];

                //https://developer.android.com/reference/android/hardware/SensorEvent#sensor.type_gyroscope
                if (previousTimestamp != 0L) {
                    final float deltaT = (event.timestamp - previousTimestamp) / 1e9f;
                    final float omegaMagnitude = filteredRotation.magnitude();

                    if (omegaMagnitude > EPSILON) {
                        filteredRotation = filteredRotation.divide(omegaMagnitude);
                    }

                    float thetaOverTwo = omegaMagnitude * deltaT / 2.0f;
                    float sinThetaOverTwo = (float) sin(thetaOverTwo);
                    float cosThetaOverTwo = (float) cos(thetaOverTwo);
                    deltaRotationVector[0] = sinThetaOverTwo * filteredRotation.getX();
                    deltaRotationVector[1] = sinThetaOverTwo * filteredRotation.getY();
                    deltaRotationVector[2] = sinThetaOverTwo * filteredRotation.getZ();
                    deltaRotationVector[3] = cosThetaOverTwo;
                }

                previousTimestamp = event.timestamp;
                float[] deltaRotationMatrix = new float[9];
                SensorManager.getRotationMatrixFromVector(deltaRotationMatrix, deltaRotationVector);

                final Matrix rotationMatrix = convertMatrix(deltaRotationMatrix);
                point = point.multiply(rotationMatrix);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        sensorManager.registerListener(gyroscopeListener, gyroscope, SensorManager.SENSOR_DELAY_FASTEST);
    }

    public void unregisterGyroscopeListener() {
        previousTimestamp = 0L;
        sensorManager.unregisterListener(gyroscopeListener, gyroscope);
    }

    public Vector3 getRotatedPoint() {
        return point;
    }

    private Matrix convertMatrix(float[] matrix) {
        float[][] data = new float[][]{
                {matrix[0], matrix[1], matrix[2]},
                {matrix[3], matrix[4], matrix[5]},
                {matrix[6], matrix[7], matrix[8]}
        };
        return new Matrix(data);
    }
}
