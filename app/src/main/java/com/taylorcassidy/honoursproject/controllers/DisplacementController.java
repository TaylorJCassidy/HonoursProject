package com.taylorcassidy.honoursproject.controllers;

import com.taylorcassidy.honoursproject.models.Vector3;

import java.util.function.Consumer;

public class DisplacementController {

    private final AccelerometerController accelerometerController;
    private Vector3 currentVelocity = new Vector3();
    private Vector3 currentDisplacement = new Vector3();
    private long previousReadingTimestamp;

    public DisplacementController(AccelerometerController accelerometerController) {
        this.accelerometerController = accelerometerController;
    }

    public void registerDisplacementListener(Consumer<Vector3> consumer) {
        accelerometerController.registerAccelerometerListener(acceleration -> {
            if (previousReadingTimestamp != 0L) {
                final float deltaT = (acceleration.getTimestamp() - previousReadingTimestamp) / 1e9f; //convert from nanoseconds to seconds
                final Vector3 velocity = currentVelocity.add(acceleration.multiply(deltaT)); //v = u + (a * t)
                final Vector3 displacement = velocity.multiply(deltaT); //d = v * t

                currentDisplacement = currentDisplacement.add(displacement);
                currentVelocity = velocity;
            }

            previousReadingTimestamp = acceleration.getTimestamp();
            consumer.accept(currentDisplacement);
        });
    }

    public void unregisterDisplacementController() {
        accelerometerController.unregisterAccelerometerListener();
        previousReadingTimestamp = 0L;
        currentDisplacement = new Vector3();
        currentVelocity = new Vector3();
    }
}
