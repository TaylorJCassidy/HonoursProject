package com.taylorcassidy.honoursproject.controllers;

import com.taylorcassidy.honoursproject.filter.FilterFactory;
import com.taylorcassidy.honoursproject.filter.Vector3FilterChainer;
import com.taylorcassidy.honoursproject.models.Vector3;

import java.util.List;
import java.util.function.BiConsumer;

public class VelocityController {
    private final AccelerometerController accelerometerController;
    private List<FilterFactory.FilterTypes> filterTypes;
    private Vector3 velocity = new Vector3();

    public VelocityController(AccelerometerController accelerometerController, List<FilterFactory.FilterTypes> filterTypes) {
        this.accelerometerController = accelerometerController;
        this.filterTypes = filterTypes;
    }

    public void registerVelocityListener(BiConsumer<Vector3, Long> consumer) {
        final Vector3FilterChainer filterChain = new Vector3FilterChainer.Builder().withFilterTypes(filterTypes).build();
        accelerometerController.registerAccelerometerListener((acceleration, deltaT) -> {
            if (deltaT != 0L) {
                final float t = deltaT / 1e9f; //convert from nanoseconds to seconds
                velocity = filterChain.filter(velocity.add(acceleration.multiply(t)));
            }

            consumer.accept(velocity, deltaT);
        });
    }

    public void unregisterVelocityListener() {
        accelerometerController.unregisterAccelerometerListener();
        velocity = new Vector3();
    }

    public List<FilterFactory.FilterTypes> getFilterTypes() {
        return filterTypes;
    }

    public void setFilterTypes(List<FilterFactory.FilterTypes> filterTypes) {
        this.filterTypes = filterTypes;
    }
}
