package com.taylorcassidy.honoursproject.controllers;

import com.taylorcassidy.honoursproject.filter.FilterFactory;
import com.taylorcassidy.honoursproject.filter.Vector3FilterChainer;
import com.taylorcassidy.honoursproject.models.Vector3;

import java.util.List;
import java.util.function.BiConsumer;

public class VelocityController {
    private final AccelerometerController accelerometerController;
    private final FileController fileController;
    private List<FilterFactory.FilterTypes> filterTypes;
    private Vector3 velocity = new Vector3();
    private boolean shouldLogToFile = false;

    public VelocityController(AccelerometerController accelerometerController, FileController fileController, List<FilterFactory.FilterTypes> filterTypes) {
        this.accelerometerController = accelerometerController;
        this.fileController = fileController;
        this.filterTypes = filterTypes;
    }

    public void registerVelocityListener(BiConsumer<Vector3, Long> consumer) {
        final Vector3FilterChainer filterChain = new Vector3FilterChainer.Builder(new FilterFactory()).withFilterTypes(filterTypes).build();
        if (shouldLogToFile) fileController.open("velX,velY,velZ", "velocity");

        accelerometerController.registerAccelerometerListener((acceleration, deltaT) -> {
            if (deltaT != 0L) {
                final float t = deltaT / 1e9f; //convert from nanoseconds to seconds
                velocity = filterChain.filter(velocity.add(acceleration.multiply(t)));
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

    public List<FilterFactory.FilterTypes> getFilterTypes() {
        return filterTypes;
    }

    public void setFilterTypes(List<FilterFactory.FilterTypes> filterTypes) {
        this.filterTypes = filterTypes;
    }

    public boolean isShouldLogToFile() {
        return shouldLogToFile;
    }

    public void setShouldLogToFile(boolean shouldLogToFile) {
        this.shouldLogToFile = shouldLogToFile;
    }
}
