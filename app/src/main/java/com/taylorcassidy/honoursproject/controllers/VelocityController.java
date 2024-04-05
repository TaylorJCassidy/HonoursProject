package com.taylorcassidy.honoursproject.controllers;

import com.taylorcassidy.honoursproject.filter.Vector3FilterChainer;
import com.taylorcassidy.honoursproject.models.Vector3;

import java.util.function.BiConsumer;

public class VelocityController {
    private final AccelerometerController accelerometerController;
    private final FileController fileController;
    private final Vector3FilterChainer filterChainer;
    private Vector3 velocity = new Vector3();
    private boolean shouldLogToFile = false;

    public VelocityController(AccelerometerController accelerometerController, FileController fileController, Vector3FilterChainer filterChainer) {
        this.accelerometerController = accelerometerController;
        this.fileController = fileController;
        this.filterChainer = filterChainer;
    }

    public void registerVelocityListener(BiConsumer<Vector3, Long> consumer) {
        if (shouldLogToFile) fileController.open("velX,velY,velZ", "velocity");

        accelerometerController.registerAccelerometerListener((acceleration, deltaT) -> {
            if (deltaT != 0L) {
                final float t = deltaT / 1e9f; //convert from nanoseconds to seconds
                velocity = filterChainer.filter(velocity.add(acceleration.multiply(t)));
            }

            consumer.accept(velocity, deltaT);
            if (shouldLogToFile) fileController.write(velocity.toCSV());
        });
    }

    public void unregisterVelocityListener() {
        accelerometerController.unregisterAccelerometerListener();
        if (shouldLogToFile) fileController.close();
        velocity = new Vector3();
    }

    public Vector3FilterChainer getFilterChainer() {
        return filterChainer;
    }

    public boolean shouldLogToFile() {
        return shouldLogToFile;
    }

    public void setShouldLogToFile(boolean shouldLogToFile) {
        this.shouldLogToFile = shouldLogToFile;
    }
}
