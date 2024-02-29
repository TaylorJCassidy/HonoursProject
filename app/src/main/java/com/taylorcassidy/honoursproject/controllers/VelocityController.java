package com.taylorcassidy.honoursproject.controllers;

import com.taylorcassidy.honoursproject.models.Vector3;

import java.util.function.BiConsumer;

public class VelocityController {
    private final AccelerometerController accelerometerController;
    private Vector3 velocity = new Vector3();

    public VelocityController(AccelerometerController accelerometerController) {
        this.accelerometerController = accelerometerController;
    }

    public void registerVelocityListener(BiConsumer<Vector3, Long> consumer) {
        accelerometerController.registerAccelerometerListener((acceleration, deltaT) -> {
            if (deltaT != 0L) {
                final float t = deltaT / 1e9f; //convert from nanoseconds to seconds
                velocity = velocity.add(acceleration.multiply(t));
            }

            consumer.accept(velocity, deltaT);
        });
    }

    public void unregisterVelocityListener() {
        accelerometerController.unregisterAccelerometerListener();
        velocity = new Vector3();
    }
}
